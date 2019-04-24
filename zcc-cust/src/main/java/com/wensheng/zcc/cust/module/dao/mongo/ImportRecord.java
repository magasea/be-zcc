package com.wensheng.zcc.cust.module.dao.mongo;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import javax.persistence.Index;

@Document( collection = "IMPORT_CONSUMER_LOG")
@Data
public class ImportRecord {

    @Id
    String id;

    @Indexed
    String originId;

    int type;

    @Indexed
    Long custId;

}
