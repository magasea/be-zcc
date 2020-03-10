package com.wensheng.zcc.cust.module.helper;

import com.wensheng.zcc.common.utils.base.EnumUtils;
import java.util.function.Function;

public enum ItemTypeEnum {

  BADASSETPACK(1, "不良资产包"),
  SINGLEPROJ(2, "单体项目"),
  REALASSETS(3, "实物资产"),
  CIVILASSET(4,"民用房产"),
  COMMERIALASSET(5,"商业房产"),
  INDUSTRYASSET(6,"工业房产"),
  OTHERASSET(7,"其他房产"),

  OTHER(100, "其它"),

  ;

  int id;
  String name;
  ItemTypeEnum(int id, String name){
    this.id = id;
    this.name = name;
  }


  private static final Function<Integer, ItemTypeEnum> func =
      EnumUtils.lookupMap(ItemTypeEnum.class, e -> e.getId());
  public static ItemTypeEnum lookupByIdUntil(int id) {
    return func.apply(id);
  }


  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
