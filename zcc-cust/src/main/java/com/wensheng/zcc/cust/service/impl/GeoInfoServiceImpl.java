package com.wensheng.zcc.cust.service.impl;

import com.wensheng.zcc.cust.dao.mysql.mapper.CustTrdInfoMapper;
import com.wensheng.zcc.cust.module.dao.mongo.CustTrdGeo;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdInfo;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdInfoExample;
import com.wensheng.zcc.cust.service.BasicInfoService;
import com.wensheng.zcc.cust.service.GeoInfoService;
import java.time.Instant;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@Slf4j
public class GeoInfoServiceImpl implements GeoInfoService {

  @Autowired
  CustTrdInfoMapper custTrdInfoMapper;

  @Autowired
  MongoTemplate mongoTemplate;

  @Autowired
  ComnfuncGrpcServiceImpl comnfuncGrpcService;

  @Autowired
  BasicInfoService basicInfoService;

  static final Long period_week_second = 60*60*24*7L;

  @Override
  @Scheduled(cron = "${spring.task.scheduling.cronExprGeo}")
  public void searchGeoInfoForTrd() {
    int offset = 0;
    int pageSize = 20;
    CustTrdInfoExample custTrdInfoExample = new CustTrdInfoExample();
    custTrdInfoExample.setOrderByClause("id asc");
    RowBounds rowBounds = new RowBounds(offset, pageSize);
    boolean stop = false;
    String regionName = null;
    Query query = null;
    Long updateTime = Instant.now().getEpochSecond();
    while(!stop){
      List<CustTrdInfo> custTrdInfoList = custTrdInfoMapper.selectByExampleWithRowbounds(null, rowBounds);
      rowBounds = null;
      if(CollectionUtils.isEmpty(custTrdInfoList)){
        stop = true;
        rowBounds = null;
        continue;
      }
      for(CustTrdInfo custTrdInfo: custTrdInfoList){

        try {
          regionName = basicInfoService.getRegionNameByCode(Long.valueOf(custTrdInfo.getDebtCity()));
        } catch (Exception e) {
          log.error("", e);
          continue;
        }

        query = new Query();
        query.addCriteria(Criteria.where("custTrdInfoId").is(custTrdInfo.getId()));
        List<CustTrdGeo> custTrdGeos = mongoTemplate.find(query, CustTrdGeo.class);
        CustTrdGeo custTrdGeo = null;
        if(CollectionUtils.isEmpty(custTrdGeos)){
          custTrdGeo = new CustTrdGeo();

        }else{
          custTrdGeo = custTrdGeos.get(0);
        }
        GeoJsonPoint geoJsonPoint = null;
        if(custTrdGeo.getUpdateTime() == null || updateTime - custTrdGeo.getUpdateTime() > period_week_second){
          try {
            geoJsonPoint = comnfuncGrpcService.getGeoInfoFromAddress(regionName, regionName);
          } catch (Exception e) {
            e.printStackTrace();
            continue;
          }
          custTrdGeo.setLocation(geoJsonPoint);
          custTrdGeo.setCustTrdInfoId(custTrdInfo.getId());
          custTrdGeo.setBuyerId(custTrdInfo.getBuyerId());
          custTrdGeo.setBuyerType(custTrdInfo.getBuyerType());
          custTrdGeo.setAmount(custTrdInfo.getTotalDebtAmount());
          custTrdGeo.setTitle(custTrdInfo.getInfoTitle());
          custTrdGeo.setDebtCity(custTrdInfo.getDebtCity());
          custTrdGeo.setUrl(custTrdInfo.getInfoUrl());
          custTrdGeo.setUpdateTime(updateTime);
        }else{
          continue;
        }




        mongoTemplate.save(custTrdGeo);

      }

      offset = pageSize + offset;
      rowBounds = new RowBounds(offset, pageSize);


    }

  }


}
