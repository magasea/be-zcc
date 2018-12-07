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


public class AmcGrntor {
    private long id;
    private long grntctrtId;
    private long debtId;
    private int type;
    private long cmpyId;
    private String name;
    private String notes;

    
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    
    
    public long getGrntctrtId() {
        return grntctrtId;
    }

    public void setGrntctrtId(long grntctrtId) {
        this.grntctrtId = grntctrtId;
    }

    
    
    public long getDebtId() {
        return debtId;
    }

    public void setDebtId(long debtId) {
        this.debtId = debtId;
    }

    
    
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    
    
    public long getCmpyId() {
        return cmpyId;
    }

    public void setCmpyId(long cmpyId) {
        this.cmpyId = cmpyId;
    }

    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
    
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AmcGrntor amcGrntor = (AmcGrntor) o;
        return id == amcGrntor.id &&
                grntctrtId == amcGrntor.grntctrtId &&
                debtId == amcGrntor.debtId &&
                type == amcGrntor.type &&
                cmpyId == amcGrntor.cmpyId &&
                Objects.equals(name, amcGrntor.name) &&
                Objects.equals(notes, amcGrntor.notes);
    }

    
    public int hashCode() {

        return Objects.hash(id, grntctrtId, debtId, type, cmpyId, name, notes);
    }
}
