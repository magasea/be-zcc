package com.wensheng.zcc.wechat.module.vo;

import com.wensheng.zcc.wechat.module.dto.ResponseOfWxUserCummulate;
import com.wensheng.zcc.wechat.module.dto.ResponseOfWxUserSummary;
import lombok.Data;

@Data
public class WXUserStatistics {
  ResponseOfWxUserSummary userSummary;
  ResponseOfWxUserCummulate userCummulate;
}
