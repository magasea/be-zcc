package com.wensheng.zcc.amc.module.dao.mysql.auto.ext;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebt;
import java.util.List;
import lombok.Data;

/**
 * @author chenwei on 1/7/19
 * @project zcc-backend
 */
@Data
public class AmcDebtExt extends AmcDebt {

  List<Long> amcAssetIds ;

}
