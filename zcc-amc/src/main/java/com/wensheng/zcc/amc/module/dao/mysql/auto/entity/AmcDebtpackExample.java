package com.wensheng.zcc.amc.module.dao.mysql.auto.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AmcDebtpackExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AmcDebtpackExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andTitleIsNull() {
            addCriterion("title is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("title is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("title =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("title <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("title >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("title >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("title <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("title <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("title like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("title not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("title in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("title not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("title between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("title not between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andBaseAmountIsNull() {
            addCriterion("base_amount is null");
            return (Criteria) this;
        }

        public Criteria andBaseAmountIsNotNull() {
            addCriterion("base_amount is not null");
            return (Criteria) this;
        }

        public Criteria andBaseAmountEqualTo(Long value) {
            addCriterion("base_amount =", value, "baseAmount");
            return (Criteria) this;
        }

        public Criteria andBaseAmountNotEqualTo(Long value) {
            addCriterion("base_amount <>", value, "baseAmount");
            return (Criteria) this;
        }

        public Criteria andBaseAmountGreaterThan(Long value) {
            addCriterion("base_amount >", value, "baseAmount");
            return (Criteria) this;
        }

        public Criteria andBaseAmountGreaterThanOrEqualTo(Long value) {
            addCriterion("base_amount >=", value, "baseAmount");
            return (Criteria) this;
        }

        public Criteria andBaseAmountLessThan(Long value) {
            addCriterion("base_amount <", value, "baseAmount");
            return (Criteria) this;
        }

        public Criteria andBaseAmountLessThanOrEqualTo(Long value) {
            addCriterion("base_amount <=", value, "baseAmount");
            return (Criteria) this;
        }

        public Criteria andBaseAmountIn(List<Long> values) {
            addCriterion("base_amount in", values, "baseAmount");
            return (Criteria) this;
        }

        public Criteria andBaseAmountNotIn(List<Long> values) {
            addCriterion("base_amount not in", values, "baseAmount");
            return (Criteria) this;
        }

        public Criteria andBaseAmountBetween(Long value1, Long value2) {
            addCriterion("base_amount between", value1, value2, "baseAmount");
            return (Criteria) this;
        }

        public Criteria andBaseAmountNotBetween(Long value1, Long value2) {
            addCriterion("base_amount not between", value1, value2, "baseAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAmountIsNull() {
            addCriterion("total_amount is null");
            return (Criteria) this;
        }

        public Criteria andTotalAmountIsNotNull() {
            addCriterion("total_amount is not null");
            return (Criteria) this;
        }

        public Criteria andTotalAmountEqualTo(Long value) {
            addCriterion("total_amount =", value, "totalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAmountNotEqualTo(Long value) {
            addCriterion("total_amount <>", value, "totalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAmountGreaterThan(Long value) {
            addCriterion("total_amount >", value, "totalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAmountGreaterThanOrEqualTo(Long value) {
            addCriterion("total_amount >=", value, "totalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAmountLessThan(Long value) {
            addCriterion("total_amount <", value, "totalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAmountLessThanOrEqualTo(Long value) {
            addCriterion("total_amount <=", value, "totalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAmountIn(List<Long> values) {
            addCriterion("total_amount in", values, "totalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAmountNotIn(List<Long> values) {
            addCriterion("total_amount not in", values, "totalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAmountBetween(Long value1, Long value2) {
            addCriterion("total_amount between", value1, value2, "totalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAmountNotBetween(Long value1, Long value2) {
            addCriterion("total_amount not between", value1, value2, "totalAmount");
            return (Criteria) this;
        }

        public Criteria andSettleDateIsNull() {
            addCriterion("settle_date is null");
            return (Criteria) this;
        }

        public Criteria andSettleDateIsNotNull() {
            addCriterion("settle_date is not null");
            return (Criteria) this;
        }

        public Criteria andSettleDateEqualTo(Date value) {
            addCriterion("settle_date =", value, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateNotEqualTo(Date value) {
            addCriterion("settle_date <>", value, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateGreaterThan(Date value) {
            addCriterion("settle_date >", value, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateGreaterThanOrEqualTo(Date value) {
            addCriterion("settle_date >=", value, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateLessThan(Date value) {
            addCriterion("settle_date <", value, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateLessThanOrEqualTo(Date value) {
            addCriterion("settle_date <=", value, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateIn(List<Date> values) {
            addCriterion("settle_date in", values, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateNotIn(List<Date> values) {
            addCriterion("settle_date not in", values, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateBetween(Date value1, Date value2) {
            addCriterion("settle_date between", value1, value2, "settleDate");
            return (Criteria) this;
        }

        public Criteria andSettleDateNotBetween(Date value1, Date value2) {
            addCriterion("settle_date not between", value1, value2, "settleDate");
            return (Criteria) this;
        }

        public Criteria andCreditorIsNull() {
            addCriterion("creditor is null");
            return (Criteria) this;
        }

        public Criteria andCreditorIsNotNull() {
            addCriterion("creditor is not null");
            return (Criteria) this;
        }

        public Criteria andCreditorEqualTo(String value) {
            addCriterion("creditor =", value, "creditor");
            return (Criteria) this;
        }

        public Criteria andCreditorNotEqualTo(String value) {
            addCriterion("creditor <>", value, "creditor");
            return (Criteria) this;
        }

        public Criteria andCreditorGreaterThan(String value) {
            addCriterion("creditor >", value, "creditor");
            return (Criteria) this;
        }

        public Criteria andCreditorGreaterThanOrEqualTo(String value) {
            addCriterion("creditor >=", value, "creditor");
            return (Criteria) this;
        }

        public Criteria andCreditorLessThan(String value) {
            addCriterion("creditor <", value, "creditor");
            return (Criteria) this;
        }

        public Criteria andCreditorLessThanOrEqualTo(String value) {
            addCriterion("creditor <=", value, "creditor");
            return (Criteria) this;
        }

        public Criteria andCreditorLike(String value) {
            addCriterion("creditor like", value, "creditor");
            return (Criteria) this;
        }

        public Criteria andCreditorNotLike(String value) {
            addCriterion("creditor not like", value, "creditor");
            return (Criteria) this;
        }

        public Criteria andCreditorIn(List<String> values) {
            addCriterion("creditor in", values, "creditor");
            return (Criteria) this;
        }

        public Criteria andCreditorNotIn(List<String> values) {
            addCriterion("creditor not in", values, "creditor");
            return (Criteria) this;
        }

        public Criteria andCreditorBetween(String value1, String value2) {
            addCriterion("creditor between", value1, value2, "creditor");
            return (Criteria) this;
        }

        public Criteria andCreditorNotBetween(String value1, String value2) {
            addCriterion("creditor not between", value1, value2, "creditor");
            return (Criteria) this;
        }

        public Criteria andCreditorBranchIsNull() {
            addCriterion("creditor_branch is null");
            return (Criteria) this;
        }

        public Criteria andCreditorBranchIsNotNull() {
            addCriterion("creditor_branch is not null");
            return (Criteria) this;
        }

        public Criteria andCreditorBranchEqualTo(String value) {
            addCriterion("creditor_branch =", value, "creditorBranch");
            return (Criteria) this;
        }

        public Criteria andCreditorBranchNotEqualTo(String value) {
            addCriterion("creditor_branch <>", value, "creditorBranch");
            return (Criteria) this;
        }

        public Criteria andCreditorBranchGreaterThan(String value) {
            addCriterion("creditor_branch >", value, "creditorBranch");
            return (Criteria) this;
        }

        public Criteria andCreditorBranchGreaterThanOrEqualTo(String value) {
            addCriterion("creditor_branch >=", value, "creditorBranch");
            return (Criteria) this;
        }

        public Criteria andCreditorBranchLessThan(String value) {
            addCriterion("creditor_branch <", value, "creditorBranch");
            return (Criteria) this;
        }

        public Criteria andCreditorBranchLessThanOrEqualTo(String value) {
            addCriterion("creditor_branch <=", value, "creditorBranch");
            return (Criteria) this;
        }

        public Criteria andCreditorBranchLike(String value) {
            addCriterion("creditor_branch like", value, "creditorBranch");
            return (Criteria) this;
        }

        public Criteria andCreditorBranchNotLike(String value) {
            addCriterion("creditor_branch not like", value, "creditorBranch");
            return (Criteria) this;
        }

        public Criteria andCreditorBranchIn(List<String> values) {
            addCriterion("creditor_branch in", values, "creditorBranch");
            return (Criteria) this;
        }

        public Criteria andCreditorBranchNotIn(List<String> values) {
            addCriterion("creditor_branch not in", values, "creditorBranch");
            return (Criteria) this;
        }

        public Criteria andCreditorBranchBetween(String value1, String value2) {
            addCriterion("creditor_branch between", value1, value2, "creditorBranch");
            return (Criteria) this;
        }

        public Criteria andCreditorBranchNotBetween(String value1, String value2) {
            addCriterion("creditor_branch not between", value1, value2, "creditorBranch");
            return (Criteria) this;
        }

        public Criteria andOriginalCreditorIsNull() {
            addCriterion("original_creditor is null");
            return (Criteria) this;
        }

        public Criteria andOriginalCreditorIsNotNull() {
            addCriterion("original_creditor is not null");
            return (Criteria) this;
        }

        public Criteria andOriginalCreditorEqualTo(String value) {
            addCriterion("original_creditor =", value, "originalCreditor");
            return (Criteria) this;
        }

        public Criteria andOriginalCreditorNotEqualTo(String value) {
            addCriterion("original_creditor <>", value, "originalCreditor");
            return (Criteria) this;
        }

        public Criteria andOriginalCreditorGreaterThan(String value) {
            addCriterion("original_creditor >", value, "originalCreditor");
            return (Criteria) this;
        }

        public Criteria andOriginalCreditorGreaterThanOrEqualTo(String value) {
            addCriterion("original_creditor >=", value, "originalCreditor");
            return (Criteria) this;
        }

        public Criteria andOriginalCreditorLessThan(String value) {
            addCriterion("original_creditor <", value, "originalCreditor");
            return (Criteria) this;
        }

        public Criteria andOriginalCreditorLessThanOrEqualTo(String value) {
            addCriterion("original_creditor <=", value, "originalCreditor");
            return (Criteria) this;
        }

        public Criteria andOriginalCreditorLike(String value) {
            addCriterion("original_creditor like", value, "originalCreditor");
            return (Criteria) this;
        }

        public Criteria andOriginalCreditorNotLike(String value) {
            addCriterion("original_creditor not like", value, "originalCreditor");
            return (Criteria) this;
        }

        public Criteria andOriginalCreditorIn(List<String> values) {
            addCriterion("original_creditor in", values, "originalCreditor");
            return (Criteria) this;
        }

        public Criteria andOriginalCreditorNotIn(List<String> values) {
            addCriterion("original_creditor not in", values, "originalCreditor");
            return (Criteria) this;
        }

        public Criteria andOriginalCreditorBetween(String value1, String value2) {
            addCriterion("original_creditor between", value1, value2, "originalCreditor");
            return (Criteria) this;
        }

        public Criteria andOriginalCreditorNotBetween(String value1, String value2) {
            addCriterion("original_creditor not between", value1, value2, "originalCreditor");
            return (Criteria) this;
        }

        public Criteria andNotesIsNull() {
            addCriterion("notes is null");
            return (Criteria) this;
        }

        public Criteria andNotesIsNotNull() {
            addCriterion("notes is not null");
            return (Criteria) this;
        }

        public Criteria andNotesEqualTo(String value) {
            addCriterion("notes =", value, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesNotEqualTo(String value) {
            addCriterion("notes <>", value, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesGreaterThan(String value) {
            addCriterion("notes >", value, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesGreaterThanOrEqualTo(String value) {
            addCriterion("notes >=", value, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesLessThan(String value) {
            addCriterion("notes <", value, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesLessThanOrEqualTo(String value) {
            addCriterion("notes <=", value, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesLike(String value) {
            addCriterion("notes like", value, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesNotLike(String value) {
            addCriterion("notes not like", value, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesIn(List<String> values) {
            addCriterion("notes in", values, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesNotIn(List<String> values) {
            addCriterion("notes not in", values, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesBetween(String value1, String value2) {
            addCriterion("notes between", value1, value2, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesNotBetween(String value1, String value2) {
            addCriterion("notes not between", value1, value2, "notes");
            return (Criteria) this;
        }

        public Criteria andAmcCompanyIdIsNull() {
            addCriterion("amc_company_id is null");
            return (Criteria) this;
        }

        public Criteria andAmcCompanyIdIsNotNull() {
            addCriterion("amc_company_id is not null");
            return (Criteria) this;
        }

        public Criteria andAmcCompanyIdEqualTo(Long value) {
            addCriterion("amc_company_id =", value, "amcCompanyId");
            return (Criteria) this;
        }

        public Criteria andAmcCompanyIdNotEqualTo(Long value) {
            addCriterion("amc_company_id <>", value, "amcCompanyId");
            return (Criteria) this;
        }

        public Criteria andAmcCompanyIdGreaterThan(Long value) {
            addCriterion("amc_company_id >", value, "amcCompanyId");
            return (Criteria) this;
        }

        public Criteria andAmcCompanyIdGreaterThanOrEqualTo(Long value) {
            addCriterion("amc_company_id >=", value, "amcCompanyId");
            return (Criteria) this;
        }

        public Criteria andAmcCompanyIdLessThan(Long value) {
            addCriterion("amc_company_id <", value, "amcCompanyId");
            return (Criteria) this;
        }

        public Criteria andAmcCompanyIdLessThanOrEqualTo(Long value) {
            addCriterion("amc_company_id <=", value, "amcCompanyId");
            return (Criteria) this;
        }

        public Criteria andAmcCompanyIdIn(List<Long> values) {
            addCriterion("amc_company_id in", values, "amcCompanyId");
            return (Criteria) this;
        }

        public Criteria andAmcCompanyIdNotIn(List<Long> values) {
            addCriterion("amc_company_id not in", values, "amcCompanyId");
            return (Criteria) this;
        }

        public Criteria andAmcCompanyIdBetween(Long value1, Long value2) {
            addCriterion("amc_company_id between", value1, value2, "amcCompanyId");
            return (Criteria) this;
        }

        public Criteria andAmcCompanyIdNotBetween(Long value1, Long value2) {
            addCriterion("amc_company_id not between", value1, value2, "amcCompanyId");
            return (Criteria) this;
        }

        public Criteria andPackStatusIsNull() {
            addCriterion("pack_status is null");
            return (Criteria) this;
        }

        public Criteria andPackStatusIsNotNull() {
            addCriterion("pack_status is not null");
            return (Criteria) this;
        }

        public Criteria andPackStatusEqualTo(Integer value) {
            addCriterion("pack_status =", value, "packStatus");
            return (Criteria) this;
        }

        public Criteria andPackStatusNotEqualTo(Integer value) {
            addCriterion("pack_status <>", value, "packStatus");
            return (Criteria) this;
        }

        public Criteria andPackStatusGreaterThan(Integer value) {
            addCriterion("pack_status >", value, "packStatus");
            return (Criteria) this;
        }

        public Criteria andPackStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("pack_status >=", value, "packStatus");
            return (Criteria) this;
        }

        public Criteria andPackStatusLessThan(Integer value) {
            addCriterion("pack_status <", value, "packStatus");
            return (Criteria) this;
        }

        public Criteria andPackStatusLessThanOrEqualTo(Integer value) {
            addCriterion("pack_status <=", value, "packStatus");
            return (Criteria) this;
        }

        public Criteria andPackStatusIn(List<Integer> values) {
            addCriterion("pack_status in", values, "packStatus");
            return (Criteria) this;
        }

        public Criteria andPackStatusNotIn(List<Integer> values) {
            addCriterion("pack_status not in", values, "packStatus");
            return (Criteria) this;
        }

        public Criteria andPackStatusBetween(Integer value1, Integer value2) {
            addCriterion("pack_status between", value1, value2, "packStatus");
            return (Criteria) this;
        }

        public Criteria andPackStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("pack_status not between", value1, value2, "packStatus");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}