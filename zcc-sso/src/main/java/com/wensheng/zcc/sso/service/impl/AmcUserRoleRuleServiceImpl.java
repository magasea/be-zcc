package com.wensheng.zcc.sso.service.impl;

import com.google.gson.annotations.SerializedName;
import com.wensheng.zcc.sso.dao.mysql.mapper.AmcUserRoleRuleMapper;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcUserRoleRule;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcUserRoleRuleExample;
import com.wensheng.zcc.sso.service.AmcUserRoleRuleService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@CacheConfig(cacheNames = {"USER"})
public class AmcUserRoleRuleServiceImpl implements AmcUserRoleRuleService {

  @Autowired
  AmcUserRoleRuleMapper amcUserRoleRuleMapper;

  @Override
  @Cacheable
  public List<Integer> getRolesByDeptAndTitle(int deptId, int titleId) {
    AmcUserRoleRuleExample amcUserRoleRuleExample = new AmcUserRoleRuleExample();
    amcUserRoleRuleExample.createCriteria().andDeptIdEqualTo(deptId).andTitleEqualTo(titleId);

    List<AmcUserRoleRule> amcUserRoleRules =
        amcUserRoleRuleMapper.selectByExample(amcUserRoleRuleExample);

    return amcUserRoleRules.stream().map(item -> item.getRoleId()).collect(Collectors.toList());
  }
}
