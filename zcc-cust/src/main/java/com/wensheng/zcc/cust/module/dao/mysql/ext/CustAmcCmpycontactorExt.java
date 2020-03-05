package com.wensheng.zcc.cust.module.dao.mysql.ext;

import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustAmcCmpycontactor;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdInfo;
import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class CustAmcCmpycontactorExt {
    private Long id;

    private CustAmcCmpycontactor custAmcCmpycontactor;

    private List<CustTrdInfo> custTrdInfoList;
}