package com.wensheng.zcc.amc.module.dao.mysql.auto.ext;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcOrigCreditor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.ZccDebtpack;
import java.util.List;
import lombok.Data;

/**
 * @author chenwei on 1/9/19
 * @project zcc-backend
 */
@Data
public class AmcDebtpackExt {
  Long id;
  ZccDebtpack zccDebtpackInfo;
  List<AmcOrigCreditor> amcOrigCreditorList;

}
