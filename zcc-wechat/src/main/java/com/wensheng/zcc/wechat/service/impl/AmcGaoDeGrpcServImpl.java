package com.wensheng.zcc.wechat.service.impl;

import com.google.gson.Gson;
import com.wensheng.zcc.common.module.dto.GaoDeReGeoResult;
import com.wensheng.zcc.common.module.dto.ReGeoCode;
import com.wensheng.zcc.common.module.dto.WXUserGeoRecord;
import com.wensheng.zcc.common.utils.AmcBeanUtils;
import com.wenshengamc.zcc.common.Common.GeoJson;
import com.wenshengamc.zcc.comnfunc.gaodegeo.ComnFuncServiceGrpc.ComnFuncServiceBlockingStub;
import com.wenshengamc.zcc.comnfunc.gaodegeo.WXUserGeoReq;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

@Service
public class AmcGaoDeGrpcServImpl {

  @Autowired
  @Qualifier("comnFuncStub")
  ComnFuncServiceBlockingStub comnFuncService;




  private RestTemplate restTemplate  = new RestTemplate();

  @Autowired
  MongoTemplate mongoTemplate;

  private Gson gson = new Gson();

  @PostConstruct
  void init(){

  }

  public boolean getAddressFromGeoPoint(WXUserGeoRecord wxUserGeoRecord){
    WXUserGeoReq.Builder wxUserGeoReqOrBuilder = WXUserGeoReq.newBuilder();
    AmcBeanUtils.copyProperties(wxUserGeoRecord, wxUserGeoReqOrBuilder);
    GeoJson.Builder geoJsonBuilder = GeoJson.newBuilder();
    AmcBeanUtils.copyProperties(wxUserGeoRecord.getLocation(), geoJsonBuilder);
    geoJsonBuilder.addAllCoordinates( wxUserGeoRecord.getLocation().getCoordinates());
    wxUserGeoReqOrBuilder.setLocation(geoJsonBuilder);
    WXUserGeoReq resp = comnFuncService.getAddress(wxUserGeoReqOrBuilder.build() );


    wxUserGeoRecord.setAddress(resp.getAddress());

    wxUserGeoRecord.setCity(resp.getCity());

    wxUserGeoRecord.setProvince(resp.getProvince());

    return true;
  }

  public void updateGeoAddressOfUser(){
    Query query = new Query();
    query.addCriteria(Criteria.where("address").is(null));
    List<WXUserGeoRecord> wxUserGeoRecordList = mongoTemplate.find(query, WXUserGeoRecord.class);
    for(WXUserGeoRecord wxUserGeoRecord: wxUserGeoRecordList){
      if(getAddressFromGeoPoint(wxUserGeoRecord)){
        wxUserGeoRecord.setUpdateTime(LocalDate.ofInstant(Instant.now(), ZoneOffset.UTC));
        mongoTemplate.save(wxUserGeoRecord);
      }
    }
  }

}
