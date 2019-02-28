package com.wensheng.zcc.amc.module.dao.mongo.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "DEBT_ADDITIONAL")
@Data
public class DebtAdditional {
    @Id
    String _id;
    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    Long amcDebtId;
    String desc;					// 抵押物主图片路径
}
