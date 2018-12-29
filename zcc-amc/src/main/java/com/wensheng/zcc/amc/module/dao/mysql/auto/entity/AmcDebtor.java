package com.wensheng.zcc.amc.module.dao.mysql.auto.entity;

public class AmcDebtor {
    private Long id;

    private Long debtId;

    private Long companyId;

    private String debtContract;

    private String note;

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

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getDebtContract() {
        return debtContract;
    }

    public void setDebtContract(String debtContract) {
        this.debtContract = debtContract == null ? null : debtContract.trim();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }
}