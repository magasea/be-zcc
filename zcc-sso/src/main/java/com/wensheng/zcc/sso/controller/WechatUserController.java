package com.wensheng.zcc.sso.controller;

import com.wensheng.zcc.sso.module.vo.WechatLogin;
import com.wensheng.zcc.sso.module.vo.WechatLoginResult;
import com.wensheng.zcc.sso.service.WechatService;
import com.wensheng.zcc.sso.service.util.VerifyCodeUtil;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author chenwei on 3/14/19
 * @project zcc-backend
 */
@Controller
@RequestMapping(value="/wechat")
public class WechatUserController {

  @Autowired
  WechatService wechatService;

  @RequestMapping(value="/wechatLogin",method= RequestMethod.POST)
  @ResponseBody
  public WechatLoginResult wechatLogin(@RequestBody WechatLogin login) throws IOException {

    return wechatService.loginWechat(login.getCode());

  }

  @RequestMapping(value="/registryPhone",method= RequestMethod.POST)
  @ResponseBody
  public WechatLoginResult wechatRegistryPhone(@RequestBody WechatLogin login) throws IOException {

    return wechatService.loginWechat(login.getCode());

  }


}
