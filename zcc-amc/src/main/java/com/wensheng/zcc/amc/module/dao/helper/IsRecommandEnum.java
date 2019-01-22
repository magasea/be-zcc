package com.wensheng.zcc.amc.module.dao.helper;

import com.wensheng.zcc.amc.module.dao.helper.base.EnumUtils;
import java.util.function.Function;

/**
 * @author chenwei on 1/2/19
 * @project zcc-backend
 */

public enum IsRecommandEnum {
  NOT_RECOMMAND(1, "不推荐"),
  RECOMMAND_AMC(2, "AMC推荐"),
  RECOMMAND_GLOBAL(3, "全局推荐"),

  NOT_SET(-1, "待定"),
  ;
  int id;
  String name;

  IsRecommandEnum(int id, String name){
    this.id = id;
    this.name = name;

  }
  private static final Function<String, IsRecommandEnum> func =
      EnumUtils.lookupMap(IsRecommandEnum.class, e -> e.getName());

  public static IsRecommandEnum lookupByDisplayNameUtil(String name) {
    return func.apply(name);
  }

  public int getId(){
    return this.id;
  }

  public String getName(){
    return this.name;
  }
}
