package com.wensheng.zcc.cust.module.dao.mongo;

import java.util.Date;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document( collection = "CUST_TRD_GEO")
public class CustTrdGeo {

  @Id
  String Id;
  Long buyerId;
  @Indexed
  Long custTrdInfoId;
  Integer buyerType;
  @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
  GeoJsonPoint location;
  Long updateTime;

}
