package com.wensheng.zcc.amc.module.vo;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcSaleMenu;
import com.wensheng.zcc.common.module.dto.AmcSaleFilter;
import java.util.List;
import lombok.Data;

@Data
public class AmcSaleMenuPageVo {
  AmcSaleMenu amcSaleMenu;

  AmcSaleFilter amcSaleFilter;
  List<Object> resultList;
}
