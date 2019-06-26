package com.wensheng.zcc.wechat.module.dao.mysql.auto.entity;

public class WechatMsgCkitem {
    private Long id;

    private Long msgId;

    private Long articleIdx;

    private Integer userDeclareState;

    private Integer auditState;

    private String originalArticleUrl;

    private Integer originalArticleType;

    private Integer canReprint;

    private Integer needReplaceContent;

    private Integer needShowReprintSource;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMsgId() {
        return msgId;
    }

    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }

    public Long getArticleIdx() {
        return articleIdx;
    }

    public void setArticleIdx(Long articleIdx) {
        this.articleIdx = articleIdx;
    }

    public Integer getUserDeclareState() {
        return userDeclareState;
    }

    public void setUserDeclareState(Integer userDeclareState) {
        this.userDeclareState = userDeclareState;
    }

    public Integer getAuditState() {
        return auditState;
    }

    public void setAuditState(Integer auditState) {
        this.auditState = auditState;
    }

    public String getOriginalArticleUrl() {
        return originalArticleUrl;
    }

    public void setOriginalArticleUrl(String originalArticleUrl) {
        this.originalArticleUrl = originalArticleUrl == null ? null : originalArticleUrl.trim();
    }

    public Integer getOriginalArticleType() {
        return originalArticleType;
    }

    public void setOriginalArticleType(Integer originalArticleType) {
        this.originalArticleType = originalArticleType;
    }

    public Integer getCanReprint() {
        return canReprint;
    }

    public void setCanReprint(Integer canReprint) {
        this.canReprint = canReprint;
    }

    public Integer getNeedReplaceContent() {
        return needReplaceContent;
    }

    public void setNeedReplaceContent(Integer needReplaceContent) {
        this.needReplaceContent = needReplaceContent;
    }

    public Integer getNeedShowReprintSource() {
        return needShowReprintSource;
    }

    public void setNeedShowReprintSource(Integer needShowReprintSource) {
        this.needShowReprintSource = needShowReprintSource;
    }
}