package com.wensheng.zcc.amc.module.dao.helper;

import com.wensheng.zcc.amc.module.dao.helper.base.EnumUtils;
import java.util.function.Function;

/**
 * @author chenwei on 1/7/19
 * @project zcc-backend
 */
public enum OrderByEnum {
  DESC(0, "desc"),
  ASC(1, "asc"),
  ;
  private int id;
  private String name;
  OrderByEnum(int id, String name){
    this.id = id;
    this.name = name;
  }

  private static final Function<Integer, OrderByEnum> func =
      EnumUtils.lookupMap(OrderByEnum.class, e -> e.getId());
  public static OrderByEnum lookupByDisplayNameUtil(Integer id) {
    return func.apply(id);
  }
  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
