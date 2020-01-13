package com.wensheng.zcc.amc.service.impl;

import com.google.gson.Gson;
import com.wensheng.zcc.amc.module.vo.AmcAssetVo;
import com.wensheng.zcc.amc.module.vo.AmcDebtVo;
import com.wensheng.zcc.amc.service.AmcContactorService;
import com.wensheng.zcc.amc.service.KafkaService;
import com.wensheng.zcc.common.mq.kafka.KafkaParams;
import com.wensheng.zcc.common.mq.kafka.module.AmcUserOperation;
import com.wensheng.zcc.common.mq.kafka.module.WechatUserLocation;
import com.wensheng.zcc.common.params.sso.SSOAmcUser;
import com.wensheng.zcc.common.utils.AmcBeanUtils;
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

  @Value("${env.name}")
  String env;

  private Gson gson = null;

  private String MQ_TOPIC_AMC_USEROPER = null;

  @Value("${spring.kafka.topic-amc-debt-create}")
  private String mqTopicDebtCreate;

  @Value("${spring.kafka.topic-amc-asset-create}")
  private String mqTopicAssetCreate;

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
  public void listenUserOperation(ConsumerRecord<String, WechatUserLocation> cr,
      @Payload SSOAmcUser payload) {
    log.info("Logger 1 [JSON] received key {}: Type [{}] | Payload: {} | Record: {}", cr.key(),
        typeIdHeader(cr.headers()), payload, cr.toString());
    String gsonStr = null;
    try{
      gsonStr = gson.toJson(payload);
      SSOAmcUser amcUser = new SSOAmcUser();
      AmcBeanUtils.copyProperties(payload, amcUser);


      amcContactorService.syncContactorWithNewUser(amcUser);
//      wszccTemplate.save(amcUser);

    }catch (Exception ex){
      log.error("Failed to handle:{}", gsonStr, ex);
    }

  }
}
