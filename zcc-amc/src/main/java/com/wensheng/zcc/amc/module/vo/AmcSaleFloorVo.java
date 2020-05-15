package com.wensheng.zcc.amc.module.vo;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcSaleFloor;
import com.wensheng.zcc.common.module.dto.AmcSaleFilter;
import lombok.Data;

@Data
public class AmcSaleFloorVo {
  AmcSaleFloor amcSaleFloor;

  AmcSaleFilter amcSaleFilter;
  AmcSaleRecomItems amcRecommItem;

}
