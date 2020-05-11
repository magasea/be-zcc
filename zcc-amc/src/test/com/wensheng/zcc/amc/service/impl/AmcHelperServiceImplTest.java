package com.wensheng.zcc.amc.service.impl;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.CurtInfo;
import com.wensheng.zcc.amc.service.AmcHelperService;
import java.util.Arrays;
import java.util.List;
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
@Slf4j
@ActiveProfiles(value = "dev")
public class AmcHelperServiceImplTest {

  @Autowired
  AmcHelperService amcHelperService;

  @Test
  public void queryCurtInfoByCityNames() throws Exception {
    String[] cityNames = new String[]{"杭州市","青岛市"};
    List<CurtInfo> curtInfoList = amcHelperService.queryCurtInfoByCityNames(Arrays.asList(cityNames));
    log.info("curtInfo:{}", curtInfoList);
    curtInfoList = amcHelperService.queryCurtInfoByCityNames(Arrays.asList(cityNames));
    log.info("curtInfo:{}", curtInfoList);
    cityNames = new String[]{"邯郸市"};
    curtInfoList = amcHelperService.queryCurtInfoByCityNames(Arrays.asList(cityNames));
    log.info("curtInfo:{}", curtInfoList);
    String[] provNames = new String[]{"河北省"};
    curtInfoList = amcHelperService.queryCurtInfoByProvNames(Arrays.asList(provNames));
    log.info("curtInfo:{}", curtInfoList);
    provNames = new String[]{"山东省"};
    curtInfoList = amcHelperService.queryCurtInfoByProvNames(Arrays.asList(provNames));
    log.info("curtInfo:{}", curtInfoList);

  }


}
