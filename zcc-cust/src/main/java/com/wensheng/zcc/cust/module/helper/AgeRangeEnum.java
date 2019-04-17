package com.wensheng.zcc.cust.module.helper;

/**
 * @author chenwei on 4/17/19
 * @project miniapp-backend
 */
public enum AgeRangeEnum {
  AGERANGEIN30(1, "Age lower than 30", "年龄小于30"),
  AGERANGEIN3050(2, "Age between 30 to 50", "年龄介于30到50"),
  AGERANGEIN50(1, "Age greater than 50", "年龄大于50"),
  ;

  AgeRangeEnum(Integer id, String name, String cname){
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
