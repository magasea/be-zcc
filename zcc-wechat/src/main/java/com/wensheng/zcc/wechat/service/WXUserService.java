package com.wensheng.zcc.wechat.service;

public interface WXUserService {
  public void tagUserTask() throws Exception;

  void syncUserInfoFromWX();
}
