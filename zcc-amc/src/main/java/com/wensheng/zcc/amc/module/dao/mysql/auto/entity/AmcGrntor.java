package com.wensheng.zcc.amc.module.dao.mysql.auto.entity;

public class AmcGrntor {
    private Long id;

    private Integer type;

    private Long cmpyId;

    private Long originCmpyId;

    private String personId;

    private String name;

    private String notes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId == null ? null : personId.trim();
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