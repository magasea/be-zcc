package com.wensheng.zcc.wechat.service;

import com.wensheng.zcc.wechat.module.vo.WXSign4Url;

public interface WXToolService {
  WXSign4Url makeSignKey(String url) throws Exception;
}
