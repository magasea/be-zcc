package com.wensheng.zcc.amc.service.impl;

import com.wensheng.zcc.amc.dao.mysql.mapper.AmcDebtContactorMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcDebtMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcOrigCreditorMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.ZccDebtpackMapper;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebt;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtExample;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcOrigCreditor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcOrigCreditorExample;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.ZccDebtpack;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.ZccDebtpackExample;
import com.wensheng.zcc.amc.service.AmcDebtpackService;
import com.wensheng.zcc.amc.utils.SQLUtils;
import com.wensheng.zcc.common.params.sso.AmcLocationEnum;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chenwei on 1/7/19
 * @project zcc-backend
 */
@Service
@Slf4j
@CacheConfig(cacheNames = {"DEBTPACK"})
public class AmcDebtpackServiceImpl implements AmcDebtpackService {

  @Autowired
  ZccDebtpackMapper zccDebtpackMapper;

  @Autowired
  AmcOrigCreditorMapper amcOrigCreditorMapper;



  @Autowired
  AmcDebtContactorMapper amcDebtContactorMapper;

  @Autowired
  AmcDebtMapper amcDebtMapper;

  @Override
  @Transactional
  public ZccDebtpack create(ZccDebtpack zccDebtpack) throws Exception {
    zccDebtpackMapper.insertSelective(zccDebtpack);

    return zccDebtpack;
  }

  @Override
  public ZccDebtpack del(ZccDebtpack zccDebtpack) {
    return null;
  }

  @Override
  public ZccDebtpack update(ZccDebtpack zccDebtpack) {
    ZccDebtpack zccDebtpackVo = new ZccDebtpack();

    zccDebtpackMapper.updateByPrimaryKey(zccDebtpack);

    return zccDebtpack;
  }



  @Override
  public List<ZccDebtpack> queryAll(int offset, int size) {

    ZccDebtpackExample zccDebtpackExample = new ZccDebtpackExample();
    zccDebtpackExample.createCriteria().andIdGreaterThan(0L);
    RowBounds rowBounds = new RowBounds(offset, size);
    List<ZccDebtpack> zccDebtpacks = zccDebtpackMapper.selectByExampleWithRowbounds(zccDebtpackExample, rowBounds);

    return zccDebtpacks;
  }

  @Override
  public ZccDebtpack get(Long amcDebtpackId) {
    ZccDebtpack zccDebtpack = zccDebtpackMapper.selectByPrimaryKey(amcDebtpackId);
       return zccDebtpack;
  }

  @Override
  public boolean exist(Long amcDebtpackId) {
    ZccDebtpackExample zccDebtpackExample = new ZccDebtpackExample();
    Long count =  zccDebtpackMapper.countByExample(zccDebtpackExample);
    return count > 0;
  }

  @Override
  public List<ZccDebtpack> query(ZccDebtpack queryCond, int offset, int size) {
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
  @Cacheable()
  public List<ZccDebtpack> queryPacksWithLocation(AmcLocationEnum location) {
    ZccDebtpackExample zccDebtpackExample = new ZccDebtpackExample();

    zccDebtpackExample.createCriteria().andAreaEqualTo(location.getId());
    List<ZccDebtpack> zccDebtpacks = zccDebtpackMapper.selectByExample(zccDebtpackExample);
    return zccDebtpacks;
  }


  @Override
  public List<ZccDebtpack> queryAllDebtPacks(int offset, int size, Map<String, Direction> orderByParam) throws Exception {

      ZccDebtpackExample zccDebtpackExample = new ZccDebtpackExample();
      zccDebtpackExample.setOrderByClause(SQLUtils.getOrderBy(orderByParam));
      RowBounds rowBounds = new RowBounds(offset, size);
      List<ZccDebtpack> zccDebtpacks = zccDebtpackMapper.selectByExampleWithRowbounds(zccDebtpackExample,
          rowBounds);
      return zccDebtpacks;


  }

  @Override
  public Long getTotalCnt4Debtpacks() {
    ZccDebtpackExample zccDebtpackExample = new ZccDebtpackExample();
    Long count = zccDebtpackMapper.countByExample(zccDebtpackExample);
    return count;
  }


}
