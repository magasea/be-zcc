package com.wensheng.zcc.sso.controller;

import com.wensheng.zcc.common.params.AmcCmpyEnum;
import com.wensheng.zcc.common.params.AmcPage;
import com.wensheng.zcc.common.params.PageReqRepHelper;
import com.wensheng.zcc.common.params.sso.AmcUserValidEnum;
import com.wensheng.zcc.common.utils.AmcBeanUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils.AmcExceptions;
import com.wensheng.zcc.sso.aop.AmcUserCreateChecker;
import com.wensheng.zcc.sso.aop.AmcUserModifyChecker;
import com.wensheng.zcc.sso.aop.LogExecutionTime;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcCompany;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcDept;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcRolePermission;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcUser;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcUserExample;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcUserRoleRule;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.ext.AmcUserExt;
import com.wensheng.zcc.sso.module.vo.AmcCmpyDeptVo;
import com.wensheng.zcc.sso.module.vo.AmcSpecialUserVo;
import com.wensheng.zcc.sso.module.vo.RolePermVo;
import com.wensheng.zcc.sso.module.vo.UserRoleModifyVo;
import com.wensheng.zcc.sso.service.AmcBasicService;
import com.wensheng.zcc.sso.service.AmcSsoService;
import com.wensheng.zcc.sso.service.AmcUserService;
import com.wensheng.zcc.sso.service.util.QueryParam;
import com.wensheng.zcc.sso.service.util.SQLUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author chenwei on 3/14/19
 * @project zcc-backend
 */
@Controller
@RequestMapping("/amc/user")
@Slf4j
public class AmcUserController {

  @Autowired
  AmcUserService amcUserService;

  @Autowired
  AmcBasicService amcBasicService;

  @Autowired
  AmcSsoService amcSsoService;

  @Autowired
  private ConsumerTokenServices tokenServices;

  @Autowired
  private TokenStore tokenStore;

  @Value("${spring.security.oauth2.client.registration.amc-admin.client-id}")
  private String amcAdminClientId;


  @PreAuthorize("hasRole('AMC_ADMIN') and hasPermission(#amcId,'crud_amcuser')")
  @RequestMapping(value = "/amcid/{amcid}/amc-user/create", method = RequestMethod.POST)
  @ResponseBody
  public AmcUser createUser(@RequestBody AmcUser amcUser){

    AmcUser amcUserResult = amcUserService.createUser(amcUser);
    return amcUserResult;
  }

  @PreAuthorize("hasRole('SYSTEM_ADMIN')")
  @RequestMapping(value = "/amcid/{amcid}/amc-user/create_amc_admin", method = RequestMethod.POST)
  @ResponseBody
  public AmcUser createAmcAdmin(@RequestBody AmcUser amcUser){


    AmcUser amcUserResult = amcUserService.createAmcAdmin(amcUser);


    return amcUserResult;
  }

  @AmcUserCreateChecker
  @PreAuthorize("hasRole('SYSTEM_ADMIN') or (hasRole('AMC_ADMIN') and hasPermission(#amcId,'crud_amcuser'))")
  @RequestMapping(value = "/amcid/{amcId}/amc-user/create_amc_user", method = RequestMethod.POST)
  @ResponseBody
  public String createAmcUser(@RequestBody AmcUser amcUser, @PathVariable Long amcId){

    amcUser.setCompanyId(amcId);
    AmcUser amcUserResult = amcUserService.createAmcUser(amcUser);
    if(amcUserResult == null){
      return "false";
    }

    return "succeed";
  }
  @PreAuthorize("hasRole('SYSTEM_ADMIN') or  hasPermission(#amcId,'PERM_AMCACCOUNT_VIEW')")
  @RequestMapping(value = "/amcid/{amcId}/amc-user/amcUsersTest", method = RequestMethod.POST)
  @ResponseBody
  @LogExecutionTime


