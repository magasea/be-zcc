package com.wensheng.zcc.sso.module.helper;

/**
 * @author chenwei on 2/26/19
 * @project zcc-backend
 */
public enum AmcRolesEnum {
  ROLE_AMC_USER("ROLE_AMC_USER", 1),
  ;
  AmcRolesEnum(String name , int id){
    this.name = name;
    this.id = id;
  }
  private String name;
  private int id;

}
