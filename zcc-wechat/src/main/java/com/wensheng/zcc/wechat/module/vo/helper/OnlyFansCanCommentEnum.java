package com.wensheng.zcc.wechat.module.vo.helper;

public enum OnlyFansCanCommentEnum {

  ALL(0, "all can comment"),
  ONLY_FANS(1, "only fans can comment"),
  ;
  int id;
  String name;
  OnlyFansCanCommentEnum(int id, String name){
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
