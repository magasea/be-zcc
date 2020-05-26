package com.wensheng.zcc.amc.module.vo;

import java.util.List;
import lombok.Data;

@Data
public class AmcSaleGetRandomListInPage {
  Integer pgSize = 6;
  Integer total = 0;
  List<Long> assetIds = null;
  List<Long> debtIds = null;

}
