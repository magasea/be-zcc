package com.wensheng.zcc.amc.module.dao.mongo.entity;

import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author chenwei on 4/10/19
 * @project miniapp-backend
 */
@Data
@Document("AMC_OPERLOG")
public class AmcOperLog {
  @Id
  String id;

  String userName;
  Long userId;
  @Indexed
  Long debtId;
  Integer actionId;
  Long assetId;
  String comment;
  LocalDateTime dateTime;


}
