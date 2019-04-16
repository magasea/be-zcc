package com.wensheng.zcc.cust.module.dao.mysql.auto.entity;

public class CustIntrstInfo {
    private Long id;

    private Long custId;

    private Byte custType;

    private String intrstCity;

    private Integer intrstType;

    private Integer investScale;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public Byte getCustType() {
        return custType;
    }

    public void setCustType(Byte custType) {
        this.custType = custType;
    }

    public String getIntrstCity() {
        return intrstCity;
    }

    public void setIntrstCity(String intrstCity) {
        this.intrstCity = intrstCity == null ? null : intrstCity.trim();
    }

    public Integer getIntrstType() {
        return intrstType;
    }

    public void setIntrstType(Integer intrstType) {
        this.intrstType = intrstType;
    }

    public Integer getInvestScale() {
        return investScale;
    }

    public void setInvestScale(Integer investScale) {
        this.investScale = investScale;
    }
}