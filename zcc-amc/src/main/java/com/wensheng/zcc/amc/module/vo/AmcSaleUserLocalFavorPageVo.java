package com.wensheng.zcc.amc.module.vo;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcSaleBanner;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcSaleFloor;
import com.wensheng.zcc.common.module.dto.AmcSaleFilter;
import com.wensheng.zcc.common.module.dto.WXUserFavor;
import java.util.List;
import lombok.Data;

@Data
public class AmcSaleUserLocalFavorPageVo {
  WXUserFavor wxUserFavor;
  AmcSaleFloor amcSaleFloor;
  AmcSaleFilter amcSaleFilter;
  List<Object> assetList;
  List<Object> debtList;
}
