package com.wensheng.zcc.amc.config;

import com.wensheng.zcc.amc.service.impl.WechatGrpcService;
import com.wenshengamc.zcc.comnfunc.gaodegeo.ComnFuncServiceGrpc;
import com.wenshengamc.zcc.comnfunc.gaodegeo.ComnFuncServiceGrpc.ComnFuncServiceBlockingStub;
import com.wenshengamc.zcc.wechat.WechatGrpcServiceGrpc;
import com.wenshengamc.zcc.wechat.WechatGrpcServiceGrpc.WechatGrpcServiceBlockingStub;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcConfig {
  @Value("${grpc.client.wechat.host}")
  String wechatHost;


  @Value("${grpc.client.wechat.port}")
  int wechatPort;

  @Value("${grpc.client.comnfunc.host}")
  String comnfuncHost;


  @Value("${grpc.client.comnfunc.port}")
  int comnfuncPort;

  @Bean
  ManagedChannel wechatChannel(){
    ManagedChannelBuilder managedChannelBuilder = ManagedChannelBuilder.forAddress(wechatHost, wechatPort).usePlaintext();
    return managedChannelBuilder.build();
  }


  @Bean
  WechatGrpcServiceBlockingStub wechatServiceStub(ManagedChannel wechatChannel){
    WechatGrpcServiceGrpc.WechatGrpcServiceBlockingStub wechatService =
        WechatGrpcServiceGrpc.newBlockingStub(wechatChannel);
    return wechatService;
  }


  @Bean
  ManagedChannel comnfuncChannel(){
    ManagedChannelBuilder managedChannelBuilder = ManagedChannelBuilder.forAddress(comnfuncHost, comnfuncPort).usePlaintext();
    return managedChannelBuilder.build();
  }


  @Bean
  ComnFuncServiceBlockingStub  comnfuncServiceStub(ManagedChannel comnfuncChannel){
    ComnFuncServiceGrpc.ComnFuncServiceBlockingStub comnFuncServiceBlockingStub =
        ComnFuncServiceGrpc.newBlockingStub(comnfuncChannel);
    return comnFuncServiceBlockingStub;
  }
}
