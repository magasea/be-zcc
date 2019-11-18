package com.wensheng.zcc.cust.config;

import java.io.Serializable;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
    if ((auth == null)  || !(permission instanceof String)){
      return false;
    }
    if(targetDomainObject != null){
      String targetType = targetDomainObject.getClass().getSimpleName().toUpperCase();
      if(targetType.equals("LONG")){
        return hasPrivilege(auth, String.format("PERM_%s",targetDomainObject),
            permission.toString().toUpperCase());
      }else{
        return hasPrivilege(auth, targetType, permission.toString().toUpperCase());
      }
    }else{
      return hasPrivilege(auth, permission.toString().toUpperCase());
    }


  }

  private boolean hasPrivilege(Authentication auth, String permission) {
    boolean result = false;
    result = auth.getAuthorities().contains(new SimpleGrantedAuthority(permission.toUpperCase()));

    return result;
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
    boolean result = false;
    result = auth.getAuthorities().contains(new SimpleGrantedAuthority(permission.toUpperCase()));

    return result;
  }
}
