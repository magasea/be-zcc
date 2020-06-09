package com.wensheng.zcc.wechat.module.dao.mysql.auto.entity;

import java.util.Date;

public class WechatUserStatic {
    private Long id;

    private Long onlineTime;

    private Date lastTime;

    private Long watchCount;

    private Long wechatUserId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(Long onlineTime) {
        this.onlineTime = onlineTime;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public Long getWatchCount() {
        return watchCount;
    }

    public void setWatchCount(Long watchCount) {
        this.watchCount = watchCount;
    }

    public Long getWechatUserId() {
        return wechatUserId;
    }

    public void setWechatUserId(Long wechatUserId) {
        this.wechatUserId = wechatUserId;
    }
}