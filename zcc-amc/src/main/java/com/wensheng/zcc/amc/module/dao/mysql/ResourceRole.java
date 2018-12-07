package com.wensheng.zcc.amc.module.dao.mysql;


import java.util.Objects;




public class ResourceRole {
    private long id;
    private int assetType;
    private int debtType;
    private int accessRole;

    
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    
    
    public int getAssetType() {
        return assetType;
    }

    public void setAssetType(int assetType) {
        this.assetType = assetType;
    }

    
    
    public int getDebtType() {
        return debtType;
    }

    public void setDebtType(int debtType) {
        this.debtType = debtType;
    }

    
    
    public int getAccessRole() {
        return accessRole;
    }

    public void setAccessRole(int accessRole) {
        this.accessRole = accessRole;
    }

    
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResourceRole that = (ResourceRole) o;
        return id == that.id &&
                assetType == that.assetType &&
                debtType == that.debtType &&
                accessRole == that.accessRole;
    }

    
    public int hashCode() {

        return Objects.hash(id, assetType, debtType, accessRole);
    }
}
