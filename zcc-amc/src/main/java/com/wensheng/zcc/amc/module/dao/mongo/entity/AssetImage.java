package com.wensheng.zcc.amc.module.dao.mongo.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ASSET_IMAGE")
@CompoundIndexes({
    @CompoundIndex(name = "ossPath_assetId", def = "{'ossPath' : 1, 'amcAssetId': 1}", unique = true)
})
@Data
public class AssetImage {
    String _id;
    String description;

    String ossPath;
    String wechatPath;
    String mediaId;
    String originalName;
    Boolean mainPic; //是否为主图片
    int tag; //是否位置图， 其它图片
    @Indexed(direction = IndexDirection.ASCENDING)
    Long amcAssetId;

}
