package com.wensheng.zcc.amc.service.impl.helper;

import com.wensheng.zcc.amc.module.dao.helper.AreaUnitEnum;
import com.wensheng.zcc.amc.module.dao.mongo.entity.AssetAdditional;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcAsset;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebt;
import com.wensheng.zcc.amc.module.vo.AmcAssetVo;
import com.wensheng.zcc.amc.module.vo.AmcDebtVo;
import com.wensheng.zcc.amc.utils.AmcNumberUtils;
import com.wensheng.zcc.amc.utils.ExceptionUtils;
import com.wensheng.zcc.amc.utils.ExceptionUtils.AmcExceptions;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.CollectionUtils;

/**
 * @author chenwei on 1/10/19
 * @project zcc-backend
 */
@Slf4j
public class Dao2VoUtils {




  public static AmcAssetVo convertDo2Vo(AmcAsset amcAsset, MongoTemplate wszccTemplate) throws Exception {



    AmcAssetVo amcAssetVo = new AmcAssetVo();
    BeanUtils.copyProperties(amcAsset, amcAssetVo);
    if(amcAsset.getValuation() != null && amcAsset.getValuation() > 0){
      amcAssetVo.setValuation(AmcNumberUtils.getDecimalFromLongDiv100(amcAsset.getValuation()));
    }


    if(amcAsset.getLandArea() != null && amcAsset.getLandArea() > 0){
      amcAssetVo.setLandArea(AmcNumberUtils.getDecimalFromLongDiv100(amcAsset.getLandArea()));
      if(amcAsset.getLandAreaUnit() == null || AreaUnitEnum.lookupByDisplayTypeUtil(amcAsset.getLandAreaUnit()) != null){
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
      }
    }
    if(amcAsset.getArea()  != null && amcAsset.getArea() > 0){
      amcAssetVo.setArea(AmcNumberUtils.getDecimalFromLongDiv100(amcAsset.getArea()));
    }

    Query query = new Query();
    query.addCriteria(Criteria.where("amcAssetId").is(amcAsset.getId()));
    List<AssetAdditional> assetAdditionals = wszccTemplate.find(query, AssetAdditional.class);
    if(!CollectionUtils.isEmpty(assetAdditionals)){
      amcAssetVo.setAssetAdditional(assetAdditionals.get(0));
    }
    return amcAssetVo;


  }
  public static List<AmcAssetVo> convertDoList2VoList(List<AmcAsset> amcAssets, MongoTemplate wszccTemplate) throws Exception {
    List<AmcAssetVo> amcAssetVos = new ArrayList<>();
    for(AmcAsset amcAsset: amcAssets){
      amcAssetVos.add(convertDo2Vo(amcAsset, wszccTemplate));
    }
    return amcAssetVos;
  }

  public static AmcDebtVo convertDo2Vo(AmcDebt amcDebt) {
    AmcDebtVo amcDebtVo = new AmcDebtVo();
    BeanUtils.copyProperties(amcDebt, amcDebtVo);
    if(amcDebt.getTotalAmount() > 0 ){
      amcDebtVo.setTotalAmount(AmcNumberUtils.getDecimalFromLongDiv100(amcDebt.getTotalAmount()));
    }
    if(amcDebt.getValuation() > 0 ){
      amcDebtVo.setValuation(AmcNumberUtils.getDecimalFromLongDiv100(amcDebt.getValuation()));
    }
    if(amcDebt.getBaseAmount() > 0 ){
      amcDebtVo.setBaseAmount(AmcNumberUtils.getDecimalFromLongDiv100(amcDebt.getBaseAmount()));
    }
    return amcDebtVo;

  }
}
