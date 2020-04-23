package com.wensheng.zcc.amc.module.vo;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcSaleFloor;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class AmcSaleFloorVo {
  AmcSaleFloor amcSaleFloor;

  AmcSaleFilter amcSaleFilter;
  AmcSaleFilter amcRecommItem;

}
