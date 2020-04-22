package com.wensheng.zcc.amc.module.vo;

import com.wensheng.zcc.amc.module.dao.mongo.entity.AssetAdditional;
import com.wensheng.zcc.amc.module.dao.mongo.entity.AssetImage;
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

  private Integer sealedState;

  private Integer assetNature;

  private Integer publishState;

  private Long amcId;

  private String amcAssetCode;

  private String zccAssetCode;

  private BigDecimal totalValuation;

  private Long debtId;

  private Integer isRecom;

  private BigDecimal startPrice;

  private BigDecimal buildingArea;

  private BigDecimal buildingUnitPrice;

  private BigDecimal landArea;

  private Integer landAreaUnit;

  private BigDecimal landUnitPrice;

  private Date publishDate;

  private String province;

  private String city;

  private String county;

  private String address;

  private String buildingName;

  private String gpsLng;

  private String gpsLat;

  private Long createdBy;

  private Date createdDate;

  private Long updateBy;

  private Date updateDate;

  private String amcContactorName;

  private String amcContactorPhone;

  private String note;

//  private AmcDebtContactor amcContactorId;

  AssetAdditional assetAdditional;

  AssetImage assetImage;

}
