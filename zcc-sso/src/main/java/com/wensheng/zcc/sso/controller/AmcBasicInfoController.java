package com.wensheng.zcc.sso.controller;

import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcRole;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcRolePermission;
import com.wensheng.zcc.sso.service.AmcUserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author chenwei on 3/15/19
 * @project zcc-backend
 */
@Controller
@RequestMapping("/amc/sso/basicInfo")
public class AmcBasicInfoController {

  @Autowired
  AmcUserService amcUserService;

  @RequestMapping(value = "/roles", method = RequestMethod.POST)
  @ResponseBody
  public List<AmcRole> getAmcRoles(){

    List<AmcRole> amcRoles = amcUserService.getAmcRoles();
    return amcRoles;

  }

  @RequestMapping(value = "/role-perms", method = RequestMethod.POST)
  @ResponseBody
  public List<AmcRolePermission> getAmcRolePerms(){
    List<AmcRolePermission> amcRolePermissions = amcUserService.getAmcRolePerms();
    return amcRolePermissions;
  }


}
