package com.wensheng.zcc.amc.config;

import javax.servlet.MultipartConfigElement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author chenwei on 1/3/19
 * @project zcc-backend
 */
@Configuration
public class ControllerConfig implements WebMvcConfigurer {
  @Bean(name = "multipartResolver")
  public CommonsMultipartResolver multipartResolver()
  {
    CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
    multipartResolver.setMaxUploadSize(20848820);
    return multipartResolver;
  }



  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**");
  }

}
