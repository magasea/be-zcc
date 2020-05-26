package com.wensheng.zcc.wechat.service.impl;

import com.google.gson.Gson;
import com.wensheng.zcc.common.module.dto.WechatCode2SessionVo;
import com.wensheng.zcc.common.mq.kafka.KafkaParams;
import com.wensheng.zcc.common.mq.kafka.module.WechatUserLocation;

import com.wensheng.zcc.wechat.service.KafkaService;
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


  @KafkaListener( topics = "${kafka.topic_wxuser_zcclogin}", clientIdPrefix = "zcc-wechat",
      containerFactory = "kafkaListenerContainerFactory")
  public void listenUserOperation(ConsumerRecord<String, WechatCode2SessionVo> cr,
      @Payload WechatCode2SessionVo amcUser) {
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



  private static String typeIdHeader(Headers headers) {
    return StreamSupport.stream(headers.spliterator(), false)
        .filter(header -> header.key().equals("__TypeId__"))
        .findFirst().map(header -> new String(header.value())).orElse("N/A");
  }

}
