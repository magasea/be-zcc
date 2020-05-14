package com.wensheng.zcc.comnfunc.service;



import com.wensheng.zcc.comnfunc.module.dto.AmcRegionItem;

import java.util.List;

public interface RegionService {


  List<AmcRegionItem> getRegionByName(String regName) throws Exception;



}
