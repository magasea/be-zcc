package com.wensheng.zcc.common.mq.kafka.module;

import java.util.Date;
import lombok.Data;

@Data
public class SSOUserDto {
  private Long id;

  private String userName;

  private String userCname;

  private String password;

  private String mobilePhone;

  private String salePhone;

  private String email;

  private Long deptId;

  private Long companyId;

  private Integer location;

  private Integer lgroup;

  private Integer valid;

  private Date expireDate;

  private Integer title;

  private String nickName;

  private String note;

  private String wxImgUrl;

  private String imgUrl;

  private Integer sex;

  private String familyName;

  private Long createBy;

  private Date createDate;

  private Long updateBy;

  private Date updateDate;
}
