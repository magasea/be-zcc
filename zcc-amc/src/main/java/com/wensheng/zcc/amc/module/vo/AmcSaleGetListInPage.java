package com.wensheng.zcc.amc.module.vo;

import java.util.List;
import lombok.Data;

@Data
public class AmcSaleGetListInPage {
  Integer pgSize = 6;
  Integer total = 0;
  Long lastAssetId = -1L;
  Long lastDebtId = -1L;

}
