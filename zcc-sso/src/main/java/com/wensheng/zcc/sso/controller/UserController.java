package com.wensheng.zcc.sso.controller;

import com.wensheng.zcc.sso.module.vo.LoginVo;
import com.wensheng.zcc.sso.module.vo.UserCreateVo;
import java.awt.Image;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author chenwei on 1/31/19
 * @project zcc-backend
 */
@Controller

public class UserController {

  private static final String VERIFY_CODE = "verify_code";
  private static final String VERIFY_CODE_IMAGE = "verify_code_image";


  @RequestMapping(value = "/user/verifycode")
  public boolean verifyVCode(String vcode){
    return false;
  }

  @RequestMapping(value = "/user/getvc")
  public Image getVC(){
    return null;
  }


  @RequestMapping(value = "/login")
  @ResponseBody
  public String login(@RequestBody LoginVo loginVo){
    System.out.println("loginVo:" + loginVo.getUserName());
    return "wensheng";
  }

  @PreAuthorize("#oauth2.hasScope('client') and #oauth2.hasScope('write')")
  @RequestMapping(value="/amc/user/create")
  @ResponseBody
  public String createUser(@RequestBody UserCreateVo userCreateVo){

    return "success";
  }

}
