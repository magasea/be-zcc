package com.wensheng.zcc.amc.module.dao.mongo.entity;

import javax.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
@Document(collection = "ASSET_DOCUMENT")
@Data
public class AssetDocument {
    @Id
    String _id;

    Integer docType;		// 文件类型如PDF，word，excel，图片
    @Indexed
    String title;		// 文件显示名
    String description;	// 文件描述
    String textPath;		// OCR文件路径
    String ossPath = null;		// 文件路径
    String keywords;		// 关键词方便搜索
    Integer readCount = 0;	// 阅读次数。初始建立时为0
    String originalName; // 文件名，对应的是originalName
    Date publishTime;
    Integer publishLevel;
    String      category; //分类,关系到给不给用户观看
    @Indexed( direction = IndexDirection.ASCENDING)
    Long amcAssetId;

    String originAssetId;
}
