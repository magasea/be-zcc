package com.wensheng.zcc.comnfunc.service.impl;

import com.google.gson.Gson;
import com.wensheng.zcc.common.module.LatLng;
import com.wensheng.zcc.comnfunc.module.dto.BaiduContent;
import com.wensheng.zcc.comnfunc.module.dto.baidu.Content;
import java.io.UnsupportedEncodingException;
import lombok.extern.slf4j.Slf4j;
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
public class AmcBaiDuLogisQueryTest {

  private Gson gson = new Gson();

  @Autowired
  AmcBaiDuLogisQuery amcBaiDuLogisQuery;

  @Test
  public void getAddressByIp() throws UnsupportedEncodingException {
    Content addressByIp = amcBaiDuLogisQuery.getAddressByIp("182.47.145.251");
    log.info(gson.toJson(addressByIp));
  }

  @Test
  public void getLogisByAddress() throws UnsupportedEncodingException {
    LatLng result = amcBaiDuLogisQuery.getLogisByAddress("百度大厦");
    log.info(gson.toJson(result));
  }
}
