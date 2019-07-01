package com.wensheng.zcc.comnfunc.service;

import com.wensheng.zcc.common.module.dto.WXUserGeoRecord;

public interface GaoDeService {
  public boolean getAddressFromGeoPoint(WXUserGeoRecord wxUserGeoRecord);

}
