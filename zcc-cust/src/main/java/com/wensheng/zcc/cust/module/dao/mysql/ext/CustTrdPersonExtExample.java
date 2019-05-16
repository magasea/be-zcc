package com.wensheng.zcc.cust.module.dao.mysql.ext;

import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdPersonExample;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class CustTrdPersonExtExample extends CustTrdPersonExample {
   protected String filterByClause;
   protected String limitByClause;
   protected String whereClause;
}