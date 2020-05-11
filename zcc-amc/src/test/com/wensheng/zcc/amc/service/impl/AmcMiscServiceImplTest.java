package com.wensheng.zcc.amc.service.impl;

import com.wensheng.zcc.amc.service.AmcMiscService;
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
public class AmcMiscServiceImplTest {

  @Autowired
  AmcMiscService amcMiscService;

  @Test
  public void updateClickCountInfo() {
    amcMiscService.updateClickCountInfo();
  }

  @Test
  public void updateHasImgTag() {
    amcMiscService.updateHasImgTag();
  }
}
