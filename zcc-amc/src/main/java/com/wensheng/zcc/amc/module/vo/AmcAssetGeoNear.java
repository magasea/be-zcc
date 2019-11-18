package com.wensheng.zcc.amc.module.vo;

import java.util.List;
import lombok.Data;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

@Data
public class AmcAssetGeoNear {
  GeoJsonPoint geoJsonPoint;
  List<AmcAssetVo> amcAssetVoList;
  Integer[] distanceRange;
}
