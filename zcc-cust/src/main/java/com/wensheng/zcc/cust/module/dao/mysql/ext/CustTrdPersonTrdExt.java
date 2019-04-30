package com.wensheng.zcc.cust.module.dao.mysql.ext;

import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdInfo;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdPerson;
import java.util.List;
import lombok.Data;

@Data
public class CustTrdPersonTrdExt {
  Long id;
  CustTrdPerson custTrdPerson;
  List<CustTrdInfo> custTrdInfoList;
}
