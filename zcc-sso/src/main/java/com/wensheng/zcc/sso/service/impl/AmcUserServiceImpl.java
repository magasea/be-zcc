package com.wensheng.zcc.sso.service.impl;

import com.wensheng.zcc.common.params.AmcRolesEnum;
import com.wensheng.zcc.common.params.AmcSpecialUserEnum;
import com.wensheng.zcc.common.params.sso.AmcDeptEnum;
import com.wensheng.zcc.common.params.sso.AmcSSOTitleEnum;
import com.wensheng.zcc.common.params.sso.AmcUserValidEnum;
import com.wensheng.zcc.common.params.sso.SSOAmcUser;
import com.wensheng.zcc.common.utils.AmcBeanUtils;
import com.wensheng.zcc.common.utils.AmcDateUtils;
import com.wensheng.zcc.sso.aop.AmcUserQueryChecker;
import com.wensheng.zcc.sso.aop.LogExecutionTime;
import com.wensheng.zcc.sso.dao.mysql.mapper.AmcPermissionMapper;
import com.wensheng.zcc.sso.dao.mysql.mapper.AmcRoleMapper;
import com.wensheng.zcc.sso.dao.mysql.mapper.AmcRolePermissionMapper;
import com.wensheng.zcc.sso.dao.mysql.mapper.AmcUserMapper;
import com.wensheng.zcc.sso.dao.mysql.mapper.AmcUserRoleMapper;
import com.wensheng.zcc.sso.dao.mysql.mapper.AmcUserRoleRuleMapper;
import com.wensheng.zcc.sso.dao.mysql.mapper.ext.AmcUserExtMapper;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcPermission;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcRole;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcRolePermission;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcRolePermissionExample;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcUser;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcUserExample;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcUserRole;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcUserRoleExample;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcUserRoleRuleExample;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.ext.AmcUserExt;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.ext.AmcUserExtExample;
import com.wensheng.zcc.sso.service.AmcTokenService;
import com.wensheng.zcc.sso.service.AmcUserRoleRuleService;
import com.wensheng.zcc.sso.service.AmcUserService;
import com.wensheng.zcc.sso.service.util.QueryParam;
import com.wensheng.zcc.sso.service.util.SQLUtils;
import com.wensheng.zcc.sso.service.util.UserUtils;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * @author chenwei on 3/14/19
 * @project zcc-backend
 */
@Service
@Slf4j
@CacheConfig(cacheNames = {"USER"})
public class AmcUserServiceImpl implements AmcUserService {

  @Autowired
  AmcUserMapper amcUserMapper;


  @Autowired
  AmcUserExtMapper amcUserExtMapper;

  @Autowired
  AmcUserRoleMapper amcUserRoleMapper;

  @Autowired
  AmcRoleMapper amcRoleMapper;

  @Autowired
  AmcRolePermissionMapper amcRolePermissionMapper;

  @Autowired
  AmcPermissionMapper amcPermissionMapper;

  @Autowired
  AmcUserRoleRuleMapper amcUserRoleRuleMapper;

  @Autowired
  AmcUserRoleRuleService amcUserRoleRuleService;

  @Resource(name = "tokenServices")
  private ConsumerTokenServices tokenServices;

  @Resource(name = "tokenStore")
  private TokenStore tokenStore;

  @Value("${spring.security.oauth2.client.registration.amc-admin.client-id}")
  private String amcAdminClientId;

  private final String defaultPasswd = "wensheng";

  @Autowired
  AmcTokenService amcTokenService;