  public List<AmcUser> getAmcUsersTest( @PathVariable Long amcId) {
    return amcUserService.getAmcUsers(amcId);
  }
  @PreAuthorize("hasRole('SYSTEM_ADMIN') or hasPermission(#amcId,'PERM_AMCACCOUNT_VIEW')")
  @RequestMapping(value = "/amcid/{amcId}/amc-user/amcUsers", method = RequestMethod.POST)
  @ResponseBody
  public AmcPage<AmcUserExt> getAmcUsers( @PathVariable Long amcId, @RequestBody  QueryParam queryParam) {
    Map<String, Direction> orderByParam = PageReqRepHelper.getOrderParam(queryParam.getPageInfo());
    if (CollectionUtils.isEmpty(orderByParam)) {
      orderByParam.put("id", Direction.DESC);
      orderByParam.put("user_name", Direction.DESC);
      orderByParam.put("update_date", Direction.DESC);
    }
    if(null == amcId || amcId < 0){
      amcId = Long.valueOf(AmcCmpyEnum.CMPY_WENSHENG.getId());
    }
    List<AmcUserExt> queryResults = null;
    Long totalCount = null;
    int offset = PageReqRepHelper.getOffset(queryParam.getPageInfo());
    AmcUserExample amcUserExample = SQLUtils.getAmcUserExample(queryParam);
    amcUserExample.createCriteria().andCompanyIdEqualTo(amcId);
    totalCount = amcUserService.queryAmcUserCount(amcUserExample);
    try{
      RowBounds rowBounds = new RowBounds(offset, queryParam.getPageInfo().getSize());
      try{
        amcUserExample.setOrderByClause(SQLUtils.getOrderBy(orderByParam, rowBounds));
      }catch (Exception ex){
        log.error("set order by exception:", ex);
      }
      queryResults = amcUserService.queryAmcUserPage(amcUserExample);
    }catch (Exception ex){
      log.error("got error when query:"+ex.getMessage());
      throw ex;
    }

//    Page<AmcAssetVo> page = PageReqRepHelper.getPageResp(totalCount, queryResults, assetQueryParam.getPageInfo());
    return PageReqRepHelper.getAmcPage(queryResults, totalCount );

  }

  @PreAuthorize("hasRole('SYSTEM_ADMIN') or (hasRole('AMC_ADMIN') and hasPermission(#amcId,'crud_amcuser'))")
  @RequestMapping(value = "/amcid/{amcId}/amc-user/amcUsers/searchByPhone", method = RequestMethod.POST)
  @ResponseBody
  public List<AmcUser> searchAmcUserByPhone( @PathVariable Long amcId, @RequestParam("mobilePhone") String mobilePhone)
      throws Exception {
    if(StringUtils.isEmpty(mobilePhone)){
      throw ExceptionUtils.getAmcException(AmcExceptions.MISSING_MUST_PARAM,
          String.format("mobilePhone:%s", mobilePhone));
    }

    List<AmcUser> amcUserResult = amcUserService.searchUserByPhone(mobilePhone);
    return amcUserResult;

  }

  @PreAuthorize("hasRole('SYSTEM_ADMIN') or (hasRole('AMC_ADMIN') and hasPermission(#amcId,'crud_amcuser'))")
  @RequestMapping(value = "/amcid/{amcId}/amc-user/amcUsers/searchByName", method = RequestMethod.POST)
  @ResponseBody
  public List<AmcUser> searchAmcUserByName( @PathVariable Long amcId, @RequestBody String name)
      throws Exception {
    if(StringUtils.isEmpty(name)){
      throw ExceptionUtils.getAmcException(AmcExceptions.MISSING_MUST_PARAM,
          String.format("name:%s", name));
    }

    List<AmcUser> amcUserResult = amcUserService.searchUserByName(name);
    return amcUserResult;

  }

  @PreAuthorize("hasRole('SYSTEM_ADMIN')")
  @RequestMapping(value = "/amc-user/allUsers", method = RequestMethod.POST)
  @ResponseBody
  public AmcPage<AmcUserExt> getAllUsers( @RequestBody QueryParam queryParam){

    Map<String, Direction> orderByParam = PageReqRepHelper.getOrderParam(queryParam.getPageInfo());


    if (CollectionUtils.isEmpty(orderByParam)) {
      orderByParam.put("id", Direction.DESC);
      orderByParam.put("user_name", Direction.DESC);
      orderByParam.put("update_date", Direction.DESC);
    }
    List<AmcUserExt> queryResults = null;
    Long totalCount = null;
    int offset = PageReqRepHelper.getOffset(queryParam.getPageInfo());
    try{

      queryResults = amcUserService.queryUserPage(offset, queryParam.getPageInfo().getSize(), queryParam,
          orderByParam);
      totalCount = amcUserService.queryUserCount(queryParam);


    }catch (Exception ex){
      log.error("got error when query:"+ex.getMessage());
      throw ex;
    }

//    Page<AmcAssetVo> page = PageReqRepHelper.getPageResp(totalCount, queryResults, assetQueryParam.getPageInfo());
    return PageReqRepHelper.getAmcPage(queryResults, totalCount );

  }
  @AmcUserModifyChecker
  @PreAuthorize("hasRole('SYSTEM_ADMIN')")
  @RequestMapping(value = "/amc-user/modifyUser", method = RequestMethod.POST)
  @ResponseBody
  public String modifyUser(@RequestParam Long userId, @RequestParam AmcUserValidEnum amcUserValidEnum) throws Exception{
    amcUserService.modifyUserValidState(userId, amcUserValidEnum);
    return "successed";
  }

