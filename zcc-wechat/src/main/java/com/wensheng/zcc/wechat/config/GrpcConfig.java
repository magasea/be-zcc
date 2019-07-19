package com.wensheng.zcc.wechat.config;

import com.wensheng.zcc.common.utils.UnknownStatusDescriptionInterceptor;
import com.wensheng.zcc.wechat.service.impl.WechatGrpcService;
import com.wenshengamc.zcc.comnfunc.gaodegeo.ComnFuncServiceGrpc;
import com.wenshengamc.zcc.comnfunc.gaodegeo.ComnFuncServiceGrpc.ComnFuncServiceBlockingStub;
import com.wenshengamc.zcc.comnfunc.gaodegeo.ComnFuncServiceGrpc.ComnFuncServiceStub;
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
import org.springframework.context.annotation.Primary;

@Configuration
public class GrpcConfig {


  @Value("${grpc.client.comnfunc.host}")
  String comnfuncHost;

  @Value("${grpc.client.comnfunc.pubhost}")
  String comnfuncPubHost;
  @Value("${grpc.client.comnfunc.pubport}")
  int comnfuncPubPort;
 @Value("${grpc.client.comnfunc.port}")
 int comnfuncPort;


  @Value("${grpc.server.port}")
  int grpcPort;

  @Autowired
  WechatGrpcService wechatGrpcService;

  @Primary
 @Bean(name = "comnFuncChannel")
  ManagedChannel comnFuncChannel(){
   ManagedChannelBuilder managedChannelBuilder = ManagedChannelBuilder.forAddress(comnfuncHost, comnfuncPort).usePlaintext();
   return managedChannelBuilder.build();
 }

  @Bean(name = "comnFuncPubChannel")
  ManagedChannel comnFuncPubChannel(){
    ManagedChannelBuilder managedChannelBuilder =
        ManagedChannelBuilder.forAddress(comnfuncPubHost, comnfuncPubPort).usePlaintext();
    return managedChannelBuilder.build();
  }

 @Bean(name = "comnFuncService")
 @Primary
 ComnFuncServiceBlockingStub comnFuncServiceStub(ManagedChannel comnFuncChanel){
   ComnFuncServiceGrpc.ComnFuncServiceBlockingStub comnFuncService = ComnFuncServiceGrpc.newBlockingStub(comnFuncChanel);
   return comnFuncService;
 }
  @Bean(name = "comnFuncPubService")
  ComnFuncServiceBlockingStub comnFuncPubServiceStub(){
    ManagedChannel managedChannel = comnFuncPubChannel();
    ComnFuncServiceGrpc.ComnFuncServiceBlockingStub comnFuncService =
        ComnFuncServiceGrpc.newBlockingStub(managedChannel);
    return comnFuncService;
  }

  @Bean
  Server server() throws InterruptedException, IOException {
    UnknownStatusDescriptionInterceptor unknownStatusDescriptionInterceptor = new UnknownStatusDescriptionInterceptor(
        Arrays.asList(IllegalArgumentException.class, Exception.class, RuntimeException.class));
    Server server = ServerBuilder.forPort(grpcPort)
        .addService(ServerInterceptors.intercept(wechatGrpcService, unknownStatusDescriptionInterceptor))
        .build();

    return server;

  }


}
