package com.wensheng.zcc.cust.module.vo;

import java.util.List;
import lombok.Data;

@Data
public class CustCountVo {
  private Long allPersonCount;
  private Long allCmpycount;
  private Long updatePersonCount;
  private Long updateCmpycount;
  private Long creatPersonCount;
  private Long creatCmpycount;
  private Long tradePersonCount;
  private Long tradeCmpycount;
}
