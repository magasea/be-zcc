package com.wensheng.zcc.amc.module.dao.mysql.auto.entity;

public class AmcCreditorDebtpack {
    private Long id;

    private Long creditorId;

    private Long debtpackId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreditorId() {
        return creditorId;
    }

    public void setCreditorId(Long creditorId) {
        this.creditorId = creditorId;
    }

    public Long getDebtpackId() {
        return debtpackId;
    }

    public void setDebtpackId(Long debtpackId) {
        this.debtpackId = debtpackId;
    }
}