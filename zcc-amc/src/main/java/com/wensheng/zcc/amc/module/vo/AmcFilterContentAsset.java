package com.wensheng.zcc.amc.module.vo;

import com.wensheng.zcc.common.aop.SQLInjectionSafe;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class AmcFilterContentAsset extends AmcFilterContentItem implements Serializable {
  private static final long serialVersionUID = 1L;

//  List<Long> assetIds = null;
  List<Long> area = null;
  List<Long> landArea = null;
  String locationCode = null;
  List<Long> valuation = null;
  List<Integer> sealStatus = null;
  List<Integer> assetTypes = null;
  List<String> amcContactorNames = null;
  int orderByField = -1;

}
