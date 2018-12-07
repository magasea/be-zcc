package com.wensheng.zcc.amc.module.dao.mongo.origin;

import java.util.Objects;

/**
 * 
 * 
 */


public class Debtor {
    private long id;
    private long debtId;
    private long companyId;
    private String debtContract;

    
    
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

    
    
    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    
    
    public String getDebtContract() {
        return debtContract;
    }

    public void setDebtContract(String debtContract) {
        this.debtContract = debtContract;
    }

    
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Debtor amcDebtor = (Debtor) o;
        return id == amcDebtor.id &&
                debtId == amcDebtor.debtId &&
                companyId == amcDebtor.companyId &&
                Objects.equals(debtContract, amcDebtor.debtContract);
    }

    
    public int hashCode() {

        return Objects.hash(id, debtId, companyId, debtContract);
    }
}
