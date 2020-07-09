package com.wensheng.zcc.wechat.module.dao.mysql.auto.entity;

import java.util.Date;

public class WechatUserPendingPhone {
    private Long id;

    private String pendingPhone;

    private String verifyCode;

    private Long userId;

    private Date vcodeTime;

    private Integer sentTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPendingPhone() {
        return pendingPhone;
    }

    public void setPendingPhone(String pendingPhone) {
        this.pendingPhone = pendingPhone == null ? null : pendingPhone.trim();
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode == null ? null : verifyCode.trim();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getVcodeTime() {
        return vcodeTime;
    }

    public void setVcodeTime(Date vcodeTime) {
        this.vcodeTime = vcodeTime;
    }

    public Integer getSentTime() {
        return sentTime;
    }

    public void setSentTime(Integer sentTime) {
        this.sentTime = sentTime;
    }
}