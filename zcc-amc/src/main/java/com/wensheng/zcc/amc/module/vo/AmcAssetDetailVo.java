package com.wensheng.zcc.amc.module.vo;

import com.wensheng.zcc.amc.module.dao.mongo.entity.AssetAdditional;
import com.wensheng.zcc.amc.module.dao.mongo.entity.AssetComment;
import com.wensheng.zcc.amc.module.dao.mongo.entity.AssetDocument;
import com.wensheng.zcc.amc.module.dao.mongo.entity.AssetImage;
import com.wensheng.zcc.amc.module.dao.mongo.entity.AssetWarrantInfo;
import java.util.List;
import lombok.Data;

/**
 * @author chenwei on 1/7/19
 * @project zcc-backend
 */
@Data
public class AmcAssetDetailVo {
  AmcAssetVo amcAssetVo;
  AssetAdditional assetAdditional;
  List<AssetComment> assetComments;
  List<AssetDocument> assetDocument;
  List<AssetImage> assetImageList;
  List<AssetWarrantInfo> assetWarrantInfos;

}
