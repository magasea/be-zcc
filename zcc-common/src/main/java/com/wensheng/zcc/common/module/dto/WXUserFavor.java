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
  @Indexed(unique = true)
  String openId;
  String unionId;
  String phone;
  //城市编码
  String locationCode;
  AmcSaleFilter amcSaleFilter;
  Date createTime;
  Date updateTime;

}
