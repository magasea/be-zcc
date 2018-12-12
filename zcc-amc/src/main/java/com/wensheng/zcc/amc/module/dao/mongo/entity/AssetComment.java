package com.wensheng.zcc.amc.module.dao.mongo.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
@Document(collection = "ASSET_COMMENT")
@Data
public class AssetComment {
    @Id
    String _id;
    Long postedBy;
    String nickname;
    String content;
    Long replyTo = 0L;
    String replyToName;
    Long topicId = 0L;
    Date createTime;
    Integer likeCount = 0;
    Integer dislikeCount = 0;
    String originAssetId;
    @Indexed(direction = IndexDirection.ASCENDING)
    Long assetId;
}
