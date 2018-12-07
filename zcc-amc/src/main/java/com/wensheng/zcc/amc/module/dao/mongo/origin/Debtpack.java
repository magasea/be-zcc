package com.wensheng.zcc.amc.module.dao.mongo.origin;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * 
 * 
 */


public class Debtpack {
    private long id;
    private String title;
    private String baseAmount;
    private String totalAmount;
    private Timestamp settleDate;
    private String creditor;
    private String creditorBranch;
    private String originalCreditor;
    private String notes;
    private long amcCompanyId;
    private int packStatus;

    
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    
    
    public String getCreditor() {
        return creditor;
    }

    public void setCreditor(String creditor) {
        this.creditor = creditor;
    }

    
    
    public String getCreditorBranch() {
        return creditorBranch;
    }

    public void setCreditorBranch(String creditorBranch) {
        this.creditorBranch = creditorBranch;
    }

    
    
    public String getOriginalCreditor() {
        return originalCreditor;
    }

    public void setOriginalCreditor(String originalCreditor) {
        this.originalCreditor = originalCreditor;
    }

    
    
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    
    
    public long getAmcCompanyId() {
        return amcCompanyId;
    }

    public void setAmcCompanyId(long amcCompanyId) {
        this.amcCompanyId = amcCompanyId;
    }

    
    
    public int getPackStatus() {
        return packStatus;
    }

    public void setPackStatus(int packStatus) {
        this.packStatus = packStatus;
    }

    
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Debtpack that = (Debtpack) o;
        return id == that.id &&
                amcCompanyId == that.amcCompanyId &&
                packStatus == that.packStatus &&
                Objects.equals(title, that.title) &&
                Objects.equals(baseAmount, that.baseAmount) &&
                Objects.equals(totalAmount, that.totalAmount) &&
                Objects.equals(settleDate, that.settleDate) &&
                Objects.equals(creditor, that.creditor) &&
                Objects.equals(creditorBranch, that.creditorBranch) &&
                Objects.equals(originalCreditor, that.originalCreditor) &&
                Objects.equals(notes, that.notes);
    }

    
    public int hashCode() {

        return Objects.hash(id, title, baseAmount, totalAmount, settleDate, creditor, creditorBranch, originalCreditor, notes, amcCompanyId, packStatus);
    }
}
