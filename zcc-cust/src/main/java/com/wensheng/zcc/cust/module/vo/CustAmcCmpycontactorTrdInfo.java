package com.wensheng.zcc.cust.module.vo;

import java.util.Date;
import lombok.Data;

@Data
public class CustAmcCmpycontactorTrdInfo {
    private Long id;

    private String name;

    private String phone;

    private String province;

    private String city;

    private String address;

    private String title;

    private String company;

    private String historyCmpy;

    private String recorderName;

    private String favoriteCityPrep;

    private String favoriteTypePrep;

    private String favoriteCityUpdate;

    private String favoriteTypeUpdate;

    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date updateTime;

}