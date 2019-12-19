package com.wensheng.zcc.amc.module.dao.mongo.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wensheng.zcc.amc.config.GeoJsonDeserializer;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJson;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "ASSET_ADDITIONAL")
@Data
public class AssetAdditional {
    @Id
    String _id;
    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    Long amcAssetId;
    String description;				// 抵押物描述
    Integer viewCount	=0;			// 浏览次数
    Integer likeCount	=0;			// 点赞次数
    Integer watchCount	=0;			// 关注次数
    Integer commentCount =0;			// 评论次数
    int isRecommanded = 1; //是否推荐
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    @JsonDeserialize(using = GeoJsonDeserializer.class)
    private GeoJson location = null;
}
