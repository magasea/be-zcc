package com.wensheng.zcc.amc.module.dao.helper;

import com.wensheng.zcc.amc.module.dao.helper.base.EnumUtils;
import java.util.function.Function;

/**
 * @author chenwei on 1/2/19
 * @project zcc-backend
 */
public enum DebtorRoleEnum {

  NO_INFO(-1, "不确定"),
  PERSONAL(1, "个人"),
  COMPANY(2, "公司"),
  ;

  private int id;
  private String name;
  DebtorRoleEnum(int id, String name){
    this.id = id;
    this.name = name;
  }

  private static final Function<String, DebtorRoleEnum> func =
      EnumUtils.lookupMap(DebtorRoleEnum.class, e -> e.getName());
  public static DebtorRoleEnum lookupByDisplayNameUtil(String name) {
    return func.apply(name);
  }

  private static final Function<Integer, DebtorRoleEnum> funcId =
      EnumUtils.lookupMap(DebtorRoleEnum.class, e -> e.getId());
  public static DebtorRoleEnum lookupByDisplayNameUtil(Integer id) {
    return funcId.apply(id);
  }
  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
