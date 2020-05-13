package com.wensheng.zcc.amc.module.vo;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcSaleFloor;
import java.util.List;
import lombok.Data;

@Data
public class AmcSalePageModVo {
  Long id;

  AmcSaleFilter amcSaleFilter;

}
