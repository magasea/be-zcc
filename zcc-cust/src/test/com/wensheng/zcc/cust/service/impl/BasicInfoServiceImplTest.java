package com.wensheng.zcc.cust.service.impl;

import com.wensheng.zcc.cust.service.BasicInfoService;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class BasicInfoServiceImplTest {

@Autowired
BasicInfoService basicInfoService;

  @Test
  public void getAmcUserPrivMap() {
    Map<String, Integer> userPrivMap = basicInfoService.getAmcUserPrivMap();
    log.info("{}",userPrivMap);
    Map<Integer, List<String>> userProvsMap = basicInfoService.getAmcUserProvsMap();
    log.info("{}",userProvsMap);

  }
}
