package com.wensheng.zcc.comnfunc.service.impl;

import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

import com.wensheng.zcc.common.module.dto.WXUserGeoRecord;
import com.wensheng.zcc.common.utils.AmcBeanUtils;
import com.wensheng.zcc.common.utils.AmcDateUtils;
import com.wensheng.zcc.comnfunc.module.vo.base.GaodeGeoQueryVal;
import com.wensheng.zcc.comnfunc.service.GaoDeService;
import com.wensheng.zcc.comnfunc.service.WXBasicService;
import com.wenshengamc.zcc.comnfunc.gaodegeo.ComnFuncServiceGrpc;
import com.wenshengamc.zcc.comnfunc.gaodegeo.WXPubTokenResp;
import com.wenshengamc.zcc.comnfunc.gaodegeo.WXUserGeoReq;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.geo.GeoJson;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

@Service
public class ComnFuncGrpcServiceImpl  extends ComnFuncServiceGrpc.ComnFuncServiceImplBase {


  @Autowired
  GaoDeService gaoDeService;

  @Autowired
  WXBasicService wxBasicService;

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

  @Override
  public void getWXPublicToken(com.wenshengamc.zcc.comnfunc.gaodegeo.WXPubTokenReq request,
      io.grpc.stub.StreamObserver<com.wenshengamc.zcc.comnfunc.gaodegeo.WXPubTokenResp> responseObserver) {

   String token = wxBasicService.getPublicToken();


    responseObserver.onNext(WXPubTokenResp.newBuilder().setWxPubToken(token).build());

    responseObserver.onCompleted();
  }


}
