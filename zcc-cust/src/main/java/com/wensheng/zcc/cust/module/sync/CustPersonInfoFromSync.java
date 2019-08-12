package com.wensheng.zcc.cust.module.sync;

import lombok.Data;

@Data
public class CustPersonInfoFromSync {
  String id;
  Integer page;
  Integer rows;
  Long createTime;
  Long updateTime;
  String name;
  String address;
  String customerId;
  String citys;
  Integer ageRange;
  Integer gender;
  String mobileNum;
  String telNum;
  String email;
  String idCardNum;
  String province;
  String city;
  String notes;
  String dataStatus;
  String birthday;
  String provinceCode;
  String provinceName;
  String cityCode;
  String cityName;
  String birthdayStr;
  String updateFromDate;
  String updateToDate;
  String updateFrom;
  String updateTo;
}
