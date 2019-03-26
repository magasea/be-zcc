package com.wensheng.zcc.sso.service.impl;

import com.wensheng.zcc.common.utils.ExceptionUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils.AmcExceptions;
import com.wensheng.zcc.sso.dao.mysql.mapper.AmcRoleMapper;
import com.wensheng.zcc.sso.dao.mysql.mapper.AmcRolePermissionMapper;
import com.wensheng.zcc.sso.dao.mysql.mapper.AmcUserMapper;
import com.wensheng.zcc.sso.dao.mysql.mapper.AmcUserRoleMapper;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcRole;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcRolePermission;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcUser;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcUserExample;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcUserRole;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcUserRoleExample;
import com.wensheng.zcc.sso.module.helper.AmcRolesEnum;
import com.wensheng.zcc.sso.module.helper.AmcUserValidEnum;
import com.wensheng.zcc.sso.service.AmcUserService;
import com.wensheng.zcc.sso.service.util.UserUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * @author chenwei on 3/14/19
 * @project zcc-backend
 */
@Service
@Slf4j
public class AmcUserServiceImpl implements AmcUserService {

  @Autowired
  AmcUserMapper amcUserMapper;

  @Autowired
  AmcUserRoleMapper amcUserRoleMapper;

  @Autowired
  AmcRoleMapper amcRoleMapper;

  @Autowired
  AmcRolePermissionMapper amcRolePermissionMapper;

  @Override
  public void modifyUserRole(Long userId, List<Long> roleIds) {
    AmcUserRoleExample amcUserRoleExample = new AmcUserRoleExample();
    amcUserRoleExample.createCriteria().andUserIdEqualTo(userId);
    List<AmcUserRole> amcUserRoles = amcUserRoleMapper.selectByExample(amcUserRoleExample);
    List<Long> addRoleIds = new ArrayList<>();
    final Set<Long>  updateRoleIds = roleIds.stream().collect(Collectors.toSet());
    final Set<Long>  historyRoleIds = amcUserRoles.stream().map(item -> item.getRoleId()).collect(Collectors.toSet());
    if(!CollectionUtils.isEmpty(amcUserRoles)){

      addRoleIds = updateRoleIds.stream().filter(item -> !historyRoleIds.contains(item)).collect(Collectors.toList());
      addRolesForUser(userId, addRoleIds);

    }

    if(!CollectionUtils.isEmpty(roleIds)){

      List<Long> delRoleIds =
          historyRoleIds.stream().filter( item -> !updateRoleIds.contains(item)).collect(Collectors.toList());
      deleteRolesFromUser(userId, delRoleIds);
    }

  }

  @Override
  public AmcUser createUser(AmcUser amcUser) {
    int cnt = amcUserMapper.insertSelective(amcUser);
    if(cnt > 0){
      return amcUser;
    }else{
      log.error(String.format("Failed to insert user"));
    }
    return amcUser;
  }

  @Override
  public List<AmcRole> getAmcRoles() {
    return amcRoleMapper.selectByExample(null);
  }

  @Override
  public List<AmcRolePermission> getAmcRolePerms() {
    return amcRolePermissionMapper.selectByExample(null);
  }

  @Override
  public AmcUser createAmcAdmin(AmcUser amcUser) {
    AmcUser amcUserCreated = createUserAndRole(amcUser, AmcRolesEnum.ROLE_AMC_ADMIN);
    return amcUserCreated;
  }

  @Override
  public AmcUser createAmcUser(AmcUser amcUser) {
    AmcUser amcUserCreated = createUserAndRole(amcUser, AmcRolesEnum.ROLE_AMC_USER);
    return amcUserCreated;
  }

  @Override
  public List<AmcUser> getAmcUsers(Long amcId) {
    AmcUserExample amcUserExample = new AmcUserExample();
    amcUserExample.createCriteria().andCompanyIdEqualTo(amcId);
    List<AmcUser> amcUsers = amcUserMapper.selectByExample(amcUserExample);
    amcUsers.stream().forEach(amcUser -> amcUser.setPassword(""));
    return amcUsers;
  }

