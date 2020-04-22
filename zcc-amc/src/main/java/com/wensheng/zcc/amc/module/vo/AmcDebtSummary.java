package com.wensheng.zcc.amc.module.vo;

import java.math.BigDecimal;
import lombok.Data;

/**
 * @author chenwei on 1/4/19
 * @project zcc-backend
 */
@Data
public class AmcDebtSummary {

  private Long debtTotalCount;

  private BigDecimal debtTotalAmount;

  private Long assetTotalCount;

  private Long debtPublishedCount;

  private Long assetPublishedCount;


}
