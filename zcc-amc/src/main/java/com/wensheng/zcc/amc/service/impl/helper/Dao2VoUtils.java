package com.wensheng.zcc.amc.service.impl.helper;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcAsset;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebt;
import com.wensheng.zcc.amc.module.vo.AmcAssetVo;
import com.wensheng.zcc.amc.module.vo.AmcDebtVo;
import com.wensheng.zcc.amc.utils.AmcNumberUtils;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;

/**
 * @author chenwei on 1/10/19
 * @project zcc-backend
 */
public class Dao2VoUtils {
  public static AmcAssetVo convertDo2Vo(AmcAsset amcAsset){
    AmcAssetVo amcAssetVo = new AmcAssetVo();
    BeanUtils.copyProperties(amcAsset, amcAssetVo);
    if(amcAsset.getEstmPrice() > 0){
      amcAssetVo.setEstmPrice(AmcNumberUtils.getDecimalFromLongDiv100(amcAsset.getEstmPrice()));
    }
    if(amcAsset.getInitPrice() > 0){
      amcAssetVo.setInitPrice(AmcNumberUtils.getDecimalFromLongDiv100(amcAsset.getInitPrice()));
    }
    if(amcAsset.getEstmPrice() > 0){
      amcAssetVo.setEstmPrice(AmcNumberUtils.getDecimalFromLongDiv100(amcAsset.getEstmPrice()));
    }
    if(amcAsset.getLandArea() > 0){
      amcAssetVo.setLandArea(AmcNumberUtils.getDecimalFromLongDiv100(amcAsset.getLandArea()));
    }

    if(amcAsset.getArea() > 0){
      amcAssetVo.setArea(AmcNumberUtils.getDecimalFromLongDiv100(amcAsset.getArea()));
    }
    return amcAssetVo;
  }
  public static List<AmcAssetVo> convertDoList2VoList(List<AmcAsset> amcAssets){
    List<AmcAssetVo> amcAssetVos = new ArrayList<>();
    for(AmcAsset amcAsset: amcAssets){
      amcAssetVos.add(convertDo2Vo(amcAsset));
    }
    return amcAssetVos;
  }

  public static AmcDebtVo convertDo2Vo(AmcDebt amcDebt) {
    AmcDebtVo amcDebtVo = new AmcDebtVo();
    BeanUtils.copyProperties(amcDebt, amcDebtVo);
    if(amcDebt.getTotalAmount() > 0 ){
      amcDebtVo.setTotalAmount(AmcNumberUtils.getDecimalFromLongDiv100(amcDebt.getTotalAmount()));
    }
    if(amcDebt.getEstimatedPrice() > 0 ){
      amcDebtVo.setEstimatedPrice(AmcNumberUtils.getDecimalFromLongDiv100(amcDebt.getEstimatedPrice()));
    }
    if(amcDebt.getBaseAmount() > 0 ){
      amcDebtVo.setBaseAmount(AmcNumberUtils.getDecimalFromLongDiv100(amcDebt.getBaseAmount()));
    }
    return amcDebtVo;

  }
}
