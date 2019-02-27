package com.wensheng.zcc.amc.service.impl;

import com.wensheng.zcc.amc.dao.mysql.mapper.AmcCreditorDebtpackMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcDebtContactorMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcDebtMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcDebtpackMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcOrigCreditorMapper;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebt;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtExample;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtpack;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtpackExample;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcOrigCreditor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcOrigCreditorExample;
import com.wensheng.zcc.amc.service.AmcDebtpackService;
import com.wensheng.zcc.amc.utils.SQLUtils;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chenwei on 1/7/19
 * @project zcc-backend
 */
@Service
@Slf4j
public class AmcDebtpackServiceImpl implements AmcDebtpackService {

  @Autowired
  AmcDebtpackMapper amcDebtpackMapper;

  @Autowired
  AmcOrigCreditorMapper amcOrigCreditorMapper;

  @Autowired
  AmcCreditorDebtpackMapper amcCreditorDebtpackMapper;

  @Autowired
  AmcDebtContactorMapper amcDebtContactorMapper;

  @Autowired
  AmcDebtMapper amcDebtMapper;

  @Override
  @Transactional
  public AmcDebtpack create(AmcDebtpack amcDebtpack) throws Exception {
    amcDebtpackMapper.insertSelective(amcDebtpack);

    return amcDebtpack;
  }

  @Override
  public AmcDebtpack del(AmcDebtpack amcDebtpack) {
    return null;
  }

  @Override
  public AmcDebtpack update(AmcDebtpack amcDebtpack) {
    AmcDebtpack amcDebtpackVo = new AmcDebtpack();

    amcDebtpackMapper.updateByPrimaryKey(amcDebtpack);

    return amcDebtpack;
  }



  @Override
  public List<AmcDebtpack> queryAll(int offset, int size) {

    AmcDebtpackExample amcDebtpackExample = new AmcDebtpackExample();
    amcDebtpackExample.createCriteria().andIdGreaterThan(0L);
    RowBounds rowBounds = new RowBounds(offset, size);
    List<AmcDebtpack> amcDebtpacks = amcDebtpackMapper.selectByExampleWithRowbounds(amcDebtpackExample, rowBounds);

    return amcDebtpacks;
  }

  @Override
  public AmcDebtpack get(Long amcDebtpackId) {
    AmcDebtpack amcDebtpack = amcDebtpackMapper.selectByPrimaryKey(amcDebtpackId);
       return amcDebtpack;
  }

  @Override
  public boolean exist(Long amcDebtpackId) {
    AmcDebtpackExample amcDebtpackExample = new AmcDebtpackExample();
    Long count =  amcDebtpackMapper.countByExample(amcDebtpackExample);
    return count > 0;
  }

  @Override
  public List<AmcDebtpack> query(AmcDebtpack queryCond, int offset, int size) {
    return null;
  }





  @Override
  public List<AmcOrigCreditor> getCreditorByDebtPackId(Long debtPackId) {
    AmcDebtExample amcDebtExample = new AmcDebtExample();
    amcDebtExample.createCriteria().andDebtpackIdEqualTo(debtPackId);
    List<AmcDebt> amcDebts = amcDebtMapper.selectByExample(amcDebtExample);
    List<Long> origCreditorIds = amcDebts.stream().map(amcDebt -> amcDebt.getOrigCreditorId()).collect(Collectors.toList());
    AmcOrigCreditorExample amcOrigCreditorExample = new AmcOrigCreditorExample();
    amcOrigCreditorExample.createCriteria().andIdIn(origCreditorIds);

    List<AmcOrigCreditor> origCreditors = amcOrigCreditorMapper.selectByExample(amcOrigCreditorExample);
    return origCreditors;
  }



  @Override
  public List<AmcDebtpack> queryAllDebtPacks(int offset, int size, Map<String, Direction> orderByParam) throws Exception {

      AmcDebtpackExample amcDebtpackExample = new AmcDebtpackExample();
      amcDebtpackExample.setOrderByClause(SQLUtils.getOrderBy(orderByParam));
      RowBounds rowBounds = new RowBounds(offset, size);
      List<AmcDebtpack> amcDebtpacks = amcDebtpackMapper.selectByExampleWithRowbounds(amcDebtpackExample,
          rowBounds);
      return amcDebtpacks;


  }

  @Override
  public Long getTotalCnt4Debtpacks() {
    AmcDebtpackExample amcDebtpackExample = new AmcDebtpackExample();
    Long count = amcDebtpackMapper.countByExample(amcDebtpackExample);
    return count;
  }


}
