package com.wensheng.zcc.wechat.module.vo;


import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class WXMaterial {
  String title;
  @SerializedName("thumb_media_id")
  String thumbMediaId;
  @SerializedName("show_cover_pic")
  int showCoverPic;
  String author;
  String digest;
  String content;
  String url;
  @SerializedName("content_source_url")
  String contentSourceUrl;

  @SerializedName(("thumb_url"))
  String thumbUrl;
  @SerializedName("need_open_comment")
  Integer needOpenComment;

  @SerializedName("only_fans_can_comment")
  Integer onlyFansCanComment;
}

