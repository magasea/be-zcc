package com.wensheng.zcc.comnfunc.service.impl;

import com.wensheng.zcc.common.module.dto.WXUserGeoRecord;
import com.wensheng.zcc.common.utils.AmcBeanUtils;
import com.wensheng.zcc.common.utils.AmcDateUtils;
import com.wensheng.zcc.comnfunc.service.GaoDeService;
import com.wenshengamc.zcc.comnfunc.gaodegeo.ComnFuncServiceGrpc;
import com.wenshengamc.zcc.comnfunc.gaodegeo.WXUserGeoReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;

@Service
public class ComnFuncGrpcServiceImpl  extends ComnFuncServiceGrpc.ComnFuncServiceImplBase {



  @Autowired
  GaoDeService gaoDeService;

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


}
