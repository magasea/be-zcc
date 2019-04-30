package com.wensheng.zcc.cust.module.dao.mongo;

import javax.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document( collection = "IMPORT_TRD_LOG")
@Data
public class ImportTrdInfoRecord {

    @Id
    String id;

    @Indexed(unique = true)
    String originId;


    @Indexed
    String origCustId;

    @Indexed
    Long custId;

    @Indexed
    Long trdId;

}
