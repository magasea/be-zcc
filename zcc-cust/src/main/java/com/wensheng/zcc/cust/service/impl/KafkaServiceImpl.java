package com.wensheng.zcc.cust.service.impl;

import com.google.gson.Gson;
import com.wensheng.zcc.common.mq.kafka.module.AmcUserOperation;
import com.wensheng.zcc.common.mq.kafka.module.WechatUserLocation;
import com.wensheng.zcc.common.utils.AmcBeanUtils;
import com.wensheng.zcc.cust.dao.mysql.mapper.CustWechatInfoMapper;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustWechatInfo;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustWechatInfoExample;
import java.util.List;
import java.util.stream.StreamSupport;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * @author chenwei on 4/17/19
 * @project miniapp-backend
 */
@Service
@Slf4j
public class KafkaServiceImpl {

  @Autowired
  CustWechatInfoMapper custWechatInfoMapper;

  private Gson gson = new Gson();

  private static String typeIdHeader(Headers headers) {
    return StreamSupport.stream(headers.spliterator(), false)
        .filter(header -> header.key().equals("__TypeId__"))
        .findFirst().map(header -> new String(header.value())).orElse("N/A");
  }

  @KafkaListener( topics = "${kafka.topic_wechatuser}", clientIdPrefix = "zcc-cust",
      containerFactory = "baWechatUserFactory")
  public void listenUserOperation(ConsumerRecord<String, WechatUserLocation> cr,
      @Payload AmcUserOperation payload) {
    log.info("Logger 1 [JSON] received key {}: Type [{}] | Payload: {} | Record: {}", cr.key(),
        typeIdHeader(cr.headers()), payload, cr.toString());
    String gsonStr = null;
    try{
      gsonStr = gson.toJson(payload);
      CustWechatInfo custWechatInfo = new CustWechatInfo();
      AmcBeanUtils.copyProperties(payload, custWechatInfo);
      CustWechatInfoExample custWechatInfoExample = new CustWechatInfoExample();
      custWechatInfoExample.createCriteria().andWechatOpenidEqualTo(custWechatInfo.getWechatOpenid());
      List<CustWechatInfo> custWechatInfoList = custWechatInfoMapper.selectByExample(custWechatInfoExample);
      if(CollectionUtils.isEmpty(custWechatInfoList)){
        custWechatInfoMapper.insertSelective(custWechatInfo);
      }else{

        custWechatInfoMapper.updateByExampleSelective(custWechatInfoList.get(0), custWechatInfoExample);
      }


    }catch (Exception ex){
      log.error("Failed to handle:{}", gsonStr, ex);
    }

  }

}
