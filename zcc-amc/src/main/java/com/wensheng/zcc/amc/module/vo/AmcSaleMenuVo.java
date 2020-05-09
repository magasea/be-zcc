package com.wensheng.zcc.amc.module.vo;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcSaleFloor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcSaleMenu;
import lombok.Data;

@Data
public class AmcSaleMenuVo extends AmcSaleMenu {


  AmcSaleFilter amcSaleFilter;


}
