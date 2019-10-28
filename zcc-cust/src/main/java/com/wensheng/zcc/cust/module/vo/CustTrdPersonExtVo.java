package com.wensheng.zcc.cust.module.vo;

import com.wensheng.zcc.cust.module.dao.mongo.CustTrdGeo;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdInfo;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdPerson;
import java.util.List;
import lombok.Data;

@Data
public class CustTrdPersonExtVo {
    CustTrdPerson custTrdPerson;
    List<CustTrdGeo> custTrdInfos;
}