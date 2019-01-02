package com.wensheng.zcc.amc.module.dao.mongo.origin;

import java.util.Date;
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
    private String baseDate;
    private String totalAmount;
    private String settleDate;
    private String status;
    private List<String> amcOwners;
    private String amcDebtCode;
    private String courtName;
    private String courtProvince;
    private String courtCity;
    private String courtCounty;
    private String editStatus;
    private String lawStatus;
    private Date publishDate;
    private String estimatedPrice;
    private String rating;
    private String amcContact1;
    private String amcContact2;
    private boolean isRecommanded;
    private Date startDate;
    private Date endDate;
    String creditor;            // 债权人（AMC）
    String creditorBranch;	        // 债权人（分行）
    String originalCreditor;	    // 原始债权人(银行)
    String guarantorType;   //担保方式
    String guarantor;        //保证人


}
