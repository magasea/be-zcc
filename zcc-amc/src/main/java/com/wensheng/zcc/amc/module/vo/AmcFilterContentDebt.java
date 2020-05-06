package com.wensheng.zcc.amc.module.vo;

import com.wensheng.zcc.amc.module.dao.helper.OrderByFieldEnum;
import com.wensheng.zcc.common.aop.SQLInjectionSafe;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class AmcFilterContentDebt extends AmcFilterContentItem implements Serializable {
  private static final long serialVersionUID = 1L;

//  List<Long> debtIds = null;
  String[] courtLocations = null;
  List<Long> baseAmount = null;
  List<String> amcContactorNames = null;
  List<Long> courtIds = null;
  int guarantType = -1;
  int orderByField = -1;

}
