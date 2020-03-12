package com.wensheng.zcc.common.utils.sso;

import com.wensheng.zcc.common.params.PageInfo;
import lombok.Data;
@Data
public class SSOQueryParam {
  int deptId;
  int location = -1;
  String name;
  String mobilePhone;
  PageInfo pageInfo;
}
