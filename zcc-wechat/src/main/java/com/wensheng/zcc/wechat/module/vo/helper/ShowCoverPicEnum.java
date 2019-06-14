package com.wensheng.zcc.wechat.module.vo.helper;

public enum ShowCoverPicEnum {
  NOT_SHOW(0, "not show"),
  SHOW(1, "show"),
  ;
  int id;
  String name;
  ShowCoverPicEnum(int id, String name){
    this.id = id;
    this.name = name;

  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
