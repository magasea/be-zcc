package com.wensheng.zcc.sso.service;

import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcRole;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcRolePermission;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcUser;
import java.util.List;

/**
 * @author chenwei on 3/14/19
 * @project zcc-backend
 */
public interface AmcUserService {

  public void modifyUserRole(Long userId, List<Long> roleIds);

  AmcUser createUser(AmcUser amcUser);

  List<AmcRole> getAmcRoles();

  List<AmcRolePermission> getAmcRolePerms();
}
