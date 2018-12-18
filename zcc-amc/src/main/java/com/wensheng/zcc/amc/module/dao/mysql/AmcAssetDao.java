package com.wensheng.zcc.amc.module.dao.mysql;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcAsset;
import lombok.Data;

@Data
public class AmcAssetDao extends AmcAsset {
  public AmcAssetDao() {
    super.setStatus(-1);
    super.setAmcAssetCode("-1");
    super.setInitPrice(-1L);
    super.setLandArea(-1L);
  }



}
