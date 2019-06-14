package com.wensheng.zcc.wechat.module.vo;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class TagCreate extends Tag {
  @SerializedName("name")
  String name;
}
