package com.wensheng.zcc.amc.service.impl;

import com.wensheng.zcc.amc.dao.mysql.mapper.AmcDebtContactorMapper;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtContactor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtContactorExample;
import com.wensheng.zcc.amc.service.AmcHelperService;
import com.wensheng.zcc.amc.utils.SQLUtils;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

/**
 * @author chenwei on 1/9/19
 * @project zcc-backend
 */
@Service
public class AmcHelperServiceImpl implements AmcHelperService {

  @Autowired
  AmcDebtContactorMapper amcDebtContactorMapper;

  private static final Map<Long, AmcDebtContactor> localAmcDebtContactorList = new WeakHashMap<Long, AmcDebtContactor>();

  public AmcDebtContactor getAmcDebtContactor(Long id){
    if (localAmcDebtContactorList.containsKey(id)){
      return localAmcDebtContactorList.get(id);
    }else{
      AmcDebtContactor AmcDebtContactor = amcDebtContactorMapper.selectByPrimaryKey(id);
      localAmcDebtContactorList.put(id, AmcDebtContactor);
      return AmcDebtContactor;
    }

  }

  public AmcDebtContactor createPerson(AmcDebtContactor AmcDebtContactor){
    amcDebtContactorMapper.insertSelective(AmcDebtContactor);
    localAmcDebtContactorList.put(AmcDebtContactor.getId(), AmcDebtContactor);
    return AmcDebtContactor;
  }

  public AmcDebtContactor updatePerson(AmcDebtContactor AmcDebtContactor){
    amcDebtContactorMapper.updateByPrimaryKeySelective(AmcDebtContactor);
    localAmcDebtContactorList.put(AmcDebtContactor.getId(), AmcDebtContactor);
    return AmcDebtContactor;
  }

  @Override
  public List<AmcDebtContactor> getAllAmcDebtContactor(Long offset, int size, Map<String, Direction> orderByParam) throws Exception {
    RowBounds rowBounds = new RowBounds(offset.intValue(), size);
    AmcDebtContactorExample amcDebtContactorExample = new AmcDebtContactorExample();
    amcDebtContactorExample.setOrderByClause(SQLUtils.getOrderBy(orderByParam));
    return amcDebtContactorMapper.selectByExampleWithRowbounds(amcDebtContactorExample, rowBounds);
  }

  @Override
  public Long getPersonTotalCount() {
    return amcDebtContactorMapper.countByExample(null);
  }


}
