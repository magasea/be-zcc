package com.wensheng.zcc.comnfunc.service;

/**
 * @author chenwei on 2/21/19
 * @project zcc-backend
 */
public interface PhoneMsgService {

  /**
   *
   * @param phoneNum
   * @return verify code
   */
  String generateVerificationCodeToPhone(String phoneNum, String vcode);

  public String generateVerificationCodeToPhoneByAliYun(String phoneNum, String vcode);

}
