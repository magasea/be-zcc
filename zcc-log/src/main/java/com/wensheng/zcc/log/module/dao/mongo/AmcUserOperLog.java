package com.wensheng.zcc.log.module.dao.mongo;

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
public class AmcUserOperLog {
  @Id
  String id;
  Long userId;
  String userName;
  Integer actionId;
  String param;
  LocalDateTime dateTime;

}
