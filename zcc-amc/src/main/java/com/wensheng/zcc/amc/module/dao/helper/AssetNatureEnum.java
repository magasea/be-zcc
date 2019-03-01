package com.wensheng.zcc.amc.module.dao.helper;

import com.wensheng.zcc.amc.module.dao.helper.base.EnumUtils;
import java.util.function.Function;

/**
 * @author chenwei on 3/1/19
 * @project zcc-backend
 */
public enum AssetNatureEnum {

  COLLATERAL(1, "质押物"),
  MORTGAGE(2, "抵押物"),
  SEAL(3, "查封物"),
  OTHER(4, "其他"),
;

  AssetNatureEnum(int type, String name){
    this.type = type;
    this.name = name;
  }
  int type;
  String name;

  private static final Function<String, AssetNatureEnum> func =
      EnumUtils.lookupMap(AssetNatureEnum.class, e -> e.getName());
  public static AssetNatureEnum lookupByDisplayNameUtil(String name) {
    return func.apply(name);
  }
  public int getType() {
    return type;
  }

  public String getName() {
    return name;
  }
}
