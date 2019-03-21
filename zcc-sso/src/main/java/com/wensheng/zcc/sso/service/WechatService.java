package com.wensheng.zcc.sso.service;

import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcWechatUser;
import com.wensheng.zcc.sso.module.vo.WechatCode2SessionVo;
import com.wensheng.zcc.sso.module.vo.WechatLoginResult;

/**
 * @author chenwei on 3/19/19
 * @project miniapp-backend
 */
public interface WechatService {
  public WechatLoginResult loginWechat(String code);

  public AmcWechatUser CUWechatUser(WechatCode2SessionVo wechatCode2SessionVo);


}
