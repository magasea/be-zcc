package com.wensheng.zcc.common.params;

import com.wensheng.zcc.common.utils.base.EnumUtils;
import java.util.function.Function;

public enum AmcSexEnum {
  MALE("MALE","男性", 1),
  FEMAILE("FEMAIL","女性", 2),
  OTHER("OTHER","其它", 3),


  ;
  private String name;
  private String cname;
  private Integer id;

  AmcSexEnum(String name, String cname, int id){
    this.name = name;
    this.cname = cname;
    this.id = id;
  }

  private static final Function<String, AmcSexEnum> func =
      EnumUtils.lookupMap(AmcSexEnum.class, e -> e.getName());
  public static AmcSexEnum lookupByDisplayNameUtil(String name) {
    return func.apply(name);
  }

  private static final Function<Integer, AmcSexEnum> funcId =
      EnumUtils.lookupMap(AmcSexEnum.class, e -> e.getId());
  public static AmcSexEnum lookupByDisplayIdUtil(Integer id) {
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
