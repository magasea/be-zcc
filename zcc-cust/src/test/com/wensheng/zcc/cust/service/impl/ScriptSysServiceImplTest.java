package com.wensheng.zcc.cust.service.impl;

import com.google.gson.Gson;
import com.wensheng.zcc.cust.dao.mysql.mapper.CustRegionMapper;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustRegion;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustRegionExample;
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



  @Test
  public void exportMapping(){
    CustRegionExample custRegionExample = new CustRegionExample() ;
    custRegionExample.createCriteria().andParentIdGreaterThanOrEqualTo(320000L).andParentIdLessThan( 330000L);
    Gson gson = new Gson();
    List<CustRegion> custRegions = custRegionMapper.selectByExample(custRegionExample);
    String result = gson.toJson(custRegions);
    System.out.println(result);

  }

}
