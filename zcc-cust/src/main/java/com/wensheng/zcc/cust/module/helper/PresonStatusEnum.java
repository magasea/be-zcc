package com.wensheng.zcc.cust.module.helper;

import com.wensheng.zcc.common.utils.base.EnumUtils;
import java.util.function.Function;

/**
 * @author chenwei on 1/2/19
 * @project zcc-backend
 */
public enum PresonStatusEnum {

  DEFAULT_STATUS(-1, "默认状态", "DEFAULT_STATUS"),
  MERGED_STATUS(2, "被合并", "MERGED_STATUS"),
  ;

  private int id;
  private String name;
  private String ename;
  PresonStatusEnum(int id, String name, String ename){
    this.id = id;
    this.name = name;
    this.ename = ename;
  }

  private static final Function<String, PresonStatusEnum> func =
      EnumUtils.lookupMap(PresonStatusEnum.class, e -> e.getName());
  public static PresonStatusEnum lookupByDisplayNameUtil(String name) {
    return func.apply(name);
  }


  private static final Function<String, PresonStatusEnum> funcEname =
      EnumUtils.lookupMap(PresonStatusEnum.class, e -> e.getEname());
  public static PresonStatusEnum lookupByDisplayENameUtil(String ename) {
    return funcEname.apply(ename);
  }



  private static final Function<Integer, PresonStatusEnum> funcId =
      EnumUtils.lookupMap(PresonStatusEnum.class, e -> e.getId());
  public static PresonStatusEnum lookupByDisplayNameUtil(Integer id) {
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
