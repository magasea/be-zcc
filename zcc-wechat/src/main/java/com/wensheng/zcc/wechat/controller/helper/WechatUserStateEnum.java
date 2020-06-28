package com.wensheng.zcc.wechat.controller.helper;


import com.wensheng.zcc.common.utils.base.EnumUtils;
import java.util.function.Function;

public enum  WechatUserStateEnum {
  WXUSER_PHONEBINDED(1, "手机号码已经绑定"),
  ;

  WechatUserStateEnum(int id,String name ){
    this.name = name;
    this.id = id;
  }
  private String name;
  private int id;


  public String getName() {
    return name;
  }

  public int getId() {
    return id;
  }

  private static final Function<String, WechatUserStateEnum> func =
      EnumUtils.lookupMap(WechatUserStateEnum.class, e -> e.getName());
  public static WechatUserStateEnum lookupByDisplayNameUtil(String name) {
    return func.apply(name);
  }

  private static final Function<Integer, WechatUserStateEnum> funcStatus =
      EnumUtils.lookupMap(WechatUserStateEnum.class, e -> e.getId());
  public static WechatUserStateEnum lookupByDisplayIdUtil(Integer id) {
    return funcStatus.apply(id);
  }

}
