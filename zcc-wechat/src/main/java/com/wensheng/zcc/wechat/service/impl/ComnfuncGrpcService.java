package com.wensheng.zcc.wechat.service.impl;

import com.wensheng.zcc.common.module.LatLng;
import com.wensheng.zcc.wechat.module.vo.MediaUploadResp;
import com.wenshengamc.zcc.common.Common.GeoJson;
import com.wenshengamc.zcc.comnfunc.gaodegeo.ComnFuncServiceGrpc.ComnFuncServiceBlockingStub;
import com.wenshengamc.zcc.comnfunc.gaodegeo.WXPubTokenReq;
import com.wenshengamc.zcc.wechat.AmcAssetImage;
import com.wenshengamc.zcc.wechat.ImageUploadResult;
import com.wenshengamc.zcc.wechat.UploadImg2WechatReq;
import com.wenshengamc.zcc.wechat.UploadImg2WechatResp;
import com.wenshengamc.zcc.wechat.WechatAssetImage;
import com.wenshengamc.zcc.wechat.WechatGrpcServiceGrpc.WechatGrpcServiceImplBase;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Slf4j
public class ComnfuncGrpcService extends WechatGrpcServiceImplBase {


  @Autowired
  ComnFuncServiceBlockingStub comnFuncPubStub;
  @Autowired
  ComnFuncServiceBlockingStub comnFuncStub;

  public String getWXPubToken(){

    return comnFuncPubStub.getWXPublicToken(WXPubTokenReq.newBuilder().build()).getWxPubToken();

  }

  public String getProvinceByGeopoint(LatLng latLng){
    GeoJson.Builder gjBuilder = GeoJson.newBuilder();

    gjBuilder.addCoordinates( latLng.getLng() );
    gjBuilder.addCoordinates(latLng.getLat());
    return comnFuncStub.getAddressByGeoPoint(gjBuilder.build()).getAddress();
  }


}
