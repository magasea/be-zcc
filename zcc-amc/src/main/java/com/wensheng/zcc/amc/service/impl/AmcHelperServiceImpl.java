package com.wensheng.zcc.amc.service.impl;

import com.wensheng.zcc.amc.dao.mysql.mapper.AmcDebtContactorMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcDebtMapper;
import com.wensheng.zcc.amc.module.dao.helper.PublishStateEnum;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebt;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtContactor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtContactorExample;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtExample;
import com.wensheng.zcc.amc.service.AmcHelperService;
import com.wensheng.zcc.amc.utils.SQLUtils;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import com.wensheng.zcc.common.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * @author chenwei on 1/9/19
 * @project zcc-backend
 */
@Service
@Slf4j
public class AmcHelperServiceImpl implements AmcHelperService {

  @Autowired
  AmcDebtContactorMapper amcDebtContactorMapper;

  @Autowired
  AmcDebtMapper amcDebtMapper;

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

  @Override
  public void deletePersons(Long[] contactorIds) throws Exception {
    StringBuilder sb = new StringBuilder();
    sb.append("Failed to delete contactor:");
    boolean failed = false;
    for(Long contactorId: contactorIds){
      if(!checkAndDelContactor(contactorId)){
        sb.append(contactorId).append(",");
        failed = true;
      }
    }
    if(failed){
      throw ExceptionUtils.getAmcException(ExceptionUtils.AmcExceptions.CANNOT_DEL, "some debt related");
    }

  }

  private boolean checkAndDelContactor(Long contactorId) {
    AmcDebtExample amcDebtExample = new AmcDebtExample();
    amcDebtExample.createCriteria().andAmcContactorIdEqualTo(contactorId)
            .andPublishStateNotEqualTo(PublishStateEnum.DELETED.getStatus());
    List<AmcDebt> amcDebts = amcDebtMapper.selectByExample(amcDebtExample);
    if(!CollectionUtils.isEmpty(amcDebts)){
      StringBuilder sb = new StringBuilder();
      amcDebts.stream().map(item -> sb.append(item.getId()).append(","));
      log.error(String.format("cannot delete contactor:%d because debt %s is not deleted", contactorId, sb));
      return false;
    }else{
      amcDebtContactorMapper.deleteByPrimaryKey(contactorId);
      return true;
    }

  }


}
