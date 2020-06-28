package com.wensheng.zcc.wechat.module.vo.helper;

import com.wensheng.zcc.common.utils.base.EnumUtils;
import java.util.function.Function;

public enum WechatUserTagEnum {
  DEFAULT(-1, "默认"),
  AMCEMPLOYEE(1, "技术部员工"),
  ;
  int id;
  String name;
  WechatUserTagEnum(int id, String name){
    this.id = id;
    this.name = name;

  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }


  private static final Function<String, WechatUserTagEnum> func =
      EnumUtils.lookupMap(WechatUserTagEnum.class, e -> e.getName());
  public static WechatUserTagEnum lookupByDisplayNameUtil(String name) {
    return func.apply(name);
  }


  private static final Function<Integer, WechatUserTagEnum> funcInt =
      EnumUtils.lookupMap(WechatUserTagEnum.class, e -> e.getId());
  public static WechatUserTagEnum lookupByDisplayIdUtil(Integer id) {
    return funcInt.apply(id);
  }
}
