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


public class AmcCmpyShareholder {
    private long id;
    private String amount;
    private String percentage;
    private String name;
    private long personId;
    private long cmpyId;
    private String subscribedAmount;
    private Timestamp subscribedDate;
    private String paidAmount;
    private Timestamp paidDate;
    private long parentHolderId;
    private int type;
    private String link;

    
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    
    
    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    
    
    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
    
    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }

    
    
    public long getCmpyId() {
        return cmpyId;
    }

    public void setCmpyId(long cmpyId) {
        this.cmpyId = cmpyId;
    }

    
    
    public String getSubscribedAmount() {
        return subscribedAmount;
    }

    public void setSubscribedAmount(String subscribedAmount) {
        this.subscribedAmount = subscribedAmount;
    }

    
    
    public Timestamp getSubscribedDate() {
        return subscribedDate;
    }

    public void setSubscribedDate(Timestamp subscribedDate) {
        this.subscribedDate = subscribedDate;
    }

    
    
    public String getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }

    
    
    public Timestamp getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(Timestamp paidDate) {
        this.paidDate = paidDate;
    }

    
    
    public long getParentHolderId() {
        return parentHolderId;
    }

    public void setParentHolderId(long parentHolderId) {
        this.parentHolderId = parentHolderId;
    }

    
    
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    
    
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AmcCmpyShareholder that = (AmcCmpyShareholder) o;
        return id == that.id &&
                personId == that.personId &&
                cmpyId == that.cmpyId &&
                parentHolderId == that.parentHolderId &&
                type == that.type &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(percentage, that.percentage) &&
                Objects.equals(name, that.name) &&
                Objects.equals(subscribedAmount, that.subscribedAmount) &&
                Objects.equals(subscribedDate, that.subscribedDate) &&
                Objects.equals(paidAmount, that.paidAmount) &&
                Objects.equals(paidDate, that.paidDate) &&
                Objects.equals(link, that.link);
    }

    
    public int hashCode() {

        return Objects.hash(id, amount, percentage, name, personId, cmpyId, subscribedAmount, subscribedDate, paidAmount, paidDate, parentHolderId, type, link);
    }
}
