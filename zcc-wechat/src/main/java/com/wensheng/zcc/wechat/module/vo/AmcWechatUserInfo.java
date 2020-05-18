package com.wensheng.zcc.wechat.module.vo;

import com.wensheng.zcc.common.module.dto.WXUserFavor;
import com.wensheng.zcc.common.module.dto.WXUserWatchObject;
import com.wensheng.zcc.wechat.module.dao.mysql.auto.entity.WechatUser;
import com.wenshengamc.zcc.wechat.WXUserWatchOnObj;
import java.util.List;
import lombok.Data;

@Data
public class AmcWechatUserInfo {
  WechatUser wechatUser;
  List<WXUserWatchObject> wxUserWatchObjectList;
  WXUserFavor wxUserFavor;
}
