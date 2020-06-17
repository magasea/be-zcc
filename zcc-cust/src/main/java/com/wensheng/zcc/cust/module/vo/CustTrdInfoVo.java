package com.wensheng.zcc.cust.module.vo;

import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class CustTrdInfoVo {
  Long custId;
  String custName;
  String custCity = "-1";
  Map<Integer, Integer> investType2Counts;
  Integer trdCount;
  Long debtTotalAmount;
  Long trdTotalAmount;

  Map<String, Integer> intrestCities;
  String phone;

  String mobileUpdate;
  String mobilePrep;
  String phoneUpdate;
  String phonePrep;

  String address;
  String addressUpdate;

  //公司爬取状态
  String crawledStatus;
}