  @Override
  @CacheEvict
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
  public AmcUser createUser(AmcUser amcUserSSO) {

    if(!StringUtils.isEmpty(amcUserSSO.getUserName()) && (amcUserSSO.getUserName().equals(AmcSpecialUserEnum.SYSTEM_ADMIN1.getName()) ||
        amcUserSSO.getMobilePhone().equals(AmcSpecialUserEnum.SYSTEM_ADMIN1.getMobileNum()) ||
        amcUserSSO.getUserName().equals(AmcSpecialUserEnum.SYSTEM_ADMIN2.getName()) ||
        amcUserSSO.getMobilePhone().equals(AmcSpecialUserEnum.SYSTEM_ADMIN2.getMobileNum())||

        amcUserSSO.getUserName().equals(AmcSpecialUserEnum.ZCC_SYSTEM_ADMIN.getName()) ||
        amcUserSSO.getMobilePhone().equals(AmcSpecialUserEnum.ZCC_SYSTEM_ADMIN.getMobileNum())
    )){
      return makeSpecuser(amcUserSSO, AmcRolesEnum.ROLE_SYSTEM_ADMIN);
    }else if(!StringUtils.isEmpty(amcUserSSO.getUserName()) && (amcUserSSO.getUserName().equals(AmcSpecialUserEnum.ZCC_CO_ADMIN.getName()) ||
        amcUserSSO.getMobilePhone().equals(AmcSpecialUserEnum.ZCC_CO_ADMIN.getMobileNum()))){
      return makeSpecuser(amcUserSSO, AmcRolesEnum.ROLE_CO_ADMIN);
    }

    boolean isInDb = false;
    boolean needUpdateDb = false;
    boolean needUpdatePrivilege = false;

    AmcUserExample amcUserExample = new AmcUserExample();
    amcUserExample.createCriteria().andMobilePhoneEqualTo(amcUserSSO.getMobilePhone());

    List<AmcUser> amcUsers = amcUserMapper.selectByExample(amcUserExample);
    List<AmcUser> amcUsersWithSSOId = new ArrayList<>();
    String histMobile = null;
    if(amcUserSSO.getId() != null && amcUserSSO.getId() != -1L){
      amcUserExample.clear();
      amcUserExample.createCriteria().andSsoUserIdEqualTo(amcUserSSO.getId());
      amcUsersWithSSOId = amcUserMapper.selectByExample(amcUserExample);


    }
    Long amcUserId = -1L;
    AmcUser amcUserNew = null;
    AmcUser amcUserInDb = null;
    if(CollectionUtils.isEmpty(amcUsers) && CollectionUtils.isEmpty(amcUsersWithSSOId)){
      amcUserNew = new AmcUser();
      if(StringUtils.isEmpty(amcUserSSO.getPassword())){

        AmcBeanUtils.copyProperties(amcUserSSO, amcUserNew);
        amcUserNew.setPassword( UserUtils.getEncode( defaultPasswd));
        amcUserNew.setCreateDate(java.sql.Date.valueOf(LocalDate.now()));
        amcUserNew.setSsoUserId(amcUserSSO.getId());
        needUpdatePrivilege = true;
      }
      int cnt = amcUserMapper.insertSelective(amcUserNew);
      if(cnt > 0){
        log.info("Insert new user with mobile phone:{}", amcUserSSO.getMobilePhone());
      }else{
        log.error("Insert new user with mobile phone:{} failed", amcUserSSO.getMobilePhone());
        return null;
      }
      amcUserId = amcUserNew.getId();
    }else{

      if(CollectionUtils.isEmpty(amcUsers) && !CollectionUtils.isEmpty(amcUsersWithSSOId)){
        //update the mobile info with the current amc sso user
        int keepIdx = 0;
        if(amcUsersWithSSOId.size() > 1){
          Date initDate = null;

          for(int idx = 0; idx < amcUsersWithSSOId.size() ; idx++){
            if(initDate == null){
              initDate = amcUsersWithSSOId.get(idx).getUpdateDate();
            }else{
              if(amcUsersWithSSOId.get(idx).getUpdateDate() != null && initDate.compareTo(amcUsersWithSSOId.get(idx).getUpdateDate()) < 0){
                  keepIdx = idx;
                  continue;
              }
            }

          }
          for(int idx = 0; idx < amcUsersWithSSOId.size() ; idx++){
            if(idx != keepIdx){
              amcUserMapper.deleteByPrimaryKey(amcUsersWithSSOId.get(idx).getId());
              AmcUserRoleExample amcUserRoleExample = new AmcUserRoleExample();
              amcUserRoleExample.createCriteria().andUserIdEqualTo(amcUsersWithSSOId.get(idx).getId());
              amcUserRoleMapper.deleteByExample(amcUserRoleExample);
              amcUserRoleExample = null;
            }
          }
        }

        amcUsersWithSSOId.get(keepIdx).setMobilePhone(amcUserSSO.getMobilePhone());
        histMobile = amcUsersWithSSOId.get(keepIdx).getMobilePhone();
        amcUsersWithSSOId.get(0).setSsoUserId(amcUserSSO.getId());
        amcUserId = amcUsersWithSSOId.get(keepIdx).getId();
        amcUserInDb = amcUsersWithSSOId.get(keepIdx);
        amcUserMapper.updateByPrimaryKeySelective(amcUsersWithSSOId.get(0));
        if(amcUsersWithSSOId.size() > 1){
          log.error("There is multiple zcc amc user with the same sso user id:{} need to be handle carefully",
              amcUserSSO.getSsoUserId());
        }
      }else if(!CollectionUtils.isEmpty(amcUsers)){
        amcUserInDb = amcUsers.get(0);
        histMobile = amcUsers.get(0).getMobilePhone();
      }
      AmcUserRoleExample amcUserRoleExample = new AmcUserRoleExample();
      amcUserRoleExample.createCriteria().andUserIdEqualTo(amcUserId);
      List<AmcUserRole> amcUserRoleList =  amcUserRoleMapper.selectByExample(amcUserRoleExample);
      if(CollectionUtils.isEmpty(amcUserRoleList) ){
        needUpdatePrivilege = true;
      }
      if(amcUserSSO.getId() != null &&amcUserInDb != null){
        needUpdateDb = true;
        amcUserInDb.setSsoUserId(amcUserSSO.getId());
      }
      if(null != amcUserInDb && !amcUserInDb.getUserCname().equals(amcUserSSO.getUserCname())){
        amcUserInDb.setUserCname(amcUserSSO.getUserCname());
        needUpdateDb = true;
      }
      if(null != amcUserInDb && !amcUserInDb.getUserName().equals(amcUserSSO.getUserName())){
        amcUserInDb.setUserName(amcUserSSO.getUserName());
        needUpdateDb = true;
      }

      if(null != amcUserInDb && amcUserInDb.getDeptId() > 0 && !amcUserSSO.getDeptId().equals(amcUserInDb.getDeptId())){
        amcUserInDb.setDeptId(amcUserSSO.getDeptId());
        needUpdateDb = true;
        needUpdatePrivilege = true;
      }
      if(null != amcUserInDb && amcUserSSO.getTitle() > 0 && !amcUserSSO.getTitle().equals(amcUserInDb.getTitle())){
        amcUserInDb.setTitle(amcUserSSO.getTitle());
        needUpdateDb = true;
        needUpdatePrivilege = true;
      }
      if(null != amcUserInDb && amcUserSSO.getLocation() > 0 && !amcUserSSO.getLocation().equals(amcUserInDb.getLocation())){
        amcUserInDb.setLocation(amcUserSSO.getLocation());
        needUpdateDb = true;
      }
      if(null != amcUserInDb && amcUserSSO.getLgroup() > 0 && !amcUserSSO.getLgroup().equals(amcUserInDb.getLgroup())){
        amcUserInDb.setLgroup(amcUserSSO.getLgroup());
        needUpdateDb = true;
      }
      if(null != amcUserInDb && amcUserSSO.getCompanyId() != null && amcUserSSO.getCompanyId() > 0 && !amcUserSSO.getCompanyId().
          equals(amcUserInDb.getCompanyId())){
        amcUserInDb.setCompanyId(amcUserSSO.getCompanyId());
        needUpdateDb = true;
        needUpdatePrivilege = true;
      }
//      if(StringUtils.isEmpty(amcUsers.get(0).getPassword())){

      amcUserInDb.setUpdateDate(java.sql.Date.valueOf(LocalDate.now()));
//      }
      if(null != amcUserInDb  && amcUserInDb.getSsoUserId() == -1L){
        //need update the user's sso userId
        amcUserInDb.setSsoUserId(amcUserSSO.getId());
        needUpdateDb = true;
      }
      if(needUpdateDb){
        amcUserMapper.updateByPrimaryKeySelective(amcUserInDb);
      }
      isInDb = true;
    }

    if(needUpdatePrivilege){
      AmcUserRole amcUserRole = new AmcUserRole();
      if(!isInDb){
        amcUserRole.setUserId(amcUserNew.getId());
      }else{
        amcUserRole.setUserId(amcUserInDb.getId());
      }

      List<Integer> roleIds =  amcUserRoleRuleService.getRolesByDeptAndTitle(amcUserSSO.getDeptId().intValue(),
          amcUserSSO.getTitle());
      if(CollectionUtils.isEmpty(roleIds)){
        log.error("Database based role control logic is not yet ready !!");

        AmcDeptEnum amcDeptEnum =  AmcDeptEnum.lookupByDisplayIdUtil(amcUserSSO.getDeptId().intValue());
        AmcSSOTitleEnum amcSSOTitleEnum = AmcSSOTitleEnum.lookupByDisplayIdUtil(amcUserSSO.getTitle());
        if(amcDeptEnum == null || amcSSOTitleEnum == null){
          log.error("Failed find valild dept enum or ssoTitle enum for user:{} deptId:{} title:{}", amcUserSSO.getId(),
              amcUserSSO.getDeptId(), amcUserSSO.getTitle());
          return amcUserSSO;
        }
        AmcRolesEnum amcRolesEnum =
            UserUtils.getRoleByUser(AmcDeptEnum.lookupByDisplayIdUtil(amcUserSSO.getDeptId().intValue()),
                AmcSSOTitleEnum.lookupByDisplayIdUtil(amcUserSSO.getTitle()));

        amcUserRole.setRoleId(Long.valueOf(amcRolesEnum.getId()));
      }else{
        amcUserRole.setRoleId(Long.valueOf(roleIds.get(0)));
      }


      AmcUserRoleExample amcUserRoleExample = new AmcUserRoleExample();
      amcUserRoleExample.createCriteria().andUserIdEqualTo(amcUserRole.getUserId());

      List<AmcUserRole> amcUserRoles =  amcUserRoleMapper.selectByExample(amcUserRoleExample);
      boolean hasRoleAlready = false;
      if(CollectionUtils.isEmpty(amcUserRoles)){
        hasRoleAlready = false;
      }else if(amcUserRoles.size() >= 1){

        for(AmcUserRole amcUserRoleItem: amcUserRoles){
          if(!amcUserRoleItem.getRoleId().equals(amcUserRole.getRoleId())){
            amcUserRoleMapper.deleteByPrimaryKey(amcUserRoleItem.getId());
          }else{
            hasRoleAlready = true;
          }
        }

      }
      if(!hasRoleAlready){
        amcUserRoleMapper.insertSelective(amcUserRole);
      }
    }
    if((needUpdateDb || needUpdatePrivilege ) && !StringUtils.isEmpty(histMobile)){
      amcTokenService.revokeTokenByMobilePhone(histMobile);
    }
    if(!isInDb){
      return amcUserNew;
    }else{
      return amcUserInDb;
    }
  }

