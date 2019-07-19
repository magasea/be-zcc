package com.wensheng.zcc.comnfunc.service.impl;

import com.wensheng.zcc.comnfunc.Application;
import com.wensheng.zcc.comnfunc.service.WXBasicService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(value = "dev")
public class WXBasicServiceImplTest {

  @Autowired
  WXBasicService wxBasicService;

  @Test
  public void getPublicToken() {
    String token = wxBasicService.getPublicToken();
    System.out.println(token);
  }
}
