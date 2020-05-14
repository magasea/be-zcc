package com.wensheng.zcc.amc.module.vo;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcSaleFloor;
import com.wensheng.zcc.common.module.dto.AmcSaleFilter;
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
