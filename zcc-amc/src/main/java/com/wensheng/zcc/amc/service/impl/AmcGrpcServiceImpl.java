package com.wensheng.zcc.amc.service.impl;

import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

import com.wenshengamc.zcc.recm.RecommendGrpc.RecommendImplBase;
import com.wenshengamc.zcc.recm.ZccAmc.RawData;
import org.lognet.springboot.grpc.GRpcService;

@GRpcService
public class AmcGrpcServiceImpl extends RecommendImplBase {


  @Override
  /**
   */
  public void loadAllRaw(com.wenshengamc.zcc.recm.ZccAmc.Notice request,
      io.grpc.stub.StreamObserver<com.wenshengamc.zcc.recm.ZccAmc.RawData> responseObserver) {
    request.getType();
//    RawData.newBuilder().addRepeatedField()
  }

}
