package com.wensheng.zcc.amc.module.vo;

import com.wensheng.zcc.common.module.dto.AmcSaleFilter;
import lombok.Data;

@Data
public class AmcSaleGetRandomListByOpenId {
  String openId;
  AmcSaleGetRandomListInPage pageInfo;
  AmcSaleFilter amcSaleFilter;
}
