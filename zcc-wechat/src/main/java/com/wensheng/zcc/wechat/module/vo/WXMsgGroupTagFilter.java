package com.wensheng.zcc.wechat.module.vo;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class WXMsgGroupTagFilter {

  @SerializedName("is_to_all")
  Boolean isToAll;
  @SerializedName("tag_id")
  Integer tagId;

}
