package com.wensheng.zcc.log.module.dao.mongo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJson;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author chenwei on 4/4/19
 * @project miniapp-backend
 */
@Document
@Data
public class WechatUserLocationLog {

  @Id
  String id;

  @Indexed
  String openId;

  @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
  GeoJson location;

  BigDecimal speed;
  BigDecimal accuracy;
  BigDecimal altitude;
  BigDecimal verticalAccuracy;
  BigDecimal horizontalAccuracy;
  LocalDateTime localDateTime;


}
