package com.wensheng.zcc.amc.module.dao.mongo.origin;

import java.util.Objects;

/**
 * 
 * 
 */


public class Cmpy {
    private long id;
    private String name;
    private String regId;
    private String socialCreditid;
    private String relatedUrl;

    
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
    
    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    
    
    public String getSocialCreditid() {
        return socialCreditid;
    }

    public void setSocialCreditid(String socialCreditid) {
        this.socialCreditid = socialCreditid;
    }

    
    
    public String getRelatedUrl() {
        return relatedUrl;
    }

    public void setRelatedUrl(String relatedUrl) {
        this.relatedUrl = relatedUrl;
    }

    
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cmpy amcCmpy = (Cmpy) o;
        return id == amcCmpy.id &&
                Objects.equals(name, amcCmpy.name) &&
                Objects.equals(regId, amcCmpy.regId) &&
                Objects.equals(socialCreditid, amcCmpy.socialCreditid) &&
                Objects.equals(relatedUrl, amcCmpy.relatedUrl);
    }

    
    public int hashCode() {

        return Objects.hash(id, name, regId, socialCreditid, relatedUrl);
    }
}
