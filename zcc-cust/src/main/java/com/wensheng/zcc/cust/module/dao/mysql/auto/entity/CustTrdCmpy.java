package com.wensheng.zcc.cust.module.dao.mysql.auto.entity;

import java.util.Date;

public class CustTrdCmpy {
    private Long id;

    private String cmpyName;

    private String uniSocialCode;

    private String legalReptive;

    private String cmpyPhone;

    private String cmpyAddr;

    private String annuReptPhone;

    private String annuReptAddr;

    private Integer dataStatus;

    private Integer dataQuality;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCmpyName() {
        return cmpyName;
    }

    public void setCmpyName(String cmpyName) {
        this.cmpyName = cmpyName == null ? null : cmpyName.trim();
    }

    public String getUniSocialCode() {
        return uniSocialCode;
    }

    public void setUniSocialCode(String uniSocialCode) {
        this.uniSocialCode = uniSocialCode == null ? null : uniSocialCode.trim();
    }

    public String getLegalReptive() {
        return legalReptive;
    }

    public void setLegalReptive(String legalReptive) {
        this.legalReptive = legalReptive == null ? null : legalReptive.trim();
    }

    public String getCmpyPhone() {
        return cmpyPhone;
    }

    public void setCmpyPhone(String cmpyPhone) {
        this.cmpyPhone = cmpyPhone == null ? null : cmpyPhone.trim();
    }

    public String getCmpyAddr() {
        return cmpyAddr;
    }

    public void setCmpyAddr(String cmpyAddr) {
        this.cmpyAddr = cmpyAddr == null ? null : cmpyAddr.trim();
    }

    public String getAnnuReptPhone() {
        return annuReptPhone;
    }

    public void setAnnuReptPhone(String annuReptPhone) {
        this.annuReptPhone = annuReptPhone == null ? null : annuReptPhone.trim();
    }

    public String getAnnuReptAddr() {
        return annuReptAddr;
    }

    public void setAnnuReptAddr(String annuReptAddr) {
        this.annuReptAddr = annuReptAddr == null ? null : annuReptAddr.trim();
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
}