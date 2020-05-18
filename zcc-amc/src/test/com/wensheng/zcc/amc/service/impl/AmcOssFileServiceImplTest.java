package com.wensheng.zcc.amc.service.impl;

import static org.junit.Assert.*;

import com.google.gson.Gson;
import com.wensheng.zcc.amc.dao.mysql.mapper.CurtInfoMapper;
import com.wensheng.zcc.amc.module.dao.helper.ImagePathClassEnum;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.CurtInfo;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.CurtInfoExample;
import com.wensheng.zcc.amc.service.AmcOssFileService;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
@Slf4j
public class AmcOssFileServiceImplTest {

  @Autowired
  AmcOssFileService amcOssFileService;

  @Autowired
  CurtInfoMapper curtInfoMapper;

  @Value("${env.image-repo}")
  String imgRepo;

  Gson gson = new Gson();

  @Value("${env.name}")
  String envName;

  @Test
  public void delFileInOss() throws Exception {
    amcOssFileService.listFilesOnOss(ImagePathClassEnum.ASSET);


    amcOssFileService.delFileInOss("http://ws-zcc.oss-cn-beijing.aliyuncs.com/asset/289/a6bd3779ab7aef6da3b6e9b9bb80471641830827.gz");

    amcOssFileService.listFilesOnOss(ImagePathClassEnum.ASSET);
  }

  @Test
  public void listFileInOss() throws Exception {
    amcOssFileService.listFilesOnOss(ImagePathClassEnum.ASSET);


  }

  @Test
  public void handleCurtFile2Oss() throws Exception {
    CurtInfoExample curtInfoExample = new CurtInfoExample();
    curtInfoExample.setOrderByClause(" id desc ");
    List<CurtInfo> curtInfoList = curtInfoMapper.selectByExample(curtInfoExample);
    StringBuilder sb = new StringBuilder(imgRepo);
    sb.append(File.separator).append("curtinfo.json");
    gson.toJson(curtInfoList, new FileWriter(sb.toString()));
    StringBuilder sbPrePath = new StringBuilder(envName);
    sbPrePath.append("/").append("curtinfo").append("/");
    String ossCurtFile = amcOssFileService.handleFile2Oss(sb.toString(), sbPrePath.toString());
    log.info(ossCurtFile);
  }
}