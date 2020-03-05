package com.wensheng.zcc.cust.module.dao.mysql.auto.entity;

import java.util.Date;

public class CustAmcCmpycontactor {
    private Long id;

    private String name;

    private String phone;

    private String mobile;

    private String province;

    private String city;

    private String address;

    private String title;

    private String company;

    private String historyCmpy;

    private String recorderName;

    private String favoriteCityPrep;

    private String favoriteTypePrep;

    private String favoriteCityUpdate;

    private String favoriteTypeUpdate;

    private Long cmpyId;

    private Long createBy;

    private Date createTime;

    private Long updateBy;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
    }

    public String getHistoryCmpy() {
        return historyCmpy;
    }

    public void setHistoryCmpy(String historyCmpy) {
        this.historyCmpy = historyCmpy == null ? null : historyCmpy.trim();
    }

    public String getRecorderName() {
        return recorderName;
    }

    public void setRecorderName(String recorderName) {
        this.recorderName = recorderName == null ? null : recorderName.trim();
    }

    public String getFavoriteCityPrep() {
        return favoriteCityPrep;
    }

    public void setFavoriteCityPrep(String favoriteCityPrep) {
        this.favoriteCityPrep = favoriteCityPrep == null ? null : favoriteCityPrep.trim();
    }

    public String getFavoriteTypePrep() {
        return favoriteTypePrep;
    }

    public void setFavoriteTypePrep(String favoriteTypePrep) {
        this.favoriteTypePrep = favoriteTypePrep == null ? null : favoriteTypePrep.trim();
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

    public Long getCmpyId() {
        return cmpyId;
    }

    public void setCmpyId(Long cmpyId) {
        this.cmpyId = cmpyId;
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

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}