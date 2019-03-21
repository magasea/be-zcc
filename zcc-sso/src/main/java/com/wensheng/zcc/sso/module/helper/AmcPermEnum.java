package com.wensheng.zcc.sso.module.helper;

/**
 * @author chenwei on 3/20/19
 * @project miniapp-backend
 */
public enum AmcPermEnum {
  PERM_AMC_CRUD("PERM_AMC_CRUD",2),
  PERM_AMC_REVIEW("PERM_AMC_REVIEW",5),
  PERM_AMC_VIEW("PERM_AMC_VIEW",3),
  PERM_BASIC_INFO("PERM_BASIC_INFO",7),
  PERM_CREATE_AMCADMIN("PERM_CREATE_AMCADMIN",6),
  PERM_CREATE_AMCUSER("PERM_CREATE_AMCUSER",1),
  PERM_LAWYERAMC_VIEW("PERM_LAWYERAMC_VIEW",4),
  ;
  private String name;
  private Integer id;

  AmcPermEnum(String name, int id){
    this.name = name;
    this.id = id;
  }



}
