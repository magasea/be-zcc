package com.wensheng.zcc.wechat.controller;

import com.wensheng.zcc.wechat.service.impl.AesException;
import com.wensheng.zcc.wechat.service.impl.WXServiceImpl;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xml.sax.SAXException;

@Controller
@RequestMapping(value = "/ws/wechat")
public class WechatController {

  @Autowired
  WXServiceImpl wxService;

  @RequestMapping(value = "/check", method = RequestMethod.GET)
  @ResponseBody
  public String checkWechat(@RequestParam("signature") String signature, @RequestParam("echostr") String echostr,
      @RequestParam("nonce") String nonce, @RequestParam("timestamp") Long timeStamp)
      throws ParserConfigurationException, AesException, SAXException, IOException {

    String response = wxService.checkWechatResp(timeStamp, nonce, echostr, signature);
    return response;

  }



}