  @PreAuthorize("hasRole('SYSTEM_ADMIN')")
  @RequestMapping(value = "/amc-user/delUser", method = RequestMethod.POST)
  @ResponseBody
  public String delUser(@RequestParam Long userId){
    amcUserService.delUser(userId);
    return "successed";
  }

  @AmcUserModifyChecker
  @PreAuthorize("hasRole('AMC_ADMIN') and hasPermission(#amcId, 'crud_amcuser')")
  @RequestMapping(value = "/amcid/{amcId}/amc-user/modifyUser", method = RequestMethod.POST)
  @ResponseBody
  public String modifyUser(@RequestParam Long userId,
      @PathVariable Long amcId, @RequestParam AmcUserValidEnum amcUserValidEnum) throws Exception {
    amcUserService.modifyUserValidState(userId, amcId, amcUserValidEnum);

    return "successed";
  }


  @PreAuthorize("hasRole('SYSTEM_ADMIN')")
  @RequestMapping(value = "/amcid/{amcid}/dept/amc-user/modifyRole", method = RequestMethod.POST)
  @ResponseBody
  public String modifyRole(@RequestBody UserRoleModifyVo userRoleModifyVo ){
    List<Long> roleIds =  userRoleModifyVo.getAmcRoleList().stream().map(item-> item.getId()).collect(Collectors.toList());
    amcUserService.modifyUserRole(userRoleModifyVo.getAmcUser().getId(), roleIds);
    return "successed";
  }



//  @PreAuthorize("hasRole('SYSTEM_ADMIN') and #oauth2.hasScope('write')")
  @PreAuthorize("hasRole('SYSTEM_ADMIN')")
  @RequestMapping(value = "/amc/amc-company/create", method = RequestMethod.POST)
  @ResponseBody
  public AmcCompany createCompany(@RequestBody AmcCompany amcCompany){

    AmcCompany amcCompanyResult = amcBasicService.createCompany(amcCompany);
    return amcCompanyResult;

  }

  @PreAuthorize("hasRole('SYSTEM_ADMIN')")
  @RequestMapping(value = "/amc/amc-company/companys", method = RequestMethod.POST)
  @ResponseBody
  public List<AmcCompany> queryCompany(){

    List<AmcCompany> amcCompanyResult = amcBasicService.queryCompany();
    return amcCompanyResult;

  }

  @RequestMapping(value = "/amc/amc-company/{companyId}/company", method = RequestMethod.POST)
  @ResponseBody
  public AmcCmpyEnum queryCompany(@PathVariable Long companyId){

    AmcCmpyEnum amcCmpyEnum = AmcCmpyEnum.lookupByDisplayIdUtil(companyId.intValue());
    return amcCmpyEnum;

  }

  @PreAuthorize("hasRole('SYSTEM_ADMIN') or  (hasRole('AMC_ADMIN') and hasPermission(#amcId, 'crud_amcuser'))")
  @RequestMapping(value = "/amc/amc-company/{amcId}/amc-department/create", method = RequestMethod.POST)
  @ResponseBody
  public AmcDept createDepartment(@RequestBody AmcDept amcDept, @PathVariable Long amcId){

    AmcDept amcDeptResult = amcBasicService.createDept(amcDept);
    return amcDeptResult;

  }

  @PreAuthorize("hasRole('SYSTEM_ADMIN') or hasPermission(#amcId, 'crud_amcuser')")
  @RequestMapping(value = "/amc/amc-company/{amcId}/amc-department/depts")
  @ResponseBody
  public Map<Long, List<AmcDept>> queryDepts( @PathVariable Long amcId){
    Map<Long, List<AmcDept>> result = null;
    if(amcId > 0){
      List<AmcDept> amcDeptResult = amcBasicService.queryDept(amcId);
      result = new HashMap<>();
      result.put(amcId, amcDeptResult);
    }else if (amcId == 0){
      log.info("it is system admin with amcid 0");
      List<AmcDept> amcDepts = amcBasicService.queryDept();
      if(!CollectionUtils.isEmpty(amcDepts)){
        result = new HashMap<>();
        for(AmcDept amcDept: amcDepts){
          if(!result.containsKey(amcDept.getCmpyId())){
            result.put(amcDept.getCmpyId(), new ArrayList<>());
          }
          result.get(amcDept.getCmpyId()).add(amcDept);
        }
      }else{
        log.error("Failed to query all depts");
        return new HashMap<>();
      }
    }


    return result;

  }

