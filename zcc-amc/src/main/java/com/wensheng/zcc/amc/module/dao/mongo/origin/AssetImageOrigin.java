package com.wensheng.zcc.amc.module.dao.mongo.origin;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "assetImage")
@Data
public class AssetImageOrigin {
  String description;
  String path;
  String originalName;
  Boolean mainPic; //是否为主图片
  Boolean isToOss = false;//是否同步到OSS
  Long asset;


}
