package com.wensheng.zcc.amc.module.vo;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcSaleFloor;
import java.util.List;
import lombok.Data;

@Data
public class AmcSaleFloorFrontEndVo {
  AmcSaleFloor amcSaleFloor;

  AmcSaleFilter amcSaleFilter;
  AmcSaleRecomItems amcRecommItem;
  List<AmcDebtVo> amcDebtVos;
  List<AmcAssetVo> amcAssetVos;

}
