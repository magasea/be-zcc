package com.wensheng.zcc.comnfunc.service.impl;

import com.wensheng.zcc.comnfunc.module.vo.base.GaodeGeoQueryVal;
import com.wensheng.zcc.comnfunc.service.GaoDeService;
import io.grpc.testing.GrpcServerRule;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)

@ActiveProfiles(value = "dev")
@Slf4j
public class GaoDeServiceImplTest {

  @Autowired
  GaoDeService gaoDeService;

  @Rule
  public final GrpcServerRule grpcServerRule = new GrpcServerRule().directExecutor();

  @Before
  public void shutdownGrpc(){
    grpcServerRule.getServer().shutdown();
  }

  @Test
  public void getGaoDeAddress() throws Exception {
    String address = "犀浦镇学园路";
    String city = "成都市";
    List<GaodeGeoQueryVal> gaodeGeoQueryVals =  gaoDeService.getGeoInfoFromAddress(address, city);
    for(GaodeGeoQueryVal gaodeGeoQueryVal: gaodeGeoQueryVals){
      log.info(gaodeGeoQueryVal.getFormattedAddress());
    }
  }
}
