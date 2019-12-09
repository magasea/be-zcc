package com.wensheng.zcc.sso.module.vo;

import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcSpecUser;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcUser;
import java.util.List;
import lombok.Data;

@Data
public class AmcSpecialUserVo extends AmcSpecUser implements Comparable<AmcSpecUser>{
  AmcUser amcUser;
  List<Long> permIds;

  @Override
  public int compareTo(AmcSpecUser o) {
    if(this.getUpdateDate() == null){
      return -1;
    }
    if(o.getUpdateDate() == null){
      return 1;
    }
    return this.getUpdateDate().compareTo( o.getUpdateDate());
  }
}
