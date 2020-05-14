package com.wensheng.zcc.amc.service.impl;

import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

import com.google.gson.Gson;
import com.wensheng.zcc.common.module.dto.AmcSaleFilter;
import com.wensheng.zcc.common.module.dto.WXUserWatchObject;
import com.wenshengamc.zcc.wechat.UploadDebtImg2WechatResp;
import com.wenshengamc.zcc.wechat.WXUserFavor;
import com.wenshengamc.zcc.wechat.WXUserReq;
import com.wenshengamc.zcc.wechat.WXUserWatchOnObj;
import com.wenshengamc.zcc.wechat.WXUserWatchOnObjResp;
import com.wenshengamc.zcc.wechat.WechatGrpcServiceGrpc.WechatGrpcServiceBlockingStub;
import com.wenshengamc.zcc.wechat.WechatGrpcServiceGrpc.WechatGrpcServiceImplBase;
import io.grpc.ManagedChannel;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class WechatGrpcService extends WechatGrpcServiceImplBase {


  @Autowired
  WechatGrpcServiceBlockingStub wechatServiceStub;

  private Gson gson = new Gson();




  public com.wenshengamc.zcc.wechat.UploadImg2WechatResp uploadImage2Wechat(com.wenshengamc.zcc.wechat.UploadImg2WechatReq request) {
    return wechatServiceStub.uploadImage2Wechat(request);
  }

  public UploadDebtImg2WechatResp uploadDebtImage2Wechat(com.wenshengamc.zcc.wechat.UploadDebtImg2WechatReq request) {
    return wechatServiceStub.uploadDebtImage2Wechat(request);
  }

  public List<WXUserWatchOnObj> getWXUserWatchedOn(String openId){
    WXUserReq.Builder WXURB = WXUserReq.newBuilder();
    WXURB.setOpenId(openId);
    WXUserWatchOnObjResp wxUserWatchOnObjResp = wechatServiceStub.getWXUserWatchOnObjs(WXURB.build());
    return wxUserWatchOnObjResp.getWxUserWatchOnObjsList();
  }

  public AmcSaleFilter getWXUserFavor(String openId){
    WXUserReq.Builder WXURB = WXUserReq.newBuilder();
    WXURB.setOpenId(openId);
    WXUserFavor wxUserFavor  = wechatServiceStub.getWXUserFavor(WXURB.build());
    if(StringUtils.isEmpty(wxUserFavor.getUserFavor())){
      return null;
    }
    AmcSaleFilter amcSaleFilter = gson.fromJson(wxUserFavor.getUserFavor(), AmcSaleFilter.class);
    return amcSaleFilter;
  }
}
