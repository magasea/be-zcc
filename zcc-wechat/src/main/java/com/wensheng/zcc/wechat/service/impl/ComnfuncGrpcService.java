package com.wensheng.zcc.wechat.service.impl;

import com.wensheng.zcc.common.module.LatLng;
import com.wensheng.zcc.common.module.dto.AmcRegionInfo;
import com.wensheng.zcc.wechat.module.vo.MediaUploadResp;
import com.wensheng.zcc.wechat.module.vo.UserLngLat;
import com.wenshengamc.zcc.common.Common.GeoJson;
import com.wenshengamc.zcc.comnfunc.gaodegeo.ComnFuncServiceGrpc;
import com.wenshengamc.zcc.comnfunc.gaodegeo.ComnFuncServiceGrpc.ComnFuncServiceBlockingStub;
import com.wenshengamc.zcc.comnfunc.gaodegeo.GeoLngLatReq;
import com.wenshengamc.zcc.comnfunc.gaodegeo.GeoLocationResp;
import com.wenshengamc.zcc.comnfunc.gaodegeo.PhoneMsgReq;
import com.wenshengamc.zcc.comnfunc.gaodegeo.WXPubTokenReq;
import com.wenshengamc.zcc.comnfunc.gaodegeo.WXPubTokenResp;
import com.wenshengamc.zcc.wechat.AmcAssetImage;
import com.wenshengamc.zcc.wechat.ImageUploadResult;
import com.wenshengamc.zcc.wechat.UploadImg2WechatReq;
import com.wenshengamc.zcc.wechat.UploadImg2WechatResp;
import com.wenshengamc.zcc.wechat.WechatAssetImage;
import com.wenshengamc.zcc.wechat.WechatGrpcServiceGrpc.WechatGrpcServiceImplBase;
import io.grpc.ManagedChannel;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
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


  public AmcRegionInfo getRegionInfo(UserLngLat userLngLat){
    GeoLngLatReq.Builder gllrBuildr = GeoLngLatReq.newBuilder();
    gllrBuildr.setLng(userLngLat.getLng()).setLat(userLngLat.getLat());

    GeoLocationResp geoLocationResp =  comnFuncStub.getAmcGeoInfoByLngLat(gllrBuildr.build());
    AmcRegionInfo amcRegionInfo = new AmcRegionInfo();
    amcRegionInfo.setCityName(geoLocationResp.getCityName());
    amcRegionInfo.setCityCode(geoLocationResp.getCityCode());
    amcRegionInfo.setProvCode(geoLocationResp.getProvCode());
    amcRegionInfo.setProvName(geoLocationResp.getProvName());
    return amcRegionInfo;
  }
}
