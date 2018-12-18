package com.wensheng.zcc.amc.module.dao.mysql.auto.entity;

import java.util.Date;

public class AmcDebt {
    private Long id;

    private Long debtpackId;

    private String title;

    private Long baseAmount;

    private Date baseDate;

    private Long totalAmount;

    private Date settleDate;

    private Integer status;

    private String amcDebtCode;

    private String courtName;

    private String courtProvince;

    private String courtCity;

    private String courtCounty;

    private Integer editStatus;

    private Date publishDate;

    private String estimatedPrice;

    private String rating;

    private String amcContact1;

    private String amcContact2;

    private Integer isRecommanded;

    private Date startDate;

    private Date endDate;

    private String originId;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAmcDebtCode() {
        return amcDebtCode;
    }

    public void setAmcDebtCode(String amcDebtCode) {
        this.amcDebtCode = amcDebtCode == null ? null : amcDebtCode.trim();
    }

    public String getCourtName() {
        return courtName;
    }

    public void setCourtName(String courtName) {
        this.courtName = courtName == null ? null : courtName.trim();
    }

    public String getCourtProvince() {
        return courtProvince;
    }

    public void setCourtProvince(String courtProvince) {
        this.courtProvince = courtProvince == null ? null : courtProvince.trim();
    }

    public String getCourtCity() {
        return courtCity;
    }

    public void setCourtCity(String courtCity) {
        this.courtCity = courtCity == null ? null : courtCity.trim();
    }

    public String getCourtCounty() {
        return courtCounty;
    }

    public void setCourtCounty(String courtCounty) {
        this.courtCounty = courtCounty == null ? null : courtCounty.trim();
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

    public String getEstimatedPrice() {
        return estimatedPrice;
    }

    public void setEstimatedPrice(String estimatedPrice) {
        this.estimatedPrice = estimatedPrice == null ? null : estimatedPrice.trim();
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating == null ? null : rating.trim();
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

    public String getOriginId() {
        return originId;
    }

    public void setOriginId(String originId) {
        this.originId = originId == null ? null : originId.trim();
    }
}