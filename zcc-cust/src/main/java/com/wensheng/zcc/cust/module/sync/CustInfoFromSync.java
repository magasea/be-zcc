package com.wensheng.zcc.cust.module.sync;

import lombok.Data;

@Data
public class CustInfoFromSync {

    String address;
    String citys;
    Integer count;
    Long createTime;
    Integer dataStatus;
    String email;
    String id;
    String linkMan;
    String name;
    String notes;
    Integer page;
    String phone;
    Integer rows;
    Long total;
    Integer type;
    Long updateTime;
    CmpyInfo debtCustomerCompany;

    @Data
    public class CmpyInfo{
        String id;
        Integer page;
        Integer rows;
        Long createTime;
        Long updateTime;
        String cmpyName;
        String uniSocialCode;
        String legalReptive;
        String cmpyPhone;
        String cmpyAddr;
        String annuReptPhone;
        String annuReptAddr;
        String notes;
        String dataStatus;
    }
}
