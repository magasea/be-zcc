package com.wensheng.zcc.cust.module.helper;

/**
 * @author chenwei on 4/16/19
 * @project miniapp-backend
 */

public enum InvestTypeEnum {
  BADASSETPACK(1, "不良资产包"),
  SINGLEPROJ(2, "单体项目"),
  REALASSETS(3, "实物资产"),
  ;


  InvestTypeEnum(int id, String name){
    this.id = id;
    this.name = name;
  }

  int id;
  String name;


  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
