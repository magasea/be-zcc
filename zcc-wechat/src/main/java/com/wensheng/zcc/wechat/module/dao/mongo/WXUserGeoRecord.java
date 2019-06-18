package com.wensheng.zcc.wechat.module.dao.mongo;

import java.time.LocalDate;
import java.util.Date;
import javax.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.geo.GeoJson;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "WECHAT_USERGEO")
@Data
public class WXUserGeoRecord {

  @Id
  String id;

  String openId;

  String unionId;

  @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
  GeoJson location;

  String province;
  String city;

  String address;

  LocalDate updateTime ;

  LocalDate createTime;
}
