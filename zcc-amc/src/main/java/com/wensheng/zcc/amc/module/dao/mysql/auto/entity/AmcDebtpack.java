package com.wensheng.zcc.amc.module.dao.mysql.auto.entity;

import java.util.Date;

public class AmcDebtpack {
    private Long id;

    private String title;

    private Date settleDate;

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

    public Date getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(Date settleDate) {
        this.settleDate = settleDate;
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