package com.wensheng.zcc.cust.service.impl;

import com.google.gson.Gson;
import com.wensheng.zcc.cust.dao.mysql.mapper.CustRegionMapper;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustRegion;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustRegionExample;
import com.wensheng.zcc.cust.module.sync.CustCmpyInfoFromSync;
import com.wensheng.zcc.cust.service.SyncService;
import java.text.ParseException;
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
public class ScriptSysServiceImplTest {

  @Autowired
  CustRegionMapper custRegionMapper;

  @Autowired
  SyncService syncService;

  private Gson gson = new Gson();

  @Test
  public void exportMapping(){
    CustRegionExample custRegionExample = new CustRegionExample() ;
    custRegionExample.createCriteria().andParentIdGreaterThanOrEqualTo(320000L).andParentIdLessThan( 330000L);
    Gson gson = new Gson();
    List<CustRegion> custRegions = custRegionMapper.selectByExample(custRegionExample);
    String result = gson.toJson(custRegions);
    System.out.println(result);

  }

  @Test
  public void makeUpdateTrdDate() throws ParseException {
    syncService.makeUpDataForMissDateOfTrade();
  }
  @Test
  public void makeUpdateProvince() throws ParseException {
    syncService.makeUpDataForProvinceCodeOfTrade();
  }

  @Test
  public void checkTrdProvinceConsist() throws ParseException {
    syncService.makeCheckProvinceCodeOfTrade();
  }

  @Test
  public void getCmpyByName() throws ParseException {
    CustCmpyInfoFromSync custCmpyInfoFromSync = syncService.getCmpyInfoByName("上海文盛资产管理股份有限公司江苏分公司");
    System.out.println(gson.toJson(custCmpyInfoFromSync));
    custCmpyInfoFromSync = syncService.getCmpyInfoByName("浙江浙北资产管理有");
    System.out.println(gson.toJson(custCmpyInfoFromSync));

  }
}
