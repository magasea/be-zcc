package com.wensheng.zcc.cust.module.dao.mysql.ext;

import java.util.Date;

public class CustAmcCmpycontactorExt {
    private Long id;

    private String name;

    private String phone;

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

    private String createBy;

    private Date createTime;

    private String updateBy;

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

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}