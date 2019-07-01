package com.wensheng.zcc.cust.module.sync;

import lombok.Data;

@Data
public class PersonInfo {
    Long id;
    int page;
    int rows;
    Long createTime;
    String createTimeStr;
    Long updateTime;
    Long infoId;
    String name;
    String ageRange;
    String gender;
    String mobileNum;
    String telNum;
    String email;
    String address;
    String idCardNum;
    String province;
    String city;
    String notes;
    int dataStatus;
    String birthday;
    String birthdayStr;
    String citys;
    String provinceName;

}
