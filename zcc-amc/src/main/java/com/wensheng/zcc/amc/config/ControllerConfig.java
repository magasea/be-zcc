package com.wensheng.zcc.amc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * @author chenwei on 1/3/19
 * @project zcc-backend
 */
@Configuration
public class ControllerConfig {
  @Bean(name = "multipartResolver")
  public CommonsMultipartResolver multipartResolver()
  {
    CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
    multipartResolver.setMaxUploadSize(20848820);
    return multipartResolver;
  }
}
