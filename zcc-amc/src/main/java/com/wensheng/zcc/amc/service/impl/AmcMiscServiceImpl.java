package com.wensheng.zcc.amc.service.impl;

import com.wensheng.zcc.amc.dao.mysql.mapper.AmcDebtMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcDebtpackMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.ZccDebtpackMapper;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebt;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtExample;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.ZccDebtpack;
import com.wensheng.zcc.common.params.sso.AmcLocationEnum;
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
  ZccDebtpackMapper zccDebtpackMapper;


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
      List<ZccDebtpack> amcDeptpacks = zccDebtpackMapper.selectByExample(null);
      for(ZccDebtpack zccDebtpack : amcDeptpacks){
        if(zccDebtpack.getArea() == AmcLocationEnum.JIANGSU_LOCATION.getId()){
          areaMapper.put(zccDebtpack.getId(), "js");
        }else if(zccDebtpack.getArea() == AmcLocationEnum.GUANGDONG_LOCATION.getId()){
          areaMapper.put(zccDebtpack.getId(), "gd");
        }else if(zccDebtpack.getArea() == AmcLocationEnum.ZHEJIANG_LOCATION.getId()){
          areaMapper.put(zccDebtpack.getId(), "zj");
        }else if(zccDebtpack.getArea() == AmcLocationEnum.SHANGHAI_LOCATION.getId()){
          areaMapper.put(zccDebtpack.getId(),"sh");
        }else if(zccDebtpack.getArea() == AmcLocationEnum.BEIJING_LOCATION.getId()){
          areaMapper.put(zccDebtpack.getId(), "bj");
        }
      }
    }
    String amcDebtCode = String.format("wensheng-%s-%s%s", areaMapper.get(debtPackId),
        AmcDateUtils.getCurrentYear(), debtId);
    return amcDebtCode;

  }

  public String generateZccAssetCode( Long debtId, Long assetId){
    if(areaMapper == null || CollectionUtils.isEmpty(areaMapper)){
      areaMapper = new HashMap<>();
      List<ZccDebtpack> amcDeptpacks = zccDebtpackMapper.selectByExample(null);
      for(ZccDebtpack zccDebtpack : amcDeptpacks){
        if(zccDebtpack.getArea() == AmcLocationEnum.JIANGSU_LOCATION.getId()){
          areaMapper.put(zccDebtpack.getId(), "js");
        }else if(zccDebtpack.getArea() == AmcLocationEnum.GUANGDONG_LOCATION.getId()){
          areaMapper.put(zccDebtpack.getId(), "gd");
        }else if(zccDebtpack.getArea() == AmcLocationEnum.ZHEJIANG_LOCATION.getId()){
          areaMapper.put(zccDebtpack.getId(), "zj");
        }else if(zccDebtpack.getArea() == AmcLocationEnum.SHANGHAI_LOCATION.getId()){
          areaMapper.put(zccDebtpack.getId(),"sh");
        }else if(zccDebtpack.getArea() == AmcLocationEnum.BEIJING_LOCATION.getId()){
          areaMapper.put(zccDebtpack.getId(), "bj");
        }
      }
    }
    AmcDebt debt = amcDebtMapper.selectByPrimaryKey(debtId);
    String amcAssetCode = String.format("wensheng-%s-%s%s", areaMapper.get(debt.getDebtpackId()),
        AmcDateUtils.getCurrentYear(), assetId);
    return amcAssetCode;

  }
}
