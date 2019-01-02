package com.wensheng.zcc.amc.module.dao.mysql.auto.entity;

import java.util.Date;

public class AmcGrntctrct {
    private Long id;

    private Long debtId;

    private Long originDebtId;

    private Long originContractId;

    private Long amount;

    private Long originGrantorId;

    private Long grantorId;

    private Integer type;

    private String notes;

    private String contractNumber;

    private Date signDate;

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

    public Long getOriginDebtId() {
        return originDebtId;
    }

    public void setOriginDebtId(Long originDebtId) {
        this.originDebtId = originDebtId;
    }

    public Long getOriginContractId() {
        return originContractId;
    }

    public void setOriginContractId(Long originContractId) {
        this.originContractId = originContractId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getOriginGrantorId() {
        return originGrantorId;
    }

    public void setOriginGrantorId(Long originGrantorId) {
        this.originGrantorId = originGrantorId;
    }

    public Long getGrantorId() {
        return grantorId;
    }

    public void setGrantorId(Long grantorId) {
        this.grantorId = grantorId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes == null ? null : notes.trim();
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber == null ? null : contractNumber.trim();
    }

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }
}