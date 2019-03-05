package com.wensheng.zcc.amc.module.dao.mongo.entity;

import javax.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author chenwei on 3/5/19
 * @project zcc-backend
 */
@Document(collection = "WCQR_IMAGE")
@Data
public class WechatQrImage {

  @Id
  String _id;
  @Indexed
  String fileName;


}
