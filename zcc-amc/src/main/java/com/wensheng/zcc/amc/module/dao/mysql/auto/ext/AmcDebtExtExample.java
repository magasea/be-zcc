package com.wensheng.zcc.amc.module.dao.mysql.auto.ext;

import com.wensheng.zcc.amc.module.dao.helper.MysqlPageInfo;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtExample;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;

/**
 * @author chenwei on 3/13/19
 * @project zcc-backend
 */
public class AmcDebtExtExample extends AmcDebtExample {
  public static class Criteria extends GeneratedCriteria {

    protected Criteria() {
      super();
    }

    public Criteria andWithPageInfo(MysqlPageInfo mysqlPageInfo) {
      StringBuffer sb = new StringBuffer();
      sb.append(" LIMIT ");
      sb.append(mysqlPageInfo.getOffset()).append(" , ").append(mysqlPageInfo.getPageSize());

      addCriterion(sb.toString());

      return this;
    }
  }

}
