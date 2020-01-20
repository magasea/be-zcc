package com.wensheng.zcc.cust.module.helper;

import com.wensheng.zcc.common.utils.base.EnumUtils;
import java.util.function.Function;

/**
 * @author chenwei on 1/2/19
 * @project zcc-backend
 */
public enum CustTypeEnum {

  NO_INFO(-1, "不确定", "NOT SURE"),
  PERSON(1, "个人", "PERSON"),
  COMPANY(2, "公司", "COMPANY"),
  BANK(3, "银行", "BANK"),
  ;

  private int id;
  private String name;
  private String ename;
  CustTypeEnum(int id, String name, String ename){
    this.id = id;
    this.name = name;
    this.ename = ename;
  }

  private static final Function<String, CustTypeEnum> func =
      EnumUtils.lookupMap(CustTypeEnum.class, e -> e.getName());
  public static CustTypeEnum lookupByDisplayNameUtil(String name) {
    return func.apply(name);
  }


  private static final Function<String, CustTypeEnum> funcEname =
      EnumUtils.lookupMap(CustTypeEnum.class, e -> e.getEname());
  public static CustTypeEnum lookupByDisplayENameUtil(String ename) {
    return funcEname.apply(ename);
  }



  private static final Function<Integer, CustTypeEnum> funcId =
      EnumUtils.lookupMap(CustTypeEnum.class, e -> e.getId());
  public static CustTypeEnum lookupByDisplayNameUtil(Integer id) {
    return funcId.apply(id);
  }
  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }
  public String getEname() {
    return ename;
  }
}
