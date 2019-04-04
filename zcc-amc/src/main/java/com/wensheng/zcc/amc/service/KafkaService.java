package com.wensheng.zcc.amc.service;

import com.wensheng.zcc.amc.module.vo.base.BaseActionVo;
import com.wensheng.zcc.common.mq.kafka.module.AmcUserOperation;
import com.wensheng.zcc.common.mq.kafka.module.WechatUserLocation;

/**
 * @author chenwei on 4/1/19
 * @project miniapp-backend
 */
public interface KafkaService {


  void send(AmcUserOperation amcUserOperation);
}
