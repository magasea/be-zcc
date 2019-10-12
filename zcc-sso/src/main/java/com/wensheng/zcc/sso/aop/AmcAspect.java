package com.wensheng.zcc.sso.aop;

import com.wensheng.zcc.common.params.AmcRolesEnum;
import com.wensheng.zcc.common.utils.ExceptionUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils.AmcExceptions;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcUser;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcUserExample;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcUserRole;
import com.wensheng.zcc.sso.module.vo.AmcUserDetail;
import com.wensheng.zcc.sso.service.AmcUserService;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * @author chenwei on 1/15/19
 * @project zcc-backend
 */
@Aspect
@Component
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
    AmcUserExample.Criteria criteria = amcUserExample.or();
    if(amcUserDetail.getId() != null && 0 < amcUserDetail.getId() && (amcUserDetail.getAuthorities().contains(new SimpleGrantedAuthority(AmcRolesEnum.ROLE_AMC_LOCAL_ADMIN.getName()))||
        amcUserDetail.getAuthorities().contains(new SimpleGrantedAuthority(AmcRolesEnum.ROLE_AMC_LOCAL_VISITOR.getName())))){
      log.info("It is local admin and local visitor query, so only local users can be selected");
      criteria.andDeptIdEqualTo(amcUserDetail.getDeptId()).andLocationEqualTo(amcUserDetail.getLocation());

    }

    return joinPoint.proceed(new Object[]{amcUserExample});
  }

  @Around("@annotation(AmcUserModifyChecker)")
  public Object aroundUserModify(ProceedingJoinPoint joinPoint) throws Throwable {
    log.info("now get the point cut");
    CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
    Long targetUserId = null;
    for(int idx = 0; idx < codeSignature.getParameterNames().length; idx ++){
      if( "userId".equals(codeSignature.getParameterNames()[idx])){
        targetUserId = (Long) joinPoint.getArgs()[idx];
        break;
      }
    }
    List<AmcUserRole> amcUserRoleList =  amcUserService.getAmcUserRoles(targetUserId);
    if(CollectionUtils.isEmpty(amcUserRoleList)){
      log.error("Target user:{} doesn't have role", targetUserId);
      callReturn(joinPoint);
    }
    Set<Long> roleIds = amcUserRoleList.stream().map(item -> item.getRoleId()).collect(Collectors.toSet());

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//    Set<String> roleAndPerms =
//        authentication.getAuthorities().stream().map(item -> ((GrantedAuthority) item).getAuthority()).collect(
//            Collectors.toSet());
    AmcUserDetail amcUserDetail = (AmcUserDetail) authentication.getPrincipal();
    if(roleIds.contains(Long.valueOf(AmcRolesEnum.ROLE_SYSTEM_ADMIN.getId()))){
      throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_USER_OPERATION,"修改系统管理员的用户属性请联系开发技术部");
    }
    if(amcUserDetail.getId() != null && 0 < amcUserDetail.getId() ){
      List<AmcUserRole> currUserRoleList = amcUserService.getAmcUserRoles(amcUserDetail.getId());

      if(CollectionUtils.isEmpty(currUserRoleList)){
        log.error("Current user:{} doesn't have role", amcUserDetail.getId());
      }
      boolean isSysAdmin = false;
      for(AmcUserRole amcUserRole: currUserRoleList){
        if(amcUserRole.getRoleId() == AmcRolesEnum.ROLE_SYSTEM_ADMIN.getId()){
          isSysAdmin = true;
        }
        if(roleIds.contains(amcUserRole.getRoleId())){
          throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_USER_OPERATION,"不能修改同一个级别的用户属性");
        }
      }
      AmcUser amcUser = amcUserService.getUserById(targetUserId);
      if(amcUserDetail.getLocation() != null && !amcUserDetail.getLocation().equals(amcUser.getLocation()) && !isSysAdmin ){
        throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_USER_OPERATION,String.format("非系统管理员,"
            + "不能修改另一个区域的用户属性, 当前用户地域:%d 要修改的用户地域:%d", amcUserDetail.getLocation(), amcUser.getLocation()));
      }

      log.info("It is amc_admin query, so only local users can be selected");

    }

    return joinPoint.proceed();
  }

  private Object callReturn(ProceedingJoinPoint joinPoint) throws Throwable {
    return joinPoint.proceed();
  }

  @Around("@annotation(LogExecutionTime)")
  public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable{
    final long start = System.currentTimeMillis();
    final Object proceed = joinPoint.proceed();
    final long executime = System.currentTimeMillis() - start;
    log.info(String.format("%s executed in %d ms", joinPoint.getSignature(), executime));
    return proceed;
  }


}
