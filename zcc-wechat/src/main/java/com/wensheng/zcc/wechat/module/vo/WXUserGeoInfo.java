package com.wensheng.zcc.wechat.module.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WXUserGeoInfo extends WXNotifyMsg{


  @JsonProperty("Latitude")
  Double latitude;
  @JsonProperty("Longitude")
  Double longitude;
  @JsonProperty("Precision")
  Double precision;

}
