package com.wensheng.zcc.common.utils.sso;

import com.wensheng.zcc.common.params.PageInfo;
import lombok.Data;
@Data
public class SSOQueryParam {
  int deptId;
  int location;
  String name;
  String mobilePhone;
  PageInfo pageInfo;
}
