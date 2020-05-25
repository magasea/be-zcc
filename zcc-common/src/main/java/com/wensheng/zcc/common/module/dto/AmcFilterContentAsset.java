package com.wensheng.zcc.common.module.dto;

import com.wensheng.zcc.common.aop.SQLInjectionSafe;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class AmcFilterContentAsset extends AmcFilterContentItem implements Serializable {
  private static final long serialVersionUID = 1L;

//  List<Long> assetIds = null;
//  建筑面积
  List<Long> area = null;
//  土地面积
  List<Long> landArea = null;
//  城市列
  List<String> cityCode = null;
//  省份列
  List<String> provCode = null;
//  评估价
  List<Long> valuation = null;
//  查封状态
  List<Integer> sealStatus = null;
//  资产类型
  List<Integer> assetTypes = null;
//  联系人名字列
  List<String> amcContactorNames = null;
//  排序选项 图片 优先或者访问量优先
  int orderByField = -1;

}
