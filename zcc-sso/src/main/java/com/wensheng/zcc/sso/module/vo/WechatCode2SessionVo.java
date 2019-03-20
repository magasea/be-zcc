package com.wensheng.zcc.sso.module.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author chenwei on 3/20/19
 * @project miniapp-backend
 */
@Data
public class WechatCode2SessionVo {
  @JsonProperty("session_key")
  String sessionKey;
  String openid;
  String unionid;
  String errcode;
}
