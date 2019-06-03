package com.wensheng.zcc.cust.module.helper;

import com.wensheng.zcc.common.utils.base.EnumUtils;
import java.util.function.Function;

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


  private static final Function<Integer, InvestTypeEnum> func =
      EnumUtils.lookupMap(InvestTypeEnum.class, e -> e.getId());
  public static InvestTypeEnum lookupByIdUntil(int id) {
    return func.apply(id);
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
