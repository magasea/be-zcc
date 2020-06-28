package com.wensheng.zcc.comnfunc.service;

import com.wensheng.zcc.common.module.LatLng;
import com.wensheng.zcc.common.module.dto.WXUserGeoRecord;
import com.wensheng.zcc.comnfunc.module.vo.base.GaodeGeoQueryIPResp;
import com.wensheng.zcc.comnfunc.module.vo.base.GaodeGeoQueryVal;
import com.wensheng.zcc.comnfunc.module.vo.base.GaodeRegeoQueryVal;
import com.wenshengamc.zcc.common.Common.GeoJson;
import java.util.List;

public interface GaoDeService {
  public boolean getAddressFromGeoPoint(WXUserGeoRecord wxUserGeoRecord);

  public List<GaodeGeoQueryVal> getGeoInfoFromAddress(String address, String city) throws Exception;

  public GaodeRegeoQueryVal getAddressFromGeoPoint(LatLng latLng) throws Exception;

  public GaodeGeoQueryIPResp getAddressByIp(String ipadd);
}
