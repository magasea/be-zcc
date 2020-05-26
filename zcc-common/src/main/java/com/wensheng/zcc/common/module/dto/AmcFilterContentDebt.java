package com.wensheng.zcc.common.module.dto;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class AmcFilterContentDebt extends AmcFilterContentItem implements Serializable {
  private static final long serialVersionUID = 1L;

//  List<Long> debtIds = null;
//  法院所在城市列
  List<String> courtCities = null;
//  法院所在省列
  List<String> courtProvs = null;
//  本金范围
  List<Long> baseAmount = null;
//  联系人名字列
  List<String> amcContactorNames = null;
//  法院id 列
  List<Long> courtIds = null;
//  担保类型列
  List<Integer> guarantType = null;
//  债权类型列
  List<Integer> debtType = null;
//  排序选项 图片优先 或者 访问量优先
  int orderByField = -1;

}
