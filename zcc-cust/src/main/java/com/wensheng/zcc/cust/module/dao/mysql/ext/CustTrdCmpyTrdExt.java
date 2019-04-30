package com.wensheng.zcc.cust.module.dao.mysql.ext;

import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdCmpy;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdInfo;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data

public class CustTrdCmpyTrdExt implements Serializable {
  Long id;
  CustTrdCmpy custTrdCmpy;
  List<CustTrdInfo> custTrdInfoList;

}
