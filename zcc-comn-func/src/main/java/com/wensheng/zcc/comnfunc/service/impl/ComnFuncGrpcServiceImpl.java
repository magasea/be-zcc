package com.wensheng.zcc.comnfunc.service.impl;

import com.wensheng.zcc.common.module.LatLng;
import com.wensheng.zcc.common.module.dto.WXUserGeoRecord;
import com.wensheng.zcc.common.utils.AmcBeanUtils;
import com.wensheng.zcc.common.utils.AmcDateUtils;
import com.wensheng.zcc.comnfunc.module.vo.base.GaodeGeoQueryVal;
import com.wensheng.zcc.comnfunc.module.vo.base.GaodeRegeoQueryVal;
import com.wensheng.zcc.comnfunc.service.GaoDeService;
import com.wensheng.zcc.comnfunc.service.PhoneMsgService;
import com.wensheng.zcc.comnfunc.service.RegionService;
import com.wensheng.zcc.comnfunc.service.WXBasicService;
import com.wenshengamc.zcc.comnfunc.gaodegeo.*;
import io.grpc.Status;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

@Service
@GRpcService(interceptors = LogInterceptor.class)
@Slf4j
public class ComnFuncGrpcServiceImpl  extends ComnFuncServiceGrpc.ComnFuncServiceImplBase {


  @Autowired
  GaoDeService gaoDeService;

  @Autowired
  WXBasicService wxBasicService;

  @Autowired
  RegionService regionService;

  @Autowired
  PhoneMsgService phoneMsgService;

  @Override
  public void getAddress(com.wenshengamc.zcc.comnfunc.gaodegeo.WXUserGeoReq request,
      io.grpc.stub.StreamObserver<com.wenshengamc.zcc.comnfunc.gaodegeo.WXUserGeoReq> responseObserver) {
    try{
      WXUserGeoRecord wxUserGeoRecord = new WXUserGeoRecord();
      AmcBeanUtils.copyProperties(request, wxUserGeoRecord);
      wxUserGeoRecord.setLocation(new GeoJsonPoint(request.getLocation().getCoordinates(0),
          request.getLocation().getCoordinates(1)));
      gaoDeService.getAddressFromGeoPoint(wxUserGeoRecord);
      WXUserGeoReq.Builder wxUserGeoReqBuilder =  WXUserGeoReq.newBuilder();
      AmcBeanUtils.copyProperties(wxUserGeoRecord, wxUserGeoReqBuilder);
      if(wxUserGeoRecord.getCreateTime() != null){
        wxUserGeoReqBuilder.setCreateTime(AmcDateUtils.fromLocalDate(wxUserGeoRecord.getCreateTime()));
      }
      if(wxUserGeoRecord.getUpdateTime() != null){
        wxUserGeoReqBuilder.setUpdateTime(AmcDateUtils.fromLocalDate(wxUserGeoRecord.getUpdateTime()));
      }


      responseObserver.onNext(wxUserGeoReqBuilder.build());
      responseObserver.onCompleted();
    }catch (Exception ex){
      responseObserver.onError(ex);
    }


  }
  @Override
  @Scheduled(cron = "${spring.scheduling}")
  public void getGeoByAddress(com.wenshengamc.zcc.comnfunc.gaodegeo.Address request,
      io.grpc.stub.StreamObserver<com.wenshengamc.zcc.common.Common.GeoJson> responseObserver) {
    List<GaodeGeoQueryVal> gaodeGeoQueryVals = null;
    try {
      gaodeGeoQueryVals = gaoDeService.getGeoInfoFromAddress(request.getAddress(),
          request.getCity());
    } catch (Exception e) {

      responseObserver.onError(Status.INTERNAL.withDescription(String.format("Failed to get address for :%s",
          request.getAddress())).withCause(e).asRuntimeException());
      return;

    }
    if(CollectionUtils.isEmpty(gaodeGeoQueryVals) || StringUtils.isEmpty(gaodeGeoQueryVals.get(0).getLocation())){

      responseObserver.onError(new Exception(String.format("failed to get geoInfo for:%s, city:%s",
          request.getAddress(), request.getCity())));
      return;
    }
    String[] coordinates = gaodeGeoQueryVals.get(0).getLocation().split(",");
    com.wenshengamc.zcc.common.Common.GeoJson.Builder gjBuilder =
        com.wenshengamc.zcc.common.Common.GeoJson.newBuilder();
    gjBuilder.addCoordinates(Double.valueOf(coordinates[0])).addCoordinates(Double.valueOf(coordinates[1]));
    responseObserver.onNext(gjBuilder.build());
    responseObserver.onCompleted();
  }

