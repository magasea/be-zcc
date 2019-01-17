package com.wensheng.zcc.amc.module.dao.mysql.auto.entity;

import java.util.Date;

public class AmcDebt {
    private Long id;

    private Long debtpackId;

    private Long amcId;

    private String title;

    private Long baseAmount;

    private String baseAmountDesc;

    private Date baseDate;

    private Long totalAmount;

    private String totalAmountDesc;

    private Date settleDate;

    private Long courtId;

    private String amcDebtCode;

    private Integer editStatus;

    private Date publishDate;

    private String lawStatDesc;

    private Long estimatedPrice;

    private Long amcContact1;

    private Long amcContact2;

    private Integer isRecommanded;

    private Date startDate;

    private Date endDate;

    private Long origDebtId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDebtpackId() {
        return debtpackId;
    }

    public void setDebtpackId(Long debtpackId) {
        this.debtpackId = debtpackId;
    }

    public Long getAmcId() {
        return amcId;
    }

    public void setAmcId(Long amcId) {
        this.amcId = amcId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Long getBaseAmount() {
        return baseAmount;
    }

    public void setBaseAmount(Long baseAmount) {
        this.baseAmount = baseAmount;
    }

    public String getBaseAmountDesc() {
        return baseAmountDesc;
    }

    public void setBaseAmountDesc(String baseAmountDesc) {
        this.baseAmountDesc = baseAmountDesc == null ? null : baseAmountDesc.trim();
    }

    public Date getBaseDate() {
        return baseDate;
    }

    public void setBaseDate(Date baseDate) {
        this.baseDate = baseDate;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTotalAmountDesc() {
        return totalAmountDesc;
    }

    public void setTotalAmountDesc(String totalAmountDesc) {
        this.totalAmountDesc = totalAmountDesc == null ? null : totalAmountDesc.trim();
    }

    public Date getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(Date settleDate) {
        this.settleDate = settleDate;
    }

    public Long getCourtId() {
        return courtId;
    }

    public void setCourtId(Long courtId) {
        this.courtId = courtId;
    }

    public String getAmcDebtCode() {
        return amcDebtCode;
    }

    public void setAmcDebtCode(String amcDebtCode) {
        this.amcDebtCode = amcDebtCode == null ? null : amcDebtCode.trim();
    }

    public Integer getEditStatus() {
        return editStatus;
    }

    public void setEditStatus(Integer editStatus) {
        this.editStatus = editStatus;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getLawStatDesc() {
        return lawStatDesc;
    }

    public void setLawStatDesc(String lawStatDesc) {
        this.lawStatDesc = lawStatDesc == null ? null : lawStatDesc.trim();
    }

    public Long getEstimatedPrice() {
        return estimatedPrice;
    }

    public void setEstimatedPrice(Long estimatedPrice) {
        this.estimatedPrice = estimatedPrice;
    }

    public Long getAmcContact1() {
        return amcContact1;
    }

    public void setAmcContact1(Long amcContact1) {
        this.amcContact1 = amcContact1;
    }

    public Long getAmcContact2() {
        return amcContact2;
    }

    public void setAmcContact2(Long amcContact2) {
        this.amcContact2 = amcContact2;
    }

    public Integer getIsRecommanded() {
        return isRecommanded;
    }

    public void setIsRecommanded(Integer isRecommanded) {
        this.isRecommanded = isRecommanded;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getOrigDebtId() {
        return origDebtId;
    }

    public void setOrigDebtId(Long origDebtId) {
        this.origDebtId = origDebtId;
    }
}