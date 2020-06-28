package com.wensheng.zcc.wechat.module.vo;

import lombok.Data;

@Data
public class UserLngLat {
  String openId;
  Double lng;
  Double lat;
  String lastIp;
  String lastIpCityCode;
  String lastIpCityName;
}
