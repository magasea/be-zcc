package com.wensheng.zcc.model;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;


@Entity
@Table(name = "ZCC_MINIAPP_USER", schema = "ZCC", catalog = "")
public class ZccMiniappUser {
    private long id;
    private String phoneNum;
    private String username;
    private String wechatId;
    private String initcode;
    private String password;
    private Date loginTime;
    private Integer gender;
    private String location;
    private Timestamp createdTime;
    private Timestamp updateTime;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "phone_num", nullable = true, length = 20)
    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    @Basic
    @Column(name = "username", nullable = true, length = 35)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "wechat_id", nullable = true, length = 45)
    public String getWechatId() {
        return wechatId;
    }

    public void setWechatId(String wechatId) {
        this.wechatId = wechatId;
    }

    @Basic
    @Column(name = "initcode", nullable = true, length = 45)
    public String getInitcode() {
        return initcode;
    }

    public void setInitcode(String initcode) {
        this.initcode = initcode;
    }

    @Basic
    @Column(name = "password", nullable = true, length = 255)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "login_time", nullable = true)
    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    @Basic
    @Column(name = "gender", nullable = true)
    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    @Basic
    @Column(name = "location", nullable = true, length = 100)
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Basic
    @Column(name = "created_time", nullable = true)
    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    @Basic
    @Column(name = "update_time", nullable = true)
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ZccMiniappUser that = (ZccMiniappUser) o;
        return id == that.id &&
                Objects.equals(phoneNum, that.phoneNum) &&
                Objects.equals(username, that.username) &&
                Objects.equals(wechatId, that.wechatId) &&
                Objects.equals(initcode, that.initcode) &&
                Objects.equals(password, that.password) &&
                Objects.equals(loginTime, that.loginTime) &&
                Objects.equals(gender, that.gender) &&
                Objects.equals(location, that.location) &&
                Objects.equals(createdTime, that.createdTime) &&
                Objects.equals(updateTime, that.updateTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, phoneNum, username, wechatId, initcode, password, loginTime, gender, location, createdTime, updateTime);
    }
}
