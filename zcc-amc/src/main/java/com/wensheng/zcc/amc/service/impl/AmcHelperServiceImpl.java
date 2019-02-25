package com.wensheng.zcc.amc.service.impl;

import com.wensheng.zcc.amc.dao.mysql.mapper.AmcPersonMapper;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcPerson;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcPersonExample;
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
  AmcPersonMapper amcPersonMapper;

  private static final Map<Long, AmcPerson> localAmcPersonList = new WeakHashMap<Long, AmcPerson>();

  public AmcPerson getAmcPerson(Long id){
    if (localAmcPersonList.containsKey(id)){
      return localAmcPersonList.get(id);
    }else{
      AmcPerson amcPerson = amcPersonMapper.selectByPrimaryKey(id);
      localAmcPersonList.put(id, amcPerson);
      return amcPerson;
    }

  }

  public AmcPerson createPerson(AmcPerson amcPerson){
    Long id = Long.valueOf(amcPersonMapper.insertSelective(amcPerson));
    amcPerson.setId(id);
    localAmcPersonList.put(id, amcPerson);
    return amcPerson;
  }

  public AmcPerson updatePerson(AmcPerson amcPerson){
    amcPersonMapper.updateByPrimaryKeySelective(amcPerson);
    localAmcPersonList.put(amcPerson.getId(), amcPerson);
    return amcPerson;
  }

  @Override
  public List<AmcPerson> getAllAmcPerson(Long offset, int size, Map<String, Direction> orderByParam) throws Exception {
    RowBounds rowBounds = new RowBounds(offset.intValue(), size);
    AmcPersonExample amcPersonExample = new AmcPersonExample();
    amcPersonExample.setOrderByClause(SQLUtils.getOrderBy(orderByParam));
    return amcPersonMapper.selectByExampleWithRowbounds(amcPersonExample, rowBounds);
  }

  @Override
  public Long getPersonTotalCount() {
    return amcPersonMapper.countByExample(null);
  }


}
