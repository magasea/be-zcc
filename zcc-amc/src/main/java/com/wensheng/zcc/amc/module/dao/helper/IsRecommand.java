package com.wensheng.zcc.amc.module.dao.helper;

import com.wensheng.zcc.amc.module.dao.helper.base.EnumUtils;
import java.util.function.Function;
import lombok.Data;

/**
 * @author chenwei on 1/2/19
 * @project zcc-backend
 */

public enum IsRecommand {
  RECOMMAND(1, "推荐"),
  NOT_RECOMMAND(2, "不推荐"),
  NOT_SET(-1, "待定"),
  ;
  int id;
  String name;

  IsRecommand(int id, String name){
    this.id = id;
    this.name = name;

  }
  private static final Function<String, IsRecommand> func =
      EnumUtils.lookupMap(IsRecommand.class, e -> e.getName());

  public static IsRecommand lookupByDisplayNameUtil(String name) {
    return func.apply(name);
  }

  public int getId(){
    return this.id;
  }

  public String getName(){
    return this.name;
  }
}
