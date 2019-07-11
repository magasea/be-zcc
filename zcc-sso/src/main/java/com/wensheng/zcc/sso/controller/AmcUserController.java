package com.wensheng.zcc.sso.controller;

import com.wensheng.zcc.common.utils.AmcBeanUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils.AmcExceptions;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcCompany;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcDept;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcUser;
import com.wensheng.zcc.sso.module.helper.AmcUserValidEnum;
import com.wensheng.zcc.sso.module.vo.AmcCmpyDeptVo;
import com.wensheng.zcc.sso.module.vo.UserCreateVo;
import com.wensheng.zcc.sso.module.vo.UserRoleModifyVo;
import com.wensheng.zcc.sso.service.AmcBasicService;
import com.wensheng.zcc.sso.service.AmcUserService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
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
public class AmcUserController {

  @Autowired
  AmcUserService amcUserService;

  @Autowired
  AmcBasicService amcBasicService;

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

  @PreAuthorize("hasRole('SYSTEM_ADMIN') or (hasRole('AMC_ADMIN') and hasPermission(#amcId,'crud_amcuser'))")
  @RequestMapping(value = "/amcid/{amcId}/amc-user/amcUsers", method = RequestMethod.POST)
  @ResponseBody
  public List<AmcUser> getAmcUsers( @PathVariable Long amcId){

    List<AmcUser> amcUserResult = amcUserService.getAmcUsers(amcId);
    return amcUserResult;

  }

  @PreAuthorize("hasRole('SYSTEM_ADMIN') or (hasRole('AMC_ADMIN') and hasPermission(#amcId,'crud_amcuser'))")
  @RequestMapping(value = "/amcid/{amcId}/amc-user/amcUsers/search", method = RequestMethod.POST)
  @ResponseBody
  public List<AmcUser> searchAmcUser( @PathVariable Long amcId, @RequestParam("mobilePhone") String mobilePhone)
      throws Exception {
    if(StringUtils.isEmpty(mobilePhone)){
      throw ExceptionUtils.getAmcException(AmcExceptions.MISSING_MUST_PARAM,
          String.format("mobilePhone:%s", mobilePhone));
    }

    List<AmcUser> amcUserResult = amcUserService.searchUser(mobilePhone);
    return amcUserResult;

  }

  @PreAuthorize("hasRole('SYSTEM_ADMIN')")
  @RequestMapping(value = "/amc-user/allUsers", method = RequestMethod.POST)
  @ResponseBody
  public List<AmcUser> getAllUsers( ){

    List<AmcUser> amcUserResult = amcUserService.getAllUsers();
    return amcUserResult;

  }

  @PreAuthorize("hasRole('SYSTEM_ADMIN')")
  @RequestMapping(value = "/amc-user/modifyUser", method = RequestMethod.POST)
  @ResponseBody
  public String modifyUser(@RequestParam Long userId, @RequestParam AmcUserValidEnum amcUserValidEnum){
    amcUserService.modifyUserValidState(userId, amcUserValidEnum);
    return "successed";
  }

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

  @PreAuthorize("hasRole('SYSTEM_ADMIN') or hasPermission(#companyId, 'AMC_VIEW')")
  @RequestMapping(value = "/amc/amc-company/{companyId}/company", method = RequestMethod.POST)
  @ResponseBody
  public AmcCompany queryCompany(@PathVariable Long companyId){

    AmcCompany amcCompanyResult = amcBasicService.queryCompany(companyId);
    return amcCompanyResult;

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
  public List<AmcDept> queryDepts( @PathVariable Long amcId){

    List<AmcDept> amcDeptResult = amcBasicService.queryDept(amcId);
    return amcDeptResult;

  }

  @PreAuthorize("hasRole('SYSTEM_ADMIN') or hasRole('AMC_ADMIN')")
  @RequestMapping(value = "/amc/amc-company/createCmpyDept", method = RequestMethod.POST)
  @ResponseBody
  public AmcCmpyDeptVo createAmcCmpyDept(@RequestBody AmcCmpyDeptVo amcCmpyDeptVo) throws Exception {

    AmcCmpyDeptVo amcCmpyDeptVoResult = amcBasicService.createModifyCmpyDept(amcCmpyDeptVo);
    return amcCmpyDeptVoResult;
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

  @Data
  class AmcBasicUser{
    Long id;
    String userName;
    String mobilePhone;
  }




}
