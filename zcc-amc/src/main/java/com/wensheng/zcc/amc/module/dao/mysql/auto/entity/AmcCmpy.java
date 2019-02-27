package com.wensheng.zcc.amc.module.dao.mysql.auto.entity;

public class AmcCmpy {
    private Long id;

    private String name;

    private String relatedUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getRelatedUrl() {
        return relatedUrl;
    }

    public void setRelatedUrl(String relatedUrl) {
        this.relatedUrl = relatedUrl == null ? null : relatedUrl.trim();
    }
}