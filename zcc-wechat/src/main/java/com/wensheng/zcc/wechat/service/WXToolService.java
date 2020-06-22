package com.wensheng.zcc.wechat.service;

import com.wensheng.zcc.common.module.dto.WechatUserInfo;
import com.wensheng.zcc.wechat.module.vo.RecomUserVisitInfo;
import com.wensheng.zcc.wechat.module.vo.WXSign4Url;
import java.util.List;

public interface WXToolService {
  WXSign4Url makeSignKey(String url) throws Exception;
  public void syncUserVisitInfoWithRecomm();
  public void notifyWechatUserLogin(WechatUserInfo wechatUserInfo);

  void patchUserFirstLoginTime(String jsonItems);

  void patchGeoRecord();

  void patchUserFavor();

  void pushUserVisitInfo(List<RecomUserVisitInfo> recomUserVisitInfos);
}
