package com.wensheng.zcc.amc.module.dao.mysql;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

/**
 * 
 * 
 */


public class AmcPerson {
    private long id;
    private String lastName;
    private String firstName;
    private int gender;
    private int personalDocType;
    private String personalDoc;
    private String nation;
    private String notes;
    private String address;
    private long amcCmpyId;

    
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    
    
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    
    
    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    
    
    public int getPersonalDocType() {
        return personalDocType;
    }

    public void setPersonalDocType(int personalDocType) {
        this.personalDocType = personalDocType;
    }

    
    
    public String getPersonalDoc() {
        return personalDoc;
    }

    public void setPersonalDoc(String personalDoc) {
        this.personalDoc = personalDoc;
    }

    
    
    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    
    
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    
    
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    
    
    public long getAmcCmpyId() {
        return amcCmpyId;
    }

    public void setAmcCmpyId(long amcCmpyId) {
        this.amcCmpyId = amcCmpyId;
    }

    
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AmcPerson amcPerson = (AmcPerson) o;
        return id == amcPerson.id &&
                gender == amcPerson.gender &&
                personalDocType == amcPerson.personalDocType &&
                amcCmpyId == amcPerson.amcCmpyId &&
                Objects.equals(lastName, amcPerson.lastName) &&
                Objects.equals(firstName, amcPerson.firstName) &&
                Objects.equals(personalDoc, amcPerson.personalDoc) &&
                Objects.equals(nation, amcPerson.nation) &&
                Objects.equals(notes, amcPerson.notes) &&
                Objects.equals(address, amcPerson.address);
    }

    
    public int hashCode() {

        return Objects.hash(id, lastName, firstName, gender, personalDocType, personalDoc, nation, notes, address, amcCmpyId);
    }
}
