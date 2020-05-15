package com.wensheng.zcc.amc.module.vo;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcSaleMenu;
import com.wensheng.zcc.common.module.dto.AmcSaleFilter;
import lombok.Data;

@Data
public class AmcSaleMenuVo extends AmcSaleMenu {


  AmcSaleFilter amcSaleFilter;


}
