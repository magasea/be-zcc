package com.wensheng.zcc.sso.service.impl;

import com.wensheng.zcc.common.params.AmcRolesEnum;
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
import com.wensheng.zcc.sso.dao.mysql.mapper.ext.AmcUserExtMapper;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcPermission;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcRole;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcRolePermission;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcUser;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcUserExample;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcUserRole;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcUserRoleExample;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.ext.AmcUserExt;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.ext.AmcUserExtExample;
import com.wensheng.zcc.sso.service.AmcTokenService;
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
  public AmcUser createUser(AmcUser amcUser) {
    boolean isInDb = false;
    boolean needUpdateDb = false;
    boolean needUpdatePrivilege = false;

    AmcUserExample amcUserExample = new AmcUserExample();
    amcUserExample.createCriteria().andMobilePhoneEqualTo(amcUser.getMobilePhone());

    List<AmcUser> amcUsers = amcUserMapper.selectByExample(amcUserExample);
    if(CollectionUtils.isEmpty(amcUsers)){
      if(StringUtils.isEmpty(amcUser.getPassword())){
        amcUser.setPassword( UserUtils.getEncode( String.format("%s-%s",defaultPasswd, AmcDateUtils.getFormatedDate())));
        amcUser.setCreateDate(java.sql.Date.valueOf(LocalDate.now()));
        needUpdatePrivilege = true;
      }
      int cnt = amcUserMapper.insertSelective(amcUser);
      if(cnt > 0){
        log.info("Insert new user with mobile phone:{}", amcUser.getMobilePhone());
      }else{
        log.error("Insert new user with mobile phone:{} failed", amcUser.getMobilePhone());
        return null;
      }
    }else{

      if(amcUser.getDeptId() > 0 && amcUser.getDeptId() != amcUsers.get(0).getDeptId()){
        amcUsers.get(0).setDeptId(amcUser.getDeptId());
        needUpdateDb = true;
        needUpdatePrivilege = true;
      }
      if(amcUser.getTitle() > 0 && amcUser.getTitle() != amcUsers.get(0).getTitle()){
        amcUsers.get(0).setTitle(amcUser.getTitle());
        needUpdateDb = true;
        needUpdatePrivilege = true;
      }
      if(amcUser.getLocation() > 0 && amcUser.getLocation() != amcUsers.get(0).getLocation()){
        amcUsers.get(0).setLocation(amcUser.getLocation());
        needUpdateDb = true;
      }
      if(amcUser.getLgroup() > 0 && amcUser.getLgroup() != amcUsers.get(0).getLgroup()){
        amcUsers.get(0).setLgroup(amcUser.getLgroup());
        needUpdateDb = true;
      }
      if(amcUser.getCompanyId() > 0 && amcUser.getCompanyId() != amcUsers.get(0).getCompanyId()){
        amcUsers.get(0).setCompanyId(amcUser.getCompanyId());
        needUpdateDb = true;
        needUpdatePrivilege = true;
      }
//      if(StringUtils.isEmpty(amcUsers.get(0).getPassword())){

        amcUsers.get(0).setUpdateDate(java.sql.Date.valueOf(LocalDate.now()));
//      }
      if(needUpdateDb){
        amcUserMapper.updateByPrimaryKeySelective(amcUsers.get(0));
      }
      isInDb = true;
    }

    if(needUpdatePrivilege){
      AmcUserRole amcUserRole = new AmcUserRole();
      if(!isInDb){
        amcUserRole.setUserId(amcUser.getId());
      }else{
        amcUserRole.setUserId(amcUsers.get(0).getId());
      }
      AmcRolesEnum amcRolesEnum =
          UserUtils.getRoleByUser(AmcDeptEnum.lookupByDisplayIdUtil(amcUser.getDeptId().intValue()),
              AmcSSOTitleEnum.lookupByDisplayIdUtil(amcUser.getTitle()));
      amcUserRole.setRoleId(Long.valueOf(amcRolesEnum.getId()));
      AmcUserRoleExample amcUserRoleExample = new AmcUserRoleExample();
      amcUserRoleExample.createCriteria().andUserIdEqualTo(amcUserRole.getUserId());

      List<AmcUserRole> amcUserRoles =  amcUserRoleMapper.selectByExample(amcUserRoleExample);
      boolean hasRoleAlready = false;
      if(CollectionUtils.isEmpty(amcUserRoles)){
        hasRoleAlready = false;
      }else if(amcUserRoles.size() >= 1){

        for(AmcUserRole amcUserRoleItem: amcUserRoles){
          if(amcUserRoleItem.getRoleId() != amcUserRole.getRoleId()){
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
    if(needUpdateDb || needUpdatePrivilege){
      amcTokenService.revokeTokenByMobilePhone(amcUser.getMobilePhone());
    }
    if(!isInDb){
      return amcUser;
    }else{
      return amcUsers.get(0);
    }
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
    AmcUserExample amcUserExample = SQLUtils.getAmcUserExample(queryParam);
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
    List<AmcUser> amcUsers1 = this.getAmcUsers(amcUserExample);
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
