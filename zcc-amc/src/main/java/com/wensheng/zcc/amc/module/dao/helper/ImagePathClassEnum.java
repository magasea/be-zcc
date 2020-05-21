package com.wensheng.zcc.amc.module.dao.helper;

/**
 * @author chenwei on 1/15/19
 * @project zcc-backend
 */
public enum ImagePathClassEnum {
  DEBT(1, "debt"),
  ASSET(2, "asset"),
  SALEMENU(3, "salemenu"),
  SALEBANNER(4, "salebanner"),
  SALEMENUPAGE(5, "salemenupage"),
//  SALEBANNERPAGE(6, "salebannerpage"),

  SALEFLOORPAGE(6, "salefloorpage"),
  ;
  int id;
  String name;
  ImagePathClassEnum(int id, String name){
    this.id = id;
    this.name = name;
  }

  public int getId(){
    return id;
  }

  public String getName(){
    return name;
  }

}
