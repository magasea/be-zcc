package com.wensheng.zcc.amc.service.impl;

import com.wensheng.zcc.amc.dao.mysql.mapper.AmcDebtMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcDebtpackMapper;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebt;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtExample;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtpack;
import com.wensheng.zcc.common.utils.AmcDateUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@Slf4j
public class AmcMiscServiceImpl {

  @Autowired
  AmcDebtMapper amcDebtMapper;

  @Autowired
  AmcDebtpackMapper amcDebtpackMapper;


  Map<Long, String> areaMapper = null;
  /**
   * generate amc code to empty field
   * for example: wensheng-gd-00001
   */
  public void patchAmcDebtCode(){


    AmcDebtExample amcDebtExample = new AmcDebtExample();
    amcDebtExample.createCriteria().andZccDebtCodeEqualTo("-1");
    List<AmcDebt> amcDebts = amcDebtMapper.selectByExample(amcDebtExample);
    for(AmcDebt amcDebt: amcDebts){
      if(amcDebt.getDebtpackId() != null &&  amcDebt.getDebtpackId() != -1L && areaMapper.containsKey(amcDebt.getDebtpackId())){
        String amcDebtCode = generateZccDebtCode(amcDebt.getDebtpackId(), amcDebt.getId());
        amcDebt.setZccDebtCode(amcDebtCode);
        amcDebtMapper.updateByPrimaryKeySelective(amcDebt);
      }else{
        log.error("amcDebt id:{}, amcDebtpackId:{}", amcDebt.getId(), amcDebt.getDebtpackId());
      }
    }

  }

  public String generateZccDebtCode(Long debtPackId, Long debtId){
    if(areaMapper == null || CollectionUtils.isEmpty(areaMapper)){
      areaMapper = new HashMap<>();
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
    }
    String amcDebtCode = String.format("Wensheng-%s-%s%s", areaMapper.get(debtPackId),
        AmcDateUtils.getCurrentYear(), debtId);
    return amcDebtCode;

  }

  public String generateZccAssetCode( Long debtId, Long assetId){
    if(areaMapper == null || CollectionUtils.isEmpty(areaMapper)){
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
    }
    AmcDebt debt = amcDebtMapper.selectByPrimaryKey(debtId);
    String amcAssetCode = String.format("Wensheng-%s-%s%s", areaMapper.get(debt.getDebtpackId()),
        AmcDateUtils.getCurrentYear(), assetId);
    return amcAssetCode;

  }
}
