package com.wensheng.zcc.amc.module.dao.mysql.auto.entity;

public class AmcDebtpack {
    private Long id;

    private String title;

    private String packTitle;

    private String notes;

    private Long amcId;

    private String amcDebtpackCode;

    private Integer packStatus;

    private Long amcCmpyId;

    private Integer area;

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

    public String getPackTitle() {
        return packTitle;
    }

    public void setPackTitle(String packTitle) {
        this.packTitle = packTitle == null ? null : packTitle.trim();
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes == null ? null : notes.trim();
    }

    public Long getAmcId() {
        return amcId;
    }

    public void setAmcId(Long amcId) {
        this.amcId = amcId;
    }

    public String getAmcDebtpackCode() {
        return amcDebtpackCode;
    }

    public void setAmcDebtpackCode(String amcDebtpackCode) {
        this.amcDebtpackCode = amcDebtpackCode == null ? null : amcDebtpackCode.trim();
    }

    public Integer getPackStatus() {
        return packStatus;
    }

    public void setPackStatus(Integer packStatus) {
        this.packStatus = packStatus;
    }

    public Long getAmcCmpyId() {
        return amcCmpyId;
    }

    public void setAmcCmpyId(Long amcCmpyId) {
        this.amcCmpyId = amcCmpyId;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }
}