package com.wensheng.zcc.amc.module.vo;

import com.wensheng.zcc.amc.module.dao.mongo.entity.DebtAdditional;
import com.wensheng.zcc.amc.module.dao.mongo.entity.DebtImage;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcCreditor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcGrntor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtContactor;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
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

  private String zccDebtCode;

  private Integer publishState;

  private Date publishDate;

  private Integer lawsuitState;

  private String lawsuitStateDesc;

  private BigDecimal valuation;


  private String amcContactorName;

  private String amcContactorPhone;

  private Integer isRecommanded;

  private Integer guarantType;

  private Date recommStartDate;

  private Date recommEndDate;

  private Long origCreditorId;

  private String briefDesc;
  private Long updateBy;

  private Date updateDate;

  private Long createdBy;

  private Date createdDate;
  List<AmcAssetVo> assetVos;

  private DebtAdditional debtAdditional;

  DebtImage debtImage;



}
