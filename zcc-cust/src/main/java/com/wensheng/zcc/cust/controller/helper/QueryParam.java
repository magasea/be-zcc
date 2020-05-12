package com.wensheng.zcc.cust.controller.helper;

import com.wensheng.zcc.common.aop.SQLInjectionSafe;
import com.wensheng.zcc.common.params.PageInfo;
import java.util.List;
import lombok.Data;

@Data
public class QueryParam {

  int custType;
  String city;
  List<String> custCity;
  String name;
  Integer itemType = -1;
  Integer trdType = -1;
  List<Integer> investDebtScales;
  List<Integer> investTrdScales;
  PageInfo pageInfo;
  int exportSize = -1;
  boolean allowNoTrd = false;
  //只适用于最近更改的公司信息查询
  String latestStartDay;
  String latestEndDay;


}
