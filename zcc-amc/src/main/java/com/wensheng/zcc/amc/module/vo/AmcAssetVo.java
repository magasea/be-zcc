package com.wensheng.zcc.amc.module.vo;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @author chenwei on 1/7/19
 * @project zcc-backend
 */
@Data
public class AmcAssetVo {
  private Long id;

  private String title;

  private Integer type;

  private Integer status;

  private Integer state;

  private Integer editStatus;

  private Long amcId;

  private String amcAssetCode;

  private String zccAssetCode;

  private Short isMainAsset;

  private BigDecimal estmPrice;

  private Long debtId;

  private BigDecimal initPrice;

  private Integer restrictions;

  private Long area;

  private Long landArea;

  private Date publishDate;

  private String province;

  private String city;

  private String county;

  private String street;

  private String buildingName;

  private String gpsLng;

  private String gpsLat;

  private Long createBy;

  private Long amcConactor;
}