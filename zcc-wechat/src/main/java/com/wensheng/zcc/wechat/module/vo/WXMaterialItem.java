package com.wensheng.zcc.wechat.module.vo;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import lombok.Data;

@Data
public class WXMaterialItem extends GeneralResp{
  @SerializedName("news_item")
  List<WXMaterial> newsItem;
  @SerializedName("create_time")
  Long createTime;

  @SerializedName("update_time")
  Long updateTime;
}
