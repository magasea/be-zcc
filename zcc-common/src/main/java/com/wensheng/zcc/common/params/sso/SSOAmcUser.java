package com.wensheng.zcc.common.params.sso;

import java.util.Date;
import lombok.Data;

@Data
public class SSOAmcUser {
  private Long id;

  private String userName;
  private String userCname;

  private String mobilePhone;

  private String email;

  private Long deptId;

  private Long companyId;

  private Integer location;

  private Integer lgroup;

  private Integer valid;

  private Integer title;

  private String nickName;

  private Long createBy;

  private Date createDate;

  private Long updateBy;

  private Date updateDate;
}
