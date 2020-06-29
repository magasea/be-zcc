package com.wensheng.zcc.comnfunc.service.impl;

import com.google.gson.Gson;
import com.wensheng.zcc.comnfunc.module.dto.AmcRegionItem;
import com.wensheng.zcc.comnfunc.service.RegionService;
import java.util.List;
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
public class RegionServiceImplTest {

  @Autowired
  RegionService regionService;


  private Gson gson = new Gson();

  @Test
  public void getRegionByName() throws Exception {
    List<AmcRegionItem> result1 = regionService.getRegionByName("湖南省");

    System.out.println(gson.toJson(result1));

    List<AmcRegionItem> result2 = regionService.getRegionByName("长沙市");
    System.out.println(gson.toJson(result2));


  }
}
