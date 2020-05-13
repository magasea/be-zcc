package com.wensheng.zcc.common.module.dto;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "WECHAT_USERWATCHOBJECT")
@CompoundIndexes({
    @CompoundIndex(name = "openId_objectId_type", def = "{'openId' : 1, 'objectId': 1, 'type':1}", unique = true)
})
@Data
public class WXUserWatchObject {

  @Id
  Long id;

  Integer type;

  String openId;
  String unionId;
  String phone;
  Long objectId;
  Date createTime;
  Date updateTime;

}
