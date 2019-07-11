package com.wensheng.zcc.amc.module.dao.mongo.entity;

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
    String mainPic;					// 抵押物主图片路径
    String keywords;					// 关键词f
    String description;				// 抵押物描述
    String warrant;         //权证信息
    String bidLink;					// 拍卖详情的链接
    Long courtId;           //法院id
    String zipCode;					// 邮政编码
    Float gpsLng;					// 坐标 （只适用于不动产）
    Float gpsLat;					// 坐标  （只适用于不动产）

    String amcAssetCode;				// AMC内部编码
    Integer viewCount	=0;			// 浏览次数
    Integer likeCount	=0;			// 点赞次数
    Integer watchCount	=0;			// 关注次数
    Integer commentCount =0;			// 评论次数

    String otherCatalog;  //填写的其他的类型

    String amcNotes;
    String linkUrl; //点击查看报告的链接
    Long amcContact2;  //联系人2
    int isRecommanded = 1; //是否推荐
    Date startDate;      //推荐资产的开始时间
    Date endDate;        //推荐资产的结束时间

    String reportPath;
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    GeoJson location;
}
