package com.wensheng.zcc.amc.module.dao.mysql.auto.entity;

public class AmcDebtor {
    private Long id;

    private String name;

    private Integer type;

    private String personId;

    private Long companyId;

    private Long origCmpyId;

    private String note;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId == null ? null : personId.trim();
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getOrigCmpyId() {
        return origCmpyId;
    }

    public void setOrigCmpyId(Long origCmpyId) {
        this.origCmpyId = origCmpyId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }
}