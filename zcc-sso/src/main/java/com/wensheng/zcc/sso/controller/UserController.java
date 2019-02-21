package com.wensheng.zcc.sso.controller;

import com.wensheng.zcc.sso.module.vo.LoginVo;
import com.wensheng.zcc.sso.module.vo.UserCreateVo;
import com.wensheng.zcc.sso.service.util.VerifyCodeUtil;
import java.awt.Image;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

  @RequestMapping(value="/getImage",method= RequestMethod.GET)
  public void authImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
    response.setContentType("image/jpeg");
    // 生成随机字串
    String verifyCode = VerifyCodeUtil.getAuthCode();
    // 存入会话session
    HttpSession session = request.getSession(true);
    // 删除以前的
    session.removeAttribute("verCode");
    session.removeAttribute("codeTime");
    session.setAttribute("verCode", verifyCode.toLowerCase());
    session.setAttribute("codeTime", LocalDateTime.now());
    // 生成图片
//    int w = 100, h = 30;
    OutputStream out = response.getOutputStream();
    VerifyCodeUtil.outputImage( out, verifyCode);
//    HttpHeaders headers = new HttpHeaders();
//    ResponseEntity<byte[]> responseEntity = new ResponseEntity<byte[]>(VerifyCodeUtil.outputImage(verifyCode), headers,
//        HttpStatus.OK);
//    return responseEntity;
  }

  
}
