package com.wensheng.zcc.amc.module.vo;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcPerson;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import lombok.Data;

/**
 * @author chenwei on 1/10/19
 * @project zcc-backend
 */
@Data
public class AmcDebtCreateVo {

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

  private String lawStatDesc;

  private Long estimatedPrice;

  private Long amcContact1;

  private Long amcContact2;

  private Integer isRecommanded;

  private Date startDate;

  private Date endDate;

  private List<Long> creditors;



}
