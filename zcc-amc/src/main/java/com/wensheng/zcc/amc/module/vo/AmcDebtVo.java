package com.wensheng.zcc.amc.module.vo;

import com.wensheng.zcc.amc.module.dao.mongo.entity.DebtImage;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcPerson;
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

  private BigDecimal estimatedPrice;

  private AmcPerson amcContact1;

  private AmcPerson amcContact2;

  private Integer isRecommanded;

  private Date startDate;

  private Date endDate;

  List<AmcAssetVo> assetVos;

  DebtImage debtImage;

}
