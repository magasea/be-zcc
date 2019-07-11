package com.wensheng.zcc.cust.config;

import com.wenshengamc.zcc.comnfunc.gaodegeo.ComnFuncServiceGrpc;
import com.wenshengamc.zcc.comnfunc.gaodegeo.ComnFuncServiceGrpc.ComnFuncServiceBlockingStub;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcConfig {

  @Value("${grpc.client.comnfunc.port}")
  int comnfuncPort;

  @Value("${grpc.client.comnfunc.host}")
  String comnfuncHost;

  @Bean
  ManagedChannel comnfuncChannel(){
    ManagedChannelBuilder managedChannelBuilder = ManagedChannelBuilder.forAddress(comnfuncHost, comnfuncPort).usePlaintext();
    return managedChannelBuilder.build();
  }


  @Bean
  ComnFuncServiceBlockingStub comnfuncServiceStub(ManagedChannel comnfuncChannel){
    ComnFuncServiceGrpc.ComnFuncServiceBlockingStub comnFuncServiceBlockingStub =
        ComnFuncServiceGrpc.newBlockingStub(comnfuncChannel);
    return comnFuncServiceBlockingStub;
  }

}
