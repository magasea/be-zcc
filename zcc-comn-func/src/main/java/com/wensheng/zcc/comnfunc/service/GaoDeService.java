package com.wensheng.zcc.comnfunc.service;

import com.wensheng.zcc.common.module.dto.WXUserGeoRecord;
import com.wensheng.zcc.comnfunc.module.vo.base.GaodeGeoQueryVal;
import java.util.List;

public interface GaoDeService {
  public boolean getAddressFromGeoPoint(WXUserGeoRecord wxUserGeoRecord);

  public List<GaodeGeoQueryVal> getGeoInfoFromAddress(String address, String city) throws Exception;

}
