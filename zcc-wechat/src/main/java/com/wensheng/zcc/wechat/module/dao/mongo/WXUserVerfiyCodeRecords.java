package com.wensheng.zcc.wechat.module.dao.mongo;

import java.util.Date;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "WXUSER_VERIFYCODE_RECORDS")
@Data
public class WXUserVerfiyCodeRecords {
  @Id
  String id;
  String openId;
  String phone;
  String verifyCode;
  Date createTime;

}
