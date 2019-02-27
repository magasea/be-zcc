package com.wensheng.zcc.amc.module.dao.mysql;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcAsset;
import lombok.Data;

@Data
public class AmcAssetDao extends AmcAsset {
  public AmcAssetDao() {
    super.setSealedState(-1);
    super.setAmcAssetCode("-1");
    super.setStartPrice(-1L);
    super.setLandArea(-1L);
  }



}
