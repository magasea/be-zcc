package com.wensheng.zcc.cust.module.dao.mysql.auto.entity;

import java.util.Date;

public class CustTrdInfo {
    private Long id;

    private Integer itemType;

    private Integer trdType;

    private String infoTitle;

    private Integer packCount;

    private String infoId;

    private String infoSource;

    private String infoWebsite;

    private String infoUrl;

    private String trdProvince;

    private String trdCity;

    private String trdAmountOrig;

    private Long trdAmount;

    private Long baseAmount;

    private Long interestAmount;

    private Long totalAmount;

    private Integer trdStatus;

    private Date trdDate;

    private Date infoTime;

    private String notes;

    private Integer buyerType;

    private Long buyerId;

    private Integer sellerType;

    private Long sellerId;

    private String trdContactorName;

    private String trdContactorAddr;

    private Integer dataStatus;

    private Integer dataQuality;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    public Integer getTrdType() {
        return trdType;
    }

    public void setTrdType(Integer trdType) {
        this.trdType = trdType;
    }

    public String getInfoTitle() {
        return infoTitle;
    }

    public void setInfoTitle(String infoTitle) {
        this.infoTitle = infoTitle == null ? null : infoTitle.trim();
    }

    public Integer getPackCount() {
        return packCount;
    }

    public void setPackCount(Integer packCount) {
        this.packCount = packCount;
    }

    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId == null ? null : infoId.trim();
    }

    public String getInfoSource() {
        return infoSource;
    }

    public void setInfoSource(String infoSource) {
        this.infoSource = infoSource == null ? null : infoSource.trim();
    }

    public String getInfoWebsite() {
        return infoWebsite;
    }

    public void setInfoWebsite(String infoWebsite) {
        this.infoWebsite = infoWebsite == null ? null : infoWebsite.trim();
    }

    public String getInfoUrl() {
        return infoUrl;
    }

    public void setInfoUrl(String infoUrl) {
        this.infoUrl = infoUrl == null ? null : infoUrl.trim();
    }

    public String getTrdProvince() {
        return trdProvince;
    }

    public void setTrdProvince(String trdProvince) {
        this.trdProvince = trdProvince == null ? null : trdProvince.trim();
    }

    public String getTrdCity() {
        return trdCity;
    }

    public void setTrdCity(String trdCity) {
        this.trdCity = trdCity == null ? null : trdCity.trim();
    }

    public String getTrdAmountOrig() {
        return trdAmountOrig;
    }

    public void setTrdAmountOrig(String trdAmountOrig) {
        this.trdAmountOrig = trdAmountOrig == null ? null : trdAmountOrig.trim();
    }

    public Long getTrdAmount() {
        return trdAmount;
    }

    public void setTrdAmount(Long trdAmount) {
        this.trdAmount = trdAmount;
    }

    public Long getBaseAmount() {
        return baseAmount;
    }

    public void setBaseAmount(Long baseAmount) {
        this.baseAmount = baseAmount;
    }

    public Long getInterestAmount() {
        return interestAmount;
    }

    public void setInterestAmount(Long interestAmount) {
        this.interestAmount = interestAmount;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getTrdStatus() {
        return trdStatus;
    }

    public void setTrdStatus(Integer trdStatus) {
        this.trdStatus = trdStatus;
    }

    public Date getTrdDate() {
        return trdDate;
    }

    public void setTrdDate(Date trdDate) {
        this.trdDate = trdDate;
    }

    public Date getInfoTime() {
        return infoTime;
    }

    public void setInfoTime(Date infoTime) {
        this.infoTime = infoTime;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes == null ? null : notes.trim();
    }

    public Integer getBuyerType() {
        return buyerType;
    }

    public void setBuyerType(Integer buyerType) {
        this.buyerType = buyerType;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public Integer getSellerType() {
        return sellerType;
    }

    public void setSellerType(Integer sellerType) {
        this.sellerType = sellerType;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public String getTrdContactorName() {
        return trdContactorName;
    }

    public void setTrdContactorName(String trdContactorName) {
        this.trdContactorName = trdContactorName == null ? null : trdContactorName.trim();
    }

    public String getTrdContactorAddr() {
        return trdContactorAddr;
    }

    public void setTrdContactorAddr(String trdContactorAddr) {
        this.trdContactorAddr = trdContactorAddr == null ? null : trdContactorAddr.trim();
    }

    public Integer getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(Integer dataStatus) {
        this.dataStatus = dataStatus;
    }

    public Integer getDataQuality() {
        return dataQuality;
    }

    public void setDataQuality(Integer dataQuality) {
        this.dataQuality = dataQuality;
    }
}