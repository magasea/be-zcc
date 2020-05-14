package com.wensheng.zcc.wechat.module.vo;

import java.util.List;
import lombok.Data;

@Data
public class WXUserWatchOnCheckVo {
  String openId = "-1";
  List<WXUserWatchOnObject> wxUserWatchOnObjects = null;
}
