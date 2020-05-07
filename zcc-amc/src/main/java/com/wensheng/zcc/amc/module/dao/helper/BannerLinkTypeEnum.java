package com.wensheng.zcc.amc.module.dao.helper;

/**
 * @author chenwei on 1/15/19
 * @project zcc-backend
 */
public enum BannerLinkTypeEnum {
  SINGLEASSET(1, "单资产活动"),
  MULTIPLEASSETS(2, "多资产活动"),
  BRAND(3, "品牌"),
  COOPERS(4, "异业合作"),
  ;
  int id;
  String name;
  BannerLinkTypeEnum(int id, String name){
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
