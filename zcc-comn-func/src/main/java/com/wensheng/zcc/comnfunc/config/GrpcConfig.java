package com.wensheng.zcc.comnfunc.config;

import com.wensheng.zcc.comnfunc.service.impl.ComnFuncGrpcServiceImpl;
import com.wensheng.zcc.common.utils.UnknownStatusDescriptionInterceptor;
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

  @Value("${grpc.port}")
  int grpcPort;

  @Value("${grpc.client.comnfunc.pubhost}")
  String comnfuncPubHost;
  @Value("${grpc.client.comnfunc.pubport}")
  int comnfuncPubPort;


  @Bean(name = "comnFuncPubChannel")
  ManagedChannel comnFuncPubChannel(){
    ManagedChannelBuilder managedChannelBuilder =
        ManagedChannelBuilder.forAddress(comnfuncPubHost, comnfuncPubPort).usePlaintext();
    return managedChannelBuilder.build();
  }

  @Autowired
  ComnFuncGrpcServiceImpl comnFuncGrpcService;

  @Bean
  Server server() throws InterruptedException, IOException {
    UnknownStatusDescriptionInterceptor unknownStatusDescriptionInterceptor = new UnknownStatusDescriptionInterceptor(
        Arrays.asList(IllegalArgumentException.class, Exception.class, RuntimeException.class));
    Server server = ServerBuilder.forPort(grpcPort)
        .addService(ServerInterceptors.intercept(comnFuncGrpcService, unknownStatusDescriptionInterceptor))
        .build();
   return server;

  }

}
