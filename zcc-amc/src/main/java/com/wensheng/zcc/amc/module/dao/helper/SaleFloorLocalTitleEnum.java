package com.wensheng.zcc.amc.module.dao.helper;

import com.wensheng.zcc.common.utils.base.EnumUtils;
import java.util.function.Function;

public enum SaleFloorLocalTitleEnum {
  LOCAL_LEVEL_CITY(1, "属地优选资产", "智能本地化推介"),
  LOCAL_LEVEL_PROV(2, "所在省优选资产","智能本地化推荐"),
  LOCAL_LEVEL_RANGE(3, "距离优选资产","智能距离推荐"),

  ;

  private int id;
  private String title;
  private String slogon;
  SaleFloorLocalTitleEnum(int id, String title, String slogon){
    this.id = id;
    this.title = title;
    this.slogon = slogon;
  }
  private static final Function<String, SaleFloorLocalTitleEnum> func =
      EnumUtils.lookupMap(SaleFloorLocalTitleEnum.class, e -> e.getTitle());
  public static SaleFloorLocalTitleEnum lookupByDisplayNameUtil(String title) {
    return func.apply(title);
  }

  private static final Function<Integer, SaleFloorLocalTitleEnum> funcid =
      EnumUtils.lookupMap(SaleFloorLocalTitleEnum.class, e -> e.getId());
  public static SaleFloorLocalTitleEnum lookupByDisplayIdUtil(Integer id) {
    return funcid.apply(id);
  }

  public int getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getSlogon() {
    return slogon;
  }
}
