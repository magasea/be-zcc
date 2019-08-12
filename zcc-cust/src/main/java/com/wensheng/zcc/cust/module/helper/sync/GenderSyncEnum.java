package com.wensheng.zcc.cust.module.helper.sync;

import com.wensheng.zcc.common.utils.base.EnumUtils;
import java.util.function.Function;

/**
 * @author chenwei on 1/2/19
 * @project zcc-backend
 */
public enum GenderSyncEnum {

  MALE(1, "男"),
  FEMALE(2, "女"),
  UNKNOWN(3, "未知"),
  ;

  private int id;
  private String name;
  GenderSyncEnum(int id, String name){
    this.id = id;
    this.name = name;
  }

  private static final Function<String, GenderSyncEnum> func =
      EnumUtils.lookupMap(GenderSyncEnum.class, e -> e.getName());
  public static GenderSyncEnum lookupByDisplayNameUtil(String name) {
    return func.apply(name);
  }

  private static final Function<Integer, GenderSyncEnum> funcId =
      EnumUtils.lookupMap(GenderSyncEnum.class, e -> e.getId());
  public static GenderSyncEnum lookupByDisplayNameUtil(Integer id) {
    return funcId.apply(id);
  }
  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
