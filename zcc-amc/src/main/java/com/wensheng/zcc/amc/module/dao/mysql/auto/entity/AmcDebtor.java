package com.wensheng.zcc.amc.module.dao.mysql.auto.entity;

public class AmcDebtor {
    private Long id;

    private Long debtId;

    private String debtorName;

    private Integer debtorType;

    private Integer role;

    private Long companyId;

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

    public String getDebtorName() {
        return debtorName;
    }

    public void setDebtorName(String debtorName) {
        this.debtorName = debtorName == null ? null : debtorName.trim();
    }

    public Integer getDebtorType() {
        return debtorType;
    }

    public void setDebtorType(Integer debtorType) {
        this.debtorType = debtorType;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }
}