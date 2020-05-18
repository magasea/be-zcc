package com.wensheng.zcc.amc.service.impl;

import com.wensheng.zcc.amc.aop.LogExecutionTime;
import com.wensheng.zcc.amc.controller.helper.QueryCurtParam;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcDebtContactorMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcDebtMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.CurtInfoMapper;
import com.wensheng.zcc.amc.module.dao.helper.PublishStateEnum;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.*;
import com.wensheng.zcc.amc.service.AmcContactorService;
import com.wensheng.zcc.amc.service.AmcHelperService;
import com.wensheng.zcc.amc.utils.SQLUtils;
import com.wensheng.zcc.common.params.AmcPage;
import com.wensheng.zcc.common.params.sso.SSOAmcUser;
import com.wensheng.zcc.common.utils.ExceptionUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils.AmcExceptions;
import com.wensheng.zcc.common.utils.sso.SSOQueryParam;
import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author chenwei on 1/9/19
 * @project zcc-backend
 */
@Service
@Slf4j
@CacheConfig(cacheNames = {"COURT"})
public class AmcHelperServiceImpl implements AmcHelperService {

  @Autowired
  AmcDebtContactorMapper amcDebtContactorMapper;

  @Autowired
  AmcDebtMapper amcDebtMapper;

  @Autowired
  AmcContactorService amcContactorService;

  @Autowired
  CurtInfoMapper curtInfoMapper;

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

  @Override
  public AmcPage<SSOAmcUser> getSsoUserList(SSOQueryParam ssoQueryParam){
    return amcContactorService.getSsoAmcUsers(ssoQueryParam);
  }

  @Override
  public CurtInfo addCurt(CurtInfo curtInfo) throws Exception {
    curtInfoMapper.insert(curtInfo);
    return curtInfo;
  }

  @Override
  @Cacheable
  @LogExecutionTime
  public List<CurtInfo> getAllCurts() throws Exception {

    return curtInfoMapper.selectByExample(null);

  }

  @Override
  @Cacheable
  @LogExecutionTime
  public List<CurtInfo> getCurtByIds(List<Long> ids) throws Exception {
    CurtInfoExample curtInfoExample = new CurtInfoExample();
    curtInfoExample.createCriteria().andIdIn(ids);
    return curtInfoMapper.selectByExample(curtInfoExample);

  }

  @Override
  public boolean delCurt(Long curtId) throws Exception {
    curtInfoMapper.deleteByPrimaryKey(curtId);
    return true;
  }

  @Override
  public boolean delCurtByQuery(QueryCurtParam queryCurtParam) throws Exception {
    CurtInfoExample curtInfoExample = new CurtInfoExample();
    CurtInfoExample.Criteria criteria = curtInfoExample.createCriteria();
    if(!StringUtils.isEmpty(queryCurtParam.getProvince())){
      criteria.andCurtProvinceEqualTo(queryCurtParam.getProvince());
    }
    if(!StringUtils.isEmpty(queryCurtParam.getCity())){
      criteria.andCurtCityEqualTo(queryCurtParam.getCity());
    }

    if(!StringUtils.isEmpty(queryCurtParam.getCounty())){
      criteria.andCurtCountyEqualTo(queryCurtParam.getCounty());
    }
    curtInfoMapper.deleteByExample(curtInfoExample);
    return true;
  }

  @Override
  public List<CurtInfo> queryCurtInfo(QueryCurtParam queryCurtParam) throws Exception {

    CurtInfoExample curtInfoExample = new CurtInfoExample();
    curtInfoExample.setOrderByClause(" id desc ");
    CurtInfoExample.Criteria criteria = curtInfoExample.createCriteria();

    int offset = 0;
    int size = 20;
    if(queryCurtParam.getPageInfo() != null && queryCurtParam.getPageInfo().getOffset() >= 0){
      if(queryCurtParam.getPageInfo().getSize() > 0){
        offset = queryCurtParam.getPageInfo().getOffset();
        size = queryCurtParam.getPageInfo().getSize();

      }
    }
    RowBounds rowBounds = new RowBounds(offset, size);
    if(!StringUtils.isEmpty(queryCurtParam.getProvince())){
      criteria.andCurtProvinceEqualTo(queryCurtParam.getProvince());
    }
    if(!StringUtils.isEmpty(queryCurtParam.getCity())){
      criteria.andCurtCityEqualTo(queryCurtParam.getCity());
    }

    if(!StringUtils.isEmpty(queryCurtParam.getCounty())){
      criteria.andCurtCountyEqualTo(queryCurtParam.getCounty());
    }
    return curtInfoMapper.selectByExampleWithRowbounds(curtInfoExample, rowBounds);

  }

