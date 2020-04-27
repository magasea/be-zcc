package com.wensheng.zcc.amc.module.dao.mysql.auto.entity;

import java.util.Date;

public class AmcAsset {
    private Long id;

    private String title;

    private Integer type;

    private Integer sealedState;

    private Integer assetNature;

    private Integer publishState;

    private Integer isRecom;

    private Long amcId;

    private String amcAssetCode;

    private String zccAssetCode;

    private Long totalValuation;

    private Long debtId;

    private Long buildingArea;

    private Long buildingUnitPrice;

    private Long landArea;

    private Integer landAreaUnit;

    private Long landUnitPrice;

    private Date publishDate;

    private String province;

    private String city;

    private String county;

    private String address;

    private String buildingName;

    private Long updateBy;

    private Date updateDate;

    private Long createdBy;

    private Date createdDate;

    private Long amcContactorId;

    private String amcContactorName;

    private String amcContactorPhone;

    private String note;

    private Long visitCount;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSealedState() {
        return sealedState;
    }

    public void setSealedState(Integer sealedState) {
        this.sealedState = sealedState;
    }

    public Integer getAssetNature() {
        return assetNature;
    }

    public void setAssetNature(Integer assetNature) {
        this.assetNature = assetNature;
    }

    public Integer getPublishState() {
        return publishState;
    }

    public void setPublishState(Integer publishState) {
        this.publishState = publishState;
    }

    public Integer getIsRecom() {
        return isRecom;
    }

    public void setIsRecom(Integer isRecom) {
        this.isRecom = isRecom;
    }

    public Long getAmcId() {
        return amcId;
    }

    public void setAmcId(Long amcId) {
        this.amcId = amcId;
    }

    public String getAmcAssetCode() {
        return amcAssetCode;
    }

    public void setAmcAssetCode(String amcAssetCode) {
        this.amcAssetCode = amcAssetCode == null ? null : amcAssetCode.trim();
    }

    public String getZccAssetCode() {
        return zccAssetCode;
    }

    public void setZccAssetCode(String zccAssetCode) {
        this.zccAssetCode = zccAssetCode == null ? null : zccAssetCode.trim();
    }

    public Long getTotalValuation() {
        return totalValuation;
    }

    public void setTotalValuation(Long totalValuation) {
        this.totalValuation = totalValuation;
    }

    public Long getDebtId() {
        return debtId;
    }

    public void setDebtId(Long debtId) {
        this.debtId = debtId;
    }

    public Long getBuildingArea() {
        return buildingArea;
    }

    public void setBuildingArea(Long buildingArea) {
        this.buildingArea = buildingArea;
    }

    public Long getBuildingUnitPrice() {
        return buildingUnitPrice;
    }

    public void setBuildingUnitPrice(Long buildingUnitPrice) {
        this.buildingUnitPrice = buildingUnitPrice;
    }

    public Long getLandArea() {
        return landArea;
    }

    public void setLandArea(Long landArea) {
        this.landArea = landArea;
    }

    public Integer getLandAreaUnit() {
        return landAreaUnit;
    }

    public void setLandAreaUnit(Integer landAreaUnit) {
        this.landAreaUnit = landAreaUnit;
    }

    public Long getLandUnitPrice() {
        return landUnitPrice;
    }

    public void setLandUnitPrice(Long landUnitPrice) {
        this.landUnitPrice = landUnitPrice;
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
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county == null ? null : county.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName == null ? null : buildingName.trim();
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getAmcContactorId() {
        return amcContactorId;
    }

    public void setAmcContactorId(Long amcContactorId) {
        this.amcContactorId = amcContactorId;
    }

    public String getAmcContactorName() {
        return amcContactorName;
    }

    public void setAmcContactorName(String amcContactorName) {
        this.amcContactorName = amcContactorName == null ? null : amcContactorName.trim();
    }

    public String getAmcContactorPhone() {
        return amcContactorPhone;
    }

    public void setAmcContactorPhone(String amcContactorPhone) {
        this.amcContactorPhone = amcContactorPhone == null ? null : amcContactorPhone.trim();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public Long getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(Long visitCount) {
        this.visitCount = visitCount;
    }
}