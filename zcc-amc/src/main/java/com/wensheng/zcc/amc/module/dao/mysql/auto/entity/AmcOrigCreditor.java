package com.wensheng.zcc.amc.module.dao.mysql.auto.entity;

public class AmcOrigCreditor {
    private Long id;

    private String creditorName;

    private String desc;

    private Long debtpackId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreditorName() {
        return creditorName;
    }

    public void setCreditorName(String creditorName) {
        this.creditorName = creditorName == null ? null : creditorName.trim();
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }

    public Long getDebtpackId() {
        return debtpackId;
    }

    public void setDebtpackId(Long debtpackId) {
        this.debtpackId = debtpackId;
    }
}