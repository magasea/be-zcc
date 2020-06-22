package com.wensheng.zcc.wechat.module.vo;

import lombok.Data;

@Data
public class RecomUserVisitInfo {
  String openid;
  Long firstLoginTime;
  Long lastTime;
  Long timeSpent;
}
