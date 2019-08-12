package com.wensheng.zcc.cust.module.sync;

import lombok.Data;

@Data
public class CustCmpyInfoFromSync {
  String id;
  Integer page;
  Integer rows;
  Long createTime;
  Long updateTime;
  String cmpyName;
  String uniSocialCode;
  String legalReptive;
  String cmpyPhone;
  String cmpyAddr;
  String annuReptPhone;
  String annuReptAddr;
  String notes;
  String dataStatus;
  String provinceCode;
  String provinceName;
  String cityCode;
  String cityName;
  String updateFromDate;
  String updateToDate;
  String updateFrom;
  String updateTo;
}
