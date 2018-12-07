package com.wensheng.zcc.amc.module.dao.mysql.auto.entity;

public class ResourceRole {
    private Long id;

    private Integer assetType;

    private Integer debtType;

    private Integer accessRole;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAssetType() {
        return assetType;
    }

    public void setAssetType(Integer assetType) {
        this.assetType = assetType;
    }

    public Integer getDebtType() {
        return debtType;
    }

    public void setDebtType(Integer debtType) {
        this.debtType = debtType;
    }

    public Integer getAccessRole() {
        return accessRole;
    }

    public void setAccessRole(Integer accessRole) {
        this.accessRole = accessRole;
    }
}