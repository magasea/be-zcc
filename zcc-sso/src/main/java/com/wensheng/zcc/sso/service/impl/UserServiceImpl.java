package com.wensheng.zcc.sso.service.impl;

import com.wensheng.zcc.common.params.AmcPermEnum;
import com.wensheng.zcc.common.params.AmcRolesEnum;
import com.wensheng.zcc.common.utils.AmcBeanUtils;
import com.wensheng.zcc.common.utils.AmcExceptions;
import com.wensheng.zcc.sso.dao.mysql.mapper.AmcPermissionMapper;
import com.wensheng.zcc.sso.dao.mysql.mapper.AmcRoleMapper;
import com.wensheng.zcc.sso.dao.mysql.mapper.AmcRolePermissionMapper;
import com.wensheng.zcc.sso.dao.mysql.mapper.AmcSpecUserMapper;
import com.wensheng.zcc.sso.dao.mysql.mapper.AmcUserMapper;
import com.wensheng.zcc.sso.dao.mysql.mapper.AmcUserRoleMapper;
import com.wensheng.zcc.sso.dao.mysql.mapper.ext.AmcUserExtMapper;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcPermission;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcRole;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcRoleExample;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcSpecUser;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcSpecUserExample;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcUser;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcUserExample;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcUserRole;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.ext.AmcUserExt;
import com.wensheng.zcc.common.params.sso.AmcUserValidEnum;
import com.wensheng.zcc.common.module.amc.vo.AmcUserDetail;
import com.wensheng.zcc.sso.service.UserService;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * @author chenwei on 1/30/19
 * @project zcc-backend
 */
@Service("userService")
@Slf4j
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

  @Autowired
  AmcRoleMapper amcRoleMapper;

  @Autowired
  AmcSpecUserMapper amcSpecUserMapper;

  @Autowired
  PasswordEncoder passwordEncoder;
//
//  @Autowired
//  AuthenticationManager authenticationManagerBean;
//
//  @Autowired
//  DefaultTokenServices tokenServices;
//
//  @Autowired
//  DaoAuthenticationProvider daoAuthenticationProvider;

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
    boolean userEnabled = amcUsers.get(0).getValid().equals(AmcUserValidEnum.VALID.getId());
    AmcUserDetail amcUserDetail = new AmcUserDetail(amcUsers.get(0).getMobilePhone(),"",userEnabled, userEnabled,userEnabled,
        userEnabled,grantedAuthorityAuthorities);
    AmcBeanUtils.copyProperties(amcUsers.get(0), amcUserDetail);

