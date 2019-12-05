package com.wensheng.zcc.sso.service;

import java.util.List;

public interface AmcUserRoleRuleService {
  List<Integer> getRolesByDeptAndTitle(int deptId, int titleId);
}
