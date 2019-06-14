package com.wensheng.zcc.wechat.module.vo.helper;

public enum NeedOpenCommentEnum {
  CLOSE(0, "close comment"),
  OPEN(1, "open for comment"),
  ;
  int id;
  String name;
  NeedOpenCommentEnum(int id, String name){
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
