package com.wensheng.zcc.amc.module.dao.mysql.auto.entity;

public class AmcPerson {
    private Long id;

    private Long deptId;

    private String name;

    private String phoneNumber;

    private String telNumber;

    private Integer gender;

    private Integer personalDocType;

    private String personalDoc;

    private String nation;

    private String notes;

    private String address;

    private Long amcCmpyId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber == null ? null : phoneNumber.trim();
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber == null ? null : telNumber.trim();
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getPersonalDocType() {
        return personalDocType;
    }

    public void setPersonalDocType(Integer personalDocType) {
        this.personalDocType = personalDocType;
    }

    public String getPersonalDoc() {
        return personalDoc;
    }

    public void setPersonalDoc(String personalDoc) {
        this.personalDoc = personalDoc == null ? null : personalDoc.trim();
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation == null ? null : nation.trim();
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes == null ? null : notes.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Long getAmcCmpyId() {
        return amcCmpyId;
    }

    public void setAmcCmpyId(Long amcCmpyId) {
        this.amcCmpyId = amcCmpyId;
    }
}