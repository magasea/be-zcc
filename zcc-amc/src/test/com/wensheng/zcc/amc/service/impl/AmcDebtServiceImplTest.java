package com.wensheng.zcc.amc.service.impl;

import static org.junit.Assert.*;

import com.wensheng.zcc.amc.service.AmcDebtService;
import org.checkerframework.checker.units.qual.A;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author chenwei on 1/7/19
 * @project zcc-backend
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration
@ActiveProfiles(value = "dev")
public class AmcDebtServiceImplTest {

  @Autowired
  AmcDebtService amcDebtService;

  @Test
  public void queryAllExt() throws Exception {

    amcDebtService.queryAllExt(0, 20, null);
  }
}