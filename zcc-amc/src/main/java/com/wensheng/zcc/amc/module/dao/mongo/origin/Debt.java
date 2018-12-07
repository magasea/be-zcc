package com.wensheng.zcc.amc.module.dao.mongo.origin;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * 
 * 
 */


public class Debt {
    private long id;
    private long debtpackId;
    private String title;
    private String baseAmount;
    private Timestamp baseDate;
    private String totalAmount;
    private Timestamp settleDate;
    private int status;
    private String amcDebtCode;
    private String courtName;
    private String courtProvince;
    private String courtCity;
    private String courtCounty;
    private int editStatus;
    private Timestamp publishDate;
    private String estimatedPrice;
    private String rating;
    private String amcContact1;
    private String amcContact2;
    private int isRecommanded;
    private Timestamp startDate;
    private Timestamp endDate;

    
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    
    
    public long getDebtpackId() {
        return debtpackId;
    }

    public void setDebtpackId(long debtpackId) {
        this.debtpackId = debtpackId;
    }

    
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    
    
    public String getBaseAmount() {
        return baseAmount;
    }

    public void setBaseAmount(String baseAmount) {
        this.baseAmount = baseAmount;
    }

    
    
    public Timestamp getBaseDate() {
        return baseDate;
    }

    public void setBaseDate(Timestamp baseDate) {
        this.baseDate = baseDate;
    }

    
    
    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    
    
    public Timestamp getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(Timestamp settleDate) {
        this.settleDate = settleDate;
    }

    
    
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    
    
    public String getAmcDebtCode() {
        return amcDebtCode;
    }

    public void setAmcDebtCode(String amcDebtCode) {
        this.amcDebtCode = amcDebtCode;
    }

    
    
    public String getCourtName() {
        return courtName;
    }

    public void setCourtName(String courtName) {
        this.courtName = courtName;
    }

    
    
    public String getCourtProvince() {
        return courtProvince;
    }

    public void setCourtProvince(String courtProvince) {
        this.courtProvince = courtProvince;
    }

    
    
    public String getCourtCity() {
        return courtCity;
    }

    public void setCourtCity(String courtCity) {
        this.courtCity = courtCity;
    }

    
    
    public String getCourtCounty() {
        return courtCounty;
    }

    public void setCourtCounty(String courtCounty) {
        this.courtCounty = courtCounty;
    }

    
    
    public int getEditStatus() {
        return editStatus;
    }

    public void setEditStatus(int editStatus) {
        this.editStatus = editStatus;
    }

    
    
    public Timestamp getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Timestamp publishDate) {
        this.publishDate = publishDate;
    }

    
    
    public String getEstimatedPrice() {
        return estimatedPrice;
    }

    public void setEstimatedPrice(String estimatedPrice) {
        this.estimatedPrice = estimatedPrice;
    }

    
    
    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    
    
    public String getAmcContact1() {
        return amcContact1;
    }

    public void setAmcContact1(String amcContact1) {
        this.amcContact1 = amcContact1;
    }

    
    
    public String getAmcContact2() {
        return amcContact2;
    }

    public void setAmcContact2(String amcContact2) {
        this.amcContact2 = amcContact2;
    }

    
    
    public int getIsRecommanded() {
        return isRecommanded;
    }

    public void setIsRecommanded(int isRecommanded) {
        this.isRecommanded = isRecommanded;
    }

    
    
    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    
    
    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Debt amcDebt = (Debt) o;
        return id == amcDebt.id &&
                debtpackId == amcDebt.debtpackId &&
                status == amcDebt.status &&
                editStatus == amcDebt.editStatus &&
                isRecommanded == amcDebt.isRecommanded &&
                Objects.equals(title, amcDebt.title) &&
                Objects.equals(baseAmount, amcDebt.baseAmount) &&
                Objects.equals(baseDate, amcDebt.baseDate) &&
                Objects.equals(totalAmount, amcDebt.totalAmount) &&
                Objects.equals(settleDate, amcDebt.settleDate) &&
                Objects.equals(amcDebtCode, amcDebt.amcDebtCode) &&
                Objects.equals(courtName, amcDebt.courtName) &&
                Objects.equals(courtProvince, amcDebt.courtProvince) &&
                Objects.equals(courtCity, amcDebt.courtCity) &&
                Objects.equals(courtCounty, amcDebt.courtCounty) &&
                Objects.equals(publishDate, amcDebt.publishDate) &&
                Objects.equals(estimatedPrice, amcDebt.estimatedPrice) &&
                Objects.equals(rating, amcDebt.rating) &&
                Objects.equals(amcContact1, amcDebt.amcContact1) &&
                Objects.equals(amcContact2, amcDebt.amcContact2) &&
                Objects.equals(startDate, amcDebt.startDate) &&
                Objects.equals(endDate, amcDebt.endDate);
    }

    
    public int hashCode() {

        return Objects.hash(id, debtpackId, title, baseAmount, baseDate, totalAmount, settleDate, status, amcDebtCode, courtName, courtProvince, courtCity, courtCounty, editStatus, publishDate, estimatedPrice, rating, amcContact1, amcContact2, isRecommanded, startDate, endDate);
    }
}
