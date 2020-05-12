package com.wensheng.zcc.cust.module.dao.mysql.ext;

import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdCmpyExample;
import lombok.Data;

@Data
public class CustTrdCmpyExtExample extends CustTrdCmpyExample {
  protected String filterByClause ;
  protected String whereFilterByClause ;
  protected String limitByClause;
  protected String whereClause;

}
