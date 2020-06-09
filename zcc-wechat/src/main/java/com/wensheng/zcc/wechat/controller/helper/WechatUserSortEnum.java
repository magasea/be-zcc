package com.wensheng.zcc.wechat.controller.helper;


import com.wensheng.zcc.common.utils.base.EnumUtils;
import java.util.function.Function;

public enum WechatUserSortEnum {
  SUBSCRIBE(1, "用户注册时间"),
  LASTLOGIN(2,"最近登录时间"),
  TIMESPENT(3,"访问总时长"),
  WATCHCOUNT(4, "关注数"),

  ;

  WechatUserSortEnum(int id,String name ){
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

  private static final Function<String, WechatUserSortEnum> func =
      EnumUtils.lookupMap(WechatUserSortEnum.class, e -> e.getName());
  public static WechatUserSortEnum lookupByDisplayNameUtil(String name) {
    return func.apply(name);
  }

  private static final Function<Integer, WechatUserSortEnum> funcStatus =
      EnumUtils.lookupMap(WechatUserSortEnum.class, e -> e.getId());
  public static WechatUserSortEnum lookupByDisplayIdUtil(Integer id) {
    return funcStatus.apply(id);
  }

}
