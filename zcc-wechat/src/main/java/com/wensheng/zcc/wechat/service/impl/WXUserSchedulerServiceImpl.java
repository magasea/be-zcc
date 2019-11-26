package com.wensheng.zcc.wechat.service.impl;

import com.wensheng.zcc.wechat.service.WXUserSchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class WXUserSchedulerServiceImpl implements WXUserSchedulerService {

  @Autowired
  ComnfuncGrpcService comnfuncGrpcService;

  @Autowired
  MongoTemplate mongoTemplate;

  @Override
  public void tagUserWithProvince() {
    Query query = new Query();
//    query.addCriteria(Criteria.where("createTime"))

//    comnfuncGrpcService.getProvinceByGeopoint()
  }
}
