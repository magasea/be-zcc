package com.wensheng.zcc.amc.module.vo;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcSaleFloor;
import com.wensheng.zcc.common.module.dto.AmcSaleFilter;
import com.wensheng.zcc.common.module.dto.WXUserFavor;
import java.util.List;
import lombok.Data;

@Data
public class AmcSaleFloorUserFavorPageVo {
  AmcSaleFloor amcSaleFloor;

  WXUserFavor wxUserFavor;
  List<Object> resultList;
}
