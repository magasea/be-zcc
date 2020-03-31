package com.wensheng.zcc.amc.service;

import com.wensheng.zcc.common.module.dto.Region;
import com.wensheng.zcc.common.params.AmcPage;
import com.wensheng.zcc.common.params.sso.SSOAmcUser;
import com.wensheng.zcc.common.utils.sso.SSOQueryParam;

import java.util.List;

public interface RegionService {



  List<Region> getRegionByName(String regionName);
  Region getRegionById(Long regionId);

}
