package com.wensheng.zcc.sso.module.helper;

/**
 * @author chenwei on 2/26/19
 * @project zcc-backend
 */
public enum AmcRolesEnum {
  ROLE_SYSTEM_ADMIN("ROLE_SYSTEM_ADMIN", 1),
  ROLE_AMC_ADMIN("ROLE_AMC_ADMIN", 2),
  ROLE_AMC_USER("ROLE_AMC_USER", 3),
  ROLE_ZCC_CLIENT("ROLE_ZCC_CLIENT", 4),
  ;
  AmcRolesEnum(String name , int id){
    this.name = name;
    this.id = id;
  }
  private String name;
  private int id;

}
