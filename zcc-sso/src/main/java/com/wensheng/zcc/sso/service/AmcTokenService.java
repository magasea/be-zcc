package com.wensheng.zcc.sso.service;

public interface AmcTokenService {
  boolean revokeTokenByMobilePhone(String mobilePhone);
  boolean revokeTokenAll();
}
