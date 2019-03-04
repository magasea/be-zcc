package com.wensheng.zcc.amc.service.impl;

import static org.junit.Assert.*;

import com.wensheng.zcc.amc.service.AmcOssFileService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author chenwei on 3/4/19
 * @project zcc-backend
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration
@ActiveProfiles(value = "dev")
public class AmcOssFileServiceImplTest {

  @Autowired
  AmcOssFileService amcOssFileService;

  @Test
  public void delFileInOss() throws Exception {

    amcOssFileService.listFilesOnOss();
  }
}