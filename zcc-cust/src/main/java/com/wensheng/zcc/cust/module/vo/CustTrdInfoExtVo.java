package com.wensheng.zcc.cust.module.vo;

import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdInfo;
import java.util.Date;
import lombok.Data;

@Data
public class CustTrdInfoExtVo extends CustTrdInfo {

  private String buyerName;

}
