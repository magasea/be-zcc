package com.wensheng.zcc.wechat.module.dto;

import lombok.Data;

@Data
public class GaodeIpCity {
  String province;
  String provinceCode;
  String city;
  String adcode;
  Double lat;
  Double lng;
}
