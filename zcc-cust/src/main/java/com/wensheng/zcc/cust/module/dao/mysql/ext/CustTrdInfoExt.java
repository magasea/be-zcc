package com.wensheng.zcc.cust.module.dao.mysql.ext;

import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdInfo;
import java.util.Date;
import lombok.Data;

@Data
public class CustTrdInfoExt extends CustTrdInfo {
    public String sellerName;
}