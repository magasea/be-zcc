package com.wensheng.zcc.cust.module.dao.mysql.ext;

import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdInfoExample;
import lombok.Data;

@Data
public class CustTrdInfoExtExample extends CustTrdInfoExample {
   protected String filterByClause;
   protected String limitByClause;
   protected String whereClause;
}