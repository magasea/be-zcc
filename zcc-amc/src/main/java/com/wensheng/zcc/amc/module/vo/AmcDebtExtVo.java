package com.wensheng.zcc.amc.module.vo;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtContactor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcInfo;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcOrigCreditor;
import com.wensheng.zcc.common.params.sso.SSOAmcUser;
import java.util.List;
import lombok.Data;

/**
 * @author chenwei on 1/17/19
 * @project zcc-backend
 */
@Data
public class AmcDebtExtVo {
  AmcDebtVo amcDebtVo;
  AmcInfo amcInfos;
  List<AmcDebtor> amcDebtors;
  SSOAmcUser amcDebtContactor;
  AmcOrigCreditor origCreditor;
}