  @Override
  @Cacheable
  public List<CurtInfo> queryCurtInfoByCityNames(List<String> cityNames) throws Exception {

    CurtInfoExample curtInfoExample = new CurtInfoExample();
    if(cityNames != null && !CollectionUtils.isEmpty(cityNames)){
      CurtInfoExample.Criteria criteriaCourt = curtInfoExample.createCriteria();
      if(cityNames.size() == 1){
        criteriaCourt.andCurtCityEqualTo(cityNames.get(0));
      }else{
        criteriaCourt.andCurtCityIn(cityNames);
      }

      return curtInfoMapper.selectByExample(curtInfoExample);

    }
    return null;

    
  }

  @Override
  @Cacheable
  public List<CurtInfo> queryCurtInfoByProvNames(List<String> provNames) throws Exception {
    CurtInfoExample curtInfoExample = new CurtInfoExample();
    if(provNames != null && !CollectionUtils.isEmpty(provNames)){
      CurtInfoExample.Criteria criteriaCourt = curtInfoExample.createCriteria();
      if(provNames.size() == 1){
        criteriaCourt.andCurtProvinceEqualTo(provNames.get(0));
      }else{
        criteriaCourt.andCurtProvinceIn(provNames);
      }
      return curtInfoMapper.selectByExample(curtInfoExample);
    }
    return null;
  }

  @Override
  public Long queryCurtInfoCount(QueryCurtParam queryCurtParam) throws Exception {
    CurtInfoExample curtInfoExample = new CurtInfoExample();
    CurtInfoExample.Criteria criteria = curtInfoExample.createCriteria();


    if(!StringUtils.isEmpty(queryCurtParam.getProvince())){
      criteria.andCurtProvinceEqualTo(queryCurtParam.getProvince());
    }
    if(!StringUtils.isEmpty(queryCurtParam.getCity())){
      criteria.andCurtCityEqualTo(queryCurtParam.getCity());
    }

    if(!StringUtils.isEmpty(queryCurtParam.getCounty())){
      criteria.andCurtCountyEqualTo(queryCurtParam.getCounty());
    }
    return curtInfoMapper.countByExample(curtInfoExample);
  }

  @Override
  public AmcDebtContactor createPerson(AmcDebtContactor AmcDebtContactor){
    amcDebtContactorMapper.insertSelective(AmcDebtContactor);
    localAmcDebtContactorList.put(AmcDebtContactor.getId(), AmcDebtContactor);
    return AmcDebtContactor;
  }

  @Override
  public AmcDebtContactor updatePerson(AmcDebtContactor AmcDebtContactor){
    amcDebtContactorMapper.updateByPrimaryKeySelective(AmcDebtContactor);
    localAmcDebtContactorList.put(AmcDebtContactor.getId(), AmcDebtContactor);
    return AmcDebtContactor;
  }

  @Override
  public List<AmcDebtContactor> getAllAmcDebtContactor(Long offset, int size, Map<String, Direction> orderByParam,
      int location) throws Exception {
    RowBounds rowBounds = new RowBounds(offset.intValue(), size);
    AmcDebtContactorExample amcDebtContactorExample = new AmcDebtContactorExample();
    if(location > 0){
      amcDebtContactorExample.createCriteria().andLocationEqualTo(location);
    }
    amcDebtContactorExample.setOrderByClause(SQLUtils.getOrderBy(orderByParam));
    return amcDebtContactorMapper.selectByExampleWithRowbounds(amcDebtContactorExample, rowBounds);
  }

  @Override
  public Long getPersonTotalCount(int location) {
    AmcDebtContactorExample amcDebtContactorExample = new AmcDebtContactorExample();

    if(location > 0){
      amcDebtContactorExample.createCriteria().andLocationEqualTo(location);
    }
    return amcDebtContactorMapper.countByExample(amcDebtContactorExample);
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
