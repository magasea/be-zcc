package com.wensheng.zcc.sso.controller;

import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcCompany;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcDept;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcUser;
import com.wensheng.zcc.sso.module.vo.AmcCmpyDeptVo;
import com.wensheng.zcc.sso.module.vo.UserRoleModifyVo;
import com.wensheng.zcc.sso.service.AmcBasicService;
import com.wensheng.zcc.sso.service.AmcUserService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
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

  @PreAuthorize("#oauth2.clientHasRole('AMC_ADMIN') and #oauth2.hasScope({amcId})")
  @RequestMapping(value = "/amcid/{amcid}/dept/amc-user/create", method = RequestMethod.POST)
  @ResponseBody
  public AmcUser createUser(@RequestBody AmcUser amcUser){

    AmcUser amcUserResult = amcUserService.createUser(amcUser);
    return amcUserResult;
  }

  @PreAuthorize("hasRole('AMC_ADMIN') and #oauth2.hasScope('write')")
  @RequestMapping(value = "/amcid/{amcid}/dept/amc-user/modifyRole", method = RequestMethod.POST)
  @ResponseBody
  public String modifyRole(@RequestBody UserRoleModifyVo userRoleModifyVo ){
    List<Long> roleIds =  userRoleModifyVo.getAmcRoleList().stream().map(item-> item.getId()).collect(Collectors.toList());
    amcUserService.modifyUserRole(userRoleModifyVo.getAmcUser().getId(), roleIds);
    return "successed";
  }

//  @PreAuthorize("#oauth2.clientHasRole('SYSTEM_ADMIN') and #oauth2.hasScope('write')")
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

  @PreAuthorize("hasRole('SYSTEM_ADMIN') or  (hasRole('AMC_ADMIN') and hasPermission(#amcId, 'write'))")
  @RequestMapping(value = "/amc/amc-company/{amcId}/amc-department/create", method = RequestMethod.POST)
  @ResponseBody
  public AmcDept createDepartment(@RequestBody AmcDept amcDept, @PathVariable Long amcId){

    AmcDept amcDeptResult = amcBasicService.createDept(amcDept);
    return amcDeptResult;

  }

  @PreAuthorize("hasRole('SYSTEM_ADMIN') or hasPermission(#amcId, 'read')")
  @RequestMapping(value = "/amc/amc-company/{amcId}/amc-department/depts")
  @ResponseBody
  public List<AmcDept> queryDepts( @PathVariable Long amcId){

    List<AmcDept> amcDeptResult = amcBasicService.queryDept(amcId);
    return amcDeptResult;

  }

  @PreAuthorize("#oauth2.clientHasRole('SYSTEM_ADMIN') or #oauth2.hasScope('AMC_ADMIN')")
  @RequestMapping(value = "/amc/amc-company/createCmpyDept", method = RequestMethod.POST)
  @ResponseBody
  public AmcCmpyDeptVo createAmcCmpyDept(@RequestBody AmcCmpyDeptVo amcCmpyDeptVo) throws Exception {

    AmcCmpyDeptVo amcCmpyDeptVoResult = amcBasicService.createModifyCmpyDept(amcCmpyDeptVo);
    return amcCmpyDeptVoResult;
  }




}