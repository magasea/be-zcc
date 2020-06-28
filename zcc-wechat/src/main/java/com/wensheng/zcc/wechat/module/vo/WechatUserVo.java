package com.wensheng.zcc.wechat.module.vo;

import com.wensheng.zcc.common.module.dto.WXUserFavor;
import com.wensheng.zcc.common.module.dto.WXUserGeoRecord;
import com.wensheng.zcc.common.module.dto.WXUserWatchObject;
import com.wensheng.zcc.wechat.module.dao.mysql.auto.entity.WechatUser;
import com.wensheng.zcc.wechat.module.dao.mysql.auto.entity.WechatUserStatic;
import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class WechatUserVo  {
    WechatUser wechatUser;
    WechatUserStatic wechatUserStatic;
    List<WXUserWatchObject> wxUserWatchDebts;
    List<WXUserWatchObject> wxUserWatchAssets;
    WXUserGeoRecord wxUserGeoRecord;
    WXUserFavor wxUserFavor;
}