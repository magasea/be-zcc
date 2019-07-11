package com.wensheng.zcc.cust.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration
@ActiveProfiles(value = "dev")
public class GeoInfoServiceImplTest {

  @Autowired
  GeoInfoServiceImpl geoInfoService;

  @Test
  public void searchGeoInfoForTrd() {
    geoInfoService.searchGeoInfoForTrd();
  }
}
