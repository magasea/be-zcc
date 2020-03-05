package com.wensheng.zcc.cust.module.vo;

import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustAmcCmpycontactor;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdInfo;
import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class CustAmcCmpycontactorExtVo {
    private Long id;

    private CustAmcCmpycontactor custAmcCmpycontactor;
    private Map<String, Integer> favorCityPrep;
    private Map<String, Integer> favorTypePrep;


}