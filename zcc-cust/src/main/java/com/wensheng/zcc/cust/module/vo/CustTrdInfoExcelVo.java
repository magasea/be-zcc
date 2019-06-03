package com.wensheng.zcc.cust.module.vo;

import java.util.Map;
import lombok.Data;

@Data
public class CustTrdInfoExcelVo {
  Long custId;
  String custName;
  Map<String, Integer> investType2Counts;
  Integer trdCount;
  Long trdTotalAmount;
  Map<String, Integer> intrestCities;
  String phone;
  String address;

}
