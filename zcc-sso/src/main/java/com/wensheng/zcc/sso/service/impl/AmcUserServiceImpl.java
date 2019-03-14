package com.wensheng.zcc.sso.service.impl;

import com.wensheng.zcc.sso.dao.mysql.mapper.AmcUserMapper;
import com.wensheng.zcc.sso.dao.mysql.mapper.AmcUserRoleMapper;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcRole;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcUserRole;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcUserRoleExample;
import com.wensheng.zcc.sso.service.AmcUserService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author chenwei on 3/14/19
 * @project zcc-backend
 */
@Service
public class AmcUserServiceImpl implements AmcUserService {

  @Autowired
  AmcUserMapper amcUserMapper;

  @Autowired
  AmcUserRoleMapper amcUserRoleMapper;

  @Override
  public void modifyUserRole(Long userId, List<Long> roleIds) {
    AmcUserRoleExample amcUserRoleExample = new AmcUserRoleExample();
    amcUserRoleExample.createCriteria().andUserIdEqualTo(userId);
    List<AmcUserRole> amcUserRoles = amcUserRoleMapper.selectByExample(amcUserRoleExample);
    Set<Long> historyRoleIds = new HashSet<>();
    Set<Long> updateRoleIds = new HashSet<>();


  }
}
