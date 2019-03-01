package com.wensheng.zcc.amc.module.dao.helper;

import com.wensheng.zcc.amc.module.dao.helper.base.EnumUtils;
import java.util.function.Function;

/**
 * @author chenwei on 2/27/19
 * @project zcc-backend
 */
public enum GuarantTypeEnum {
  MORTGAGE(1, "抵押"),
  WARRANTY(2, "保证"),
  COMBINED(3, "混合"),
  COMBINED1(4, "抵押+保证+质押"),
  CHIBOR(5, "拆借"),
  ;

  GuarantTypeEnum(int type, String name){
    this.type = type;
    this.name = name;
  }
  //    抵押 保证 混合
  int type;
  String name;

  private static final Function<String, AssetTypeEnum> func =
      EnumUtils.lookupMap(AssetTypeEnum.class, e -> e.getName());
  public static AssetTypeEnum lookupByDisplayNameUtil(String name) {
    return func.apply(name);
  }
  public int getType() {
    return type;
  }

  public String getName() {
    return name;
  }
}
