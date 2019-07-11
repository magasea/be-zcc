package com.wensheng.zcc.comnfunc.service.impl;

import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

import com.wensheng.zcc.common.module.dto.WXUserGeoRecord;
import com.wensheng.zcc.common.utils.AmcBeanUtils;
import com.wensheng.zcc.common.utils.AmcDateUtils;
import com.wensheng.zcc.comnfunc.module.vo.base.GaodeGeoQueryVal;
import com.wensheng.zcc.comnfunc.service.GaoDeService;
import com.wenshengamc.zcc.comnfunc.gaodegeo.ComnFuncServiceGrpc;
import com.wenshengamc.zcc.comnfunc.gaodegeo.WXUserGeoReq;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.geo.GeoJson;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

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
  @Override
  public void getGeoByAddress(com.wenshengamc.zcc.comnfunc.gaodegeo.Address request,
      io.grpc.stub.StreamObserver<com.wenshengamc.zcc.common.Common.GeoJson> responseObserver) {
    List<GaodeGeoQueryVal> gaodeGeoQueryVals = null;
    try {
      gaodeGeoQueryVals = gaoDeService.getGeoInfoFromAddress(request.getAddress(),
          request.getCity());
    } catch (Exception e) {
      responseObserver.onError(e);
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


}
