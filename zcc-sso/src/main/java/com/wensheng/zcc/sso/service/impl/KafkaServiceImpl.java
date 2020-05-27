package com.wensheng.zcc.sso.service.impl;

import com.google.gson.Gson;
import com.wensheng.zcc.common.module.dto.WechatCode2SessionVo;
import com.wensheng.zcc.common.mq.kafka.KafkaParams;
import com.wensheng.zcc.common.mq.kafka.module.AmcUserOperation;
import com.wensheng.zcc.common.mq.kafka.module.WechatUserLocation;
import com.wensheng.zcc.common.utils.AmcBeanUtils;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcUser;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcWechatUser;
import com.wensheng.zcc.sso.service.AmcTokenService;
import com.wensheng.zcc.sso.service.AmcUserService;
import com.wensheng.zcc.sso.service.KafkaService;
import java.util.stream.StreamSupport;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
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
  MongoTemplate wszccTemplate;

  @Value("${kafka.topic_amc_login}")
  String topicAmcLogin;

  @Value("${env.name}")
  String env;

  @Value("${kafka.topic_wxuser_zcclogin}")
  String topicWXuserZccLogin;

  @Autowired
  AmcTokenService amcTokenService;

  @Autowired
  AmcUserService amcUserService;

  private String MQ_TOPIC_WECHAT_USERLOCATION = null;
  private String MQ_TOPIC_WECHAT_USERCREATE = null;


  private Gson gson = new Gson();


  @PostConstruct
  void init(){
    MQ_TOPIC_WECHAT_USERLOCATION = String.format("%s_%s",KafkaParams.MQ_TOPIC_WECHAT_USERLOCATION, env);
    MQ_TOPIC_WECHAT_USERCREATE = String.format("%s_%s",KafkaParams.MQ_TOPIC_WECHAT_USERCREATE, env);
  }


  @Override
  public void send(WechatUserLocation wechatUserLocation) {
    kafkaTemplate.send(MQ_TOPIC_WECHAT_USERLOCATION, wechatUserLocation);
  }

  @Override
  public void send(AmcWechatUser amcWechatUser) {
    kafkaTemplate.send(MQ_TOPIC_WECHAT_USERCREATE, amcWechatUser);
  }

  @Override
  public void send(String topic, AmcUser amcUser) {
    kafkaTemplate.send(topic, amcUser);
  }


  private static String typeIdHeader(Headers headers) {
    return StreamSupport.stream(headers.spliterator(), false)
        .filter(header -> header.key().equals("__TypeId__"))
        .findFirst().map(header -> new String(header.value())).orElse("N/A");
  }


  @KafkaListener( topics = "${kafka.topic-sso-userchanged}", clientIdPrefix = "zcc-sso",
      containerFactory = "kafkaListenerStringContainerFactory")
  public void listenUserOperation(ConsumerRecord<String, AmcUser> cr,
      @Payload AmcUser amcUser) {
    log.info("Logger 1 [JSON] received key {}: Type [{}] | Payload: {} | Record: {}", cr.key(),
        typeIdHeader(cr.headers()), amcUser, cr.toString());
    String gsonStr = null;
    try{
//      gsonStr = gson.toJson(payload);
//      AmcUser amcUser = new AmcUser();
//      AmcBeanUtils.copyProperties(payload, amcUser);
      amcTokenService.revokeTokenByMobilePhone(amcUser.getMobilePhone());
//      wszccTemplate.save(amcUser);
      amcUserService.createUser(amcUser);
    }catch (Exception ex){
      log.error("Failed to handle:{}", gsonStr, ex);
    }

  }

  public String getLoginTopic(){
    return topicAmcLogin;
  }

  @Override
  public void send(WechatCode2SessionVo wechatCode2SessionVo) {
    log.info("send topic:{}", topicAmcLogin);
    kafkaTemplate.send(topicWXuserZccLogin, wechatCode2SessionVo);
  }
}
