package com.wensheng.zcc.comnfunc.service.impl;

import com.wensheng.zcc.comnfunc.Application;
import com.wensheng.zcc.comnfunc.service.WXBasicService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(value = "dev")
public class WXBasicServiceImplTest {

  @Autowired
  WXBasicService wxBasicService;

  @Autowired
  Environment environment;

  @Test
  public void getPublicToken() {
    String profileName = null;
    for(String envName: environment.getActiveProfiles()){
      profileName = envName;
    }
    String token = wxBasicService.getPublicToken(profileName);
    System.out.println(token);
  }
}
