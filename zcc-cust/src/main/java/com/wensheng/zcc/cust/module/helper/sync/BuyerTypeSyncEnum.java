package com.wensheng.zcc.cust.module.helper.sync;

import com.wensheng.zcc.common.utils.base.EnumUtils;

import java.util.function.Function;

/**
 * @author chenwei on 1/2/19
 * @project zcc-backend
 */
public enum BuyerTypeSyncEnum {

  NO_INFO(-1, "不确定"),
  PERSON(2, "个人"),
  BANK(1, "银行"),
  COMPANY(3, "其他公司"),
  ;

  private int id;
  private String name;
  BuyerTypeSyncEnum(int id, String name){
    this.id = id;
    this.name = name;
  }

  private static final Function<String, BuyerTypeSyncEnum> func =
      EnumUtils.lookupMap(BuyerTypeSyncEnum.class, e -> e.getName());
  public static BuyerTypeSyncEnum lookupByDisplayNameUtil(String name) {
    return func.apply(name);
  }

  private static final Function<Integer, BuyerTypeSyncEnum> funcId =
      EnumUtils.lookupMap(BuyerTypeSyncEnum.class, e -> e.getId());
  public static BuyerTypeSyncEnum lookupByDisplayNameUtil(Integer id) {
    return funcId.apply(id);
  }
  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
