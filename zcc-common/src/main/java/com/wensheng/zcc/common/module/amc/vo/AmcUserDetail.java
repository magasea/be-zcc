package com.wensheng.zcc.common.module.amc.vo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * @author chenwei on 4/8/19
 * @project miniapp-backend
 */
@Data
public class AmcUserDetail extends User implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;

  private String userName;

  private String userCname;

  private String password;

  private String mobilePhone;

  private String email;

  private Long deptId;

  private Long companyId;

  private Integer location;

  private Long ssoUserId;

  private Integer valid;

  private Integer title;

  private String nickName;

  private Long createBy;

  private Date createDate;

  private Long updateBy;

  private Date updateDate;

  public AmcUserDetail(String username, String password,
      Collection<GrantedAuthority> authorities) {
    super(username, password, authorities);
  }

  public AmcUserDetail(String username, String password, boolean enabled, boolean accountNonExpired,
      boolean credentialsNonExpired,
      boolean accountNonLocked,
      Collection<GrantedAuthority> authorities) {
    super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
  }


}
