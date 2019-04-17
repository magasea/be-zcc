package com.wensheng.zcc.cust.module.helper;

/**
 * @author chenwei on 4/16/19
 * @project miniapp-backend
 */
public enum InvestScaleEnum {
  INVEST_SCALE_LVL1(1, "5000000"),
  INVEST_SCALE_LVL2(2, "10000000"),
  INVEST_SCALE_LVL3(3, "20000000"),
  INVEST_SCALE_LVL4(4, "50000000"),
  INVEST_SCALE_LVL5(5, "100000000"),

  ;

  int id;
  String name;

  InvestScaleEnum(int id, String name){
    this.id = id;
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

}
