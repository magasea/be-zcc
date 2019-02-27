package com.wensheng.zcc.amc.service;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtContactor;
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

  public List<AmcDebtContactor> getAllAmcDebtContactor(Long offset, int size,  Map<String, Direction> orderByParam) throws Exception;

  Long getPersonTotalCount();

}
