package com.wensheng.zcc.sso.module.vo;

import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class RolePermVo {
  Map<Long, List<Long>> rolePerms;

}
