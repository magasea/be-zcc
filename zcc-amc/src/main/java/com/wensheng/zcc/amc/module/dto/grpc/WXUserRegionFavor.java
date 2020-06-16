package com.wensheng.zcc.amc.module.dto.grpc;

import lombok.Data;

@Data
public class WXUserRegionFavor {
  String openId = null;
  String unionId = null;
  Double lastLat = null;
  Double lastLng = null;
  String locationCode = null;
  String locationCityName = null;
  String locationProvName = null;
  String lastIp = null;
  String lastIpCity = null;
  String lastIpCityCode = null;
  String lastIpProv = null;
  Double lastIpLat = null;
  Double lastIpLng = null;
}
