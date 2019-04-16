package com.wensheng.zcc.cust.module.dao.mysql.auto.entity;

public class CustTrdInfoTotal {
    private Long id;

    private Byte channel;

    private Long trdInfoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Byte getChannel() {
        return channel;
    }

    public void setChannel(Byte channel) {
        this.channel = channel;
    }

    public Long getTrdInfoId() {
        return trdInfoId;
    }

    public void setTrdInfoId(Long trdInfoId) {
        this.trdInfoId = trdInfoId;
    }
}