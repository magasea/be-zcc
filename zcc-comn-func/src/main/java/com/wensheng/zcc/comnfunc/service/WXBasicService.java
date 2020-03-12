package com.wensheng.zcc.comnfunc.service;

import com.wensheng.zcc.comnfunc.utils.wechat.AesException;
import javax.xml.parsers.ParserConfigurationException;

public interface WXBasicService {
  public String getPublicToken(String profileName);

  String checkWechatResp(Long timeStamp, String nonce, String echostr, String signature) throws AesException, ParserConfigurationException, AesException;
}