  @PreAuthorize("hasRole('SYSTEM_ADMIN') or hasRole('AMC_ADMIN')")
  @RequestMapping(value = "/amc/amc-company/createCmpyDept", method = RequestMethod.POST)
  @ResponseBody
  public AmcCmpyDeptVo createAmcCmpyDept(@RequestBody AmcCmpyDeptVo amcCmpyDeptVo) throws Exception {

    AmcCmpyDeptVo amcCmpyDeptVoResult = amcBasicService.createModifyCmpyDept(amcCmpyDeptVo);
    return amcCmpyDeptVoResult;
  }


  @PreAuthorize("hasRole('SYSTEM_ADMIN')")
  @RequestMapping(method = RequestMethod.POST, value = "/amcid/{amcId}/amc-user/amcUsers/revokeByName")
  @ResponseBody
  public void revokeTokenByUserName( @RequestParam String userName) {

    Collection<OAuth2AccessToken> accessTokens = tokenStore.findTokensByClientIdAndUserName(amcAdminClientId,
        userName);
    for(OAuth2AccessToken oAuth2AccessToken : accessTokens){
      tokenServices.revokeToken(oAuth2AccessToken.getValue());
    }
  }

  @RequestMapping(value = "/amcid/amcUsers", method = RequestMethod.POST)
  @ResponseBody
  public List<AmcBasicUser> getAmcBasicUsers(){
    List<AmcUser> amcUsers = amcUserService.getAllUsers();
    List<AmcBasicUser> amcBasicUsers = new ArrayList<>();
    for(AmcUser amcUser: amcUsers){
      AmcBasicUser amcBasicUser = new AmcBasicUser();
      AmcBeanUtils.copyProperties(amcUser, amcBasicUser);
      amcBasicUsers.add(amcBasicUser);
    }
    return amcBasicUsers;
  }

  @RequestMapping(value = "/sso/getUserDetailsById/{userId}", method = RequestMethod.POST)
  @ResponseBody
  public UserDetails getUserDetailsById(@PathVariable Long userId){

    return amcSsoService.getUserDetailByUserId(userId);
  }

  @RequestMapping(value = "/sso/specialUser/mod", method =  RequestMethod.POST)
  @ResponseBody
  public String modSpecialUser(@RequestBody Long userId, @RequestBody List<Long> permIds){
    long currentUser = -1L;
    return amcSsoService.modifySpecUser(userId, permIds, currentUser);
  }

  @RequestMapping(value = "/sso/specialUser/get", method =  RequestMethod.POST)
  @ResponseBody
  public List<AmcSpecialUserVo> getSpecialUser(){

    return amcSsoService.getSpecUser();
  }

  @RequestMapping(value = "/sso/userRoleRules/get", method =  RequestMethod.POST)
  @ResponseBody
  public List<AmcUserRoleRule> getZccRoleRules(){

    return amcSsoService.getZccRoleRules();
  }

  @RequestMapping(value = "/sso/userRoleRules/create", method =  RequestMethod.POST)
  @ResponseBody
  public String  createZccRoleRules(@RequestBody List<AmcUserRoleRule> amcUserRoleRules){
    Long userId = -1L;
    return amcSsoService.createZccRoleRules(amcUserRoleRules, userId);
  }


  @RequestMapping(value = "/sso/userRoleRules/modify", method =  RequestMethod.POST)
  @ResponseBody
  public String  modifyZccRoleRules(@RequestBody List<AmcUserRoleRule> amcUserRoleRules){
    Long userId = -1L;
    return amcSsoService.modifyZccRoleRules(amcUserRoleRules, userId);
  }

  @RequestMapping(value = "/sso/roleperms/get", method = RequestMethod.POST)
  @ResponseBody
  public List<AmcRolePermission> getAmcRolePerms(){
    List<AmcRolePermission> amcRolePermissions = amcUserService.getAmcRolePerms();
    return amcRolePermissions;
  }

  @RequestMapping(value = "/sso/roleperms/createAndUpdate", method = RequestMethod.POST)
  @ResponseBody
  public boolean createAndUpdate(@RequestBody RolePermVo rolePermVo){
    return amcUserService.updateOrCreateRolePerms(rolePermVo.getRolePerms());

  }

  @Data
  class AmcBasicUser{
    Long id;
    String userName;
    String mobilePhone;
  }





}
