package com.wensheng.zcc.cust.module.dao.mysql.ext;

import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustAmcCmpycontactorExample;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class CustAmcCmpycontactorExtExample extends CustAmcCmpycontactorExample {
    protected String filterByClause ;
    protected String limitByClause;
    protected String whereClause;

}