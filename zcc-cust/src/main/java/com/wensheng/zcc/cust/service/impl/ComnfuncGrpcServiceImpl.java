package com.wensheng.zcc.cust.service.impl;

import com.wenshengamc.zcc.comnfunc.gaodegeo.Address;
import com.wenshengamc.zcc.comnfunc.gaodegeo.ComnFuncServiceGrpc.ComnFuncServiceBlockingStub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.geo.GeoJson;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ComnfuncGrpcServiceImpl  {

  @Autowired
  ComnFuncServiceBlockingStub comnfuncServiceStub;

  public GeoJsonPoint getGeoInfoFromAddress(String address, String city){
    Address.Builder aBuilder = Address.newBuilder();

    if(!StringUtils.isEmpty(address)){
     aBuilder.setAddress(address);
    }
    if(StringUtils.isEmpty(city)){
      aBuilder.setCity(city);
    }
    com.wenshengamc.zcc.common.Common.GeoJson geoJson = comnfuncServiceStub.getGeoByAddress(aBuilder.build());
    GeoJsonPoint geoJsonPoint = new GeoJsonPoint(geoJson.getCoordinates(0), geoJson.getCoordinates(1));
    return geoJsonPoint;
  }

}
