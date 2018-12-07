package com.wensheng.zcc.amc.module.dao.mysql;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * 
 * 
 */


public class AmcGrntctrct {
    private long id;
    private long debtId;
    private String amount;
    private int type;
    private String notes;
    private String contractNumber;
    private Timestamp signDate;

    
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    
    
    public long getDebtId() {
        return debtId;
    }

    public void setDebtId(long debtId) {
        this.debtId = debtId;
    }

    
    
    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    
    
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    
    
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    
    
    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    
    
    public Timestamp getSignDate() {
        return signDate;
    }

    public void setSignDate(Timestamp signDate) {
        this.signDate = signDate;
    }

    
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AmcGrntctrct that = (AmcGrntctrct) o;
        return id == that.id &&
                debtId == that.debtId &&
                type == that.type &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(notes, that.notes) &&
                Objects.equals(contractNumber, that.contractNumber) &&
                Objects.equals(signDate, that.signDate);
    }

    
    public int hashCode() {

        return Objects.hash(id, debtId, amount, type, notes, contractNumber, signDate);
    }
}
