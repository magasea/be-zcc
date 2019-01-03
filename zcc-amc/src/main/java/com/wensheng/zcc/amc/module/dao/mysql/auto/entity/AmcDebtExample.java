package com.wensheng.zcc.amc.module.dao.mysql.auto.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AmcDebtExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AmcDebtExample() {
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

        public Criteria andDebtpackIdIsNull() {
            addCriterion("debtpack_id is null");
            return (Criteria) this;
        }

        public Criteria andDebtpackIdIsNotNull() {
            addCriterion("debtpack_id is not null");
            return (Criteria) this;
        }

        public Criteria andDebtpackIdEqualTo(Long value) {
            addCriterion("debtpack_id =", value, "debtpackId");
            return (Criteria) this;
        }

        public Criteria andDebtpackIdNotEqualTo(Long value) {
            addCriterion("debtpack_id <>", value, "debtpackId");
            return (Criteria) this;
        }

        public Criteria andDebtpackIdGreaterThan(Long value) {
            addCriterion("debtpack_id >", value, "debtpackId");
            return (Criteria) this;
        }

        public Criteria andDebtpackIdGreaterThanOrEqualTo(Long value) {
            addCriterion("debtpack_id >=", value, "debtpackId");
            return (Criteria) this;
        }

        public Criteria andDebtpackIdLessThan(Long value) {
            addCriterion("debtpack_id <", value, "debtpackId");
            return (Criteria) this;
        }

        public Criteria andDebtpackIdLessThanOrEqualTo(Long value) {
            addCriterion("debtpack_id <=", value, "debtpackId");
            return (Criteria) this;
        }

        public Criteria andDebtpackIdIn(List<Long> values) {
            addCriterion("debtpack_id in", values, "debtpackId");
            return (Criteria) this;
        }

        public Criteria andDebtpackIdNotIn(List<Long> values) {
            addCriterion("debtpack_id not in", values, "debtpackId");
            return (Criteria) this;
        }

        public Criteria andDebtpackIdBetween(Long value1, Long value2) {
            addCriterion("debtpack_id between", value1, value2, "debtpackId");
            return (Criteria) this;
        }

        public Criteria andDebtpackIdNotBetween(Long value1, Long value2) {
            addCriterion("debtpack_id not between", value1, value2, "debtpackId");
            return (Criteria) this;
        }

        public Criteria andAmcIdIsNull() {
            addCriterion("amc_id is null");
            return (Criteria) this;
        }

        public Criteria andAmcIdIsNotNull() {
            addCriterion("amc_id is not null");
            return (Criteria) this;
        }

        public Criteria andAmcIdEqualTo(Long value) {
            addCriterion("amc_id =", value, "amcId");
            return (Criteria) this;
        }

        public Criteria andAmcIdNotEqualTo(Long value) {
            addCriterion("amc_id <>", value, "amcId");
            return (Criteria) this;
        }

        public Criteria andAmcIdGreaterThan(Long value) {
            addCriterion("amc_id >", value, "amcId");
            return (Criteria) this;
        }

        public Criteria andAmcIdGreaterThanOrEqualTo(Long value) {
            addCriterion("amc_id >=", value, "amcId");
            return (Criteria) this;
        }

        public Criteria andAmcIdLessThan(Long value) {
            addCriterion("amc_id <", value, "amcId");
            return (Criteria) this;
        }

        public Criteria andAmcIdLessThanOrEqualTo(Long value) {
            addCriterion("amc_id <=", value, "amcId");
            return (Criteria) this;
        }

        public Criteria andAmcIdIn(List<Long> values) {
            addCriterion("amc_id in", values, "amcId");
            return (Criteria) this;
        }

        public Criteria andAmcIdNotIn(List<Long> values) {
            addCriterion("amc_id not in", values, "amcId");
            return (Criteria) this;
        }

        public Criteria andAmcIdBetween(Long value1, Long value2) {
            addCriterion("amc_id between", value1, value2, "amcId");
            return (Criteria) this;
        }

        public Criteria andAmcIdNotBetween(Long value1, Long value2) {
            addCriterion("amc_id not between", value1, value2, "amcId");
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

        public Criteria andBaseAmountDescIsNull() {
            addCriterion("base_amount_desc is null");
            return (Criteria) this;
        }

        public Criteria andBaseAmountDescIsNotNull() {
            addCriterion("base_amount_desc is not null");
            return (Criteria) this;
        }

        public Criteria andBaseAmountDescEqualTo(String value) {
            addCriterion("base_amount_desc =", value, "baseAmountDesc");
            return (Criteria) this;
        }

        public Criteria andBaseAmountDescNotEqualTo(String value) {
            addCriterion("base_amount_desc <>", value, "baseAmountDesc");
            return (Criteria) this;
        }

        public Criteria andBaseAmountDescGreaterThan(String value) {
            addCriterion("base_amount_desc >", value, "baseAmountDesc");
            return (Criteria) this;
        }

        public Criteria andBaseAmountDescGreaterThanOrEqualTo(String value) {
            addCriterion("base_amount_desc >=", value, "baseAmountDesc");
            return (Criteria) this;
        }

        public Criteria andBaseAmountDescLessThan(String value) {
            addCriterion("base_amount_desc <", value, "baseAmountDesc");
            return (Criteria) this;
        }

        public Criteria andBaseAmountDescLessThanOrEqualTo(String value) {
            addCriterion("base_amount_desc <=", value, "baseAmountDesc");
            return (Criteria) this;
        }

        public Criteria andBaseAmountDescLike(String value) {
            addCriterion("base_amount_desc like", value, "baseAmountDesc");
            return (Criteria) this;
        }

        public Criteria andBaseAmountDescNotLike(String value) {
            addCriterion("base_amount_desc not like", value, "baseAmountDesc");
            return (Criteria) this;
        }

        public Criteria andBaseAmountDescIn(List<String> values) {
            addCriterion("base_amount_desc in", values, "baseAmountDesc");
            return (Criteria) this;
        }

        public Criteria andBaseAmountDescNotIn(List<String> values) {
            addCriterion("base_amount_desc not in", values, "baseAmountDesc");
            return (Criteria) this;
        }

        public Criteria andBaseAmountDescBetween(String value1, String value2) {
            addCriterion("base_amount_desc between", value1, value2, "baseAmountDesc");
            return (Criteria) this;
        }

        public Criteria andBaseAmountDescNotBetween(String value1, String value2) {
            addCriterion("base_amount_desc not between", value1, value2, "baseAmountDesc");
            return (Criteria) this;
        }

        public Criteria andBaseDateIsNull() {
            addCriterion("base_date is null");
            return (Criteria) this;
        }

        public Criteria andBaseDateIsNotNull() {
            addCriterion("base_date is not null");
            return (Criteria) this;
        }

        public Criteria andBaseDateEqualTo(Date value) {
            addCriterion("base_date =", value, "baseDate");
            return (Criteria) this;
        }

        public Criteria andBaseDateNotEqualTo(Date value) {
            addCriterion("base_date <>", value, "baseDate");
            return (Criteria) this;
        }

        public Criteria andBaseDateGreaterThan(Date value) {
            addCriterion("base_date >", value, "baseDate");
            return (Criteria) this;
        }

        public Criteria andBaseDateGreaterThanOrEqualTo(Date value) {
            addCriterion("base_date >=", value, "baseDate");
            return (Criteria) this;
        }

        public Criteria andBaseDateLessThan(Date value) {
            addCriterion("base_date <", value, "baseDate");
            return (Criteria) this;
        }

        public Criteria andBaseDateLessThanOrEqualTo(Date value) {
            addCriterion("base_date <=", value, "baseDate");
            return (Criteria) this;
        }

        public Criteria andBaseDateIn(List<Date> values) {
            addCriterion("base_date in", values, "baseDate");
            return (Criteria) this;
        }

        public Criteria andBaseDateNotIn(List<Date> values) {
            addCriterion("base_date not in", values, "baseDate");
            return (Criteria) this;
        }

        public Criteria andBaseDateBetween(Date value1, Date value2) {
            addCriterion("base_date between", value1, value2, "baseDate");
            return (Criteria) this;
        }

        public Criteria andBaseDateNotBetween(Date value1, Date value2) {
            addCriterion("base_date not between", value1, value2, "baseDate");
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

        public Criteria andTotalAmountDescIsNull() {
            addCriterion("total_amount_desc is null");
            return (Criteria) this;
        }

        public Criteria andTotalAmountDescIsNotNull() {
            addCriterion("total_amount_desc is not null");
            return (Criteria) this;
        }

        public Criteria andTotalAmountDescEqualTo(String value) {
            addCriterion("total_amount_desc =", value, "totalAmountDesc");
            return (Criteria) this;
        }

        public Criteria andTotalAmountDescNotEqualTo(String value) {
            addCriterion("total_amount_desc <>", value, "totalAmountDesc");
            return (Criteria) this;
        }

        public Criteria andTotalAmountDescGreaterThan(String value) {
            addCriterion("total_amount_desc >", value, "totalAmountDesc");
            return (Criteria) this;
        }

        public Criteria andTotalAmountDescGreaterThanOrEqualTo(String value) {
            addCriterion("total_amount_desc >=", value, "totalAmountDesc");
            return (Criteria) this;
        }

        public Criteria andTotalAmountDescLessThan(String value) {
            addCriterion("total_amount_desc <", value, "totalAmountDesc");
            return (Criteria) this;
        }

        public Criteria andTotalAmountDescLessThanOrEqualTo(String value) {
            addCriterion("total_amount_desc <=", value, "totalAmountDesc");
            return (Criteria) this;
        }

        public Criteria andTotalAmountDescLike(String value) {
            addCriterion("total_amount_desc like", value, "totalAmountDesc");
            return (Criteria) this;
        }

        public Criteria andTotalAmountDescNotLike(String value) {
            addCriterion("total_amount_desc not like", value, "totalAmountDesc");
            return (Criteria) this;
        }

        public Criteria andTotalAmountDescIn(List<String> values) {
            addCriterion("total_amount_desc in", values, "totalAmountDesc");
            return (Criteria) this;
        }

        public Criteria andTotalAmountDescNotIn(List<String> values) {
            addCriterion("total_amount_desc not in", values, "totalAmountDesc");
            return (Criteria) this;
        }

        public Criteria andTotalAmountDescBetween(String value1, String value2) {
            addCriterion("total_amount_desc between", value1, value2, "totalAmountDesc");
            return (Criteria) this;
        }

        public Criteria andTotalAmountDescNotBetween(String value1, String value2) {
            addCriterion("total_amount_desc not between", value1, value2, "totalAmountDesc");
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

        public Criteria andCourtIdIsNull() {
            addCriterion("court_id is null");
            return (Criteria) this;
        }

        public Criteria andCourtIdIsNotNull() {
            addCriterion("court_id is not null");
            return (Criteria) this;
        }

        public Criteria andCourtIdEqualTo(Long value) {
            addCriterion("court_id =", value, "courtId");
            return (Criteria) this;
        }

        public Criteria andCourtIdNotEqualTo(Long value) {
            addCriterion("court_id <>", value, "courtId");
            return (Criteria) this;
        }

        public Criteria andCourtIdGreaterThan(Long value) {
            addCriterion("court_id >", value, "courtId");
            return (Criteria) this;
        }

        public Criteria andCourtIdGreaterThanOrEqualTo(Long value) {
            addCriterion("court_id >=", value, "courtId");
            return (Criteria) this;
        }

        public Criteria andCourtIdLessThan(Long value) {
            addCriterion("court_id <", value, "courtId");
            return (Criteria) this;
        }

        public Criteria andCourtIdLessThanOrEqualTo(Long value) {
            addCriterion("court_id <=", value, "courtId");
            return (Criteria) this;
        }

        public Criteria andCourtIdIn(List<Long> values) {
            addCriterion("court_id in", values, "courtId");
            return (Criteria) this;
        }

        public Criteria andCourtIdNotIn(List<Long> values) {
            addCriterion("court_id not in", values, "courtId");
            return (Criteria) this;
        }

        public Criteria andCourtIdBetween(Long value1, Long value2) {
            addCriterion("court_id between", value1, value2, "courtId");
            return (Criteria) this;
        }

        public Criteria andCourtIdNotBetween(Long value1, Long value2) {
            addCriterion("court_id not between", value1, value2, "courtId");
            return (Criteria) this;
        }

        public Criteria andAmcDebtCodeIsNull() {
            addCriterion("amc_debt_code is null");
            return (Criteria) this;
        }

        public Criteria andAmcDebtCodeIsNotNull() {
            addCriterion("amc_debt_code is not null");
            return (Criteria) this;
        }

        public Criteria andAmcDebtCodeEqualTo(String value) {
            addCriterion("amc_debt_code =", value, "amcDebtCode");
            return (Criteria) this;
        }

        public Criteria andAmcDebtCodeNotEqualTo(String value) {
            addCriterion("amc_debt_code <>", value, "amcDebtCode");
            return (Criteria) this;
        }

        public Criteria andAmcDebtCodeGreaterThan(String value) {
            addCriterion("amc_debt_code >", value, "amcDebtCode");
            return (Criteria) this;
        }

        public Criteria andAmcDebtCodeGreaterThanOrEqualTo(String value) {
            addCriterion("amc_debt_code >=", value, "amcDebtCode");
            return (Criteria) this;
        }

        public Criteria andAmcDebtCodeLessThan(String value) {
            addCriterion("amc_debt_code <", value, "amcDebtCode");
            return (Criteria) this;
        }

        public Criteria andAmcDebtCodeLessThanOrEqualTo(String value) {
            addCriterion("amc_debt_code <=", value, "amcDebtCode");
            return (Criteria) this;
        }

        public Criteria andAmcDebtCodeLike(String value) {
            addCriterion("amc_debt_code like", value, "amcDebtCode");
            return (Criteria) this;
        }

        public Criteria andAmcDebtCodeNotLike(String value) {
            addCriterion("amc_debt_code not like", value, "amcDebtCode");
            return (Criteria) this;
        }

        public Criteria andAmcDebtCodeIn(List<String> values) {
            addCriterion("amc_debt_code in", values, "amcDebtCode");
            return (Criteria) this;
        }

        public Criteria andAmcDebtCodeNotIn(List<String> values) {
            addCriterion("amc_debt_code not in", values, "amcDebtCode");
            return (Criteria) this;
        }

        public Criteria andAmcDebtCodeBetween(String value1, String value2) {
            addCriterion("amc_debt_code between", value1, value2, "amcDebtCode");
            return (Criteria) this;
        }

        public Criteria andAmcDebtCodeNotBetween(String value1, String value2) {
            addCriterion("amc_debt_code not between", value1, value2, "amcDebtCode");
            return (Criteria) this;
        }

        public Criteria andEditStatusIsNull() {
            addCriterion("edit_status is null");
            return (Criteria) this;
        }

        public Criteria andEditStatusIsNotNull() {
            addCriterion("edit_status is not null");
            return (Criteria) this;
        }

        public Criteria andEditStatusEqualTo(Integer value) {
            addCriterion("edit_status =", value, "editStatus");
            return (Criteria) this;
        }

        public Criteria andEditStatusNotEqualTo(Integer value) {
            addCriterion("edit_status <>", value, "editStatus");
            return (Criteria) this;
        }

        public Criteria andEditStatusGreaterThan(Integer value) {
            addCriterion("edit_status >", value, "editStatus");
            return (Criteria) this;
        }

        public Criteria andEditStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("edit_status >=", value, "editStatus");
            return (Criteria) this;
        }

        public Criteria andEditStatusLessThan(Integer value) {
            addCriterion("edit_status <", value, "editStatus");
            return (Criteria) this;
        }

        public Criteria andEditStatusLessThanOrEqualTo(Integer value) {
            addCriterion("edit_status <=", value, "editStatus");
            return (Criteria) this;
        }

        public Criteria andEditStatusIn(List<Integer> values) {
            addCriterion("edit_status in", values, "editStatus");
            return (Criteria) this;
        }

        public Criteria andEditStatusNotIn(List<Integer> values) {
            addCriterion("edit_status not in", values, "editStatus");
            return (Criteria) this;
        }

        public Criteria andEditStatusBetween(Integer value1, Integer value2) {
            addCriterion("edit_status between", value1, value2, "editStatus");
            return (Criteria) this;
        }

        public Criteria andEditStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("edit_status not between", value1, value2, "editStatus");
            return (Criteria) this;
        }

        public Criteria andPublishDateIsNull() {
            addCriterion("publish_date is null");
            return (Criteria) this;
        }

        public Criteria andPublishDateIsNotNull() {
            addCriterion("publish_date is not null");
            return (Criteria) this;
        }

        public Criteria andPublishDateEqualTo(Date value) {
            addCriterion("publish_date =", value, "publishDate");
            return (Criteria) this;
        }

        public Criteria andPublishDateNotEqualTo(Date value) {
            addCriterion("publish_date <>", value, "publishDate");
            return (Criteria) this;
        }

        public Criteria andPublishDateGreaterThan(Date value) {
            addCriterion("publish_date >", value, "publishDate");
            return (Criteria) this;
        }

        public Criteria andPublishDateGreaterThanOrEqualTo(Date value) {
            addCriterion("publish_date >=", value, "publishDate");
            return (Criteria) this;
        }

        public Criteria andPublishDateLessThan(Date value) {
            addCriterion("publish_date <", value, "publishDate");
            return (Criteria) this;
        }

        public Criteria andPublishDateLessThanOrEqualTo(Date value) {
            addCriterion("publish_date <=", value, "publishDate");
            return (Criteria) this;
        }

        public Criteria andPublishDateIn(List<Date> values) {
            addCriterion("publish_date in", values, "publishDate");
            return (Criteria) this;
        }

        public Criteria andPublishDateNotIn(List<Date> values) {
            addCriterion("publish_date not in", values, "publishDate");
            return (Criteria) this;
        }

        public Criteria andPublishDateBetween(Date value1, Date value2) {
            addCriterion("publish_date between", value1, value2, "publishDate");
            return (Criteria) this;
        }

        public Criteria andPublishDateNotBetween(Date value1, Date value2) {
            addCriterion("publish_date not between", value1, value2, "publishDate");
            return (Criteria) this;
        }

        public Criteria andLawStatusIsNull() {
            addCriterion("law_status is null");
            return (Criteria) this;
        }

        public Criteria andLawStatusIsNotNull() {
            addCriterion("law_status is not null");
            return (Criteria) this;
        }

        public Criteria andLawStatusEqualTo(Integer value) {
            addCriterion("law_status =", value, "lawStatus");
            return (Criteria) this;
        }

        public Criteria andLawStatusNotEqualTo(Integer value) {
            addCriterion("law_status <>", value, "lawStatus");
            return (Criteria) this;
        }

        public Criteria andLawStatusGreaterThan(Integer value) {
            addCriterion("law_status >", value, "lawStatus");
            return (Criteria) this;
        }

        public Criteria andLawStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("law_status >=", value, "lawStatus");
            return (Criteria) this;
        }

        public Criteria andLawStatusLessThan(Integer value) {
            addCriterion("law_status <", value, "lawStatus");
            return (Criteria) this;
        }

        public Criteria andLawStatusLessThanOrEqualTo(Integer value) {
            addCriterion("law_status <=", value, "lawStatus");
            return (Criteria) this;
        }

        public Criteria andLawStatusIn(List<Integer> values) {
            addCriterion("law_status in", values, "lawStatus");
            return (Criteria) this;
        }

        public Criteria andLawStatusNotIn(List<Integer> values) {
            addCriterion("law_status not in", values, "lawStatus");
            return (Criteria) this;
        }

        public Criteria andLawStatusBetween(Integer value1, Integer value2) {
            addCriterion("law_status between", value1, value2, "lawStatus");
            return (Criteria) this;
        }

        public Criteria andLawStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("law_status not between", value1, value2, "lawStatus");
            return (Criteria) this;
        }

        public Criteria andEstimatedPriceIsNull() {
            addCriterion("estimated_price is null");
            return (Criteria) this;
        }

        public Criteria andEstimatedPriceIsNotNull() {
            addCriterion("estimated_price is not null");
            return (Criteria) this;
        }

        public Criteria andEstimatedPriceEqualTo(Long value) {
            addCriterion("estimated_price =", value, "estimatedPrice");
            return (Criteria) this;
        }

        public Criteria andEstimatedPriceNotEqualTo(Long value) {
            addCriterion("estimated_price <>", value, "estimatedPrice");
            return (Criteria) this;
        }

        public Criteria andEstimatedPriceGreaterThan(Long value) {
            addCriterion("estimated_price >", value, "estimatedPrice");
            return (Criteria) this;
        }

        public Criteria andEstimatedPriceGreaterThanOrEqualTo(Long value) {
            addCriterion("estimated_price >=", value, "estimatedPrice");
            return (Criteria) this;
        }

        public Criteria andEstimatedPriceLessThan(Long value) {
            addCriterion("estimated_price <", value, "estimatedPrice");
            return (Criteria) this;
        }

        public Criteria andEstimatedPriceLessThanOrEqualTo(Long value) {
            addCriterion("estimated_price <=", value, "estimatedPrice");
            return (Criteria) this;
        }

        public Criteria andEstimatedPriceIn(List<Long> values) {
            addCriterion("estimated_price in", values, "estimatedPrice");
            return (Criteria) this;
        }

        public Criteria andEstimatedPriceNotIn(List<Long> values) {
            addCriterion("estimated_price not in", values, "estimatedPrice");
            return (Criteria) this;
        }

        public Criteria andEstimatedPriceBetween(Long value1, Long value2) {
            addCriterion("estimated_price between", value1, value2, "estimatedPrice");
            return (Criteria) this;
        }

        public Criteria andEstimatedPriceNotBetween(Long value1, Long value2) {
            addCriterion("estimated_price not between", value1, value2, "estimatedPrice");
            return (Criteria) this;
        }

        public Criteria andAmcContact1IsNull() {
            addCriterion("amc_contact1 is null");
            return (Criteria) this;
        }

        public Criteria andAmcContact1IsNotNull() {
            addCriterion("amc_contact1 is not null");
            return (Criteria) this;
        }

        public Criteria andAmcContact1EqualTo(Long value) {
            addCriterion("amc_contact1 =", value, "amcContact1");
            return (Criteria) this;
        }

        public Criteria andAmcContact1NotEqualTo(Long value) {
            addCriterion("amc_contact1 <>", value, "amcContact1");
            return (Criteria) this;
        }

        public Criteria andAmcContact1GreaterThan(Long value) {
            addCriterion("amc_contact1 >", value, "amcContact1");
            return (Criteria) this;
        }

        public Criteria andAmcContact1GreaterThanOrEqualTo(Long value) {
            addCriterion("amc_contact1 >=", value, "amcContact1");
            return (Criteria) this;
        }

        public Criteria andAmcContact1LessThan(Long value) {
            addCriterion("amc_contact1 <", value, "amcContact1");
            return (Criteria) this;
        }

        public Criteria andAmcContact1LessThanOrEqualTo(Long value) {
            addCriterion("amc_contact1 <=", value, "amcContact1");
            return (Criteria) this;
        }

        public Criteria andAmcContact1In(List<Long> values) {
            addCriterion("amc_contact1 in", values, "amcContact1");
            return (Criteria) this;
        }

        public Criteria andAmcContact1NotIn(List<Long> values) {
            addCriterion("amc_contact1 not in", values, "amcContact1");
            return (Criteria) this;
        }

        public Criteria andAmcContact1Between(Long value1, Long value2) {
            addCriterion("amc_contact1 between", value1, value2, "amcContact1");
            return (Criteria) this;
        }

        public Criteria andAmcContact1NotBetween(Long value1, Long value2) {
            addCriterion("amc_contact1 not between", value1, value2, "amcContact1");
            return (Criteria) this;
        }

        public Criteria andAmcContact2IsNull() {
            addCriterion("amc_contact2 is null");
            return (Criteria) this;
        }

        public Criteria andAmcContact2IsNotNull() {
            addCriterion("amc_contact2 is not null");
            return (Criteria) this;
        }

        public Criteria andAmcContact2EqualTo(Long value) {
            addCriterion("amc_contact2 =", value, "amcContact2");
            return (Criteria) this;
        }

        public Criteria andAmcContact2NotEqualTo(Long value) {
            addCriterion("amc_contact2 <>", value, "amcContact2");
            return (Criteria) this;
        }

        public Criteria andAmcContact2GreaterThan(Long value) {
            addCriterion("amc_contact2 >", value, "amcContact2");
            return (Criteria) this;
        }

        public Criteria andAmcContact2GreaterThanOrEqualTo(Long value) {
            addCriterion("amc_contact2 >=", value, "amcContact2");
            return (Criteria) this;
        }

        public Criteria andAmcContact2LessThan(Long value) {
            addCriterion("amc_contact2 <", value, "amcContact2");
            return (Criteria) this;
        }

        public Criteria andAmcContact2LessThanOrEqualTo(Long value) {
            addCriterion("amc_contact2 <=", value, "amcContact2");
            return (Criteria) this;
        }

        public Criteria andAmcContact2In(List<Long> values) {
            addCriterion("amc_contact2 in", values, "amcContact2");
            return (Criteria) this;
        }

        public Criteria andAmcContact2NotIn(List<Long> values) {
            addCriterion("amc_contact2 not in", values, "amcContact2");
            return (Criteria) this;
        }

        public Criteria andAmcContact2Between(Long value1, Long value2) {
            addCriterion("amc_contact2 between", value1, value2, "amcContact2");
            return (Criteria) this;
        }

        public Criteria andAmcContact2NotBetween(Long value1, Long value2) {
            addCriterion("amc_contact2 not between", value1, value2, "amcContact2");
            return (Criteria) this;
        }

        public Criteria andIsRecommandedIsNull() {
            addCriterion("is_recommanded is null");
            return (Criteria) this;
        }

        public Criteria andIsRecommandedIsNotNull() {
            addCriterion("is_recommanded is not null");
            return (Criteria) this;
        }

        public Criteria andIsRecommandedEqualTo(Integer value) {
            addCriterion("is_recommanded =", value, "isRecommanded");
            return (Criteria) this;
        }

        public Criteria andIsRecommandedNotEqualTo(Integer value) {
            addCriterion("is_recommanded <>", value, "isRecommanded");
            return (Criteria) this;
        }

        public Criteria andIsRecommandedGreaterThan(Integer value) {
            addCriterion("is_recommanded >", value, "isRecommanded");
            return (Criteria) this;
        }

        public Criteria andIsRecommandedGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_recommanded >=", value, "isRecommanded");
            return (Criteria) this;
        }

        public Criteria andIsRecommandedLessThan(Integer value) {
            addCriterion("is_recommanded <", value, "isRecommanded");
            return (Criteria) this;
        }

        public Criteria andIsRecommandedLessThanOrEqualTo(Integer value) {
            addCriterion("is_recommanded <=", value, "isRecommanded");
            return (Criteria) this;
        }

        public Criteria andIsRecommandedIn(List<Integer> values) {
            addCriterion("is_recommanded in", values, "isRecommanded");
            return (Criteria) this;
        }

        public Criteria andIsRecommandedNotIn(List<Integer> values) {
            addCriterion("is_recommanded not in", values, "isRecommanded");
            return (Criteria) this;
        }

        public Criteria andIsRecommandedBetween(Integer value1, Integer value2) {
            addCriterion("is_recommanded between", value1, value2, "isRecommanded");
            return (Criteria) this;
        }

        public Criteria andIsRecommandedNotBetween(Integer value1, Integer value2) {
            addCriterion("is_recommanded not between", value1, value2, "isRecommanded");
            return (Criteria) this;
        }

        public Criteria andStartDateIsNull() {
            addCriterion("start_date is null");
            return (Criteria) this;
        }

        public Criteria andStartDateIsNotNull() {
            addCriterion("start_date is not null");
            return (Criteria) this;
        }

        public Criteria andStartDateEqualTo(Date value) {
            addCriterion("start_date =", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateNotEqualTo(Date value) {
            addCriterion("start_date <>", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateGreaterThan(Date value) {
            addCriterion("start_date >", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateGreaterThanOrEqualTo(Date value) {
            addCriterion("start_date >=", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateLessThan(Date value) {
            addCriterion("start_date <", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateLessThanOrEqualTo(Date value) {
            addCriterion("start_date <=", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateIn(List<Date> values) {
            addCriterion("start_date in", values, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateNotIn(List<Date> values) {
            addCriterion("start_date not in", values, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateBetween(Date value1, Date value2) {
            addCriterion("start_date between", value1, value2, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateNotBetween(Date value1, Date value2) {
            addCriterion("start_date not between", value1, value2, "startDate");
            return (Criteria) this;
        }

        public Criteria andEndDateIsNull() {
            addCriterion("end_date is null");
            return (Criteria) this;
        }

        public Criteria andEndDateIsNotNull() {
            addCriterion("end_date is not null");
            return (Criteria) this;
        }

        public Criteria andEndDateEqualTo(Date value) {
            addCriterion("end_date =", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotEqualTo(Date value) {
            addCriterion("end_date <>", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateGreaterThan(Date value) {
            addCriterion("end_date >", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateGreaterThanOrEqualTo(Date value) {
            addCriterion("end_date >=", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateLessThan(Date value) {
            addCriterion("end_date <", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateLessThanOrEqualTo(Date value) {
            addCriterion("end_date <=", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateIn(List<Date> values) {
            addCriterion("end_date in", values, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotIn(List<Date> values) {
            addCriterion("end_date not in", values, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateBetween(Date value1, Date value2) {
            addCriterion("end_date between", value1, value2, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotBetween(Date value1, Date value2) {
            addCriterion("end_date not between", value1, value2, "endDate");
            return (Criteria) this;
        }

        public Criteria andOrigDebtIdIsNull() {
            addCriterion("orig_debt_id is null");
            return (Criteria) this;
        }

        public Criteria andOrigDebtIdIsNotNull() {
            addCriterion("orig_debt_id is not null");
            return (Criteria) this;
        }

        public Criteria andOrigDebtIdEqualTo(Long value) {
            addCriterion("orig_debt_id =", value, "origDebtId");
            return (Criteria) this;
        }

        public Criteria andOrigDebtIdNotEqualTo(Long value) {
            addCriterion("orig_debt_id <>", value, "origDebtId");
            return (Criteria) this;
        }

        public Criteria andOrigDebtIdGreaterThan(Long value) {
            addCriterion("orig_debt_id >", value, "origDebtId");
            return (Criteria) this;
        }

        public Criteria andOrigDebtIdGreaterThanOrEqualTo(Long value) {
            addCriterion("orig_debt_id >=", value, "origDebtId");
            return (Criteria) this;
        }

        public Criteria andOrigDebtIdLessThan(Long value) {
            addCriterion("orig_debt_id <", value, "origDebtId");
            return (Criteria) this;
        }

        public Criteria andOrigDebtIdLessThanOrEqualTo(Long value) {
            addCriterion("orig_debt_id <=", value, "origDebtId");
            return (Criteria) this;
        }

        public Criteria andOrigDebtIdIn(List<Long> values) {
            addCriterion("orig_debt_id in", values, "origDebtId");
            return (Criteria) this;
        }

        public Criteria andOrigDebtIdNotIn(List<Long> values) {
            addCriterion("orig_debt_id not in", values, "origDebtId");
            return (Criteria) this;
        }

        public Criteria andOrigDebtIdBetween(Long value1, Long value2) {
            addCriterion("orig_debt_id between", value1, value2, "origDebtId");
            return (Criteria) this;
        }

        public Criteria andOrigDebtIdNotBetween(Long value1, Long value2) {
            addCriterion("orig_debt_id not between", value1, value2, "origDebtId");
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