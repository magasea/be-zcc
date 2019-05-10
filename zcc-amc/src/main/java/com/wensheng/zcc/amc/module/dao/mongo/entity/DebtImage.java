package com.wensheng.zcc.amc.module.dao.mongo.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "DEBT_IMAGE")
@CompoundIndexes({
    @CompoundIndex(name = "ossPath_debtId", def = "{'ossPath' : 1, 'debtId': 1}", unique = true)
})
@Data
public class DebtImage {
    String _id;
    String description;
    @Indexed(unique = true)
    String ossPath;
    String originalName;
    int tag; //是否位置图， 其它图片
    Boolean isToOss = false;//是否同步到OSS
    @Indexed(direction = IndexDirection.ASCENDING)
    Long debtId;

}
