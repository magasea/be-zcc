package com.wensheng.zcc.amc.module.vo;

import com.wensheng.zcc.amc.module.dao.mongo.entity.DebtAdditional;
import io.swagger.models.auth.In;
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

  private Integer publishState;

  private Date publishDate;

  private Integer lawsuitState;

  private String lawsuitStateDesc;

  private BigDecimal valuation;

  private Long amcContactorId;

  private Long amcContactor2Id;

  private Integer isRecommanded;

  private Integer guarantType;

  private Date recommStartDate;

  private Date recommEndDate;

  private Long origCreditorId;

  private String debtDesc;

  private Long createdBy;

  private Date createdDate;


  private List<Long> debtors;

  private DebtAdditional debtAdditional;



}
