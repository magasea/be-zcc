package com.wensheng.zcc.cust.module.dao.mysql.auto.entity;

import java.util.Date;

public class CustTrdPerson {
    private Long id;

    private String origId;

    private String name;

    private Integer ageRange;

    private Integer gender;

    private String mobileUpdate;

    private String mobilePrep;

    private String mobileHistory;

    private String phoneUpdate;

    private String phonePrep;

    private String phoneHistory;

    private String email;

    private String idCardNum;

    private String province;

    private String city;

    private String addr;

    private String historyAddr;

    private String notes;

    private Integer dataStatus;

    private Integer dataQuality;

    private Date updateTime;

    private Long updateBy;

    private Long createBy;

    private Date createTime;

    private Date syncTime;

    private Integer status;

    private String trdInfoBak;

    private String mobileNumBak;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrigId() {
        return origId;
    }

    public void setOrigId(String origId) {
        this.origId = origId == null ? null : origId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getAgeRange() {
        return ageRange;
    }

    public void setAgeRange(Integer ageRange) {
        this.ageRange = ageRange;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getMobileUpdate() {
        return mobileUpdate;
    }

    public void setMobileUpdate(String mobileUpdate) {
        this.mobileUpdate = mobileUpdate == null ? null : mobileUpdate.trim();
    }

    public String getMobilePrep() {
        return mobilePrep;
    }

    public void setMobilePrep(String mobilePrep) {
        this.mobilePrep = mobilePrep == null ? null : mobilePrep.trim();
    }

    public String getMobileHistory() {
        return mobileHistory;
    }

    public void setMobileHistory(String mobileHistory) {
        this.mobileHistory = mobileHistory == null ? null : mobileHistory.trim();
    }

    public String getPhoneUpdate() {
        return phoneUpdate;
    }

    public void setPhoneUpdate(String phoneUpdate) {
        this.phoneUpdate = phoneUpdate == null ? null : phoneUpdate.trim();
    }

    public String getPhonePrep() {
        return phonePrep;
    }

    public void setPhonePrep(String phonePrep) {
        this.phonePrep = phonePrep == null ? null : phonePrep.trim();
    }

    public String getPhoneHistory() {
        return phoneHistory;
    }

    public void setPhoneHistory(String phoneHistory) {
        this.phoneHistory = phoneHistory == null ? null : phoneHistory.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getIdCardNum() {
        return idCardNum;
    }

    public void setIdCardNum(String idCardNum) {
        this.idCardNum = idCardNum == null ? null : idCardNum.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr == null ? null : addr.trim();
    }

    public String getHistoryAddr() {
        return historyAddr;
    }

    public void setHistoryAddr(String historyAddr) {
        this.historyAddr = historyAddr == null ? null : historyAddr.trim();
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes == null ? null : notes.trim();
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getSyncTime() {
        return syncTime;
    }

    public void setSyncTime(Date syncTime) {
        this.syncTime = syncTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTrdInfoBak() {
        return trdInfoBak;
    }

    public void setTrdInfoBak(String trdInfoBak) {
        this.trdInfoBak = trdInfoBak == null ? null : trdInfoBak.trim();
    }

    public String getMobileNumBak() {
        return mobileNumBak;
    }

    public void setMobileNumBak(String mobileNumBak) {
        this.mobileNumBak = mobileNumBak == null ? null : mobileNumBak.trim();
    }
}