  @Override
  public void getWXPublicToken(com.wenshengamc.zcc.comnfunc.gaodegeo.WXPubTokenReq request,
      io.grpc.stub.StreamObserver<com.wenshengamc.zcc.comnfunc.gaodegeo.WXPubTokenResp> responseObserver) {
    String profileName = request.getProfileName();
   String token = wxBasicService.getPublicToken(profileName);


    responseObserver.onNext(WXPubTokenResp.newBuilder().setWxPubToken(token).build());

    responseObserver.onCompleted();
  }

  @Override
  public void getAddressByGeoPoint(com.wenshengamc.zcc.common.Common.GeoJson request,
      io.grpc.stub.StreamObserver<com.wenshengamc.zcc.comnfunc.gaodegeo.Address> responseObserver)  {
    LatLng latLng = new LatLng();
    latLng.setLat(request.getCoordinates(0));
    latLng.setLng(request.getCoordinates(1));
    GaodeRegeoQueryVal gaodeRegeoQueryVal = null;
    try {
      gaodeRegeoQueryVal = gaoDeService.getAddressFromGeoPoint(latLng);
    } catch (Exception e) {
      log.error("Exception:", e);
      responseObserver.onError(e);
    }
    Address.Builder aBuilder = Address.newBuilder();
    aBuilder.setAddress(gaodeRegeoQueryVal.getAddressComponent().getProvince());
//    aBuilder.setCity(gaodeRegeoQueryVal.getAddressComponent().getCity());
    responseObserver.onNext(aBuilder.build());
    responseObserver.onCompleted();
  }

  @Override
  public void getAmcRegionByName(com.wenshengamc.zcc.comnfunc.gaodegeo.AmcRegionReq request,
                                 io.grpc.stub.StreamObserver<com.wenshengamc.zcc.comnfunc.gaodegeo.AmcRegionResp> responseObserver) {
    try{
      List<com.wensheng.zcc.comnfunc.module.dto.AmcRegionItem> amcRegionItems = regionService.getRegionByName(request.getRegName());
      AmcRegionResp.Builder amcRegionBuilder = AmcRegionResp.newBuilder();
      for(com.wensheng.zcc.comnfunc.module.dto.AmcRegionItem amcRegionItem: amcRegionItems){
        AmcRegionItem.Builder ariBuilder = AmcRegionItem.newBuilder();
        AmcBeanUtils.copyProperties(amcRegionItem, ariBuilder);
        amcRegionBuilder.addAmcRegionItems(ariBuilder);
      }
      responseObserver.onNext(amcRegionBuilder.build());
      responseObserver.onCompleted();
    }catch (Exception ex){
      responseObserver.onError(ex);
    }
    ;
  }

  @Override
  public void sendVerifyCode(com.wenshengamc.zcc.comnfunc.gaodegeo.PhoneMsgReq request,
      io.grpc.stub.StreamObserver<com.wenshengamc.zcc.comnfunc.gaodegeo.PhoneMsgRep> responseObserver) {
    String code = request.getCode();
    String phone = request.getPhoneNum();
    try{
      String result = phoneMsgService.generateVerificationCodeToPhoneByAliYun(phone, code);
      PhoneMsgRep.Builder pmBuilder = PhoneMsgRep.newBuilder();
      pmBuilder.setResult(result);
      responseObserver.onNext(pmBuilder.build());
      responseObserver.onCompleted();
    }catch (Exception ex){
      responseObserver.onError(ex);
    }


  }


}
