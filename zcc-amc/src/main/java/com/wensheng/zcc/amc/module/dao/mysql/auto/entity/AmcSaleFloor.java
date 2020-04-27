package com.wensheng.zcc.amc.module.dao.mysql.auto.entity;

import java.util.Date;

public class AmcSaleFloor {
    private Long id;

    private Integer floor;

    private Integer floorSeq;

    private Integer css;

    private String title;

    private String slogon;

    private Date floorStartTime;

    private Date floorEndTime;

    private String filterContent;

    private String recomItems;

    private Integer publishState;

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

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Integer getFloorSeq() {
        return floorSeq;
    }

    public void setFloorSeq(Integer floorSeq) {
        this.floorSeq = floorSeq;
    }

    public Integer getCss() {
        return css;
    }

    public void setCss(Integer css) {
        this.css = css;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getSlogon() {
        return slogon;
    }

    public void setSlogon(String slogon) {
        this.slogon = slogon == null ? null : slogon.trim();
    }

    public Date getFloorStartTime() {
        return floorStartTime;
    }

    public void setFloorStartTime(Date floorStartTime) {
        this.floorStartTime = floorStartTime;
    }

    public Date getFloorEndTime() {
        return floorEndTime;
    }

    public void setFloorEndTime(Date floorEndTime) {
        this.floorEndTime = floorEndTime;
    }

    public String getFilterContent() {
        return filterContent;
    }

    public void setFilterContent(String filterContent) {
        this.filterContent = filterContent == null ? null : filterContent.trim();
    }

    public String getRecomItems() {
        return recomItems;
    }

    public void setRecomItems(String recomItems) {
        this.recomItems = recomItems == null ? null : recomItems.trim();
    }

    public Integer getPublishState() {
        return publishState;
    }

    public void setPublishState(Integer publishState) {
        this.publishState = publishState;
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