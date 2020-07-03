package com.wensheng.zcc.amc.service.impl;

import com.google.gson.Gson;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebt;
import com.wensheng.zcc.amc.module.dao.mysql.auto.ext.AmcDebtExt;
import com.wensheng.zcc.amc.module.vo.AmcAssetVo;
import com.wensheng.zcc.amc.module.vo.AmcDebtVo;
import com.wensheng.zcc.amc.service.AmcContactorService;
import com.wensheng.zcc.amc.service.AmcDebtService;
import com.wensheng.zcc.amc.service.KafkaService;
import com.wensheng.zcc.amc.service.MailService;
import com.wensheng.zcc.common.mq.kafka.KafkaParams;
import com.wensheng.zcc.common.mq.kafka.module.AmcUserOperation;
import com.wensheng.zcc.common.mq.kafka.module.SSOUserDto;
import com.wensheng.zcc.common.mq.kafka.module.SSOUserModDto;
import com.wensheng.zcc.common.mq.kafka.module.WechatUserLocation;
import com.wensheng.zcc.common.params.sso.AmcDeptEnum;
import com.wensheng.zcc.common.params.sso.AmcLocationEnum;
import com.wensheng.zcc.common.params.sso.AmcUserValidEnum;
import com.wensheng.zcc.common.params.sso.SSOAmcUser;
import com.wensheng.zcc.common.utils.AmcBeanUtils;
import java.util.List;
import java.util.stream.StreamSupport;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * @author chenwei on 4/1/19
 * @project miniapp-backend
 */
@Service
@Slf4j
public class KafkaServiceImpl implements KafkaService {
  @Autowired
  KafkaTemplate kafkaTemplate;

  @Autowired
  AmcContactorService amcContactorService;

  @Autowired
  AmcDebtService amcDebtService;

  @Value("${env.name}")
  String env;

  private Gson gson = null;

  private String MQ_TOPIC_AMC_USEROPER = null;

  @Value("${spring.kafka.topic-amc-debt-create}")
  private String mqTopicDebtCreate;

  @Value("${spring.kafka.topic-amc-asset-create}")
  private String mqTopicAssetCreate;

  @Autowired
  MailService mailService;

  @PostConstruct
  void init(){
    MQ_TOPIC_AMC_USEROPER = String.format("%s_%s",KafkaParams.MQ_TOPIC_AMC_USEROPER, env);
    gson = new Gson();
  }


  @Override
  public void send(AmcUserOperation amcUserOperation) {
    kafkaTemplate.send(MQ_TOPIC_AMC_USEROPER, amcUserOperation);
  }

  @Override
  public void sendDebtCreate(AmcDebtVo amcDebtVo) {
    kafkaTemplate.send(mqTopicDebtCreate, amcDebtVo);
  }

  @Override
  public void sendAssetCreate(AmcAssetVo amcAssetVo) {
    kafkaTemplate.send(mqTopicAssetCreate, amcAssetVo);
  }

  private static String typeIdHeader(Headers headers) {
    return StreamSupport.stream(headers.spliterator(), false)
        .filter(header -> header.key().equals("__TypeId__"))
        .findFirst().map(header -> new String(header.value())).orElse("N/A");
  }


  @KafkaListener( topics = "${spring.kafka.topic-sso-userchanged}", clientIdPrefix = "zcc-sso",
      containerFactory = "kafkaListenerStringContainerFactory")
  public void listenUserOperation(ConsumerRecord<String, SSOUserModDto> cr,
      @Payload SSOUserModDto userModDto) {
    log.info("Logger 1 [JSON] received key {}: Type [{}] | Payload: {} | Record: {}", cr.key(),
        typeIdHeader(cr.headers()), userModDto, cr.toString());
    String gsonStr = null;
    try{

      if(!userModDto.getAmcUserDtoHis().getLocation().equals(userModDto.getAmcUserDtoCurr().getLocation())||
          !(userModDto.getAmcUserDtoHis().getDeptId().equals(userModDto.getAmcUserDtoCurr().getDeptId()))||
          !userModDto.getAmcUserDtoCurr().getValid().equals(AmcUserValidEnum.VALID.getId())){

        List<AmcDebtExt> amcDebtExts = amcDebtService.findDebtsAssetsOfUser(userModDto.getAmcUserDtoCurr().getId());
        if(!CollectionUtils.isEmpty(amcDebtExts)){
          StringBuilder sb = new StringBuilder();
          if(userModDto.getAmcUserDtoHis().getDeptId() != -1){
            sb.append(AmcDeptEnum.lookupByDisplayIdUtil(userModDto.getAmcUserDtoHis().getDeptId().intValue()).getCname());
          }
          if(userModDto.getAmcUserDtoHis().getLocation() != -1){
            sb.append(
                AmcLocationEnum.lookupByDisplayIdUtil(userModDto.getAmcUserDtoHis().getLocation().intValue()).getCname());
          }
          sb.append("业务人员:").append(userModDto.getAmcUserDtoHis().getUserCname());
              if(!userModDto.getAmcUserDtoCurr().getValid().equals(AmcUserValidEnum.VALID.getId())){
                sb.append("已经离职:");
              }else{
                sb.append("已经发生职位变更,");
                if(userModDto.getAmcUserDtoCurr().getDeptId() != -1 && !(userModDto.getAmcUserDtoHis().getDeptId().equals(userModDto.getAmcUserDtoCurr().getDeptId()))){
                  sb.append("部门变为:").append(AmcDeptEnum.lookupByDisplayIdUtil(userModDto.getAmcUserDtoCurr().getDeptId().intValue()).getCname());
                }
                if(userModDto.getAmcUserDtoCurr().getLocation() != -1 && !userModDto.getAmcUserDtoHis().getLocation().equals(userModDto.getAmcUserDtoCurr().getLocation())){
                  sb.append("地区变为:").append(
                      AmcLocationEnum.lookupByDisplayIdUtil(userModDto.getAmcUserDtoCurr().getLocation().intValue()).getCname());
                }
              }


          sb.append("，请把下列他/她负责的资产债权移交给合适的业务人员.\n");

          for(AmcDebtExt amcDebtExt: amcDebtExts){

            sb.append("\t债权 id:[").append(amcDebtExt.getDebtInfo().getId()).append("]")
                .append(amcDebtExt.getDebtInfo().getTitle()).append("\n");

          }
          mailService.sendMail(sb.toString(), null,null);
        }


      }



//      amcContactorService.syncContactorWithNewUser(amcUser);
//      wszccTemplate.save(amcUser);

    }catch (Exception ex){
      log.error("Failed to handle:{}", gsonStr, ex);
    }

  }
}
