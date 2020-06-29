package com.wensheng.zcc.cust.module.vo;

import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdPerson;
import java.util.List;
import lombok.Data;

@Data
public class MergeCustVo{
  Long updateBy;
  List<Long> fromPersonIds;
  Long toPersonId;
}
