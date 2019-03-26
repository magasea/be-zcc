package com.wensheng.zcc.amc.module.dao.helper;

import com.wensheng.zcc.common.utils.base.EnumUtils;
import java.util.function.Function;

/**
 * @author chenwei on 1/2/19
 * @project zcc-backend
 */
public enum DebtorTypeEnum {

  NO_INFO(-1, "不确定"),
  PERSON(1, "个人"),
  COMPANY(2, "公司"),
  ;

  private int id;
  private String name;
  DebtorTypeEnum(int id, String name){
    this.id = id;
    this.name = name;
  }

  private static final Function<String, DebtorTypeEnum> func =
      EnumUtils.lookupMap(DebtorTypeEnum.class, e -> e.getName());
  public static DebtorTypeEnum lookupByDisplayNameUtil(String name) {
    return func.apply(name);
  }

  private static final Function<Integer, DebtorTypeEnum> funcId =
      EnumUtils.lookupMap(DebtorTypeEnum.class, e -> e.getId());
  public static DebtorTypeEnum lookupByDisplayNameUtil(Integer id) {
    return funcId.apply(id);
  }
  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
