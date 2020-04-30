package com.wensheng.zcc.amc.module.vo;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class AmcSaleRecommAssets extends AmcFilterContentItem implements Serializable {
  private static final long serialVersionUID = 1L;

  List<Long> assetIds = null;


}
