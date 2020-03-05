package com.wensheng.zcc.cust.service.impl;

import com.wensheng.zcc.cust.module.vo.CustAmcCmpycontactorExtVo;
import com.wensheng.zcc.cust.service.AmcContactorService;
import java.util.List;
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
public class AmcContactorServiceImplTest {

  @Autowired
  AmcContactorService amcContactorService;

  @Test
  public void getCmpyAmcContactor() {

    List<CustAmcCmpycontactorExtVo> custAmcCmpycontactorList =  amcContactorService.getCmpyAmcContactor(543L);


  }
}
