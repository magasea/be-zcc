package com.wensheng.zcc.wechat.module.vo;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import lombok.Data;

@Data
public class WXMenu {
  @SerializedName("button")
  List<WXMenuItem> button;
}
