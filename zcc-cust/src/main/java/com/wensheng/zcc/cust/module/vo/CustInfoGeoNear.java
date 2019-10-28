package com.wensheng.zcc.cust.module.vo;

import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdCmpy;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdPerson;
import java.util.List;
import lombok.Data;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
@Data
public class CustInfoGeoNear {
  GeoJsonPoint geoJsonPoint;
  List<CustTrdCmpyExtVo> custTrdCmpyList;
  List<CustTrdPersonExtVo> custTrdPersonList;
  Integer[] disttance;
}
