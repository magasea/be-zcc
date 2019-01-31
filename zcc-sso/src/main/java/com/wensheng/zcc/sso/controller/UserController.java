package com.wensheng.zcc.sso.controller;

import java.awt.Image;
import org.hibernate.validator.constraints.pl.REGON.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

  @RequestMapping(value = "user/getvc")
  public Image getVC(){
    return null;
  }

}
