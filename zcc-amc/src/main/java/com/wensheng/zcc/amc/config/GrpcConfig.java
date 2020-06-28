package com.wensheng.zcc.amc.config;

import com.wensheng.zcc.amc.service.impl.AmcGrpcServiceImpl;
import com.wensheng.zcc.amc.service.impl.WechatGrpcService;
import com.wensheng.zcc.common.utils.UnknownStatusDescriptionInterceptor;
import com.wenshengamc.sso.AmcSSOGrpcServiceGrpc;
import com.wenshengamc.sso.AmcSSOGrpcServiceGrpc.AmcSSOGrpcServiceBlockingStub;
import com.wenshengamc.zcc.comnfunc.gaodegeo.ComnFuncServiceGrpc;
import com.wenshengamc.zcc.comnfunc.gaodegeo.ComnFuncServiceGrpc.ComnFuncServiceBlockingStub;
import com.wenshengamc.zcc.wechat.WechatGrpcServiceGrpc;
import com.wenshengamc.zcc.wechat.WechatGrpcServiceGrpc.WechatGrpcServiceBlockingStub;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerInterceptors;
import java.io.IOException;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
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

  @Value("${grpc.client.amcsso.host}")
  String amcssoHost;


  @Value("${grpc.client.amcsso.port}")
  int amcssoPort;

  @Bean
  ManagedChannel amcSSOChannel(){
    ManagedChannelBuilder managedChannelBuilder = ManagedChannelBuilder.forAddress(amcssoHost, amcssoPort).usePlaintext();
    return managedChannelBuilder.build();
  }


  @Bean
  AmcSSOGrpcServiceBlockingStub amcSSOServiceStub(ManagedChannel amcSSOChannel){
    AmcSSOGrpcServiceGrpc.AmcSSOGrpcServiceBlockingStub ssoGrpcServiceStub =
        AmcSSOGrpcServiceGrpc.newBlockingStub(amcSSOChannel);
    return ssoGrpcServiceStub;
  }

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

  @Value("${grpc.port}")
  int grpcPort;

  @Autowired
  AmcGrpcServiceImpl amcGrpcService;

//  @Bean
//  Server server() throws InterruptedException, IOException {
//    UnknownStatusDescriptionInterceptor unknownStatusDescriptionInterceptor = new UnknownStatusDescriptionInterceptor(
//        Arrays.asList(IllegalArgumentException.class, Exception.class, RuntimeException.class));
//    Server server = ServerBuilder.forPort(grpcPort)
//        .addService(ServerInterceptors.intercept(amcGrpcService, unknownStatusDescriptionInterceptor))
//        .build();
//    return server;
//
//  }
}
