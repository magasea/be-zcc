package com.wensheng.zcc.wechat.module.vo;

import lombok.Data;

@Data
public class WXSign4Url {
  String accessToken;
  String ticket;
  String signKey;
  String randomStr;
  Long timeStamp;
  String url;

}
