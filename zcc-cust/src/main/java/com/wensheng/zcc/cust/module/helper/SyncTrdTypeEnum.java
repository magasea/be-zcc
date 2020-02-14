package com.wensheng.zcc.cust.module.helper;

public enum  SyncTrdTypeEnum {

  DEBTPUB(1, "转让公告"),
  AUCTION(2, "拍卖"),
  OTHER(3, "其他"),
  ;

  int id;
  String name;

  SyncTrdTypeEnum(int id, String name){
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
