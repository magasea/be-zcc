package com.wensheng.zcc.wechat.module.vo;

import lombok.Data;

@Data
public class Article {
  String title;
  String thumbMediaId;
  String author;
  String digest;
  int showCoverPic;
  String content;
  String contentSourceUrl;
  int needOpenComment;
  int onlyFansCanComment;
}
