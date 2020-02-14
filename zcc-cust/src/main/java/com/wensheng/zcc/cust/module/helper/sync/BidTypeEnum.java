package com.wensheng.zcc.cust.module.helper.sync;

import com.wensheng.zcc.common.utils.base.EnumUtils;
import java.util.function.Function;

public enum BidTypeEnum {
//bidType 住宅=1;别墅=2;商铺=3;商场=4写字楼=5;酒店=6;工业房地产=7;土地=8;综合=99

  RESIDENTIAL(1, "住宅"),
  VILLA(2, "别墅"),
  SHOP(3, "商铺"),
  MALL(4, "商场"),
  OFFICE_BUILDING(5, "写字楼"),
  HOTEL(6, "酒店"),
  INDUSTRY_REAL_ESTATE(7, "工业房地产"),
  LAND(8, "土地"),

  COMBINED(99, "综合"),

  ;
  int id;
  String cname;

  BidTypeEnum(int id, String cname){
    this.id = id;
    this.cname = cname;
  }
  private static final Function<String, BidTypeEnum> func =
      EnumUtils.lookupMap(BidTypeEnum.class, e -> e.getCname());
  public static BidTypeEnum lookupByDisplayCNameUtil(String cname) {
    return func.apply(cname);
  }

  private static final Function<Integer, BidTypeEnum> funcId =
      EnumUtils.lookupMap(BidTypeEnum.class, e -> e.getId());
  public static BidTypeEnum lookupByIdUtil(int id) {
    return funcId.apply(id);
  }
  public int getId() {
    return id;
  }

  public String getCname() {
    return cname;
  }
}
