package com.wensheng.zcc.common.params;

import java.util.Date;
import lombok.Data;

@Data
public class AmcUser {
  private Long id;

  private String userName;

  private String userCname;

  private String password;

  private String mobilePhone;

  private String email;

  private Long deptId;

  private Integer lgroup;

  private Long companyId;

  private Long ssoUserId;

  private Integer location;

  private Integer valid;

  private Integer title;

  private String nickName;

  private Long createBy;

  private Date createDate;

  private Long updateBy;

  private Date updateDate;
}
