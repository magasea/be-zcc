package com.wensheng.zcc.amc.module.dao.helper;

import com.wensheng.zcc.common.utils.base.EnumUtils;
import java.util.function.Function;

public enum HasImageEnum {
  NOTSURE(-1, "不确定"),
  HASIMAGE(1, "有图片"),
  NOIMAGE(-2, "没有图片"),

  ;

  private int status;
  private String name;
  HasImageEnum(int status, String name){
    this.status = status;
    this.name = name;
  }
  private static final Function<String, HasImageEnum> func =
      EnumUtils.lookupMap(HasImageEnum.class, e -> e.getName());
  public static HasImageEnum lookupByDisplayNameUtil(String name) {
    return func.apply(name);
  }
  public int getStatus() {
    return status;
  }

  public String getName() {
    return name;
  }
}
