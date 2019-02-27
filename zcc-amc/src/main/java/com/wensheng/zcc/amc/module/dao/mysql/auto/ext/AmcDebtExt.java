package com.wensheng.zcc.amc.module.dao.mysql.auto.ext;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcAsset;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcCreditor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebt;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcGrntor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcPerson;
import java.util.List;
import lombok.Data;

/**
 * @author chenwei on 1/7/19
 * @project zcc-backend
 */
@Data
public class AmcDebtExt  {
  Long id;
  AmcDebt debtInfo;

  List<AmcAsset> amcAssets ;
  List<AmcCreditor> creditors;

  List<AmcDebtor> amcDebtors;
}
