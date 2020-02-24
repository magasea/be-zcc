package com.wensheng.zcc.amc.service;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtContactor;
import com.wensheng.zcc.common.params.sso.SSOAmcUser;
import com.wensheng.zcc.common.utils.sso.SSOQueryParam;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Sort.Direction;

/**
 * @author chenwei on 1/9/19
 * @project zcc-backend
 */
public interface AmcHelperService {

  public AmcDebtContactor getAmcDebtContactor(Long id);

  public AmcDebtContactor createPerson(AmcDebtContactor AmcDebtContactor);

  public AmcDebtContactor updatePerson(AmcDebtContactor AmcDebtContactor);

  public List<AmcDebtContactor> getAllAmcDebtContactor(Long offset, int size, Map<String, Direction> orderByParam,
      int location) throws Exception;

  Long getPersonTotalCount(int location);

    void deletePersons(Long[] contactorIds) throws Exception;
  public List<SSOAmcUser> getSsoUserList(SSOQueryParam ssoQueryParam);
}
