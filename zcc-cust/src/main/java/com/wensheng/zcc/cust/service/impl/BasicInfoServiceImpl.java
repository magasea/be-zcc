package com.wensheng.zcc.cust.service.impl;

import com.wensheng.zcc.cust.dao.mysql.mapper.CustAmcUserprivMapper;
import com.wensheng.zcc.cust.dao.mysql.mapper.CustRegionMapper;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustAmcUserpriv;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustAmcUserprivExample;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustRegion;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustRegionExample;
import com.wensheng.zcc.cust.service.BasicInfoService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * @author chenwei on 4/18/19
 * @project miniapp-backend
 */
@Service
@Slf4j
@CacheConfig(cacheNames = {"regionName"})
public class BasicInfoServiceImpl implements BasicInfoService {
  @Autowired
  CustRegionMapper custRegionMapper;


  @Autowired
  CustAmcUserprivMapper amcUserprivMapper;

  @Override
  public List<CustRegion> getAllCustRegion() {

    return custRegionMapper.selectByExample(null);
  }

  @Override
  public List<CustRegion> getSubCustRegion(Long parentId) {
    CustRegionExample custRegionExample = new CustRegionExample();
    custRegionExample.createCriteria().andParentIdEqualTo(parentId);
    return custRegionMapper.selectByExample(custRegionExample);
  }

  @Cacheable(unless = "#result == null")
  @Override
  public String getRegionNameByCode(Long code) throws Exception {
    CustRegionExample custRegionExample = new CustRegionExample();
    custRegionExample.createCriteria().andIdEqualTo(code);
    List<CustRegion> custRegions = custRegionMapper.selectByExample(custRegionExample);
    if(CollectionUtils.isEmpty(custRegions)){
      log.error("Failed to find name for code:{}", code);
      throw new Exception(String.format("Failed to find name for code:%s", code));
    }
    return custRegions.get(0).getName();
  }

  @Cacheable(unless = "#result == null")
  @Override
  public List<CustAmcUserpriv> getAmcUserPriv() {
    CustAmcUserprivExample amcUserprivExample = new CustAmcUserprivExample();

    List<CustAmcUserpriv> custAmcUserprivs = amcUserprivMapper.selectByExample(amcUserprivExample);

    return custAmcUserprivs;
  }

  @Override
  public void  createOrUpdateAmcUserPriv(List<CustAmcUserpriv> custAmcUserprivs) {
    CustAmcUserprivExample amcUserprivExample = new CustAmcUserprivExample();

    amcUserprivMapper.deleteByExample(amcUserprivExample);

    for(CustAmcUserpriv custAmcUserpriv: custAmcUserprivs){
      amcUserprivMapper.insertSelective(custAmcUserpriv);
    }

    ;
  }
  @Cacheable(unless = "#result == null")
  @Override
  public Map<String, Integer> getAmcUserPrivMap() {
    CustAmcUserprivExample amcUserprivExample = new CustAmcUserprivExample();

    List<CustAmcUserpriv> custAmcUserprivs = amcUserprivMapper.selectByExample(amcUserprivExample);

    Map<String, Integer> userPrivMap = new HashMap<>();
    for(CustAmcUserpriv custAmcUserpriv: custAmcUserprivs){
      userPrivMap.put(custAmcUserpriv.getProvince(), custAmcUserpriv.getLocation());
    }
    return userPrivMap;
  }


  @Cacheable(unless = "#result == null")
  @Override
  public Map<Integer, List<String>> getAmcUserProvsMap() {
    CustAmcUserprivExample amcUserprivExample = new CustAmcUserprivExample();

    List<CustAmcUserpriv> custAmcUserprivs = amcUserprivMapper.selectByExample(amcUserprivExample);

    Map<Integer, List<String>> userProvsMap = new HashMap<>();
    for(CustAmcUserpriv custAmcUserpriv: custAmcUserprivs){
      if(!userProvsMap.containsKey(custAmcUserpriv.getLocation())){
        userProvsMap.put(custAmcUserpriv.getLocation(), new ArrayList<>());
      }
      userProvsMap.get(custAmcUserpriv.getLocation()).add(custAmcUserpriv.getProvince());
    }
    return userProvsMap;
  }

}
