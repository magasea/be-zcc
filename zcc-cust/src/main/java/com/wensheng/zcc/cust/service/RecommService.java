package com.wensheng.zcc.cust.service;

import com.wensheng.zcc.cust.module.vo.recom.Cust4Asset;
import com.wensheng.zcc.cust.module.vo.recom.Cust4Debt;

public interface RecommService {
  Cust4Debt queryDebtCusts(Long debtId);
  Cust4Asset queryAssetCusts(Long assetId);

}
