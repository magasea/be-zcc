package com.wensheng.zcc.cust.module.vo;

import com.wensheng.zcc.cust.module.dao.mongo.CustTrdGeo;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdCmpy;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdInfo;
import java.util.List;
import lombok.Data;

@Data
public class CustTrdCmpyExtVo {
    CustTrdCmpy custTrdCmpy;
    List<CustTrdGeo> custTrdInfos;
 }