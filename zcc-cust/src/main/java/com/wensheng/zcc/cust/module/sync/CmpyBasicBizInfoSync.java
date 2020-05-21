package com.wensheng.zcc.cust.module.sync;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class CmpyBasicBizInfoSync {

  private int page;
  private int rows;
  private String createTimeStr;
  private String id;
  private String name;
  private String type;
  private String socialCode;
  private String regCode;
  private String orgCode;
  private String taxCode;
  private String ieCode;
  private String legalPerson;
  private String operatingStatus;
  private double regCapital;
  private String regCapitalStr;
  private String paidCapital;
  private String paidCapitalStr;
  private long establishDate;
  private Object establishDateStr;
  private String operatingStart;
  private String operatingEnd;
  private String registerOrganization;
  private String approvalDate;
  private String engName;
  private String taxerQualification;
  private String employeeScale;
  private String region;
  private String industry;
  private String businessScope;
  private String historyName;
  private String entPhone;
  private String entEmail;
  private String entWebsite;
  private String entAddress;
  private String reportPhone;
  private String reportEmail;
  private String reportAddress;
  private String stockInfo;
  private long crawlingTime;
  private String cmpyProvince;
}
