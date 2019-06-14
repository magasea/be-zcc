package com.wensheng.zcc.wechat.service;

import com.wensheng.zcc.wechat.utils.wechat.AesException;
import javax.xml.parsers.ParserConfigurationException;

public interface WXBasicService {
  public String getPublicToken();

  String checkWechatResp(Long timeStamp, String nonce, String echostr, String signature) throws AesException, ParserConfigurationException;
}
