package com.wensheng.zcc.cust.module.dao.mysql.auto.entity;

import java.util.Date;

public class CustTrdCmpy {
    private Long id;

    private String cmpyName;

    private String uniSocialCode;

    private String legalReptive;

    private String cmpyPhone;

    private String historyCmpyPhone;

    private String cmpyProvince;

    private String cmpyAddr;

    private String historyCmpyAddr;

    private String annuReptPhone;

    private String annuReptAddr;

    private String favoriteCityUpdate;

    private String favoriteTypeUpdate;

    private Integer dataStatus;

    private Integer dataQuality;

    private Date updateTime;

    private Long updateBy;

    private Date createTime;

    private Long createBy;

    private Date syncTime;

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

    public String getHistoryCmpyPhone() {
        return historyCmpyPhone;
    }

    public void setHistoryCmpyPhone(String historyCmpyPhone) {
        this.historyCmpyPhone = historyCmpyPhone == null ? null : historyCmpyPhone.trim();
    }

    public String getCmpyProvince() {
        return cmpyProvince;
    }

    public void setCmpyProvince(String cmpyProvince) {
        this.cmpyProvince = cmpyProvince == null ? null : cmpyProvince.trim();
    }

    public String getCmpyAddr() {
        return cmpyAddr;
    }

    public void setCmpyAddr(String cmpyAddr) {
        this.cmpyAddr = cmpyAddr == null ? null : cmpyAddr.trim();
    }

    public String getHistoryCmpyAddr() {
        return historyCmpyAddr;
    }

    public void setHistoryCmpyAddr(String historyCmpyAddr) {
        this.historyCmpyAddr = historyCmpyAddr == null ? null : historyCmpyAddr.trim();
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

    public String getFavoriteCityUpdate() {
        return favoriteCityUpdate;
    }

    public void setFavoriteCityUpdate(String favoriteCityUpdate) {
        this.favoriteCityUpdate = favoriteCityUpdate == null ? null : favoriteCityUpdate.trim();
    }

    public String getFavoriteTypeUpdate() {
        return favoriteTypeUpdate;
    }

    public void setFavoriteTypeUpdate(String favoriteTypeUpdate) {
        this.favoriteTypeUpdate = favoriteTypeUpdate == null ? null : favoriteTypeUpdate.trim();
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Date getSyncTime() {
        return syncTime;
    }

    public void setSyncTime(Date syncTime) {
        this.syncTime = syncTime;
    }
}