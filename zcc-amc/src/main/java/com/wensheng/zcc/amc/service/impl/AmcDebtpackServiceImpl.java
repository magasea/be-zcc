package com.wensheng.zcc.amc.service.impl;

import com.wensheng.zcc.amc.dao.mysql.mapper.AmcCreditorDebtpackMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcDebtMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcDebtpackMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcOrigCreditorMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcPersonMapper;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcCreditorDebtpack;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcCreditorDebtpackExample;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebt;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtExample;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtpack;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtpackExample;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcOrigCreditor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcOrigCreditorExample;
import com.wensheng.zcc.amc.module.vo.AmcDebtpackExtVo;
import com.wensheng.zcc.amc.service.AmcDebtpackService;
import com.wensheng.zcc.amc.utils.ExceptionUtils;
import com.wensheng.zcc.amc.utils.ExceptionUtils.AmcExceptions;
import com.wensheng.zcc.amc.utils.SQLUtils;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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
  AmcPersonMapper amcPersonMapper;

  @Autowired
  AmcDebtMapper amcDebtMapper;

  @Override
  @Transactional
  public AmcDebtpackExtVo create(AmcDebtpackExtVo amcDebtpackExtVo) throws Exception {
    AmcDebtpack amcDebtpack = new AmcDebtpack();
    BeanUtils.copyProperties(amcDebtpackExtVo.getAmcDebtpackInfo(), amcDebtpack);
    Long id = Long.valueOf(amcDebtpackMapper.insertSelective(amcDebtpack));
    if(!CollectionUtils.isEmpty(amcDebtpackExtVo.getAmcOrigCreditorList())){
      //make relationship between creditor and deptpack
      for(Long amcOrigCreditor: amcDebtpackExtVo.getAmcOrigCreditorList()){
        AmcOrigCreditorExample amcCreditorExample = new AmcOrigCreditorExample();
        amcCreditorExample.createCriteria().andIdEqualTo(amcOrigCreditor);
        if(amcOrigCreditorMapper.countByExample(amcCreditorExample) > 0){
          //now delete and insert the relationship between amcDebtPack and Creditor in table AMC_CREDITOR_DEBTPACK
          AmcCreditorDebtpackExample amcCreditorDebtpackExampleDel = new AmcCreditorDebtpackExample();
          amcCreditorDebtpackExampleDel.createCriteria().andDebtpackIdEqualTo(id);
          amcCreditorDebtpackMapper.deleteByExample(amcCreditorDebtpackExampleDel);

          AmcCreditorDebtpack amcCreditorDebtpack = new AmcCreditorDebtpack();
          amcCreditorDebtpack.setDebtpackId(id);
          amcCreditorDebtpack.setCreditorId(amcOrigCreditor);
          amcCreditorDebtpackMapper.insertSelective(amcCreditorDebtpack);
        }else{
          throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_ORIG_CREDITOR);
        }
      }

    }
    amcDebtpackExtVo.setId(id);


    return amcDebtpackExtVo;
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
  public List<AmcOrigCreditor> getAllCreditors(int offset, int size, Map<String, Direction> orderByParam)
      throws Exception {
    AmcOrigCreditorExample amcOrigCreditorExample = new AmcOrigCreditorExample();
    amcOrigCreditorExample.createCriteria().andIdGreaterThan(0L);
    amcOrigCreditorExample.setOrderByClause(SQLUtils.getOrderBy(orderByParam));
    RowBounds rowBounds = new RowBounds(offset, size);
    List<AmcOrigCreditor> amcOrigCreditors = amcOrigCreditorMapper.selectByExampleWithRowbounds(amcOrigCreditorExample, rowBounds);
    return amcOrigCreditors;
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
  public Long getCreditorsCount(){

    return amcOrigCreditorMapper.countByExample(null);
  }




}
