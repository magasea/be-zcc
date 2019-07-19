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
import com.wensheng.zcc.common.utils.ExceptionUtils.AmcExceptions;
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
    for(Long contactorId: contactorIds){
      if(!checkAndDelContactor(contactorId)){
        sb.append(contactorId).append(",");

      }
    }


  }

  private boolean checkAndDelContactor(Long contactorId) throws Exception {
    AmcDebtExample amcDebtExample = new AmcDebtExample();
    amcDebtExample.createCriteria().andAmcContactorIdEqualTo(contactorId)
            .andPublishStateNotEqualTo(PublishStateEnum.DELETED.getStatus());
    List<AmcDebt> amcDebts = amcDebtMapper.selectByExample(amcDebtExample);
    if(!CollectionUtils.isEmpty(amcDebts)){
      StringBuilder sb = new StringBuilder();
      amcDebts.stream().forEach(item -> sb.append(item.getId()).append(","));
      if(sb.length() > 0){
        sb.setLength(sb.length() -1);
      }
      log.error(String.format("cannot delete contactor:%d because debt %s related", contactorId, sb));
      throw ExceptionUtils.getAmcException(AmcExceptions.CANNOT_DEL, String.format("不能删除联系人:[%d] "
          + "因为有债权 [%s] 关联到他/她, 可以考虑将债权的联系人设置为别人再做删除", contactorId, sb));

    }else{
      amcDebtContactorMapper.deleteByPrimaryKey(contactorId);
      return true;
    }


  }


}
