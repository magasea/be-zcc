package com.wensheng.zcc.wechat.service.impl;

import com.wensheng.zcc.common.module.LatLng;
import com.wensheng.zcc.common.module.dto.AmcRegionInfo;
import com.wensheng.zcc.common.module.dto.WXUserFavor;
import com.wensheng.zcc.common.utils.AmcBeanUtils;
import com.wensheng.zcc.wechat.module.dto.GaodeIpCity;
import com.wensheng.zcc.wechat.module.vo.UserLngLat;
import com.wenshengamc.zcc.common.Common.GeoJson;
import com.wenshengamc.zcc.comnfunc.gaodegeo.Address;
import com.wenshengamc.zcc.comnfunc.gaodegeo.ComnFuncServiceGrpc;
import com.wenshengamc.zcc.comnfunc.gaodegeo.ComnFuncServiceGrpc.ComnFuncServiceBlockingStub;
import com.wenshengamc.zcc.comnfunc.gaodegeo.GeoIp2LocationResp;
import com.wenshengamc.zcc.comnfunc.gaodegeo.GeoIpReq;
import com.wenshengamc.zcc.comnfunc.gaodegeo.GeoLngLatReq;
import com.wenshengamc.zcc.comnfunc.gaodegeo.GeoLocationResp;
import com.wenshengamc.zcc.comnfunc.gaodegeo.PhoneMsgReq;
import com.wenshengamc.zcc.comnfunc.gaodegeo.WXPubTokenReq;
import com.wenshengamc.zcc.wechat.WechatGrpcServiceGrpc.WechatGrpcServiceImplBase;
import io.grpc.ManagedChannel;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Slf4j
public class ComnfuncGrpcService extends WechatGrpcServiceImplBase {
  @Autowired
  @Qualifier("comnFuncChannel")
  ManagedChannel comnFuncChannel;

  @Autowired
  @Qualifier("comnFuncPubChannel")
  ManagedChannel comnFuncPubChannel;

  ComnFuncServiceBlockingStub comnFuncPubStub;

  ComnFuncServiceBlockingStub comnFuncStub;

  @PostConstruct
  void init(){
    comnFuncStub = ComnFuncServiceGrpc.newBlockingStub(comnFuncChannel);
    comnFuncPubStub = ComnFuncServiceGrpc.newBlockingStub(comnFuncPubChannel);
  }

  public String getWXPubToken(String profName){
    WXPubTokenReq.Builder wxTRB = WXPubTokenReq.newBuilder();

    return comnFuncPubStub.getWXPublicToken(WXPubTokenReq.newBuilder().build()).getWxPubToken();

  }

  public String getProvinceByGeopoint(LatLng latLng){
    GeoJson.Builder gjBuilder = GeoJson.newBuilder();

    gjBuilder.addCoordinates( latLng.getLng() );
    gjBuilder.addCoordinates(latLng.getLat());
    return comnFuncStub.getAddressByGeoPoint(gjBuilder.build()).getAddress();
  }


  public String sendVCode(String phoneNum, String vcode){
    PhoneMsgReq.Builder pmrBuilder = PhoneMsgReq.newBuilder();
    pmrBuilder.setPhoneNum(phoneNum).setCode(vcode);

    return comnFuncStub.sendVerifyCode(pmrBuilder.build()).getResult();
  }


  public AmcRegionInfo getRegionInfo(Double lng, Double lat){
    GeoLngLatReq.Builder gllrBuildr = GeoLngLatReq.newBuilder();
    gllrBuildr.setLng(lng).setLat(lat);
    GeoLocationResp geoLocationResp = null;
    try{
      geoLocationResp =  comnFuncStub.getAmcGeoInfoByLngLat(gllrBuildr.build());

    }catch (Exception ex){
      log.error("Failed to get geo info by lat:{},lng:{}",lat,lng, ex);
      return null;
    }
    AmcRegionInfo amcRegionInfo = new AmcRegionInfo();
    amcRegionInfo.setCityName(geoLocationResp.getCityName());
    if(!StringUtils.isEmpty(geoLocationResp.getCityCode())  && geoLocationResp.getCityCode().length() > 6){
      amcRegionInfo.setCityCode(geoLocationResp.getCityCode().substring(0, 6));
    }else{
      amcRegionInfo.setCityCode(geoLocationResp.getCityCode());
    }
    if(!StringUtils.isEmpty(geoLocationResp.getProvCode())&& geoLocationResp.getProvCode().length() > 6){
      amcRegionInfo.setProvCode(geoLocationResp.getProvCode().substring(0,6));
    }else{
      amcRegionInfo.setProvCode(geoLocationResp.getProvCode());
    }
    amcRegionInfo.setProvName(geoLocationResp.getProvName());
    return amcRegionInfo;
  }

  public GaodeIpCity getCityByIp(String ipadd){
    GeoIpReq.Builder gllrBuildr = GeoIpReq.newBuilder();
    gllrBuildr.setIpadd(ipadd);
    GeoIp2LocationResp geoLocationResp = null;
    try{
      geoLocationResp =  comnFuncStub.getCityByIp(gllrBuildr.build());
    }catch (Exception ex){
      log.error("Failed to get ip city by:{}", ipadd, ex);
      return  null;
    }

    GaodeIpCity gaodeIpCity = new GaodeIpCity();
    AmcBeanUtils.copyProperties(geoLocationResp, gaodeIpCity);
    return gaodeIpCity;
  }

  public GeoJsonPoint getGeoInfo(String cityName){
    Address.Builder aBuilder = Address.newBuilder();

    aBuilder.setAddress(cityName);
    aBuilder.setCity(cityName);
    GeoJson geoJson;
    GeoJsonPoint geoJsonPoint = null;
    try{
      geoJson = comnFuncStub.getGeoByAddress(aBuilder.build());
      geoJsonPoint = new GeoJsonPoint(geoJson.getCoordinates(0), geoJson.getCoordinates(1));

    }catch (Exception ex){
      log.error("", ex);

    }
    return  geoJsonPoint;

  }
}
