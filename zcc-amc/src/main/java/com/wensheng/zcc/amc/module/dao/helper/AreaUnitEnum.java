package com.wensheng.zcc.amc.module.dao.helper;

import com.wensheng.zcc.common.utils.base.EnumUtils;
import java.util.function.Function;

/**
 * @author chenwei on 2/28/19
 * @project zcc-backend
 */
public enum AreaUnitEnum {
  SQUAREMETER(1, "平米"),
  TENTHOUNDSQUM(2, "万平米"),
  MU(3, "亩"),
  GONGQING(4, "公顷")
  ;
  AreaUnitEnum(int type, String name){
    this.type = type;
    this.name = name;
  }
  //    住宅，别墅，商铺，写字楼，工业房地产，土地，机器设备，其它
  int type;
  String name;

  private static final Function<String, AreaUnitEnum> func =
      EnumUtils.lookupMap(AreaUnitEnum.class, e -> e.getName());
  public static AreaUnitEnum lookupByDisplayNameUtil(String name) {
    return func.apply(name);
  }

  private static final Function<Integer, AreaUnitEnum> funcType =
      EnumUtils.lookupMap(AreaUnitEnum.class, e -> e.getType());
  public static AreaUnitEnum lookupByDisplayTypeUtil(Integer type) {
    return funcType.apply(type);
  }
  public int getType() {
    return type;
  }

  public String getName() {
    return name;
  }
}
