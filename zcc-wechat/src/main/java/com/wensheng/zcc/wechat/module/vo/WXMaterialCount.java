package com.wensheng.zcc.wechat.module.vo;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class WXMaterialCount extends GeneralResp {
  @SerializedName("voice_count")
  Integer voiceCount;
  @SerializedName("video_count")
  Integer videoCount;
  @SerializedName("image_count")
  Integer imageCount;
  @SerializedName("news_count")
  Integer newsCount;

}
