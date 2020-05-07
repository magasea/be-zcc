package com.wensheng.zcc.wechat.service;

public interface WXUserService {
  public void tagUserTask() throws Exception;

  void syncUserInfoFromWX();

  boolean sendPhoneVcode(String openId, String phone, String code) throws Exception;

  boolean bindPhone(String openId, String phone, String code) throws Exception;

  String userSubscribe(String xmlMsg);

  String userMsg(String xmlMsg);
}
