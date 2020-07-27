package com.wensheng.zcc.amc.module.vo;

import com.wensheng.zcc.amc.module.dao.mongo.entity.DebtAdditional;
import com.wensheng.zcc.amc.module.dao.mongo.entity.DebtImage;
import com.wensheng.zcc.common.params.sso.SSOAmcUser;
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

  private Integer debtType;

  private Long amcId;

  private String title;

  private BigDecimal baseAmount;

  private String baseAmountDesc;

  private Date baseDate;

  private BigDecimal totalAmount;

  private String totalAmountDesc;

  private BigDecimal interestAmount;

  private Date settleDate;

  private Long courtId;

  private String courtName;

  private String zccDebtCode;

  private Integer publishState;

  private Date publishDate;

  private Integer lawsuitState;

  private String lawsuitStateDesc;

  private BigDecimal valuation;

  private Long curtProv;

  private Long curtCity;

  private Long curtCounty;

  private Long amcContactorId;

  private String amcContactorName;

  private String amcContactorPhone;

  private Integer amcContactorSex;

  private Integer isRecommanded;

  private Integer guarantType;

  private Date recommStartDate;

  private Date recommEndDate;

  private Long origCreditorId;

  private String briefDesc;

  private Long visitCount;

  private Long updateBy;

  private Date updateDate;

  private Long createdBy;

  private Date createdDate;
  List<AmcAssetVo> assetVos;

  private DebtAdditional debtAdditional;
  SSOAmcUser debtContactor;

  DebtImage debtImage;



}
