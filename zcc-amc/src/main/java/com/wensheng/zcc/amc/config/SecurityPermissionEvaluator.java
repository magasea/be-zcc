package com.wensheng.zcc.amc.config;

import com.wensheng.zcc.amc.module.dao.helper.EditActionEnum;
import java.io.Serializable;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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
    if ((auth == null) || (targetDomainObject == null) || !(permission instanceof String)){
      return false;
    }
    String targetType = targetDomainObject.getClass().getSimpleName().toUpperCase();
    if(targetType.equals("LONG")){
      return hasPrivilege(auth, String.format("PERM_%s",targetDomainObject),
          permission.toString().toUpperCase());
    }else{
      return hasPrivilege(auth, targetType, permission.toString().toUpperCase());
    }

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
