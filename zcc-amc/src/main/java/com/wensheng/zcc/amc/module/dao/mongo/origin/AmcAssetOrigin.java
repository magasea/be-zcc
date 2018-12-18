package com.wensheng.zcc.amc.module.dao.mongo.origin;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/*
"amc": NumberLong(2),
        "amcContact1": "朱俊宇-15261452997；83670269",
        "area": "0",
        "city": "珠海市",
        "commentCount": NumberInt(0),
        "description": "<p>中国广东省珠海市井岸镇新堂路6号14栋1单元1802房，面积208.68                    </p>",
        "editStatus": "发布",
        "estimatedPrice": 0,
        "gpsLat": "22.173527",
        "gpsLng": "113.27911",
        "likeCount": NumberInt(0),
        "mainPic": "/images/noimg.gif",
        "province": "广东省",
        "state": "抵押",
        "status": "未开始",
        "street": "井岸镇新堂路6号14栋1单元1802房",
        "title": "中国广东省珠海市井岸镇新堂路6号14栋1单元1802房",
        "type": "公寓",
        "version": NumberLong(4),
        "viewCount": NumberInt(0),
        "watchCount": NumberInt(0),
        "publishDate": ISODate("2017-05-23T07:48:25.000+08:00"),
        "amcOwners": [
            22
        ],
        "debts": [
            NumberLong(906)
        ],
        "isRecommanded": false,
        "reportPath": "/reports/Asset_Report/Asset_Report_6917.pdf"
 */
@Document(collection = "asset")
@Data
public class AmcAssetOrigin implements Serializable {
    @Id
    Long id;
    String title;					// 抵押物名称
    String mainPic;					// 抵押物主图片路径
    String type;						// 抵押物类别
    String status;					// 抵押物处置状态
    String keywords;					// 关键词f
    String description;				// 抵押物描述
    Double estimatedPrice;		// 抵押物类别司法评估价
    Double initialPrice;				// 拍卖起拍价
    String bidLink;					// 拍卖详情的链接
    String restrictions;	        // 是否限购
    String province;					// 抵押物所在省
    String city;						// 抵押物所在市
    String county;					// 抵押物所在县
    String street;					// 抵押物地址
    String buildingName;             //小区(楼盘)名字 爬取使用
    String zipCode;					// 邮政编码
    Float gpsLng;					// 坐标 （只适用于不动产）
    Float gpsLat;					// 坐标  （只适用于不动产）
    String courtName;		        // 法院名称
    String courtProvince;	        // 法院所在省
    String courtCity;		        // 法院所在市
    String courtCounty;		        // 法院所在县
    String courtInfo;               //法院信息
    String amcAssetCode;				// AMC内部编码
    Integer viewCount	=0;			// 浏览次数
    Integer likeCount	=0;			// 点赞次数
    Integer watchCount	=0;			// 关注次数
    Integer commentCount =0;			// 评论次数

    String otherCatalog;  //填写的其他的类型
    String state; //质押、冻结、抵押、查封
    String editStatus = "未发布"	;		// 1-发布， 2-未发布， 0-已删除， 3-已放弃， 4-已售出
    String  	area;			// 面积 （只适用于不动产）
    String      landArea;       //土地面积
    Date publishDate; // 发布时间
    String amcNotes;
    String linkUrl; //点击查看报告的链接
    String amcContact1; //联系人1
    String amcContact2;  //联系人2

    boolean isRecommanded = false; //是否推荐
    Date startDate;      //推荐资产的开始时间
    Date endDate;        //推荐资产的结束时间
    Long debtsNo;
    List debts ;
//    UserCompany amc;
    Long amc;

    String reportPath;

  @Override
  public String toString () {
    return ToStringBuilder.reflectionToString(this,ToStringStyle.SHORT_PREFIX_STYLE);
  }

}
