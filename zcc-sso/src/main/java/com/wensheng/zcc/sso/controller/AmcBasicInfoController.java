package com.wensheng.zcc.sso.controller;

import com.google.common.collect.Lists;
import com.wensheng.zcc.common.params.AmcBranchLocationEnum;
import com.wensheng.zcc.common.params.AmcCmpyEnum;
import com.wensheng.zcc.common.params.sso.AmcDeptEnum;
import com.wensheng.zcc.common.params.sso.AmcLocationEnum;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcPermission;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcRole;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcRolePermission;
import com.wensheng.zcc.common.params.sso.AmcUserValidEnum;
import com.wensheng.zcc.sso.service.AmcUserService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
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



  @RequestMapping(value = "/perms", method = RequestMethod.POST)
  @ResponseBody
  public List<AmcPermission> getAmcPerms(){
    List<AmcPermission> amcPermissions = amcUserService.getAmcPerms();
    return amcPermissions;
  }

  @RequestMapping(value = "/userStates", method = RequestMethod.POST)
  @ResponseBody
  public List<String> getUserStates(){

    List<String> result = new ArrayList<>();
    for(AmcUserValidEnum amcUserValidEnum : AmcUserValidEnum.values()){
      result.add(String.format("%d:%s", amcUserValidEnum.getId(), amcUserValidEnum.getName()));
    }
    return result;
  }

  @RequestMapping(value = "/amcLocations", method = RequestMethod.POST)
  @ResponseBody
  public List<String> getAmcBranchLocations(){

    List<String> result = new ArrayList<>();
    for(AmcLocationEnum amcLocationEnum : AmcLocationEnum.values()){
      result.add(String.format("%d:%s:%s", amcLocationEnum.getId(), amcLocationEnum.getName(),
          amcLocationEnum.getCname()));
    }
    return result;
  }
  @RequestMapping(value = "/amcCompanies", method = RequestMethod.POST)
  @ResponseBody
  public List<String> getAmcCompanies(){

    List<String> result = new ArrayList<>();
    for(AmcCmpyEnum amcCmpyEnum : AmcCmpyEnum.values()){
      result.add(String.format("%d:%s:%s", amcCmpyEnum.getId(), amcCmpyEnum.getName(), amcCmpyEnum.getCname()
      ));
    }
    return result;
  }
  @RequestMapping(value = "/amcDepts", method = RequestMethod.POST)
  @ResponseBody
  public List<String> getAmcDepts(){

    List<String> result = new ArrayList<>();
    for(AmcDeptEnum amcDeptEnum : AmcDeptEnum.values()){
      result.add(String.format("%d:%s:%s:%s", amcDeptEnum.getId(), amcDeptEnum.getName(), amcDeptEnum.getCname(),
          amcDeptEnum.isUsed()
      ));
    }
    return result;
  }

  @RequestMapping(value = "/amcDeptsEnums", method = RequestMethod.POST)
  @ResponseBody
  public AmcDeptEnum[] getAmcDeptsEnums(){

    return AmcDeptEnum.values();

  }
}
