package com.wensheng.zcc.amc.module.dao.mysql.auto.entity;

public class AmcGrntor {
    private Long id;

    private Long ctrtId;

    private Long originCtrtId;

    private Long debtId;

    private Long originDebtId;

    private Integer type;

    private Long cmpyId;

    private Long originCmpyId;

    private String name;

    private String notes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCtrtId() {
        return ctrtId;
    }

    public void setCtrtId(Long ctrtId) {
        this.ctrtId = ctrtId;
    }

    public Long getOriginCtrtId() {
        return originCtrtId;
    }

    public void setOriginCtrtId(Long originCtrtId) {
        this.originCtrtId = originCtrtId;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getCmpyId() {
        return cmpyId;
    }

    public void setCmpyId(Long cmpyId) {
        this.cmpyId = cmpyId;
    }

    public Long getOriginCmpyId() {
        return originCmpyId;
    }

    public void setOriginCmpyId(Long originCmpyId) {
        this.originCmpyId = originCmpyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes == null ? null : notes.trim();
    }
}