package com.wensheng.zcc.sso.config;

import com.wensheng.zcc.sso.module.vo.AmcUserDetail;
import java.io.Serializable;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

/**
 * @author chenwei on 3/19/19
 * @project miniapp-backend
 */
@Component
public class SecurityPermissionEvaluator implements PermissionEvaluator {
//  @Autowired
//  private PermissionService permissionService;

  @Override
  public boolean hasPermission(Authentication auth, Object targetDomainObject, Object permission) {
    if ((auth == null) || (targetDomainObject == null) || !(permission instanceof String)){
      return false;
    }
    AmcUserDetail amcUserDetail = (AmcUserDetail) auth.getPrincipal();
    if(amcUserDetail == null){
      return false;
    }
    if( amcUserDetail.getCompanyId().longValue() != (Long) targetDomainObject){
      return false;
    }

    return hasPrivilege(auth, permission.toString().toUpperCase());


  }

  @Override
  public boolean hasPermission(Authentication auth, Serializable targetId, String targetType, Object permission) {
    if ((auth == null) || (targetType == null) || !(permission instanceof String)) {
      return false;
    }
    return hasPrivilege(auth, targetType.toUpperCase(),
        permission.toString().toUpperCase());
  }

  private boolean hasPrivilege(Authentication auth, String targetType, String permission) {
    for (GrantedAuthority grantedAuth : auth.getAuthorities()) {
      if (grantedAuth.getAuthority().startsWith(targetType)) {
        if (grantedAuth.getAuthority().toUpperCase().contains(permission)) {
          return true;
        }
      }
    }
    return false;
  }

  private boolean hasPrivilege(Authentication auth,  String permission) {
    for (GrantedAuthority grantedAuth : auth.getAuthorities()) {

      if (grantedAuth.getAuthority().toUpperCase().contains(permission)) {
        return true;

      }
    }
    return false;
  }
}
