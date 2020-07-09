package com.wensheng.zcc.comnfunc.service.impl;

import com.google.gson.Gson;
import com.wensheng.zcc.comnfunc.module.vo.base.GaodeGeoQueryIPResp;
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

  private Gson gson = new Gson();

  @Rule
  public final GrpcServerRule grpcServerRule = new GrpcServerRule().directExecutor();

  @Before
  public void shutdownGrpc(){
    grpcServerRule.getServer().shutdown();
  }

  @Test
  public void getGaoDeAddress() throws Exception {
    String address = "银川市";
    String city = "银川市";
    List<GaodeGeoQueryVal> gaodeGeoQueryVals =  gaoDeService.getGeoInfoFromAddress(address, city);
    for(GaodeGeoQueryVal gaodeGeoQueryVal: gaodeGeoQueryVals){
      log.info(gaodeGeoQueryVal.getFormattedAddress());
    }
  }


  @Test
  public void getIpAddress() throws Exception {
    String address = "上海";
    String ip = "223.104.213.47";
    GaodeGeoQueryIPResp addressByIp = gaoDeService.getAddressByIp(ip);
    log.info(gson.toJson(addressByIp));

  }

}
