package com.wensheng.zcc.log.service;

import com.wensheng.zcc.common.mq.kafka.module.WechatUserLocation;

/**
 * @author chenwei on 4/1/19
 * @project miniapp-backend
 */
public interface KafkaService {

  void receiveWechatUserLocationMsg(WechatUserLocation wechatUserLocation);

}
