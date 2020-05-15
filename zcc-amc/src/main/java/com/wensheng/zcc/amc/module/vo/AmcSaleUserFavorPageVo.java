package com.wensheng.zcc.amc.module.vo;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcSaleBanner;
import com.wensheng.zcc.common.module.dto.AmcSaleFilter;
import com.wensheng.zcc.common.module.dto.WXUserFavor;
import java.util.List;
import lombok.Data;

@Data
public class AmcSaleUserFavorPageVo {
  WXUserFavor wxUserFavor;

  AmcSaleFilter amcSaleFilter;
  List<Object> resultList;
}
