package com.wensheng.zcc.wechat.controller;

import com.wensheng.zcc.wechat.service.impl.AmcGaoDeLogisQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@RequestMapping(value = "/ws/wechat/helper")
public class WechatHelpController {

  @Autowired
  AmcGaoDeLogisQuery amcGaoDeLogisQuery;

  @RequestMapping(value = "/fillUserGeoAddr", method = RequestMethod.POST)
  @ResponseBody
  public String fillUserGeoAddr() throws Exception {


     amcGaoDeLogisQuery.updateGeoAddressOfUser();
     return "success";

  }

}
