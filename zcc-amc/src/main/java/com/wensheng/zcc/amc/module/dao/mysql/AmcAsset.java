package com.wensheng.zcc.amc.module.dao.mysql;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;




public class AmcAsset implements Serializable {
    private Long id;
    private String title;
    private int type;
    private int status;
    private int state;
    private int editStatus;
    private long amcId;
    private String amcCode;
    private String estmPrice;
    private String initPrice;
    private int restrictions;
    private String area;
    private String landArea;
    private Date publishDate;
    private String province;
    private String city;
    private String county;
    private String street;
    private String buildingName;
    private String gpsLng;
    private String gpsLat;

    
    
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
        this.title = title;
    }

    
    
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    
    
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    
    
    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    
    
    public int getEditStatus() {
        return editStatus;
    }

    public void setEditStatus(int editStatus) {
        this.editStatus = editStatus;
    }

    
    
    public long getAmcId() {
        return amcId;
    }

    public void setAmcId(long amcId) {
        this.amcId = amcId;
    }

    
    
    public String getAmcCode() {
        return amcCode;
    }

    public void setAmcCode(String amcCode) {
        this.amcCode = amcCode;
    }

    
    
    public String getEstmPrice() {
        return estmPrice;
    }

    public void setEstmPrice(String estmPrice) {
        this.estmPrice = estmPrice;
    }

    
    
    public String getInitPrice() {
        return initPrice;
    }

    public void setInitPrice(String initPrice) {
        this.initPrice = initPrice;
    }

    
    
    public int getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(int restrictions) {
        this.restrictions = restrictions;
    }

    
    
    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    
    
    public String getLandArea() {
        return landArea;
    }

    public void setLandArea(String landArea) {
        this.landArea = landArea;
    }

    
    
    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    
    
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    
    
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    
    
    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    
    
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    
    
    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    
    
    public String getGpsLng() {
        return gpsLng;
    }

    public void setGpsLng(String gpsLng) {
        this.gpsLng = gpsLng;
    }

    
    
    public String getGpsLat() {
        return gpsLat;
    }

    public void setGpsLat(String gpsLat) {
        this.gpsLat = gpsLat;
    }

    
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AmcAsset amcAsset = (AmcAsset) o;
        return type == amcAsset.type &&
                status == amcAsset.status &&
                state == amcAsset.state &&
                editStatus == amcAsset.editStatus &&
                amcId == amcAsset.amcId &&
                restrictions == amcAsset.restrictions &&
                Objects.equals(id, amcAsset.id) &&
                Objects.equals(title, amcAsset.title) &&
                Objects.equals(amcCode, amcAsset.amcCode) &&
                Objects.equals(estmPrice, amcAsset.estmPrice) &&
                Objects.equals(initPrice, amcAsset.initPrice) &&
                Objects.equals(area, amcAsset.area) &&
                Objects.equals(landArea, amcAsset.landArea) &&
                Objects.equals(publishDate, amcAsset.publishDate) &&
                Objects.equals(province, amcAsset.province) &&
                Objects.equals(city, amcAsset.city) &&
                Objects.equals(county, amcAsset.county) &&
                Objects.equals(street, amcAsset.street) &&
                Objects.equals(buildingName, amcAsset.buildingName) &&
                Objects.equals(gpsLng, amcAsset.gpsLng) &&
                Objects.equals(gpsLat, amcAsset.gpsLat);
    }

    
    public int hashCode() {

        return Objects.hash(id, title, type, status, state, editStatus, amcId, amcCode, estmPrice, initPrice, restrictions, area, landArea, publishDate, province, city, county, street, buildingName, gpsLng, gpsLat);
    }
}
