package com.wensheng.zcc.amc.module.dao.mongo.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ASSET_IMAGE")
@Data
public class AssetImage {
    String _id;
    String description;
    @Indexed
    String ossPath;
    String originalName;
    Boolean mainPic; //是否为主图片
    int tag; //是否位置图， 其它图片
    Long originAssetId;
    @Indexed(direction = IndexDirection.ASCENDING)
    Long amcAssetId;

}
