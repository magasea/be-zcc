package com.wensheng.zcc.cust.module.dao.mongo;

import java.util.Date;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document( collection = "MAIL_NEWCMPY_DETAIL")
@Data
public class MailNewCmpyDetail {
    @Id
    private String id;

    private String userMail;

    private String toMail;

    private String ccMail;

    private String subject;

    private String text;

    private String province;

    private Date createdAt;

}