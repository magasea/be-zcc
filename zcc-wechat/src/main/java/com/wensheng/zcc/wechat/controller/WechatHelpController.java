package com.wensheng.zcc.wechat.controller;

import com.wensheng.zcc.wechat.service.impl.AmcGaoDeGrpcServImpl;
import com.wensheng.zcc.wechat.service.impl.AmcGaoDeLogisQuery;
import com.wensheng.zcc.wechat.service.impl.WXMaterialServiceImpl;
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

  @Autowired
  WXMaterialServiceImpl wxMaterialService;

  @Autowired
  AmcGaoDeGrpcServImpl amcGaoDeGrpcServ;

  @RequestMapping(value = "/fillUserGeoAddr", method = RequestMethod.POST)
  @ResponseBody
  public String fillUserGeoAddr() throws Exception {


    amcGaoDeGrpcServ.updateGeoAddressOfUser();
     return "success";

  }

  @RequestMapping(value = "/testSent", method = RequestMethod.POST)
  @ResponseBody
  public String testSent(String msg) throws Exception {


    wxMaterialService.recordMsgResult(msg);
    return "success";

  }

}
