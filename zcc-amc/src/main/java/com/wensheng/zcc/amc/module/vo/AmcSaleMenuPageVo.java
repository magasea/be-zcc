package com.wensheng.zcc.amc.module.vo;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcSaleFloor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcSaleMenu;
import java.util.List;
import lombok.Data;

@Data
public class AmcSaleMenuPageVo {
  AmcSaleMenu amcSaleMenu;

  AmcSaleFilter amcSaleFilter;
  List<Object> resultList;
}
