package com.wensheng.zcc.wechat.module.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WXMsgCPRCheckResult {
  @JsonProperty("ArticleIdx")
  Long articleIdx;
  @JsonProperty("UserDeclareState")

  Integer userDeclareState;
  @JsonProperty("AuditState")

  Integer auditState;
  @JsonProperty("OriginalArticleUrl")

  String originalArticleUrl;
  @JsonProperty("OriginalArticleType")

  Integer originalArticleType;
  @JsonProperty("CanReprint")

  Integer canReprint;
  @JsonProperty("NeedReplaceContent")

  Integer needReplaceContent;
  @JsonProperty("NeedShowReprintSource")

  Integer needShowReprintSource;

}
