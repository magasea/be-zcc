package com.wensheng.zcc.amc.module.dao.mysql.auto.entity;

public class AmcDebtStatic {
    private Long id;

    private Long debtId;

    private Long visitCount;

    private Long bizVisitCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDebtId() {
        return debtId;
    }

    public void setDebtId(Long debtId) {
        this.debtId = debtId;
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