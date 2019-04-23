package com.wensheng.zcc.cust.module.helper;

/**
 * @author chenwei on 4/17/19
 * @project miniapp-backend
 */
public enum PersonSexEnum {
  MALE(1, "male", "男"),
  FEMALE(2, "female", "女"),
  UNKNOWN(3, "unknown", "未知"),
  OTHER(4, "other", "其他"),
  ;

  PersonSexEnum(Integer id, String name, String cname){
    this.id = id;
    this.name = name;
    this.cname = cname;
  }
  int id;
  String name;
  String cname;


  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getCname() {
    return cname;
  }
}
