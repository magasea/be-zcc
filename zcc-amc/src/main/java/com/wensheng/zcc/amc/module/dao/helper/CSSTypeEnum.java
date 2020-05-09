package com.wensheng.zcc.amc.module.dao.helper;

import com.wensheng.zcc.common.utils.base.EnumUtils;
import java.util.function.Function;

public enum  CSSTypeEnum {
  LATESTASSET(1, "最新资产"),
  HOTDEBT(2, "最热债权"),
  GUESSYOURFAVOR(3, "猜你喜欢"),
  

  ;


  private int id;
  private String name;

  private static final Function<String, CSSTypeEnum> func =
      EnumUtils.lookupMap(CSSTypeEnum.class, e -> e.getName());
  public static CSSTypeEnum lookupByDisplayNameUtil(String name) {
    return func.apply(name);
  }

  private static final Function<Integer, CSSTypeEnum> funcId =
      EnumUtils.lookupMap(CSSTypeEnum.class, e -> e.getId());
  public static CSSTypeEnum lookupByIdUtil(Integer type) {
    return funcId.apply(type);
  }
  CSSTypeEnum(int id, String name){
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
