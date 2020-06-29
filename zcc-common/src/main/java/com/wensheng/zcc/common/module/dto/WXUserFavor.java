package com.wensheng.zcc.common.module.dto;

import java.util.Date;
import java.util.List;
import javax.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "WECHAT_WXUSERFAVOR")
@Data
public class WXUserFavor {

  @Id
  String id;
  @Indexed(unique = true)
  String openId;
  String unionId;
  String phone;
  //城市编码
  Double lastLat;
  Double lastLng;
  Date lastLocationTime;
  String locationCode;
  String locationCityName;
  String locationProvName;
  String lastIp;
  String lastIpCity;
  String lastIpProv;
  String lastIpCityCode;
  Double lastIpLat;
  Double lastIpLng;
  String userPrefCityName;
  String userPrefCityCode;
  String userPrefProvName;
  String userPrefProvCode;
  Double userPrefCityLat;
  Double userPrefCityLng;
  AmcSaleFilter amcSaleFilter;
  List<Long> contactorIds;
  Date createTime;
  Date updateTime;

}
