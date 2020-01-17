package com.wensheng.zcc.log.module.dao.mongo;

import com.wensheng.zcc.common.params.AmcUser;
import com.wensheng.zcc.common.params.sso.SSOAmcUser;
import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author chenwei on 4/8/19
 * @project miniapp-backend
 */
@Data
@Document
public class AmcUserLogin {
  @Id
  String id;
  AmcUser amcUser;
  LocalDateTime lastLoginTime;
  LocalDateTime currLoginTime;
}
