package com.wensheng.zcc.common.params;

import com.wensheng.zcc.common.utils.base.EnumUtils;
import java.util.function.Function;

public enum AmcDebtAssetTypeEnum {
  AMC_ASSET("AMC_ASSET","资产", 2),
  AMC_DEBT("AMC_DEBT","债权", 1),


  ;
  private String name;
  private String cname;
  private Integer id;

  AmcDebtAssetTypeEnum(String name, String cname, int id){
    this.name = name;
    this.cname = cname;
    this.id = id;
  }

  private static final Function<String, AmcDebtAssetTypeEnum> func =
      EnumUtils.lookupMap(AmcDebtAssetTypeEnum.class, e -> e.getName());
  public static AmcDebtAssetTypeEnum lookupByDisplayNameUtil(String name) {
    return func.apply(name);
  }

  private static final Function<Integer, AmcDebtAssetTypeEnum> funcId =
      EnumUtils.lookupMap(AmcDebtAssetTypeEnum.class, e -> e.getId());
  public static AmcDebtAssetTypeEnum lookupByDisplayIdUtil(Integer id) {
    return funcId.apply(id);
  }


  public String getName() {
    return name;
  }

  public Integer getId() {
    return id;
  }

  public String getCname() {
    return cname;
  }
}
