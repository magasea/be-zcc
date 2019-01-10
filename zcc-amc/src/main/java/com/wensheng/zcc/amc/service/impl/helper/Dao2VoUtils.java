package com.wensheng.zcc.amc.service.impl.helper;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcAsset;
import com.wensheng.zcc.amc.module.vo.AmcAssetVo;
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
    if(amcAsset.getEstmPrice() != null){
      amcAssetVo.setEstmPrice(AmcNumberUtils.getDecimalFromLongDiv100(amcAsset.getEstmPrice()));
    }
    if(amcAsset.getInitPrice() != null){
      amcAssetVo.setInitPrice(AmcNumberUtils.getDecimalFromLongDiv100(amcAsset.getInitPrice()));
    }
    if(amcAsset.getEstmPrice() != null){
      amcAssetVo.setEstmPrice(AmcNumberUtils.getDecimalFromLongDiv100(amcAsset.getEstmPrice()));
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

}
