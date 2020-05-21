package com.wensheng.zcc.amc.module.vo;

import com.wensheng.zcc.common.module.dto.AmcSaleFilter;
import lombok.Data;

@Data
public class AmcSaleGetListByOpenId{
  String openId;
  AmcSaleGetListInPage pageInfo;
  AmcSaleFilter amcSaleFilter;
}
