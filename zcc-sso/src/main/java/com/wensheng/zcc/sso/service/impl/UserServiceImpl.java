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
import com.wensheng.zcc.sso.service.util.ExceptionUtils;
import com.wensheng.zcc.sso.service.util.ExceptionUtils.AmcExceptions;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * @author chenwei on 1/30/19
 * @project zcc-backend
 */
@Service("userService")
public class UserServiceImpl implements UserService {

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
    AmcUserExample amcUserExample = new AmcUserExample();
    amcUserExample.createCriteria().andMobilePhoneEqualTo(phoneNumber);
    List<AmcUser> amcUsers = amcUserMapper.selectByExample(amcUserExample);
    if(CollectionUtils.isEmpty(amcUsers)){
      throw new UsernameNotFoundException(AmcExceptions.NO_SUCHUSER.toString());
    }
    List<String> authorities = getPermissions(amcUsers.get(0));

    List<GrantedAuthority> grantedAuthorityAuthorities = new ArrayList<>();
    authorities.forEach( auth -> grantedAuthorityAuthorities.add(new SimpleGrantedAuthority(auth)));
    UserDetails userDetails = new User(amcUsers.get(0).getMobilePhone(), amcUsers.get(0).getPassword(), grantedAuthorityAuthorities);
    return userDetails;
  }
  @Override
  public List<String> getPermissions(AmcUser amcUser){

    AmcUserExt amcUserExt = amcUserExtMapper.selectByExtExample(amcUser.getId());
    List<String> authorities = new ArrayList<>();

    for(AmcPermission amcPermission: amcUserExt.getAmcPermissions()){
      authorities.add(amcPermission.getName());
    }
    for(AmcRole amcRole: amcUserExt.getAmcRoles()){
      authorities.add(amcRole.getName());
    }
    return authorities;

  }
}
