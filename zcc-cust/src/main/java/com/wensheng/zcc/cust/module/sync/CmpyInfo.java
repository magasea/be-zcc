package com.wensheng.zcc.cust.module.sync;

import lombok.Data;

@Data
public class CmpyInfo {
    Long id;
    int page;
    int rows;
    Long createTime;
    String createTimeStr;
    Long updateTime;
    Long infoId;
    String cmpyName;
    String uniSocialCode;
    String legalReptive;
    String cmpyPhone;
    String cmpyAddr;
    String annuReptPhone;
    String annuReptAddr;
    int dataStatus;
    String citys;
    String provinceName;
}