//    UserDetails userDetails =generateTokenByMobilephone
//        User.builder().authorities(grantedAuthorityAuthorities).username(amcUsers.get(0).getMobilePhone()).password(amcUsers.get(0).getPassword()).disabled(!amcUsers.get(0).getValid().equals(
//        AmcUserValidEnum.VALID.getId())).build();
    return amcUserDetail;
  }
  @Override
  @Transactional(isolation = Isolation.READ_COMMITTED)
  public List<String> getPermissions(AmcUser amcUser){

    AmcUserExt amcUserExt = amcUserExtMapper.selectByExtExample(amcUser.getId());
    List<String> authorities = new ArrayList<>();
//    boolean isAmcOper = false;
//    if(amcUser.getCompanyId() != null && amcUser.getCompanyId() > 0){
//      authorities.add(String.format("PERM_%s_READ",amcUser.getCompanyId()));
//      isAmcOper = true;
//    }
//    boolean isCompanyUser = false;
//    if(amcUser.getCompanyId() != null && amcUser.getCompanyId() > 0){
//      isCompanyUser = true;
//    }
    StringBuilder sb = new StringBuilder();
    for(AmcPermission amcPermission: amcUserExt.getAmcPermissions()){
//      sb.setLength(0);
//      if(amcPermission.getName().contains("AMC") && isCompanyUser){
//        sb.append("PERM_").append(amcUser.getCompanyId()).append("_").append(amcPermission.getName().substring("PERM_".length()));
//        authorities.add(sb.toString());
//      }else{
        authorities.add(amcPermission.getName());
//      }
    }
    if(CollectionUtils.isEmpty(authorities)){
      log.error("there is empty collection in authorities, need determine authroity manually");
    }
    for(AmcRole amcRole: amcUserExt.getAmcRoles()){
      authorities.add(amcRole.getName());
//      if(isAmcOper && amcRole.getName().equals(AmcRolesEnum.ROLE_AMC_ADMIN.name())){
//        authorities.add(String.format("PERM_%s_WRITE",amcUser.getCompanyId()));
//      }
    }
    AmcSpecUserExample amcSpecUserExample = new AmcSpecUserExample();
    amcSpecUserExample.createCriteria().andUserIdEqualTo(amcUser.getId());
    List<AmcSpecUser> amcSpecUsers = amcSpecUserMapper.selectByExample(amcSpecUserExample);
    if(!CollectionUtils.isEmpty(amcSpecUsers)){
      for(AmcSpecUser amcSpecUser: amcSpecUsers){
        authorities.add(AmcPermEnum.lookupByDisplayIdUtil(amcSpecUser.getPermId().intValue()).getName());
      }
    }
    return authorities;

  }

  @Override
  public AmcUser createDefaultAmcUser(AmcUser amcUser) {
    amcUser.setPassword(passwordEncoder.encode(amcUser.getPassword()));
    amcUserExtMapper.insertSelective(amcUser);
    Long userId = amcUser.getId();
    AmcUserRole amcUserRole = new AmcUserRole();
    amcUserRole.setCreateBy(userId);
    amcUserRole.setCreateDate(Date.from(Instant.now()));
    amcUserRole.setUserId(userId);
    AmcRoleExample amcRoleExample = new AmcRoleExample();
    amcRoleExample.createCriteria().andNameEqualTo(AmcRolesEnum.ROLE_AMC_USER.name());
    List<AmcRole> amcRoles = amcRoleMapper.selectByExample(amcRoleExample);
    Long roleId = amcRoles.get(0).getId();
    amcUserRole.setRoleId(roleId);

    amcUserRoleMapper.insert(amcUserRole);
    amcUser.setId(userId);
    return amcUser;

  }

  @Override
  public Map<String, String> getTokens(AmcUser amcUser) {
//    authenticationManagerBean;
//    Authentication authentication = new UsernamePasswordAuthenticationToken(amcUser.getMobilePhone(),
//        amcUser.getPassword());
//
//    tokenServices.createAccessToken( authenticationManagerBean.authenticate(authentication) )
    return null;
  }

  @Override
  public UserDetails loadUserByUserId(Long userId) throws UsernameNotFoundException {
    AmcUserExample amcUserExample = new AmcUserExample();
    amcUserExample.createCriteria().andIdEqualTo(userId);
    List<AmcUser> amcUsers = amcUserMapper.selectByExample(amcUserExample);
    if(CollectionUtils.isEmpty(amcUsers)){
      throw new UsernameNotFoundException(AmcExceptions.NO_SUCHUSER.toString());
    }
    List<String> authorities = getPermissions(amcUsers.get(0));

    List<GrantedAuthority> grantedAuthorityAuthorities = new ArrayList<>();
    authorities.forEach( auth -> grantedAuthorityAuthorities.add(new SimpleGrantedAuthority(auth)));
    boolean userEnabled = amcUsers.get(0).getValid().equals(AmcUserValidEnum.VALID.getId());
    AmcUserDetail amcUserDetail = new AmcUserDetail(amcUsers.get(0).getMobilePhone(),"",userEnabled, userEnabled,userEnabled,
        userEnabled,grantedAuthorityAuthorities);
    AmcBeanUtils.copyProperties(amcUsers.get(0), amcUserDetail);

//    UserDetails userDetails =
//        User.builder().authorities(grantedAuthorityAuthorities).username(amcUsers.get(0).getMobilePhone()).password(amcUsers.get(0).getPassword()).disabled(!amcUsers.get(0).getValid().equals(
//        AmcUserValidEnum.VALID.getId())).build();
    return amcUserDetail;
  }

  @Override
  public UserDetails loadUserByUserPhone(String phoneNum) throws UsernameNotFoundException {
    AmcUserExample amcUserExample = new AmcUserExample();
    amcUserExample.createCriteria().andMobilePhoneEqualTo(phoneNum);
    List<AmcUser> amcUsers = amcUserMapper.selectByExample(amcUserExample);
    if(CollectionUtils.isEmpty(amcUsers)){
      throw new UsernameNotFoundException(AmcExceptions.NO_SUCHUSER.toString());
    }
    List<String> authorities = getPermissions(amcUsers.get(0));

    List<GrantedAuthority> grantedAuthorityAuthorities = new ArrayList<>();
    authorities.forEach( auth -> grantedAuthorityAuthorities.add(new SimpleGrantedAuthority(auth)));
    boolean userEnabled = amcUsers.get(0).getValid().equals(AmcUserValidEnum.VALID.getId());
    AmcUserDetail amcUserDetail = new AmcUserDetail(amcUsers.get(0).getMobilePhone(),"",userEnabled, userEnabled,userEnabled,
        userEnabled,grantedAuthorityAuthorities);
    AmcBeanUtils.copyProperties(amcUsers.get(0), amcUserDetail);

//    UserDetails userDetails =
//        User.builder().authorities(grantedAuthorityAuthorities).username(amcUsers.get(0).getMobilePhone()).password(amcUsers.get(0).getPassword()).disabled(!amcUsers.get(0).getValid().equals(
//        AmcUserValidEnum.VALID.getId())).build();
    return amcUserDetail;
  }


}