  private AmcUser makeSpecuser(AmcUser amcUser, AmcRolesEnum roleEnum) {

    //check if user exist
    AmcUserExample amcUserExample = new AmcUserExample();
    amcUserExample.createCriteria().andMobilePhoneEqualTo(amcUser.getMobilePhone());
    AmcUserRoleExample amcUserRoleExample = new AmcUserRoleExample();
//    amcUserExample.or().andUserNameEqualTo(amcUser.getUserName());
    List<AmcUser> amcUsers = amcUserMapper.selectByExample(amcUserExample);
    if(CollectionUtils.isEmpty(amcUsers)){
      //need create user
      amcUser.setPassword( UserUtils.getEncode( String.format("%s-%s",defaultPasswd, AmcDateUtils.getFormatedDate())));
      amcUser.setCreateDate(AmcDateUtils.getCurrentDate());
      int cnt = amcUserMapper.insertSelective(amcUser);
      if(cnt > 0){
        log.info("Insert new user with mobile phone:{}", amcUser.getMobilePhone());
      }else{
        log.error("Insert new user with mobile phone:{} failed", amcUser.getMobilePhone());
        return null;
      }
      //just for easy coding
      amcUsers.add(amcUser);
    }else {

      if (amcUsers.size() > 1) {
        //delete redundent user
        for (int idx = 0; idx < amcUsers.size(); idx++) {
          if (idx == 0) {
            continue;
          }
          amcUserMapper.deleteByPrimaryKey(amcUsers.get(idx).getId());
          amcUserRoleExample.clear();
          amcUserRoleExample.createCriteria().andUserIdEqualTo(amcUsers.get(idx).getId());
          amcUserRoleMapper.deleteByExample(amcUserRoleExample);
        }

      }
    }
    amcUserRoleExample.clear();
    amcUserRoleExample.createCriteria().andUserIdEqualTo(amcUsers.get(0).getId());
    List<AmcUserRole> amcUserRoles = amcUserRoleMapper.selectByExample(amcUserRoleExample);

    if (CollectionUtils.isEmpty(amcUserRoles)) {
      //need make the spec user with spec role
      AmcUserRole amcUserRole = new AmcUserRole();
      amcUserRole.setUserId(amcUsers.get(0).getId());
      switch (roleEnum) {
        case ROLE_CO_ADMIN:
          amcUserRole.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_CO_ADMIN.getId()));
          break;
        case ROLE_SYSTEM_ADMIN:
          amcUserRole.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_SYSTEM_ADMIN.getId()));
          break;
        default:
          log.error("Failed to determin role for:{}", roleEnum);
          amcUserRole.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_LOCAL_VISITOR.getId()));
      }
      amcUserRoleMapper.insertSelective(amcUserRole);
    } else {
      //check if user role correct
      for (AmcUserRole amcUserRole : amcUserRoles) {
        if (amcUserRole.getRoleId() == AmcRolesEnum.ROLE_CO_ADMIN.getId()
            || amcUserRole.getRoleId() == AmcRolesEnum.ROLE_SYSTEM_ADMIN.getId()) {
          //no need to update
          return amcUsers.get(0);
        }
      }
      log.error("The spec user doen't have default roles");
      amcUserRoleMapper.deleteByExample(amcUserRoleExample);
      //need make the spec user with spec role
      AmcUserRole amcUserRole = new AmcUserRole();
      amcUserRole.setUserId(amcUsers.get(0).getId());
      switch (roleEnum) {
        case ROLE_CO_ADMIN:
          amcUserRole.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_CO_ADMIN.getId()));
          break;
        case ROLE_SYSTEM_ADMIN:
          amcUserRole.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_SYSTEM_ADMIN.getId()));
          break;
        default:
          log.error("Failed to determin role for:{}", roleEnum);
          amcUserRole.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_LOCAL_VISITOR.getId()));
      }
      amcUserRoleMapper.insertSelective(amcUserRole);
      amcTokenService.revokeTokenByMobilePhone(amcUser.getMobilePhone());
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
  public boolean updateOrCreateRolePerms(Map<Long, List<Long>> amcRolePermissions) {
    AmcRolePermissionExample amcRolePermissionExample = new AmcRolePermissionExample();
    if(CollectionUtils.isEmpty(amcRolePermissions)){
      log.error("Empty amcRolePermissons from client");
      return false;
    }
    AmcRolePermission amcRolePermission;
    for(Map.Entry<Long, List<Long>> entry: amcRolePermissions.entrySet()){
      amcRolePermissionExample.clear();
      amcRolePermissionExample.createCriteria().andRoleIdEqualTo(entry.getKey());
      amcRolePermissionMapper.deleteByExample(amcRolePermissionExample);
      if(CollectionUtils.isEmpty(entry.getValue())){
        log.error("empty perm list for the role:{}", entry.getKey());
        continue;
      }
      amcRolePermission = new AmcRolePermission();
      amcRolePermission.setRoleId(entry.getKey());
      for(Long permId: entry.getValue()){
        amcRolePermission.setPermissionId(permId);
        amcRolePermissionMapper.insertSelective(amcRolePermission);
      }
      amcRolePermission = null;
    }
    return true;
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
  @CacheEvict
  @Transactional
  public void delUser(Long userId) {
    AmcUserRoleExample amcUserRoleExample = new AmcUserRoleExample();
    amcUserRoleExample.createCriteria().andUserIdEqualTo(userId);
    amcUserRoleMapper.deleteByExample(amcUserRoleExample);
    AmcUserExample amcUserExample = new AmcUserExample();
    amcUserExample.createCriteria().andIdEqualTo(userId);
    amcUserMapper.deleteByExample(amcUserExample);
  }

  @Override
  @Cacheable
  public AmcUser getUserById(Long userId) {
    return amcUserMapper.selectByPrimaryKey(userId);
  }

  @CacheEvict
  @Override
  public void disableUser(Long userId) {
    AmcUser amcUser = amcUserMapper.selectByPrimaryKey(userId);
    if(null == amcUser){
      log.error("Failed to find user with id:{}", userId);
      return;
    }
    Collection<OAuth2AccessToken> accessTokens = tokenStore.findTokensByClientIdAndUserName(amcAdminClientId,
        amcUser.getMobilePhone());
    for(OAuth2AccessToken oAuth2AccessToken : accessTokens){
      log.info("Revoke token:{}", oAuth2AccessToken.getValue());
      tokenServices.revokeToken(oAuth2AccessToken.getValue());
    }
  }

  @Override
  @LogExecutionTime
  public List<AmcUser> getAmcUsers(Long amcId) {
    AmcUserExample amcUserExample = new AmcUserExample();
    amcUserExample.createCriteria().andCompanyIdEqualTo(amcId);
    List<AmcUser> amcUsers = this.getAmcUsers(amcUserExample);
    amcUsers.stream().forEach(amcUser -> amcUser.setPassword(""));
    return amcUsers;
  }


  @Override
  @AmcUserQueryChecker
  public List<AmcUser> getAmcUsers(AmcUserExample amcUserExample){
    List<AmcUser> amcUsers = amcUserMapper.selectByExample(amcUserExample);
    return amcUsers;
  }


  @Override
  public List<AmcUserExt> getSubAmcUsers(AmcUserExample amcUserExample) {
    List<Long> ids = amcUserExtMapper.selectIdsByExtWithRowboundsExample(amcUserExample);
    if(CollectionUtils.isEmpty(ids)){
      log.error("no user meet critier in example:{}", amcUserExample.toString());
      return new ArrayList<AmcUserExt>();
    }
    AmcUserExtExample amcUserExampleNew = new AmcUserExtExample();
    StringBuilder sb = new StringBuilder(" au.id in ( ");
    ids.forEach(item -> sb.append(item).append(","));
    sb.setLength(sb.length() -1);
    sb.append(")");
    amcUserExampleNew.setWhereClause(sb.toString());
    List<AmcUserExt> amcUsers = amcUserExtMapper.selectByExtWithRowboundsExample(amcUserExampleNew);
    return amcUsers;
  }

  @Override
  public Long countSubAmcUsers(AmcUserExample amcUserExample) {
    return amcUserMapper.countByExample(amcUserExample);
  }
  @Override
  public List<AmcUserRole> getAmcUserRoles(Long userId) {
    AmcUserRoleExample amcUserRoleExample = new AmcUserRoleExample();
    amcUserRoleExample.createCriteria().andUserIdEqualTo(userId);
    List<AmcUserRole> amcUserRoles = amcUserRoleMapper.selectByExample(amcUserRoleExample);
    return amcUserRoles;
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



  @CacheEvict
  @Override
  public void modifyUserValidState(Long userId, AmcUserValidEnum amcUserValidEnum) throws Exception{


    AmcUser amcUser = new AmcUser();
    amcUser.setValid(amcUserValidEnum.getId());
    AmcUserExample amcUserExample = new AmcUserExample();
    amcUserExample.createCriteria().andIdEqualTo(userId);
    amcUserMapper.updateByExampleSelective(amcUser, amcUserExample);

    if( AmcUserValidEnum.LOCKED == amcUserValidEnum){
      log.info("will disableuser:{}", userId);
      disableUser(userId);
    }
  }

  @CacheEvict
  @Override
  public void modifyUserValidState(Long userId, Long amcId, AmcUserValidEnum amcUserValidEnum) throws Exception {


    AmcUserRoleExample amcUserRoleExample = new AmcUserRoleExample();
    amcUserRoleExample.createCriteria().andUserIdEqualTo(userId);
//    List<AmcUserRole> amcUserRoles = amcUserRoleMapper.selectByExample(amcUserRoleExample);
//    if(!CollectionUtils.isEmpty(amcUserRoles)){
//      AmcUserRole amcUserRole =
//          amcUserRoles.stream().filter( item -> ( item.getRoleId() == AmcRolesEnum.ROLE_SYSTEM_ADMIN.getId() ||
//              item.getRoleId() == AmcRolesEnum.ROLE_AMC_ADMIN.getId())).findAny().orElse(null);
//      if(amcUserRole != null){
//        throw ExceptionUtils.getAmcException(AmcExceptions.NOT_AUTHORIZED_FORTHISTASK, String.format("target user with "
//            + "role:%s", AmcRolesEnum.lookupByDisplayIdUtil(amcUserRole.getRoleId().intValue()).getName()));
//      }
//    }

    AmcUser amcUser = new AmcUser();
    amcUser.setValid(amcUserValidEnum.getId());
    AmcUserExample amcUserExample = new AmcUserExample();
    amcUserExample.createCriteria().andIdEqualTo(userId).andCompanyIdEqualTo(amcId);

    amcUserMapper.updateByExampleSelective(amcUser, amcUserExample);
    if( AmcUserValidEnum.LOCKED == amcUserValidEnum){
      log.info("will disableuser:{}", userId);
      disableUser(userId);
    }
  }

  @Override
  public List<AmcPermission> getAmcPerms() {
    List<AmcPermission> amcPermissions = amcPermissionMapper.selectByExample(null);
    return amcPermissions;
  }



  @Override
  public List<AmcUser> searchUserByPhone(String mobilePhone) {

    AmcUserExample amcUserExample = new AmcUserExample();
    StringBuilder sb = new StringBuilder("%");
    sb.append(mobilePhone).append("%");
    amcUserExample.createCriteria().andMobilePhoneLike(sb.toString());
    List<AmcUser> amcUsers = amcUserMapper.selectByExample(amcUserExample);
    return amcUsers;
  }

  @Override
  public List<AmcUser> searchUserByName(String name) {
    AmcUserExample amcUserExample = new AmcUserExample();
    StringBuilder sb = new StringBuilder("%");
    sb.append(name).append("%");
    amcUserExample.createCriteria().andNickNameLike(sb.toString());
    amcUserExample.or(amcUserExample.createCriteria().andUserNameLike(sb.toString()));
    List<AmcUser> amcUsers = amcUserMapper.selectByExample(amcUserExample);

    return amcUsers;
  }

  @Override
  public boolean updateOrInsertSSOUser(List<SSOAmcUser> ssoAmcUsers) {
    AmcUserExample amcUserExample = new AmcUserExample();
    for(SSOAmcUser ssoAmcUser: ssoAmcUsers){

      amcUserExample.createCriteria().andMobilePhoneEqualTo(ssoAmcUser.getMobilePhone());
      List<AmcUser>  amcUsers = amcUserMapper.selectByExample(amcUserExample);
      if(CollectionUtils.isEmpty(amcUsers)){
        //insert new user in db
        AmcUser amcUser = new AmcUser();
        AmcBeanUtils.copyProperties(ssoAmcUser, amcUser);
        amcUserMapper.insertSelective(amcUser);
        amcUser = null;
      }else{
        //update user in db
        AmcUser amcUser = amcUsers.get(0);
        amcUser.setCompanyId(ssoAmcUser.getCompanyId());
        amcUser.setDeptId(ssoAmcUser.getDeptId());
        amcUser.setLgroup(ssoAmcUser.getLgroup());
        amcUser.setTitle(ssoAmcUser.getTitle());
        amcUser.setLocation(ssoAmcUser.getLocation());
        amcUser.setNickName(ssoAmcUser.getNickName());
        amcUser.setUserName(ssoAmcUser.getUserName());
        amcUser.setValid(ssoAmcUser.getValid());
        amcUserMapper.updateByPrimaryKeySelective(amcUser);
      }
      amcUserExample = null;
      amcUserExample = new AmcUserExample();

    }
    return true;

  }

  @Override
  public List<AmcUserExt> queryUserPage(int offset, int size, QueryParam queryParam,
      Map<String, Direction> orderByParam) {
    AmcUserExtExample amcUserExample = SQLUtils.getAmcUserExample(queryParam);
    RowBounds rowBounds = new RowBounds(offset, size);
    try{

      amcUserExample.setOrderByClause(SQLUtils.getOrderBy(orderByParam, rowBounds));
    }catch (Exception ex){
      log.error("set order by exception:", ex);
    }
    List<AmcUserExt> amcUsers = amcUserExtMapper.selectByExtWithRowboundsExample(amcUserExample);
    amcUsers.forEach(item -> item.getAmcUser().setPassword(""));

    return amcUsers;
  }

  @Override
  public Long queryUserCount(QueryParam queryParam) {
    AmcUserExample amcUserExample = SQLUtils.getAmcUserExample(queryParam);

    return amcUserMapper.countByExample(amcUserExample);

  }

  @Override
  @LogExecutionTime
  public List<AmcUserExt> queryAmcUserPage(AmcUserExample amcUserExample) {

    List<AmcUserExt> amcUsers = this.getSubAmcUsers(amcUserExample);
//    List<AmcUser> amcUsers1 = this.getAmcUsers(amcUserExample);
    amcUsers.forEach(item -> item.getAmcUser().setPassword(""));
    return amcUsers;
  }

  @Override
  @AmcUserQueryChecker
  public Long queryAmcUserCount(AmcUserExample amcUserExample) {


    return this.countSubAmcUsers(amcUserExample);
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
