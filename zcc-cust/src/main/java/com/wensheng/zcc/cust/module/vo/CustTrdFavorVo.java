package com.wensheng.zcc.cust.module.vo;

import java.util.Map;
import lombok.Data;

@Data
public class CustTrdFavorVo {
  long custId;
  int custType;
  Map<String, Integer> investType2Counts;


  Map<String, Integer> intrestCities;


}
