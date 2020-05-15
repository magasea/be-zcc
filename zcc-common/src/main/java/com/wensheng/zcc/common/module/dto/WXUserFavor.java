package com.wensheng.zcc.common.module.dto;

import java.util.Date;
import javax.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "WECHAT_WXUSERFAVOR")
@Data
public class WXUserFavor {

  @Id
  String id;
  @Indexed
  String openId;
  String unionId;
  String phone;
  AmcSaleFilter amcSaleFilter;
  Date createTime;
  Date updateTime;

}
