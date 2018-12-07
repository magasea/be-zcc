package com.wensheng.zcc.amc.module.dao.mysql.auto.entity;

import java.util.Date;

public class AmcDebtpack {
    private Long id;

    private String title;

    private String baseAmount;

    private String totalAmount;

    private Date settleDate;

    private String creditor;

    private String creditorBranch;

    private String originalCreditor;

    private String notes;

    private Long amcCompanyId;

    private Integer packStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getBaseAmount() {
        return baseAmount;
    }

    public void setBaseAmount(String baseAmount) {
        this.baseAmount = baseAmount == null ? null : baseAmount.trim();
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount == null ? null : totalAmount.trim();
    }

    public Date getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(Date settleDate) {
        this.settleDate = settleDate;
    }

    public String getCreditor() {
        return creditor;
    }

    public void setCreditor(String creditor) {
        this.creditor = creditor == null ? null : creditor.trim();
    }

    public String getCreditorBranch() {
        return creditorBranch;
    }

    public void setCreditorBranch(String creditorBranch) {
        this.creditorBranch = creditorBranch == null ? null : creditorBranch.trim();
    }

    public String getOriginalCreditor() {
        return originalCreditor;
    }

    public void setOriginalCreditor(String originalCreditor) {
        this.originalCreditor = originalCreditor == null ? null : originalCreditor.trim();
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes == null ? null : notes.trim();
    }

    public Long getAmcCompanyId() {
        return amcCompanyId;
    }

    public void setAmcCompanyId(Long amcCompanyId) {
        this.amcCompanyId = amcCompanyId;
    }

    public Integer getPackStatus() {
        return packStatus;
    }

    public void setPackStatus(Integer packStatus) {
        this.packStatus = packStatus;
    }
}