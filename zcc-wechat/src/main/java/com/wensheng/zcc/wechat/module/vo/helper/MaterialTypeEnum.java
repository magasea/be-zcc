package com.wensheng.zcc.wechat.module.vo.helper;

import com.wensheng.zcc.common.utils.base.EnumUtils;
import java.util.function.Function;

public enum MaterialTypeEnum {
  IMAGE(1, "image"),
  VOICE(2, "voice"),
  VIDEO(3, "video"),
  THUMB(4, "thumb"),
  NEWS(5, "news"),
    ;

  int id;
  String name;
  MaterialTypeEnum(int id, String name){
    this.id = id;
    this.name = name;
  }

  private static final Function<String, MaterialTypeEnum> func =
      EnumUtils.lookupMap(MaterialTypeEnum.class, e -> e.getName());
  public static MaterialTypeEnum lookupByDisplayNameUtil(String name) {
    return func.apply(name);
  }


  private static final Function<Integer, MaterialTypeEnum> funcInt =
      EnumUtils.lookupMap(MaterialTypeEnum.class, e -> e.getId());
  public static MaterialTypeEnum lookupByDisplayIdUtil(Integer id) {
    return funcInt.apply(id);
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
