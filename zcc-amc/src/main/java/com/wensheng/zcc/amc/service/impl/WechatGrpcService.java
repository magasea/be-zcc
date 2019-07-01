package com.wensheng.zcc.amc.service.impl;

import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

import com.wenshengamc.zcc.wechat.WechatGrpcServiceGrpc.WechatGrpcServiceBlockingStub;
import com.wenshengamc.zcc.wechat.WechatGrpcServiceGrpc.WechatGrpcServiceImplBase;
import io.grpc.ManagedChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WechatGrpcService extends WechatGrpcServiceImplBase {


  @Autowired
  WechatGrpcServiceBlockingStub wechatServiceStub;





  public com.wenshengamc.zcc.wechat.UploadImg2WechatResp uploadImage2Wechat(com.wenshengamc.zcc.wechat.UploadImg2WechatReq request) {
    return wechatServiceStub.uploadImage2Wechat(request);
  }
}
