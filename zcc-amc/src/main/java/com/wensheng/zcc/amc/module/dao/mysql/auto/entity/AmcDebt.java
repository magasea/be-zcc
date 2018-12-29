package com.wensheng.zcc.amc.module.dao.mysql.auto.entity;

import java.util.Date;

public class AmcDebt {
    private Long id;

    private Long debtpackId;

    private Long amcId;

    private String title;

    private Long baseAmount;

    private Date baseDate;

    private Long totalAmount;

    private Date settleDate;

    private Long courtId;

    private String grantor;

    private Short grantType;

    private String amcDebtCode;

    private Integer editStatus;

    private Date publishDate;

    private Integer lawStatus;

    private Long estimatedPrice;

    private String amcContact1;

    private String amcContact2;

    private Integer isRecommanded;

    private Date startDate;

    private Date endDate;

    private Long originId;

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

    public String getGrantor() {
        return grantor;
    }

    public void setGrantor(String grantor) {
        this.grantor = grantor == null ? null : grantor.trim();
    }

    public Short getGrantType() {
        return grantType;
    }

    public void setGrantType(Short grantType) {
        this.grantType = grantType;
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

    public Integer getLawStatus() {
        return lawStatus;
    }

    public void setLawStatus(Integer lawStatus) {
        this.lawStatus = lawStatus;
    }

    public Long getEstimatedPrice() {
        return estimatedPrice;
    }

    public void setEstimatedPrice(Long estimatedPrice) {
        this.estimatedPrice = estimatedPrice;
    }

    public String getAmcContact1() {
        return amcContact1;
    }

    public void setAmcContact1(String amcContact1) {
        this.amcContact1 = amcContact1 == null ? null : amcContact1.trim();
    }

    public String getAmcContact2() {
        return amcContact2;
    }

    public void setAmcContact2(String amcContact2) {
        this.amcContact2 = amcContact2 == null ? null : amcContact2.trim();
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

    public Long getOriginId() {
        return originId;
    }

    public void setOriginId(Long originId) {
        this.originId = originId;
    }
}