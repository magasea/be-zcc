package com.wensheng.zcc.sso.module.vo;

import lombok.Data;

/**
 * @author chenwei on 2/20/19
 * @project zcc-backend
 */
@Data
public class UserCreateVo {

  String userName;
  String password;
  String repPasswd;
  String phoneNum;
  String verificationCode;
  String department;
  String group;
  String role_name;

}
