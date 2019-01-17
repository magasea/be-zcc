package com.wensheng.zcc.amc.module.vo;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcAsset;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcCreditor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebt;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcGrntor;
import java.util.List;
import lombok.Data;

/**
 * @author chenwei on 1/17/19
 * @project zcc-backend
 */
@Data
public class AmcDebtExtVo {
  AmcDebtVo amcDebtVo;
  List<AmcCreditor> creditors;
  List<AmcGrntor> amcGrntors;
}
