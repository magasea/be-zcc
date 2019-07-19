package com.wensheng.zcc.sso.aop;

import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcUser;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcUserExample;
import com.wensheng.zcc.sso.module.helper.AmcRolesEnum;
import com.wensheng.zcc.sso.module.vo.AmcUserDetail;
import com.wensheng.zcc.sso.service.AmcUserService;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

/**
 * @author chenwei on 1/15/19
 * @project zcc-backend
 */
@Aspect
@EnableAspectJAutoProxy
@Configuration
@Slf4j
public class AmcAspect {

  @Autowired
  AmcUserService amcUserService;


  @Around("@annotation(AmcUserCreateChecker)")
  public Object aroundUserCreate(ProceedingJoinPoint joinPoint) throws Throwable {
    log.info("now get the point cut");
    AmcUser amcUser = (AmcUser) joinPoint.getArgs()[0];

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if(authentication == null || ! (authentication.getDetails() instanceof OAuth2AuthenticationDetails)){
      log.error("it is web user");
      return joinPoint.proceed(new Object[]{amcUser});
    }
    log.info(authentication.getDetails().toString());
    AmcUserDetail amcUserDetail = (AmcUserDetail) authentication.getPrincipal();

    if(amcUserDetail.getId() != null && 0 < amcUserDetail.getId() && authentication.getAuthorities().contains(
        AmcRolesEnum.ROLE_AMC_USER.getName())){
//      AmcUser currUser = amcUserService.getUserById(amcUserDetail.getId());
      amcUser.setLocation(amcUserDetail.getLocation());
      amcUser.setDeptId(amcUserDetail.getDeptId());
    }

    return joinPoint.proceed(new Object[]{amcUser,joinPoint.getArgs()[1]});
  }

  @Around("@annotation(AmcUserQueryChecker)")
  public Object aroundUserQuery(ProceedingJoinPoint joinPoint) throws Throwable {
    log.info("now get the point cut");
    AmcUserExample amcUserExample = (AmcUserExample) joinPoint.getArgs()[0];

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    AmcUserDetail amcUserDetail = (AmcUserDetail) authentication.getPrincipal();

    if(amcUserDetail.getId() != null && 0 < amcUserDetail.getId() && authentication.getAuthorities().contains(
        AmcRolesEnum.ROLE_AMC_ADMIN.getName())){
      log.info("It is amc_admin query, so only local users can be selected");
      amcUserExample.createCriteria().andDeptIdEqualTo(amcUserDetail.getDeptId()).andLocationEqualTo(amcUserDetail.getLocation());

    }

    return joinPoint.proceed(new Object[]{amcUserExample});
  }


}
