package com.wensheng.zcc.wechat.module.vo;

import com.wensheng.zcc.wechat.module.vo.helper.NeedOpenCommentEnum;
import com.wensheng.zcc.wechat.module.vo.helper.OnlyFansCanCommentEnum;
import com.wensheng.zcc.wechat.module.vo.helper.ShowCoverPicEnum;
import lombok.Data;

@Data
public class ArticleVo {
  String title;
  String thumbMediaId;
  String author;
  String digest;
  ShowCoverPicEnum showCoverPic;
  String content;
  String contentSourceUrl;
  NeedOpenCommentEnum needOpenComment;
  OnlyFansCanCommentEnum onlyFansCanComment;
}
