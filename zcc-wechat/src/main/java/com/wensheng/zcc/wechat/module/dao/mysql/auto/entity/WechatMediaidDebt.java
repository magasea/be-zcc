package com.wensheng.zcc.wechat.module.dao.mysql.auto.entity;

import java.util.Date;

public class WechatMediaidDebt {
    private Long id;

    private Integer type;

    private String mediaId;

    private Long debtId;

    private String wxImgUrl;

    private String msgMediaId;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId == null ? null : mediaId.trim();
    }

    public Long getDebtId() {
        return debtId;
    }

    public void setDebtId(Long debtId) {
        this.debtId = debtId;
    }

    public String getWxImgUrl() {
        return wxImgUrl;
    }

    public void setWxImgUrl(String wxImgUrl) {
        this.wxImgUrl = wxImgUrl == null ? null : wxImgUrl.trim();
    }

    public String getMsgMediaId() {
        return msgMediaId;
    }

    public void setMsgMediaId(String msgMediaId) {
        this.msgMediaId = msgMediaId == null ? null : msgMediaId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}