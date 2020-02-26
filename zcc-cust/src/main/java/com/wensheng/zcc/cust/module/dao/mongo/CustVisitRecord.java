package com.wensheng.zcc.cust.module.dao.mongo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document( collection = "CUST_VISIT_RECORD")
@Data
public class CustVisitRecord {
  @org.springframework.data.annotation.Id
  String Id;
  @Indexed
  Long custId; //投资人ID 关联 投资人表 里的主键
  @Indexed
  Integer custSrc; // 投资人信息来源
  @Indexed
  Integer custType; // 投资人类型 公司 个人
  String  visitorName; // 拜访投资人的业务员姓名
  String  visitorRecord; // 拜访记录
  String  debtId; // 推销的债权id
  String  debtName; // 推销的债权名字
  Integer responseEnum; // 感兴趣程度 从不感兴趣 到非常感兴趣 1 - 10 分
  Long visitTime; // 拜访时间
  Long recordTime; // 记录时间
}
