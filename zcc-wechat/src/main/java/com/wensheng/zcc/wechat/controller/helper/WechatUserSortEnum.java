package com.wensheng.zcc.wechat.controller.helper;


import com.wensheng.zcc.common.utils.base.EnumUtils;
import java.util.function.Function;

public enum WechatUserSortEnum {
  FIRSTLOGIN(1, "firstlogin", "用户初次登录时间"),
  LASTLOGIN(2,"lastlogin","最近登录时间"),
  ONLINETIME(3,"onlinetime","访问总时长"),
  WATCHCOUNT(4, "watchcount","关注数"),

  ;

  WechatUserSortEnum(int id,String name, String cname ){
    this.name = name;
    this.cname = cname;
    this.id = id;
  }
  private String name;
  private String cname;
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
