package com.wensheng.zcc.amc.module.dao.helper;

import com.wensheng.zcc.common.utils.base.EnumUtils;
import java.util.function.Function;

/**
 * @author chenwei on 1/24/19
 * @project zcc-backend
 */
public enum AmcGroupEnum {
  GROUP_GUANGZHOU(1, "GZ"),
  GROUP_SHANGHAI(2,"SH"),
  GROUP_HANGZHOU(3,"HZ"),
  GROUP_NANJING(4,"NJ"),
  GROUP_BEIJING(2,"BJ"),

  ;

  int id;
  String name;
  AmcGroupEnum(int id, String name){
    this.id = id;
    this.name = name;
  }


  private static final Function<String, AmcGroupEnum> func =
      EnumUtils.lookupMap(AmcGroupEnum.class, e -> e.getName());
  public static AmcGroupEnum lookupByDisplayNameUtil(String name) {
    return func.apply(name);
  }

  private static final Function<Integer, AmcGroupEnum> funcStatus =
      EnumUtils.lookupMap(AmcGroupEnum.class, e -> e.getId());
  public static AmcGroupEnum lookupByDisplayIdUtil(Integer id) {
    return funcStatus.apply(id);
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }}
