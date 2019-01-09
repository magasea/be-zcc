package com.wensheng.zcc.amc.module.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
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

  private Long originalCreditor;

  private String notes;

  private Long amcCompanyId;

  private Integer packStatus;

  private List<Long> creditors;

}
