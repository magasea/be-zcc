package com.wensheng.zcc.amc.service.impl;

import com.wensheng.zcc.amc.service.KafkaService;
import com.wensheng.zcc.common.mq.kafka.KafkaParams;
import com.wensheng.zcc.common.mq.kafka.module.AmcUserOperation;
import com.wensheng.zcc.common.mq.kafka.module.WechatUserLocation;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * @author chenwei on 4/1/19
 * @project miniapp-backend
 */
@Service
public class KafkaServiceImpl implements KafkaService {
  @Autowired
  KafkaTemplate kafkaTemplate;

  @Value("${env-hint}")
  String env;


  private String MQ_TOPIC_AMC_USEROPER = null;

  @PostConstruct
  void init(){
    MQ_TOPIC_AMC_USEROPER = String.format("%s_%s",KafkaParams.MQ_TOPIC_AMC_USEROPER, env);
  }


  @Override
  public void send(AmcUserOperation amcUserOperation) {
    kafkaTemplate.send(MQ_TOPIC_AMC_USEROPER, amcUserOperation);
  }
}
