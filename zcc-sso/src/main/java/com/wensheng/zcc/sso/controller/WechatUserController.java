package com.wensheng.zcc.sso.controller;

import com.wensheng.zcc.common.mq.kafka.module.WechatUserLocation;
import com.wensheng.zcc.sso.module.vo.WechatLogin;
import com.wensheng.zcc.sso.module.vo.WechatLoginResult;
import com.wensheng.zcc.sso.module.vo.WechatPhoneRegistry;
import com.wensheng.zcc.common.module.dto.WechatUserInfo;
import com.wensheng.zcc.sso.service.WechatService;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author chenwei on 3/14/19
 * @project zcc-backend
 */
@Controller
@RequestMapping(value="/wechat")
@Slf4j
public class WechatUserController {

  @Autowired
  WechatService wechatService;

  @RequestMapping(value="/wechatLogin",method= RequestMethod.POST)
  @ResponseBody
  public WechatLoginResult wechatLogin(@RequestBody WechatLogin login) throws IOException {

    return wechatService.loginWechat(login.getCode());

  }

  @RequestMapping(value="/wechatPubLogin",method= RequestMethod.POST)
  @ResponseBody
  public WechatLoginResult wechatPubLogin(@RequestBody WechatLogin login) throws IOException {
    return wechatService.loginPubWechat(login);

  }

  @RequestMapping(value="/openplatform/wechatLogin",method= RequestMethod.POST)
  @ResponseBody
  public WechatLoginResult wechatLoginOpenPlatform(@RequestBody WechatLogin login) throws IOException {

    return wechatService.loginWechatOpenPlatform(login.getCode());

  }

  @RequestMapping(value="/openplatform/wechatUserInfo",method= RequestMethod.POST)
  @ResponseBody
  public WechatUserInfo wechatLoginOpenPlatform(@RequestParam String openId, @RequestParam String token) throws IOException {

    return wechatService.getWechatUserInfo(openId, token);

  }

  @RequestMapping(value="/registryPhone",method= RequestMethod.POST)
  @ResponseBody
  public String wechatRegistryPhone(@RequestBody WechatPhoneRegistry phoneRegistry) throws IOException {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    phoneRegistry.setOpenId(authentication.getName());
    return wechatService.registryPhone(phoneRegistry);

  }

  @RequestMapping(value="/registryLocation",method= RequestMethod.POST)
  @ResponseBody
  public String wechatRegistryLocation(@RequestBody WechatUserLocation wechatUserLocation) throws IOException {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    wechatUserLocation.setOpenId(authentication.getName());
    wechatService.registryUserLocation(wechatUserLocation);
    return "succeed";

  }



}
