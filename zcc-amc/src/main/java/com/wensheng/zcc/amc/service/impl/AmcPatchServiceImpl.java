package com.wensheng.zcc.amc.service.impl;

import com.wensheng.zcc.amc.dao.mysql.mapper.AmcDebtMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcDebtpackMapper;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebt;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtExample;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtpack;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AmcPatchServiceImpl {

  @Autowired
  AmcDebtMapper amcDebtMapper;

  @Autowired
  AmcDebtpackMapper amcDebtpackMapper;

  /**
   * generate amc code to empty field
   * for example: wensheng-gd-00001
   */
  public void patchAmcDebtCode(){
    Map<Long, String> areaMapper = new HashMap<>();
    List<AmcDebtpack> amcDeptpacks = amcDebtpackMapper.selectByExample(null);
    for(AmcDebtpack amcDebtpack: amcDeptpacks){
      if(amcDebtpack.getTitle().contains("江苏")){
        areaMapper.put(amcDebtpack.getId(), "JS");
      }else if(amcDebtpack.getTitle().contains("广东")){
        areaMapper.put(amcDebtpack.getId(), "GD");
      }else if(amcDebtpack.getTitle().contains("浙江")){
        areaMapper.put(amcDebtpack.getId(), "ZJ");
      }else if(amcDebtpack.getTitle().contains("上海")){
        areaMapper.put(amcDebtpack.getId(),"SH");
      }else if(amcDebtpack.getTitle().contains("北京")){
        areaMapper.put(amcDebtpack.getId(), "BJ");
      }
    }

    AmcDebtExample amcDebtExample = new AmcDebtExample();
    amcDebtExample.createCriteria().andAmcDebtCodeEqualTo("-1");
    List<AmcDebt> amcDebts = amcDebtMapper.selectByExample(amcDebtExample);
    for(AmcDebt amcDebt: amcDebts){
      if(amcDebt.getDebtpackId() != null &&  amcDebt.getDebtpackId() != -1L && areaMapper.containsKey(amcDebt.getDebtpackId())){
        String amcDebtCode = String.format("Wensheng-%s-%s", areaMapper.get(amcDebt.getDebtpackId()), amcDebt.getId());
        amcDebt.setAmcDebtCode(amcDebtCode);
        amcDebtMapper.updateByPrimaryKeySelective(amcDebt);
      }else{
        log.error("amcDebt id:{}, amcDebtpackId:{}", amcDebt.getId(), amcDebt.getDebtpackId());
      }
    }

  }

}
