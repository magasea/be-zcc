package com.wensheng.zcc.cust.module.vo;

import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdCmpy;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdPerson;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class CustsCountByTime {
  LocalDateTime startTime;
  List<CustTrdCmpy> newCustCmpiesByStartTime;
  List<CustTrdPerson> newCustPersonByStartTime;

}
