package com.wensheng.zcc.amc.module.vo;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import lombok.Data;

/**
 * @author chenwei on 1/4/19
 * @project zcc-backend
 */
@Data
public class AmcDebtVo {

  private Long id;

  private Long debtpackId;

  private Long amcId;

  private String title;

  private BigDecimal baseAmount;

  private String baseAmountDesc;

  private Date baseDate;

  private BigDecimal totalAmount;

  private String totalAmountDesc;

  private Date settleDate;

  private Long courtId;

  private String amcDebtCode;

  private Integer editStatus;

  private Date publishDate;

  private Integer lawStatus;

  private Long estimatedPrice;

  private Long amcContact1;

  private Long amcContact2;

  private Integer isRecommanded;

  private Date startDate;

  private Date endDate;

  private Long origDebtId;


}
