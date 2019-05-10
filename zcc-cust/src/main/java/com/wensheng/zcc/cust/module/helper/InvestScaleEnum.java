package com.wensheng.zcc.cust.module.helper;

import com.wensheng.zcc.common.utils.base.EnumUtils;
import java.util.function.Function;

/**
 * @author chenwei on 4/16/19
 * @project miniapp-backend
 */
public enum InvestScaleEnum {
  INVEST_SCALE_LVL1(1, "5000000", 5000000L),
  INVEST_SCALE_LVL2(2, "10000000", 10000000L),
  INVEST_SCALE_LVL3(3, "20000000", 20000000L),
  INVEST_SCALE_LVL4(4, "50000000", 50000000L),
  INVEST_SCALE_LVL5(5, "100000000", 100000000L),

  ;

  int id;
  String name;
  Long amount;

  InvestScaleEnum(int id, String name, Long amount){
    this.id = id;
    this.name = name;
    this.amount = amount;
  }


  private static final Function<Integer, InvestScaleEnum> func =
      EnumUtils.lookupMap(InvestScaleEnum.class, e -> e.getId());
  public static InvestScaleEnum lookupByIdUntil(int id) {
    return func.apply(id);
  }




  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }


  public Long getAmount() {
    return amount;
  }
}
