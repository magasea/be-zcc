package com.wensheng.zcc.common.params;

import com.wensheng.zcc.common.utils.base.EnumUtils;
import java.util.function.Function;

/**
 * @author chenwei on 3/29/19
 * @project miniapp-backend
 */
public enum AmcBranchLocationEnum {

  BEIJING_LOCATION("BEIJING_LOCATION","北京地区", 1),
  SHANGHAI_LOCATION("SHANGHAI_LOCATION","上海地区", 2),
  JIANGSU_LOCATION("JIANGSU_LOCATION","江苏地区", 3),
  ZHEJIANG_LOCATION("ZHEJIANG_LOCATION","浙江地区", 4),
  GUANGDONG_LOCATION("GUANGDONG_LOCATION","广东地区", 5),
  OTHER_LOCATION("OTHER_LOCATION","其他地区", 6),
  ;

  int id;
  String name;
  String cname;

  AmcBranchLocationEnum( String name, String cname,int id){
    this.id = id;
    this.name = name;
    this.cname = cname;
  }

  private static final Function<Integer, AmcBranchLocationEnum> func =
      EnumUtils.lookupMap(AmcBranchLocationEnum.class, e -> e.getId());
  public static AmcBranchLocationEnum lookupByIdUtil(int id) {
    return func.apply(id);
  }


  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getCname() {
    return cname;
  }
}
