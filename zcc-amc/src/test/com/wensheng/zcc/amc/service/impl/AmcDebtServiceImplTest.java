package com.wensheng.zcc.amc.service.impl;

import static org.junit.Assert.*;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebt;
import com.wensheng.zcc.amc.module.dao.mysql.auto.ext.AmcDebtExt;
import com.wensheng.zcc.amc.module.vo.AmcDebtVo;
import com.wensheng.zcc.amc.service.AmcDebtService;
import java.util.List;
import org.checkerframework.checker.units.qual.A;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

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

    List<AmcDebtExt> amcDebtExtList =  amcDebtService.queryAllExt(0, 20, null);
    for(AmcDebtExt amcDebtExt: amcDebtExtList){
      if(!CollectionUtils.isEmpty(amcDebtExt.getAmcAssetIds())){
        System.out.println(amcDebtExt.getAmcAssetIds());

      }

    }
  }

  @Test
  public void query(){
    AmcDebt queryCond = new AmcDebt();
    int offset = 0;
    int size = 20;
    List<AmcDebtVo> amcDebts = amcDebtService.query(queryCond, offset, size);
    if(!CollectionUtils.isEmpty(amcDebts)){
      System.out.println("OK");
    }
  }
}