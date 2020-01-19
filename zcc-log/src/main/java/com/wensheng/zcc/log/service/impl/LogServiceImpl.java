package com.wensheng.zcc.log.service.impl;

import com.wensheng.zcc.log.module.dao.mongo.AmcUserLogin;
import com.wensheng.zcc.log.service.LogService;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LogServiceImpl implements LogService {

  @Autowired
  MongoTemplate wszccTemplate;

  @Override
  public AmcUserLogin getUserLoginRecord(Long userSsoId) {

    Query query = new Query();

    query.addCriteria(Criteria.where("amcUser.ssoUserId").is(userSsoId));
    List<AmcUserLogin> amcUserLoginList = wszccTemplate.find(query, AmcUserLogin.class);
    Collections.sort(amcUserLoginList, new Comparator<AmcUserLogin>() {
      @Override
      public int compare(AmcUserLogin o1, AmcUserLogin o2) {
        if( o1.getCurrLoginTime().toEpochSecond(ZoneOffset.UTC) - o2.getCurrLoginTime().toEpochSecond(ZoneOffset.UTC) > 0){
          return 1;
        }
        if(o1.getCurrLoginTime().toEpochSecond(ZoneOffset.UTC) - o2.getCurrLoginTime().toEpochSecond(ZoneOffset.UTC)
            == 0){
          return 0;
        }else{
          return -1;
        }
      }
    });
    if(amcUserLoginList.size() > 1){
      for(int idx = 1; idx < amcUserLoginList.size(); idx++){
        wszccTemplate.remove(amcUserLoginList.get(idx));
      }
    }
    return amcUserLoginList.get(0);
  }
}
