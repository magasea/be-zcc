package com.wensheng.zcc.log.service.impl;

import com.google.gson.Gson;
import com.wensheng.zcc.common.mq.kafka.module.WechatUserLocation;
import com.wensheng.zcc.common.utils.AmcBeanUtils;
import com.wensheng.zcc.log.module.dao.mongo.WechatUserLocationLog;
import com.wensheng.zcc.log.service.KafkaService;
import java.util.Arrays;
import java.util.stream.StreamSupport;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

/**
 * @author chenwei on 4/1/19
 * @project miniapp-backend
 */
@Service
@Slf4j
public class KafkaServiceImpl implements KafkaService {


  private final  String TOPIC_WECHAT_USERLOCATION = null;

  @Autowired
  MongoTemplate wszccTemplate;

  private Gson gson = new Gson();

//  @Value("${env.env-prefix}")
//  private String envStr;
//
//  @PostConstruct
//  void init(){
//    TOPIC_WECHAT_USERLOCATION = String.format("%s-%s", MQ_TOPIC_WECHAT_USERLOCATION, envStr );
//  }

  @Override
  public void receiveWechatUserLocationMsg(WechatUserLocation wechatUserLocation) {

  }

  @KafkaListener( topics = "${kafka.topic-wechat-userlocation}", clientIdPrefix = "zcc-log",
      containerFactory = "baUserLocationContainerFactory")
  public void listenAsObject(ConsumerRecord<String, WechatUserLocation> cr,
      @Payload WechatUserLocation payload) {
    log.info("Logger 1 [JSON] received key {}: Type [{}] | Payload: {} | Record: {}", cr.key(),
        typeIdHeader(cr.headers()), payload, cr.toString());
    String gsonStr = null;
    try{
       gsonStr = gson.toJson(payload);
      WechatUserLocationLog wechatUserLocationLog = new WechatUserLocationLog();
      AmcBeanUtils.copyProperties(payload, wechatUserLocationLog);
      wechatUserLocationLog.setLocation(new Double[]{payload.getLongitude().doubleValue(), payload.getLatitude().doubleValue()});
      wszccTemplate.save(wechatUserLocationLog);

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