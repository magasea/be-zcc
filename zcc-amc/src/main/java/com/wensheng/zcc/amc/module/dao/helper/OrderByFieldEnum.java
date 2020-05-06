package com.wensheng.zcc.amc.module.dao.helper;

import com.wensheng.zcc.common.utils.base.EnumUtils;
import java.util.function.Function;

/**
 * @author chenwei on 1/7/19
 * @project zcc-backend
 */
public enum OrderByFieldEnum {
  ID(1, "id"),
  VISITCOUNT(2, "visitcount"),
  ;
  private int id;
  private String name;
  OrderByFieldEnum(int id, String name){
    this.id = id;
    this.name = name;
  }

  private static final Function<Integer, OrderByFieldEnum> func =
      EnumUtils.lookupMap(OrderByFieldEnum.class, e -> e.getId());
  public static OrderByFieldEnum lookupByDisplayNameUtil(Integer id) {
    return func.apply(id);
  }
  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
