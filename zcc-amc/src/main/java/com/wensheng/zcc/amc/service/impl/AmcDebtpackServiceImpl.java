package com.wensheng.zcc.amc.service.impl;

import com.wensheng.zcc.amc.dao.mysql.mapper.AmcCreditorDebtpackMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcDebtpackMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcOrigCreditorMapper;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcCreditorDebtpack;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcCreditorDebtpackExample;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtpack;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtpackExample;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcOrigCreditor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcOrigCreditorExample;
import com.wensheng.zcc.amc.module.vo.AmcDebtpackVo;
import com.wensheng.zcc.amc.service.AmcDebtpackService;
import com.wensheng.zcc.amc.utils.AmcNumberUtils;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

  @Override
  public AmcDebtpackVo create(AmcDebtpackVo amcDebtpackVo) {
    AmcDebtpack amcDebtpack = new AmcDebtpack();
    BeanUtils.copyProperties(amcDebtpackVo, amcDebtpack);
    amcDebtpack.setBaseAmount(AmcNumberUtils.getLongFromDecimalWithMult100(amcDebtpackVo.getBaseAmount()));
    amcDebtpack.setTotalAmount(AmcNumberUtils.getLongFromDecimalWithMult100(amcDebtpackVo.getTotalAmount()));
    Long id = Long.valueOf(amcDebtpackMapper.insertSelective(amcDebtpack));
    if(!CollectionUtils.isEmpty(amcDebtpackVo.getCreditors())){
      //make relationship between creditor and deptpack
      for(Long creditorId: amcDebtpackVo.getCreditors()){
        AmcCreditorDebtpackExample amcCreditorDebtpackExample = new AmcCreditorDebtpackExample();
        amcCreditorDebtpackExample.createCriteria().andCreditorIdEqualTo(creditorId).andDebtpackIdEqualTo(id);
        if(amcCreditorDebtpackMapper.countByExample(amcCreditorDebtpackExample) > 0){
          log.error("The relationship between debtpack id:"+id + " and creditor id:" + creditorId + " is already "
              + "exists");
          continue;
        }else{
          AmcCreditorDebtpack amcCreditorDebtpack = new AmcCreditorDebtpack();
          amcCreditorDebtpack.setCreditorId(creditorId);
          amcCreditorDebtpack.setDebtpackId(id);
          amcCreditorDebtpackMapper.insert(amcCreditorDebtpack);
        }
      }

    }
    amcDebtpackVo.setId(id);


    return amcDebtpackVo;
  }

  @Override
  public AmcDebtpackVo del(AmcDebtpack amcDebtpack) {
    return null;
  }

  @Override
  public AmcDebtpackVo update(AmcDebtpack amcDebtpack) {
    return null;
  }

  @Override
  public List<AmcDebtpackVo> queryAll(int offset, int size) {

    AmcDebtpackExample amcDebtpackExample = new AmcDebtpackExample();
    amcDebtpackExample.createCriteria().andIdGreaterThan(0L);
    RowBounds rowBounds = new RowBounds(offset, size);
    List<AmcDebtpack> amcDebtpacks = amcDebtpackMapper.selectByExampleWithRowbounds(amcDebtpackExample, rowBounds);

    return null;
  }

  @Override
  public AmcDebtpackVo get(Long amcDebtpackId) {
    return null;
  }

  @Override
  public List<AmcDebtpackVo> query(AmcDebtpack queryCond, int offset, int size) {
    return null;
  }

  @Override
  public AmcOrigCreditor createCreditor(AmcOrigCreditor amcOrigCreditor) {
    Long id = Long.valueOf(amcOrigCreditorMapper.insertSelective(amcOrigCreditor));
    amcOrigCreditor.setId(id);
    return amcOrigCreditor;
  }

  @Override
  public List<AmcOrigCreditor> getCreditors() {
    AmcOrigCreditorExample amcOrigCreditorExample = new AmcOrigCreditorExample();
    amcOrigCreditorExample.createCriteria().andIdGreaterThan(0L);
    List<AmcOrigCreditor> amcOrigCreditors = amcOrigCreditorMapper.selectByExample(amcOrigCreditorExample);
    return amcOrigCreditors;
  }


}
