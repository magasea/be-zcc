package com.wensheng.zcc.amc.module.dao.mysql.auto.entity;

public class AmcGrntor {
    private Long id;

    private Long grntctrtId;

    private Long debtId;

    private Integer type;

    private Long cmpyId;

    private String name;

    private String notes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGrntctrtId() {
        return grntctrtId;
    }

    public void setGrntctrtId(Long grntctrtId) {
        this.grntctrtId = grntctrtId;
    }

    public Long getDebtId() {
        return debtId;
    }

    public void setDebtId(Long debtId) {
        this.debtId = debtId;
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