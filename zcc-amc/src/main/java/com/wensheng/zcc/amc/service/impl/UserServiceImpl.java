//package com.wensheng.zcc.amc.service.impl;
//
//import com.wensheng.zcc.amc.dao.mysql.mapper.AmcPermissionMapper;
//import com.wensheng.zcc.amc.dao.mysql.mapper.AmcRoleMapper;
//import com.wensheng.zcc.amc.dao.mysql.mapper.AmcRolePermissionMapper;
//import com.wensheng.zcc.amc.dao.mysql.mapper.AmcUserMapper;
//import com.wensheng.zcc.amc.dao.mysql.mapper.AmcUserRoleMapper;
//import com.wensheng.zcc.amc.dao.mysql.mapper.ext.AmcUserExtMapper;
//import com.wensheng.zcc.amc.module.dao.helper.AmcRolesEnum;
//import com.wensheng.zcc.amc.module.dao.helper.AmcUserValidEnum;
//import com.wensheng.zcc.amc.module.dao.mysql.auto.ext.AmcUserExt;
//import com.wensheng.zcc.amc.service.UserService;
//import com.wensheng.zcc.common.module.amc.vo.AmcUserDetail;
//import com.wensheng.zcc.common.utils.AmcBeanUtils;
//import com.wensheng.zcc.common.utils.ExceptionUtils.AmcExceptions;
//import java.time.Instant;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.util.CollectionUtils;
//
///**
// * @author chenwei on 1/30/19
// * @project zcc-backend
// */
//@Service("userService")
//public class UserServiceImpl implements UserService {
//
//  @Autowired
//  AmcUserMapper amcUserMapper;
//
//  @Autowired
//  AmcPermissionMapper amcPermissionMapper;
//
//  @Autowired
//  AmcRolePermissionMapper amcRolePermissionMapper;
//
//  @Autowired
//  AmcUserRoleMapper amcUserRoleMapper;
//
//  @Autowired
//  AmcUserExtMapper amcUserExtMapper;
//
//  @Autowired
//  AmcRoleMapper amcRoleMapper;
//
//  @Autowired
//  PasswordEncoder passwordEncoder;
//
//
//  @Override
//  public List<AmcUser> getUserByPhone(String phoneNum) {
//    AmcUserExample amcUserExample = new AmcUserExample();
//    amcUserExample.createCriteria().andMobilePhoneEqualTo(phoneNum);
//    return amcUserMapper.selectByExample(amcUserExample);
//  }
//
//  @Override
//  public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
//    AmcUserExample amcUserExample = new AmcUserExample();
//    amcUserExample.createCriteria().andMobilePhoneEqualTo(phoneNumber);
//    List<AmcUser> amcUsers = amcUserMapper.selectByExample(amcUserExample);
//    if(CollectionUtils.isEmpty(amcUsers)){
//      throw new UsernameNotFoundException(AmcExceptions.NO_SUCHUSER.toString());
//    }
//    List<String> authorities = getPermissions(amcUsers.get(0));
//
//    List<GrantedAuthority> grantedAuthorityAuthorities = new ArrayList<>();
//    authorities.forEach( auth -> grantedAuthorityAuthorities.add(new SimpleGrantedAuthority(auth)));
//    boolean userEnabled = amcUsers.get(0).getValid().equals(AmcUserValidEnum.VALID.getId());
//    AmcUserDetail amcUserDetail = new AmcUserDetail(amcUsers.get(0).getMobilePhone(),"",userEnabled, userEnabled,userEnabled,
//        userEnabled,grantedAuthorityAuthorities);
//    AmcBeanUtils.copyProperties(amcUsers.get(0), amcUserDetail);
//
////    UserDetails userDetails =
////        User.builder().authorities(grantedAuthorityAuthorities).username(amcUsers.get(0).getMobilePhone()).password(amcUsers.get(0).getPassword()).disabled(!amcUsers.get(0).getValid().equals(
////        AmcUserValidEnum.VALID.getId())).build();
//    return amcUserDetail;
//  }
//
//
//  @Override
//  public UserDetails loadUserByUserId(Long userId) throws UsernameNotFoundException {
//    AmcUserExample amcUserExample = new AmcUserExample();
//    amcUserExample.createCriteria().andIdEqualTo(userId);
//    List<AmcUser> amcUsers = amcUserMapper.selectByExample(amcUserExample);
//    if(CollectionUtils.isEmpty(amcUsers)){
//      throw new UsernameNotFoundException(AmcExceptions.NO_SUCHUSER.toString());
//    }
//    List<String> authorities = getPermissions(amcUsers.get(0));
//
//    List<GrantedAuthority> grantedAuthorityAuthorities = new ArrayList<>();
//    authorities.forEach( auth -> grantedAuthorityAuthorities.add(new SimpleGrantedAuthority(auth)));
//    boolean userEnabled = amcUsers.get(0).getValid().equals(AmcUserValidEnum.VALID.getId());
//    AmcUserDetail amcUserDetail = new AmcUserDetail(amcUsers.get(0).getMobilePhone(),"",userEnabled, userEnabled,userEnabled,
//        userEnabled,grantedAuthorityAuthorities);
//    AmcBeanUtils.copyProperties(amcUsers.get(0), amcUserDetail);
//
////    UserDetails userDetails =
////        User.builder().authorities(grantedAuthorityAuthorities).username(amcUsers.get(0).getMobilePhone()).password(amcUsers.get(0).getPassword()).disabled(!amcUsers.get(0).getValid().equals(
////        AmcUserValidEnum.VALID.getId())).build();
//    return amcUserDetail;
//  }
//  @Override
//  public List<String> getPermissions(AmcUser amcUser){
//
//    AmcUserExt amcUserExt = amcUserExtMapper.selectByExtExample(amcUser.getId());
//    List<String> authorities = new ArrayList<>();
////    boolean isAmcOper = false;
////    if(amcUser.getCompanyId() != null && amcUser.getCompanyId() > 0){
////      authorities.add(String.format("PERM_%s_READ",amcUser.getCompanyId()));
////      isAmcOper = true;
////    }
//    boolean isCompanyUser = false;
//    if(amcUser.getCompanyId() != null && amcUser.getCompanyId() > 0){
//      isCompanyUser = true;
//    }
//    StringBuilder sb = new StringBuilder();
//    for(AmcPermission amcPermission: amcUserExt.getAmcPermissions()){
//      sb.setLength(0);
//      if(amcPermission.getName().contains("AMC") && isCompanyUser){
//        sb.append("PERM_").append(amcUser.getCompanyId()).append("_").append(amcPermission.getName().substring("PERM_".length()));
//        authorities.add(sb.toString());
//      }else{
//        authorities.add(amcPermission.getName());
//      }
//    }
//    for(AmcRole amcRole: amcUserExt.getAmcRoles()){
//      authorities.add(amcRole.getName());
////      if(isAmcOper && amcRole.getName().equals(AmcRolesEnum.ROLE_AMC_ADMIN.name())){
////        authorities.add(String.format("PERM_%s_WRITE",amcUser.getCompanyId()));
////      }
//    }
//    return authorities;
//
//  }
//
//  @Override
//  public AmcUser createDefaultAmcUser(AmcUser amcUser) {
//    amcUser.setPassword(passwordEncoder.encode(amcUser.getPassword()));
//    amcUserExtMapper.insertSelective(amcUser);
//    Long userId = amcUser.getId();
//    AmcUserRole amcUserRole = new AmcUserRole();
//    amcUserRole.setCreateBy(userId);
//    amcUserRole.setCreateDate(Date.from(Instant.now()));
//    amcUserRole.setUserId(userId);
//    AmcRoleExample amcRoleExample = new AmcRoleExample();
//    amcRoleExample.createCriteria().andNameEqualTo(AmcRolesEnum.ROLE_AMC_USER.name());
//    List<AmcRole> amcRoles = amcRoleMapper.selectByExample(amcRoleExample);
//    Long roleId = amcRoles.get(0).getId();
//    amcUserRole.setRoleId(roleId);
//
//    amcUserRoleMapper.insert(amcUserRole);
//    amcUser.setId(userId);
//    return amcUser;
//
//  }
//
//  @Override
//  public Map<String, String> getTokens(AmcUser amcUser) {
////    authenticationManagerBean;
////    Authentication authentication = new UsernamePasswordAuthenticationToken(amcUser.getMobilePhone(),
////        amcUser.getPassword());
////
////    tokenServices.createAccessToken( authenticationManagerBean.authenticate(authentication) )
//    return null;
//  }
//
//
//}
