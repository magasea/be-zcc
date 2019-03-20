package com.wensheng.zcc.sso.service;

import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcWechatUser;
import com.wensheng.zcc.sso.module.vo.WechatCode2SessionVo;

/**
 * @author chenwei on 3/19/19
 * @project miniapp-backend
 */
public interface WechatService {
  public String loginWechat(String code);

  public AmcWechatUser CUWechatUser(WechatCode2SessionVo wechatCode2SessionVo);


}
