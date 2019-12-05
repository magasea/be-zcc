package com.wensheng.zcc.sso.module.vo;

import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcUser;
import java.util.List;
import lombok.Data;

@Data
public class AmcSpecialUserVo {
  AmcUser amcUser;
  List<Long> permIds;
}
