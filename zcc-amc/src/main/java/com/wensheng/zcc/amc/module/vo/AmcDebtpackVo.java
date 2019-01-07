package com.wensheng.zcc.amc.module.vo;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @author chenwei on 1/7/19
 * @project zcc-backend
 */
@Data
public class AmcDebtpackVo {
  private Long id;

  private String title;

  private BigDecimal baseAmount;

  private BigDecimal totalAmount;

  private Date settleDate;

  private String creditor;

  private String creditorBranch;

  private String originalCreditor;

  private String notes;

  private Long amcCompanyId;

  private Integer packStatus;

}
