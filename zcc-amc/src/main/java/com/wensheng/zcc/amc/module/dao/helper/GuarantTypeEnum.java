package com.wensheng.zcc.amc.module.dao.helper;

import com.wensheng.zcc.common.utils.base.EnumUtils;
import java.util.function.Function;

/**
 * @author chenwei on 2/27/19
 * @project zcc-backend
 */
public enum GuarantTypeEnum {
  MORTGAGE(1, "抵押"),
  WARRANTY(2, "保证"),
  COMBINED(3, "抵押+保证"),
  COMBINED1(4, "抵押+保证+质押"),
  CHIBOR(5, "拆借"),
  PLEDGE(6, "质押"),
  ;

  GuarantTypeEnum(int type, String name){
    this.type = type;
    this.name = name;
  }
  //    抵押 保证 混合
  int type;
  String name;

  private static final Function<String, GuarantTypeEnum> func =
      EnumUtils.lookupMap(GuarantTypeEnum.class, e -> e.getName());
  public static GuarantTypeEnum lookupByDisplayNameUtil(String name) {
    return func.apply(name);
  }

  private static final Function<Integer, GuarantTypeEnum> funcId =
      EnumUtils.lookupMap(GuarantTypeEnum.class, e -> e.getType());
  public static GuarantTypeEnum lookupByIdUtil(Integer type) {
    return funcId.apply(type);
  }
  public int getType() {
    return type;
  }

  public String getName() {
    return name;
  }
}
