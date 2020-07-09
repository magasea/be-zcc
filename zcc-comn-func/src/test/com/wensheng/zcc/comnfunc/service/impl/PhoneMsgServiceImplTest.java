package com.wensheng.zcc.comnfunc.service.impl;

import com.wensheng.zcc.comnfunc.service.PhoneMsgService;
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
public class PhoneMsgServiceImplTest {

  @Autowired
  PhoneMsgService phoneMsgService;

  @Test
  public void generateVerificationCodeToPhoneByAliYun() {
    phoneMsgService.generateVerificationCodeToPhoneByAliYun("13611894398","123456");
  }
}
