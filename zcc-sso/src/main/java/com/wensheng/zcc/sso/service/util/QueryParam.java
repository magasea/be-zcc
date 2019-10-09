package com.wensheng.zcc.sso.service.util;

import com.wensheng.zcc.common.params.PageInfo;
import lombok.Data;

@Data
public class QueryParam {

  int deptId;
  int location;
  String name;
  String mobilePhone;
  PageInfo pageInfo;



}
