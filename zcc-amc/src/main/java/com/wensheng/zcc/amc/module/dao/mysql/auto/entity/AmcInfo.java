package com.wensheng.zcc.amc.module.dao.mysql.auto.entity;

public class AmcInfo {
    private Long id;

    private Long amcId;

    private String name;

    private Long branchName;

    private String address;

    private String phoneNum;

    private String note;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAmcId() {
        return amcId;
    }

    public void setAmcId(Long amcId) {
        this.amcId = amcId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getBranchName() {
        return branchName;
    }

    public void setBranchName(Long branchName) {
        this.branchName = branchName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum == null ? null : phoneNum.trim();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }
}