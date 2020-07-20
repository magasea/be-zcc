package com.wensheng.zcc.wechat.config;

import com.wensheng.zcc.common.utils.AmcExceptions;

public enum WechatExceptionEnum  {
  PHONE_CODE_MISMATCH("0204015001","手机号和验证码不匹配", "您的手机号和验证码不匹配，请重新获取验证码"),
  PHONE_CODE_LIMIT_EXCEED("0204015002","手机号码收到验证码超过 5 次", "您今天获取的验证码已超过 5 次，请明天再尝试"),

  ;

  String code;
  String name;
  String reason;
  WechatExceptionEnum(String code, String name, String reason){
    this.code = code;
    this.name = name;
    this.reason = reason;
  }



}
