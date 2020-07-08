package com.wensheng.zcc.cust.module.vo;

import java.util.List;
import lombok.Data;

@Data
public class CustCountVo {
  private Integer allPersonCount;
  private Integer allCmpycount;
  private Integer updatePersonCount;
  private Integer updateCmpycount;
  private Integer creatPersonCount;
  private Integer creatCmpycount;
  private Integer tradePersonCount;
  private Integer tradeCmpycount;
}
