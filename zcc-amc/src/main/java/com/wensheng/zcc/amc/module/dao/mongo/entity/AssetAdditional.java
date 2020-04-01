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
    String owner;                   //所有权人
    String description;				// 抵押物描述
    Integer viewCount	=0;			// 浏览次数
    Integer likeCount	=0;			// 点赞次数
    Integer watchCount	=0;			// 关注次数
    Integer commentCount =0;			// 评论次数
    int isRecommanded = 1; //是否推荐
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    @JsonDeserialize(using = GeoJsonDeserializer.class)
    private GeoJson location = null;
    String communityName; //小区名字
    String industrialPark; //工业园区
    int floor=-1;  //楼层
    int totalFloors=-1; //总楼层
    int renovation =-1; //精装修-1，普通装修-2，毛坯-3
    int elevator = -1; //有 1、无 2
    int buildingStructure = -1;//砖混-1；钢混-2；钢构-3；砖木-4；其他-5
    int landSupplyType = -1; //划拨-1；出让-2
    int landUsageType = -1; //住宅用地-1；商服用地 – 2；工业用地 - 3；采矿用地-4；仓储用地-5；其他用地-6
    int landLevel = -1; //土地级别
    String schoolInfo; //学区房信息（比如：学校名称)
    String transportInfo; //交通信息（比如：地铁站名称）
//    String monthlyRental; //租金
//    String mortgageNum; //抵押编号

    String bidLink; //拍卖链接
    String warrant; //权证信息


}
