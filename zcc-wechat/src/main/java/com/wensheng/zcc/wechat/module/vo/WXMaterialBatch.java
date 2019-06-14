package com.wensheng.zcc.wechat.module.vo;

import com.google.gson.annotations.SerializedName;
import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class WXMaterialBatch extends GeneralResp {
  @SerializedName("total_count")
  int totalCount;
  @SerializedName("item_count")
  int itemCount;
  List<WXMaterialContent> item;

}

@Data
class WXMaterialItem{
  @SerializedName("news_item")
  List<WXMaterial> newsItem;
  @SerializedName("create_time")
  Long createTime;

  @SerializedName("update_time")
  Long updateTime;
}

@Data
class WXMaterialContent{
  WXMaterialItem content;
  @SerializedName("update_time")
  Long  updateTime;
  @SerializedName("media_id")
  String mediaId;
  String name;
  String url;
}