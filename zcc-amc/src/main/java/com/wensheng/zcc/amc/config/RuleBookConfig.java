package com.wensheng.zcc.amc.config;

import com.deliveredtechnologies.rulebook.model.RuleBook;
import com.deliveredtechnologies.rulebook.spring.SpringAwareRuleBookRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author chenwei on 1/14/19
 * @project zcc-backend
 */
@Configuration
@ComponentScan(value = "com.wensheng.zcc.amc.rules")
public class RuleBookConfig {

  @Bean
  public <T>RuleBook<T> ruleBook4ZccEdit() {
    RuleBook ruleBook = new SpringAwareRuleBookRunner("com.wensheng.zcc.amc.rules.zccedit");
    return ruleBook;
  }

}
