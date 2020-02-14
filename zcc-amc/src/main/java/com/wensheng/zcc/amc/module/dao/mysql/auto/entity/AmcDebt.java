package com.wensheng.zcc.amc.module.dao.mysql.auto.entity;

import java.util.Date;

public class AmcDebt {
    private Long id;

    private Long debtpackId;

    private Long amcId;

    private String title;

    private Long baseAmount;

    private String baseAmountDesc;

    private Date baseDate;

    private Long totalAmount;

    private String totalAmountDesc;

    private Date settleDate;

    private Long courtId;

    private String zccDebtCode;

    private Integer publishState;

    private Date publishDate;

    private Integer lawsuitState;

    private String lawsuitStateDesc;

    private Long valuation;

    private Long amcContactorId;

    private Long amcContactor2Id;

    private Integer isRecommanded;

    private Integer guarantType;

    private Date recommStartDate;

    private Date recommEndDate;

    private Long origCreditorId;

    private String briefDesc;

    private Long updateBy;

    private Date updateDate;

    private Long createdBy;

    private Date createdDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDebtpackId() {
        return debtpackId;
    }

    public void setDebtpackId(Long debtpackId) {
        this.debtpackId = debtpackId;
    }

    public Long getAmcId() {
        return amcId;
    }

    public void setAmcId(Long amcId) {
        this.amcId = amcId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Long getBaseAmount() {
        return baseAmount;
    }

    public void setBaseAmount(Long baseAmount) {
        this.baseAmount = baseAmount;
    }

    public String getBaseAmountDesc() {
        return baseAmountDesc;
    }

    public void setBaseAmountDesc(String baseAmountDesc) {
        this.baseAmountDesc = baseAmountDesc == null ? null : baseAmountDesc.trim();
    }

    public Date getBaseDate() {
        return baseDate;
    }

    public void setBaseDate(Date baseDate) {
        this.baseDate = baseDate;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTotalAmountDesc() {
        return totalAmountDesc;
    }

    public void setTotalAmountDesc(String totalAmountDesc) {
        this.totalAmountDesc = totalAmountDesc == null ? null : totalAmountDesc.trim();
    }

    public Date getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(Date settleDate) {
        this.settleDate = settleDate;
    }

    public Long getCourtId() {
        return courtId;
    }

    public void setCourtId(Long courtId) {
        this.courtId = courtId;
    }

    public String getZccDebtCode() {
        return zccDebtCode;
    }

    public void setZccDebtCode(String zccDebtCode) {
        this.zccDebtCode = zccDebtCode == null ? null : zccDebtCode.trim();
    }

    public Integer getPublishState() {
        return publishState;
    }

    public void setPublishState(Integer publishState) {
        this.publishState = publishState;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Integer getLawsuitState() {
        return lawsuitState;
    }

    public void setLawsuitState(Integer lawsuitState) {
        this.lawsuitState = lawsuitState;
    }

    public String getLawsuitStateDesc() {
        return lawsuitStateDesc;
    }

    public void setLawsuitStateDesc(String lawsuitStateDesc) {
        this.lawsuitStateDesc = lawsuitStateDesc == null ? null : lawsuitStateDesc.trim();
    }

    public Long getValuation() {
        return valuation;
    }

    public void setValuation(Long valuation) {
        this.valuation = valuation;
    }

    public Long getAmcContactorId() {
        return amcContactorId;
    }

    public void setAmcContactorId(Long amcContactorId) {
        this.amcContactorId = amcContactorId;
    }

    public Long getAmcContactor2Id() {
        return amcContactor2Id;
    }

    public void setAmcContactor2Id(Long amcContactor2Id) {
        this.amcContactor2Id = amcContactor2Id;
    }

    public Integer getIsRecommanded() {
        return isRecommanded;
    }

    public void setIsRecommanded(Integer isRecommanded) {
        this.isRecommanded = isRecommanded;
    }

    public Integer getGuarantType() {
        return guarantType;
    }

    public void setGuarantType(Integer guarantType) {
        this.guarantType = guarantType;
    }

    public Date getRecommStartDate() {
        return recommStartDate;
    }

    public void setRecommStartDate(Date recommStartDate) {
        this.recommStartDate = recommStartDate;
    }

    public Date getRecommEndDate() {
        return recommEndDate;
    }

    public void setRecommEndDate(Date recommEndDate) {
        this.recommEndDate = recommEndDate;
    }

    public Long getOrigCreditorId() {
        return origCreditorId;
    }

    public void setOrigCreditorId(Long origCreditorId) {
        this.origCreditorId = origCreditorId;
    }

    public String getBriefDesc() {
        return briefDesc;
    }

    public void setBriefDesc(String briefDesc) {
        this.briefDesc = briefDesc == null ? null : briefDesc.trim();
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}