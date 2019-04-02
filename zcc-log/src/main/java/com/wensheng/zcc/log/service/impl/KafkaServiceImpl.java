package com.wensheng.zcc.log.service.impl;

import com.wensheng.zcc.common.mq.kafka.module.WechatUserLocation;
import com.wensheng.zcc.log.service.KafkaService;
import java.util.stream.StreamSupport;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Headers;
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

  @KafkaListener(topics = "${kafka.topic-wechat-userlocation}", clientIdPrefix = "zcc-log",
      containerFactory = "kafkaListenerByteArrayContainerFactory")
  public void listenAsObject(ConsumerRecord<String, WechatUserLocation> cr,
      @Payload WechatUserLocation payload) {
    log.info("Logger 1 [JSON] received key {}: Type [{}] | Payload: {} | Record: {}", cr.key(),
        typeIdHeader(cr.headers()), payload, cr.toString());
  }

  private static String typeIdHeader(Headers headers) {
    return StreamSupport.stream(headers.spliterator(), false)
        .filter(header -> header.key().equals("__TypeId__"))
        .findFirst().map(header -> new String(header.value())).orElse("N/A");
  }
}
