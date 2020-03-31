package com.wensheng.zcc.amc.service.impl;

import com.wensheng.zcc.amc.dao.mysql.mapper.AmcAssetMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcDebtContactorMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcDebtMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcDebtpackMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.ZccDebtpackMapper;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcAsset;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcAssetExample;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebt;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtContactor;
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
  AmcAssetMapper amcAssetMapper;

  @Autowired
  ZccDebtpackMapper zccDebtpackMapper;

  @Autowired
  AmcDebtContactorMapper amcDebtContactorMapper;


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
    String amcDebtCode = "-1";
    if(debtPackId != null && debtPackId > 0){
      amcDebtCode = String.format("wensheng-%s-%s%s", areaMapper.get(debtPackId),
              AmcDateUtils.getCurrentYear(), debtId);
    }

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

  public void patchAmcDebtContactor() {
    List<AmcDebt> amcDebts = amcDebtMapper.selectByExample(null);
    for(AmcDebt amcDebt: amcDebts){
      if(amcDebt.getAmcContactorName() == null || amcDebt.getAmcContactorPhone() == null||
          amcDebt.getAmcContactorName().equals("-1")||amcDebt.getAmcContactorPhone().equals("-1")){
        if(amcDebt.getAmcContactorId() !=null && amcDebt.getAmcContactorId() != -1){
          //can patch
          AmcDebtContactor amcDebtContactor = amcDebtContactorMapper.selectByPrimaryKey(amcDebt.getAmcContactorId());
          if(amcDebtContactor != null){
            amcDebt.setAmcContactorName(amcDebtContactor.getName());
            amcDebt.setAmcContactorPhone(amcDebtContactor.getPhoneNumber());
            amcDebtMapper.updateByPrimaryKeySelective(amcDebt);
            patchAmcAssets(amcDebt.getId(), amcDebtContactor);
          }
          else{
            log.error("Failed to patch debt with id:{} anc amcContactId:{}", amcDebt.getId(), amcDebt.getAmcContactorId());
          }
        }
      }
    }
    List<AmcAsset> amcAssets = amcAssetMapper.selectByExample(null);
    for(AmcAsset amcAsset: amcAssets){
      if(amcAsset.getAmcContactorName() == null || amcAsset.getAmcContactorPhone() == null || amcAsset.getAmcContactorPhone().equals("-1")
          || amcAsset.getAmcContactorName().equals("-1")){
          AmcDebtContactor amcDebtContactor = getContactorByDebtId(amcAsset.getDebtId());
          if(amcDebtContactor == null){
            log.error("Failed to patch AmcAsset with id:{}", amcAsset.getId());
          }else{
            amcAsset.setAmcContactorName(amcDebtContactor.getName());
            amcAsset.setAmcContactorPhone(amcDebtContactor.getPhoneNumber());
            amcAssetMapper.updateByPrimaryKeySelective(amcAsset);
          }
      }
    }
  }

  private AmcDebtContactor getContactorByDebtId(Long debtId){
    AmcDebt amcDebt = amcDebtMapper.selectByPrimaryKey(debtId);
    if(amcDebt != null && amcDebt.getAmcContactorId() != null && amcDebt.getAmcContactorId() > 0){
      AmcDebtContactor amcDebtContactor = amcDebtContactorMapper.selectByPrimaryKey(amcDebt.getAmcContactorId());
      return amcDebtContactor;
    }
    return null;
  }

  private void patchAmcAssets(Long id, AmcDebtContactor amcDebtContactor) {
    AmcAssetExample amcAssetExample = new AmcAssetExample();
    amcAssetExample.createCriteria().andDebtIdEqualTo(id);
    List<AmcAsset> amcAssets = amcAssetMapper.selectByExample(amcAssetExample);
    for(AmcAsset amcAsset: amcAssets){
      if(amcAsset.getAmcContactorName() == null || amcAsset.getAmcContactorPhone() == null ||
          amcAsset.getAmcContactorName().equals("-1") || amcAsset.getAmcContactorPhone().equals("-1")){
        amcAsset.setAmcContactorName(amcDebtContactor.getName());
        amcAsset.setAmcContactorPhone(amcDebtContactor.getPhoneNumber());
        amcAssetMapper.updateByPrimaryKeySelective(amcAsset);
      }
    }

  }

}
