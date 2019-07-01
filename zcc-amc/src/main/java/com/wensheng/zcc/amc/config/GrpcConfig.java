package com.wensheng.zcc.amc.config;

import com.wensheng.zcc.amc.service.impl.WechatGrpcService;
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

}
