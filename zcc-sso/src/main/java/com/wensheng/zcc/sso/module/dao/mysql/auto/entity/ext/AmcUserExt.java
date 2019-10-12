package com.wensheng.zcc.sso.module.dao.mysql.auto.entity.ext;

import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcPermission;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcRole;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcUser;
import java.util.List;
import java.util.Set;
import lombok.Data;

/**
 * @author chenwei on 1/31/19
 * @project zcc-backend
 */
@Data
public class AmcUserExt {
  Long id;
  AmcUser amcUser;
  Set<AmcRole> amcRoles;
  Set<AmcPermission> amcPermissions;

}
