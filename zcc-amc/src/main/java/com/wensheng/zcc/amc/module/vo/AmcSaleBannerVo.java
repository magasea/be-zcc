package com.wensheng.zcc.amc.module.vo;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcSaleBanner;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcSaleMenu;
import lombok.Data;

@Data
public class AmcSaleBannerVo extends AmcSaleBanner {


  AmcSaleFilter amcSaleFilter;


}
