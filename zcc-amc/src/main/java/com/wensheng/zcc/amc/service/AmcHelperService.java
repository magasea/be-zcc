package com.wensheng.zcc.amc.service;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcPerson;
import java.util.List;

/**
 * @author chenwei on 1/9/19
 * @project zcc-backend
 */
public interface AmcHelperService {

  public AmcPerson getAmcPerson(Long id);

  public AmcPerson createPerson(AmcPerson amcPerson);

  public AmcPerson updatePerson(AmcPerson amcPerson);

  public List<AmcPerson> getAllAmcPerson(Long offset, int size);

  Long getPersonTotalCount();

}
