package com.wensheng.zcc.cust.module.helper.sync;

import com.wensheng.zcc.common.utils.base.EnumUtils;

import java.util.function.Function;

/**
 * @author chenwei on 1/2/19
 * @project zcc-backend
 */
public enum CustTypeSyncEnum {

  NO_INFO(-1, "不确定"),
  PERSON(2, "个人"),
  COMPANY(1, "企业"),

  ;

  private int id;
  private String name;
  CustTypeSyncEnum(int id, String name){
    this.id = id;
    this.name = name;
  }

  private static final Function<String, CustTypeSyncEnum> func =
      EnumUtils.lookupMap(CustTypeSyncEnum.class, e -> e.getName());
  public static CustTypeSyncEnum lookupByDisplayNameUtil(String name) {
    return func.apply(name);
  }

  private static final Function<Integer, CustTypeSyncEnum> funcId =
      EnumUtils.lookupMap(CustTypeSyncEnum.class, e -> e.getId());
  public static CustTypeSyncEnum lookupByDisplayNameUtil(Integer id) {
    return funcId.apply(id);
  }
  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
