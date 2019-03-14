package com.wensheng.zcc.sso.controller;

import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcRole;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcUser;
import com.wensheng.zcc.sso.module.vo.UserRoleModifyVo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author chenwei on 3/14/19
 * @project zcc-backend
 */
@Controller
@RequestMapping("/amc/user")
public class AmcUserController {

  @PreAuthorize("#oauth2.clientHasRole('AMC_ADMIN') and #oauth2.hasScope('write')")
  @RequestMapping(value = "/amcid/{amcid}/dept/amc-user/create", method = RequestMethod.POST)
  public String createUser(@RequestBody AmcUser amcUser){
    return "successed";

  }

  @PreAuthorize("#oauth2.clientHasRole('SYSTEM_ADMIN') and #oauth2.hasScope('write')")
  @RequestMapping(value = "/amcid/{amcid}/dept/amc-user/modifyRole", method = RequestMethod.POST)
  public String modifyRole(@RequestBody UserRoleModifyVo userRoleModifyVo ){

    return "successed";

  }


}
