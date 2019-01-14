package com.wensheng.zcc.amc.service.impl;

import static org.junit.Assert.*;

import com.wensheng.zcc.amc.module.dao.helper.EditActionEnum;
import com.wensheng.zcc.amc.module.dao.helper.EditStatusEnum;
import com.wensheng.zcc.amc.service.ZccRulesService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author chenwei on 1/14/19
 * @project zcc-backend
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration
@ActiveProfiles(value = "dev")
public class ZccRulesServiceImplTest {
  @Autowired
  ZccRulesService zccRulesService;

  @Test
  public void runActionAndStatus() {

    EditStatusEnum editStatusEnum = zccRulesService.runActionAndStatus(EditActionEnum.ACT_CREATE, EditStatusEnum.INIT);
    Assert.assertEquals(editStatusEnum, EditStatusEnum.DRAFT);
  }
}