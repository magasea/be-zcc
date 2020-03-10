package com.wensheng.zcc.cust.module.vo;

import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustAmcCmpycontactor;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdInfo;
import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class CustAmcCmpycontactorTrdInfoVo {
    CustAmcCmpycontactor custAmcCmpycontactor;
    List<CustTrdInfo> custTrdInfoList;
}