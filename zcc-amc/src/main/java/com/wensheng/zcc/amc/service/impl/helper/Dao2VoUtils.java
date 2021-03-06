package com.wensheng.zcc.amc.service.impl.helper;

import com.wensheng.zcc.amc.module.dao.helper.AreaUnitEnum;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcAsset;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebt;
import com.wensheng.zcc.amc.module.vo.AmcAssetVo;
import com.wensheng.zcc.amc.module.vo.AmcDebtVo;
import com.wensheng.zcc.common.utils.AmcBeanUtils;
import com.wensheng.zcc.common.utils.AmcNumberUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils;
import com.wensheng.zcc.common.utils.AmcExceptions;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * @author chenwei on 1/10/19
 * @project zcc-backend
 */
@Slf4j
public class Dao2VoUtils {




  public static AmcAssetVo convertDo2Vo(AmcAsset amcAsset) throws Exception {



    AmcAssetVo amcAssetVo = new AmcAssetVo();
    AmcBeanUtils.copyProperties(amcAsset, amcAssetVo);
//    amcAssetVo.setAmcContactorId(new AmcDebtContactor());
//    amcAssetVo.getAmcContactorId().setId(amcAsset.getAmcContactorId());
    if(amcAsset.getTotalValuation() != null && amcAsset.getTotalValuation() > 0){
      amcAssetVo.setTotalValuation(AmcNumberUtils.getDecimalFromLongDiv100(amcAsset.getTotalValuation()));
    }


    if(amcAsset.getLandArea() != null && amcAsset.getLandArea() > 0){
      amcAssetVo.setLandArea(AmcNumberUtils.getDecimalFromLongDiv100(amcAsset.getLandArea()));
      if(amcAsset.getLandAreaUnit() != null || AreaUnitEnum.lookupByDisplayTypeUtil(amcAsset.getLandAreaUnit()) != null){
        switch (AreaUnitEnum.lookupByDisplayTypeUtil(amcAsset.getLandAreaUnit())){
          case SQUAREMETER:
            amcAssetVo.setLandArea(AmcNumberUtils.getDecimalFromLongDiv100(amcAsset.getLandArea()));

            break;
          case MU:
            amcAssetVo.setLandArea(AmcNumberUtils.getMuFromSQM(amcAsset.getLandArea()));
            break;
          case TENTHOUNDSQUM:
            amcAssetVo.setLandArea(AmcNumberUtils.getBigDecimalFromLongDiv1000000(amcAsset.getLandArea()));
            break;
          default:
            log.error("amcAssetVo.getLandAreaUnit():" + amcAssetVo.getLandAreaUnit());
            throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_LANDAREA_UNIT);

        }
      }else{
        log.error("asset with id:"+amcAsset.getId() +" landAreaUnit is:" + amcAsset.getLandAreaUnit() + " use default"
            + " squremeter to handle it");
        amcAssetVo.setLandArea(AmcNumberUtils.getDecimalFromLongDiv100(amcAsset.getLandArea()));
      }
    }
    if(amcAsset.getBuildingArea()  != null && amcAsset.getBuildingArea() > 0){
      amcAssetVo.setBuildingArea(AmcNumberUtils.getDecimalFromLongDiv100(amcAsset.getBuildingArea()));
    }

    if(amcAsset.getBuildingUnitPrice()  != null && amcAsset.getBuildingUnitPrice() > 0){
      amcAssetVo.setBuildingUnitPrice(AmcNumberUtils.getDecimalFromLongDiv100(amcAsset.getBuildingUnitPrice()));
    }
    if(amcAsset.getLandUnitPrice()  != null && amcAsset.getLandUnitPrice() > 0){
      amcAssetVo.setLandUnitPrice(AmcNumberUtils.getDecimalFromLongDiv100(amcAsset.getLandUnitPrice()));
    }

    return amcAssetVo;


  }
  public static List<AmcAssetVo> convertDoList2VoList(List<AmcAsset> amcAssets) throws Exception {
    List<AmcAssetVo> amcAssetVos = new ArrayList<>();
    for(AmcAsset amcAsset: amcAssets){
      amcAssetVos.add(convertDo2Vo(amcAsset));
    }
    return amcAssetVos;
  }

  public static AmcDebtVo convertDo2Vo(AmcDebt amcDebt) {
    AmcDebtVo amcDebtVo = new AmcDebtVo();
    AmcBeanUtils.copyProperties(amcDebt, amcDebtVo);
    if(amcDebt.getTotalAmount() > 0 ){
      amcDebtVo.setTotalAmount(AmcNumberUtils.getDecimalFromLongDiv100(amcDebt.getTotalAmount()));
    }
    if(amcDebt.getValuation() > 0 ){
      amcDebtVo.setValuation(AmcNumberUtils.getDecimalFromLongDiv100(amcDebt.getValuation()));
    }
    if(amcDebt.getBaseAmount() > 0 ){
      amcDebtVo.setBaseAmount(AmcNumberUtils.getDecimalFromLongDiv100(amcDebt.getBaseAmount()));
    }
    if(amcDebt.getInterestAmount() > 0){
      amcDebtVo.setInterestAmount(AmcNumberUtils.getDecimalFromLongDiv100(amcDebt.getInterestAmount()));
    }
    return amcDebtVo;

  }
}
