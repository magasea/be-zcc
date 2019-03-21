package com.wensheng.zcc.sso.module.vo;

import lombok.Data;

/**
 * @author chenwei on 3/14/19
 * @project zcc-backend
 */
@Data
public class WechatPhoneRegistry {
  String encryptedData;
  String iv;

}
