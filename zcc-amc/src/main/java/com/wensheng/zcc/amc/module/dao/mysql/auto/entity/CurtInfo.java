package com.wensheng.zcc.amc.module.dao.mysql.auto.entity;

public class CurtInfo {
    private Long id;

    private String curtName;

    private String curtProvince;

    private String curtCity;

    private String curtCounty;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurtName() {
        return curtName;
    }

    public void setCurtName(String curtName) {
        this.curtName = curtName == null ? null : curtName.trim();
    }

    public String getCurtProvince() {
        return curtProvince;
    }

    public void setCurtProvince(String curtProvince) {
        this.curtProvince = curtProvince == null ? null : curtProvince.trim();
    }

    public String getCurtCity() {
        return curtCity;
    }

    public void setCurtCity(String curtCity) {
        this.curtCity = curtCity == null ? null : curtCity.trim();
    }

    public String getCurtCounty() {
        return curtCounty;
    }

    public void setCurtCounty(String curtCounty) {
        this.curtCounty = curtCounty == null ? null : curtCounty.trim();
    }
}