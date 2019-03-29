package com.wensheng.zcc.amc.module.vo;

import com.wensheng.zcc.amc.module.dao.helper.DebtorRoleEnum;
import lombok.Data;

/**
 * @author chenwei on 3/28/19
 * @project miniapp-backend
 */
@Data
public class AmcDebtorCmpy {
  Long cmpyId;
  String name;
  DebtorRoleEnum role;
}
