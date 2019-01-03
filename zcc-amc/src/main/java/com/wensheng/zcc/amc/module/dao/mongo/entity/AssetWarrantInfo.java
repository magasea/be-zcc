package com.wensheng.zcc.amc.module.dao.mongo.entity;

import org.springframework.data.annotation.Id;

public class AssetWarrantInfo {
  @Id
  String _id;
  String warrantId;
  String assetId;
}
