package com.wensheng.zcc.amc.module.vo;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtpack;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcOrigCreditor;
import java.util.List;
import lombok.Data;

/**
 * @author chenwei on 1/9/19
 * @project zcc-backend
 */
@Data
public class AmcDebtpackExtVo {
  Long id;
  AmcDebtpackVo amcDebtpackInfo;
  List<AmcOrigCreditor> amcOrigCreditorList;

}
