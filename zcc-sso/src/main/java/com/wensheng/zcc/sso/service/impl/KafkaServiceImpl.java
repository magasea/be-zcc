package com.wensheng.zcc.sso.service.impl;

import com.wensheng.zcc.common.mq.kafka.KafkaParams;
import com.wensheng.zcc.common.mq.kafka.module.WechatUserLocation;
import com.wensheng.zcc.sso.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
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

  private String MQ_TOPIC_WECHAT_USERLOCATION = KafkaParams.MQ_TOPIC_WECHAT_USERLOCATION;

  @Override
  public void send(WechatUserLocation wechatUserLocation) {
    kafkaTemplate.send(MQ_TOPIC_WECHAT_USERLOCATION, wechatUserLocation);
  }
}