  @Override
  public List<AmcUser> getAmcUserByPhoneNum(String phoneNum) {
    AmcUserExample amcUserExample = new AmcUserExample();
    amcUserExample.createCriteria().andMobilePhoneEqualTo(phoneNum);
    List<AmcUser> amcUsers = amcUserMapper.selectByExample(amcUserExample);
    return amcUsers;
  }

  @Override
  public List<AmcUser> getAllUsers() {
    List<AmcUser> amcUsers =  amcUserMapper.selectByExample(null);
    amcUsers.stream().forEach(amcUser -> amcUser.setPassword(""));
    return amcUsers;
  }

  @Override
  public void modifyUserValidState(Long userId, AmcUserValidEnum amcUserValidEnum) {
    AmcUser amcUser = new AmcUser();
    amcUser.setValid(amcUserValidEnum.getId());
    AmcUserExample amcUserExample = new AmcUserExample();
    amcUserExample.createCriteria().andIdEqualTo(userId);
    amcUserMapper.updateByExampleSelective(amcUser, amcUserExample);
  }

  @Override
  public void modifyUserValidState(Long userId, Long amcId, AmcUserValidEnum amcUserValidEnum) {


    AmcUserRoleExample amcUserRoleExample = new AmcUserRoleExample();
    amcUserRoleExample.createCriteria().andUserIdEqualTo(userId);
    List<AmcUserRole> amcUserRoles = amcUserRoleMapper.selectByExample(amcUserRoleExample);
    if(!CollectionUtils.isEmpty(amcUserRoles)){
      AmcUserRole amcUserRole =
          amcUserRoles.stream().filter( item -> ( item.getRoleId() == AmcRolesEnum.ROLE_SYSTEM_ADMIN.getId() ||
              item.getRoleId() == AmcRolesEnum.ROLE_AMC_ADMIN.getId())).findAny().orElse(null);
      if(amcUserRole != null){
        ExceptionUtils.getAmcException(AmcExceptions.NOT_AUTHORIZED_FORTHISTASK, String.format("target user with "
            + "role:%s", AmcRolesEnum.lookupByDisplayIdUtil(amcUserRole.getRoleId().intValue()).getName()));
      }
    }

    AmcUser amcUser = new AmcUser();
    amcUser.setValid(amcUserValidEnum.getId());
    AmcUserExample amcUserExample = new AmcUserExample();
    amcUserExample.createCriteria().andIdEqualTo(userId).andCompanyIdEqualTo(amcId);

    amcUserMapper.updateByExampleSelective(amcUser, amcUserExample);
  }

  private AmcUser createUserAndRole(AmcUser amcUser, AmcRolesEnum amcRolesEnum){
    amcUser.setPassword(UserUtils.getEncode(amcUser.getPassword()));
    amcUserMapper.insertSelective(amcUser);
    List<Long> roleIds = new ArrayList<>();
    roleIds.add(Long.valueOf(amcRolesEnum.getId()));
    addRolesForUser(amcUser.getId(), roleIds);
    return amcUser;
  }

  private void addRolesForUser(Long userId, List<Long> addRoleIds) {
    if(CollectionUtils.isEmpty(addRoleIds)){
      return;
    }
    for(Long roleId: addRoleIds){
      AmcUserRole amcUserRole = new AmcUserRole();
      amcUserRole.setUserId(userId);
      amcUserRole.setRoleId(roleId);
      amcUserRoleMapper.insertSelective(amcUserRole);
    }
  }

  private void deleteRolesFromUser(Long userId, List<Long> roleIds){
    if(CollectionUtils.isEmpty(roleIds)){
      return;
    }
    AmcUserRoleExample amcUserRoleExample = new AmcUserRoleExample();
    amcUserRoleExample.createCriteria().andUserIdEqualTo(userId).andRoleIdIn(roleIds);
    amcUserRoleMapper.deleteByExample(amcUserRoleExample);
  }
}
