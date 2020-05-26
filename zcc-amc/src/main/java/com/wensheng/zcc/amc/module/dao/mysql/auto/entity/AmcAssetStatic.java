package com.wensheng.zcc.amc.module.dao.mysql.auto.entity;

public class AmcAssetStatic {
    private Long id;

    private Long visitCount;

    private Long bizVisitCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(Long visitCount) {
        this.visitCount = visitCount;
    }

    public Long getBizVisitCount() {
        return bizVisitCount;
    }

    public void setBizVisitCount(Long bizVisitCount) {
        this.bizVisitCount = bizVisitCount;
    }
}