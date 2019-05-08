package com.wensheng.zcc.cust.controller.helper;

import com.wensheng.zcc.common.aop.SQLInjectionSafe;
import com.wensheng.zcc.common.params.PageInfo;
import java.util.List;
import lombok.Data;

@Data
public class QueryParam {

  int custType;
  String city;
  String name;
  PageInfo pageInfo;



}
