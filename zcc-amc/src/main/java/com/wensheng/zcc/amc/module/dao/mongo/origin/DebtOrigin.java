package com.wensheng.zcc.amc.module.dao.mongo.origin;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

/**
 * 
 * 
 */

@Data
public class DebtOrigin {
    private long id;
    private Long amc;
    private long debtpackId;
    private String title;
    private String baseAmount;
    private Timestamp baseDate;
    private String totalAmount;
    private Timestamp settleDate;
    private int status;
    private List<String> amcOwners;
    private String amcDebtCode;
    private String courtName;
    private String courtProvince;
    private String courtCity;
    private String courtCounty;
    private String editStatus;
    private String lawStatus;
    private Timestamp publishDate;
    private String estimatedPrice;
    private String rating;
    private String amcContact1;
    private String amcContact2;
    private int isRecommanded;
    private Timestamp startDate;
    private Timestamp endDate;


}
