package com.wensheng.zcc.comnfunc.service;



import com.wensheng.zcc.common.module.dto.AmcRegionInfo;
import com.wensheng.zcc.comnfunc.module.dto.AmcRegionItem;

import java.util.List;

public interface RegionService {


  List<AmcRegionItem> getRegionByName(String regName) throws Exception;


  AmcRegionInfo getRegionInfoByLngLat(Double lng, Double lat);


}
