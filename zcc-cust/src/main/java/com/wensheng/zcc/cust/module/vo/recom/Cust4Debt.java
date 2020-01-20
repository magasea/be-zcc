package com.wensheng.zcc.cust.module.vo.recom;


import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdCmpy;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdPerson;
import java.util.List;
import lombok.Data;

@Data
public class Cust4Debt {
  Long debtId;
  List<CustTrdCmpy> custTrdCmpyList;
  List<CustTrdPerson> custTrdPeople;
  List<Cust4Asset> cust4Assets;
}
