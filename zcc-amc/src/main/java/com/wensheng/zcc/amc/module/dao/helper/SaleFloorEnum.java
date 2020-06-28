package com.wensheng.zcc.amc.module.dao.helper;

import com.wensheng.zcc.common.utils.base.EnumUtils;
import java.util.function.Function;

public enum SaleFloorEnum {
  LATESTASSET(1, "最新资产"),
  HOTDEBT(2, "最热债权"),
  LOCALRECOMM(3, "本地推介"),

  ;

  private int id;
  private String name;
  SaleFloorEnum(int id, String name){
    this.id = id;
    this.name = name;
  }
  private static final Function<String, SaleFloorEnum> func =
      EnumUtils.lookupMap(SaleFloorEnum.class, e -> e.getName());
  public static SaleFloorEnum lookupByDisplayNameUtil(String name) {
    return func.apply(name);
  }

  private static final Function<Integer, SaleFloorEnum> funcid =
      EnumUtils.lookupMap(SaleFloorEnum.class, e -> e.getId());
  public static SaleFloorEnum lookupByDisplayIdUtil(Integer id) {
    return funcid.apply(id);
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
