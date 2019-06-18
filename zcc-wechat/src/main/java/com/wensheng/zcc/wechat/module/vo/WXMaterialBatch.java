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

