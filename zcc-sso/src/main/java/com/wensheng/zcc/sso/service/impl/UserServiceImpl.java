package com.wensheng.zcc.sso.service.impl;

import com.google.common.collect.Lists;
import com.wensheng.zcc.sso.dao.mysql.mapper.AmcPermissionMapper;
import com.wensheng.zcc.sso.dao.mysql.mapper.AmcRolePermissionMapper;
import com.wensheng.zcc.sso.dao.mysql.mapper.AmcUserMapper;
import com.wensheng.zcc.sso.dao.mysql.mapper.AmcUserRoleMapper;
import com.wensheng.zcc.sso.dao.mysql.mapper.ext.AmcUserExtMapper;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcPermission;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcRole;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcRolePermission;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcUser;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcUserExample;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcUserRole;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.ext.AmcUserExt;
import com.wensheng.zcc.sso.service.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author chenwei on 1/30/19
 * @project zcc-backend
 */
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

  @Autowired
  AmcUserMapper amcUserMapper;

  @Autowired
  AmcPermissionMapper amcPermissionMapper;

  @Autowired
  AmcRolePermissionMapper amcRolePermissionMapper;

  @Autowired
  AmcUserRoleMapper amcUserRoleMapper;

  @Autowired
  AmcUserExtMapper amcUserExtMapper;


  @Override
  public List<AmcUser> getUserByPhone(String phoneNum) {
    AmcUserExample amcUserExample = new AmcUserExample();
    amcUserExample.createCriteria().andMobilePhoneEqualTo(phoneNum);
    return amcUserMapper.selectByExample(amcUserExample);
  }

  @Override
  public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {

    return null;
  }
  @Override
  public List<String> getPermissions(AmcUser amcUser){

    AmcUserExt amcUserExt = amcUserExtMapper.selectByExtExample(amcUser.getId());
    List<String> authorities = new ArrayList<>();
    for(AmcRole amcRole: amcUserExt.getAmcRoles()){
      authorities.add(amcRole.getName());
    }
    for(AmcPermission amcPermission: amcUserExt.getAmcPermissions()){
      authorities.add(amcPermission.getName());
    }
    return authorities;

  }
}
