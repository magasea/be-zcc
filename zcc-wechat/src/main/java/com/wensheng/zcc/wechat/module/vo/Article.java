package com.wensheng.zcc.wechat.module.vo;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Article {
  String title;
  @SerializedName("thumb_media_id")
  String thumbMediaId;
  String author;
  String digest;
  @SerializedName("show_cover_pic")
  int showCoverPic;
  String content;
  @SerializedName("content_source_url")
  String contentSourceUrl;
  @SerializedName("need_open_comment")
  int needOpenComment;
  @SerializedName("only_fans_can_comment")
  int onlyFansCanComment;
}
