package com.wensheng.zcc.amc.module.vo;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcSaleBanner;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcSaleMenu;
import java.util.List;
import lombok.Data;

@Data
public class AmcSaleHomePage {
  List<AmcSaleFloorFrontEndVo> amcSaleFloorVoList;
  List<AmcSaleBanner> amcSaleBannerList;
  List<AmcSaleMenu> amcSaleMenuList;

}
