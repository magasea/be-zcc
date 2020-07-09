package com.wensheng.zcc.cust.module.helper;

import com.wensheng.zcc.common.utils.base.EnumUtils;
import java.util.function.Function;

/**
 * @author chenwei on 1/2/19
 * @project zcc-backend
 */
public enum SelectCustTypeEnum {

  ALL(1, "所有", "ALL"),
  UPDATE(2, "最新更新", "UPDATE"),
  CREATE(3, "最新创建", "CREATE"),
  TRADE(4, "最新交易", "TRADE"),
  ;

  private int id;
  private String name;
  private String ename;
  SelectCustTypeEnum(int id, String name, String ename){
    this.id = id;
    this.name = name;
    this.ename = ename;
  }

  private static final Function<String, SelectCustTypeEnum> func =
      EnumUtils.lookupMap(SelectCustTypeEnum.class, e -> e.getName());
  public static SelectCustTypeEnum lookupByDisplayNameUtil(String name) {
    return func.apply(name);
  }


  private static final Function<String, SelectCustTypeEnum> funcEname =
      EnumUtils.lookupMap(SelectCustTypeEnum.class, e -> e.getEname());
  public static SelectCustTypeEnum lookupByDisplayENameUtil(String ename) {
    return funcEname.apply(ename);
  }



  private static final Function<Integer, SelectCustTypeEnum> funcId =
      EnumUtils.lookupMap(SelectCustTypeEnum.class, e -> e.getId());
  public static SelectCustTypeEnum lookupByDisplayNameUtil(Integer id) {
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
