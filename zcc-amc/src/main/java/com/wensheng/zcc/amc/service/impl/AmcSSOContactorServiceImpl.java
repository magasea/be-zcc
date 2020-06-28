package com.wensheng.zcc.amc.service.impl;

import com.wensheng.zcc.amc.dao.mysql.mapper.AmcAssetMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcDebtMapper;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcAsset;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcAssetExample;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebt;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtContactor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtContactorExample;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtExample;
import com.wensheng.zcc.amc.module.dto.SSOContactorDTO;
import com.wensheng.zcc.amc.service.AmcSSOContactorService;
import com.wensheng.zcc.amc.service.AmcSSORpcService;
import com.wensheng.zcc.common.params.sso.AmcUserValidEnum;
import com.wensheng.zcc.common.params.sso.SSOAmcUser;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@Slf4j
public class AmcSSOContactorServiceImpl implements AmcSSOContactorService {

  @Autowired
  AmcDebtMapper amcDebtMapper;

  @Autowired
  AmcAssetMapper amcAssetMapper;

  @Autowired
  AmcSSORpcService amcSSORpcService;

  @Override
  public void checkContactorWithSSOUser() {

    AmcDebtExample amcDebtExample = new AmcDebtExample();
    amcDebtExample.setOrderByClause(" id desc ");
    Integer offset = 0;
    Integer pageSize = 20;
    RowBounds rowBounds = new RowBounds(offset, pageSize);
    List<AmcDebt> amcDebts =  amcDebtMapper.selectByExampleWithRowbounds(amcDebtExample, rowBounds);
    Map<String, Long> contactorName2SsoIdMap = new HashMap<>();
    while(!CollectionUtils.isEmpty(amcDebts)){
      connectDebtWithContactor(amcDebts, contactorName2SsoIdMap);
      offset += pageSize;
      rowBounds = new RowBounds(offset, pageSize);
      amcDebts =  amcDebtMapper.selectByExampleWithRowbounds(amcDebtExample, rowBounds);
    }

  }

  private void connectDebtWithContactor(List<AmcDebt> amcDebts,
      Map<String, Long> contactorName2SsoIdMap) {
    List<SSOContactorDTO> ssoContactorDTOS = new ArrayList<>();
    Set<String> contactorNameSet = new HashSet<>();
    for(AmcDebt amcDebt: amcDebts){
      if(!contactorName2SsoIdMap.containsKey(amcDebt.getAmcContactorName())){
        if(!contactorNameSet.contains(amcDebt.getAmcContactorName())){
          SSOContactorDTO ssoContactorDTO = new SSOContactorDTO();
          ssoContactorDTO.setContactorName(amcDebt.getAmcContactorName());
          ssoContactorDTO.setPhoneNum(amcDebt.getAmcContactorPhone());
          ssoContactorDTOS.add(ssoContactorDTO);
          contactorNameSet.add(amcDebt.getAmcContactorName());
        }

      }else{
        amcDebt.setAmcContactorId(contactorName2SsoIdMap.get(amcDebt.getAmcContactorName()));
        amcDebtMapper.updateByPrimaryKeySelective(amcDebt);
      }

    }
    List<SSOContactorDTO> ssoContactorDTOSResult = amcSSORpcService
        .checkSSOContactorInfo(ssoContactorDTOS);
    for(SSOContactorDTO ssoContactorDTO: ssoContactorDTOSResult){
      if(!contactorName2SsoIdMap.containsKey(ssoContactorDTO.getContactorName()) && ssoContactorDTO.isFound()){
        contactorName2SsoIdMap.put(ssoContactorDTO.getContactorName(), ssoContactorDTO.getSsoUserId());
      }
    }

    for(AmcDebt amcDebt: amcDebts){
      if(contactorName2SsoIdMap.containsKey(amcDebt.getAmcContactorName())){
        amcDebt.setAmcContactorId(contactorName2SsoIdMap.get(amcDebt.getAmcContactorName()));
        amcDebtMapper.updateByPrimaryKeySelective(amcDebt);
        AmcAssetExample amcAssetExample = new AmcAssetExample();
        amcAssetExample.createCriteria().andDebtIdEqualTo(amcDebt.getId());
        AmcAsset amcAsset = new AmcAsset();
        amcAsset.setAmcContactorId(amcDebt.getAmcContactorId());
        amcAssetMapper.updateByExampleSelective(amcAsset, amcAssetExample);
        amcAsset = null;
      }else{
        log.error("Failed to find contactor in sso by contactor name:{}", amcDebt.getAmcContactorName());
      }


    }

  }
}
