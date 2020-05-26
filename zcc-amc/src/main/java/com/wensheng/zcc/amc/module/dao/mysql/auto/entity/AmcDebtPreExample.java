package com.wensheng.zcc.amc.module.dao.mysql.auto.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AmcDebtPreExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AmcDebtPreExample() {
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

        public Criteria andDebtTypeIsNull() {
            addCriterion("debt_type is null");
            return (Criteria) this;
        }

        public Criteria andDebtTypeIsNotNull() {
            addCriterion("debt_type is not null");
            return (Criteria) this;
        }

        public Criteria andDebtTypeEqualTo(Integer value) {
            addCriterion("debt_type =", value, "debtType");
            return (Criteria) this;
        }

        public Criteria andDebtTypeNotEqualTo(Integer value) {
            addCriterion("debt_type <>", value, "debtType");
            return (Criteria) this;
        }

        public Criteria andDebtTypeGreaterThan(Integer value) {
            addCriterion("debt_type >", value, "debtType");
            return (Criteria) this;
        }

        public Criteria andDebtTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("debt_type >=", value, "debtType");
            return (Criteria) this;
        }

        public Criteria andDebtTypeLessThan(Integer value) {
            addCriterion("debt_type <", value, "debtType");
            return (Criteria) this;
        }

        public Criteria andDebtTypeLessThanOrEqualTo(Integer value) {
            addCriterion("debt_type <=", value, "debtType");
            return (Criteria) this;
        }

        public Criteria andDebtTypeIn(List<Integer> values) {
            addCriterion("debt_type in", values, "debtType");
            return (Criteria) this;
        }

        public Criteria andDebtTypeNotIn(List<Integer> values) {
            addCriterion("debt_type not in", values, "debtType");
            return (Criteria) this;
        }

        public Criteria andDebtTypeBetween(Integer value1, Integer value2) {
            addCriterion("debt_type between", value1, value2, "debtType");
            return (Criteria) this;
        }

        public Criteria andDebtTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("debt_type not between", value1, value2, "debtType");
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

        public Criteria andInterestAmountIsNull() {
            addCriterion("interest_amount is null");
            return (Criteria) this;
        }

        public Criteria andInterestAmountIsNotNull() {
            addCriterion("interest_amount is not null");
            return (Criteria) this;
        }

        public Criteria andInterestAmountEqualTo(Long value) {
            addCriterion("interest_amount =", value, "interestAmount");
            return (Criteria) this;
        }

        public Criteria andInterestAmountNotEqualTo(Long value) {
            addCriterion("interest_amount <>", value, "interestAmount");
            return (Criteria) this;
        }

        public Criteria andInterestAmountGreaterThan(Long value) {
            addCriterion("interest_amount >", value, "interestAmount");
            return (Criteria) this;
        }

        public Criteria andInterestAmountGreaterThanOrEqualTo(Long value) {
            addCriterion("interest_amount >=", value, "interestAmount");
            return (Criteria) this;
        }

        public Criteria andInterestAmountLessThan(Long value) {
            addCriterion("interest_amount <", value, "interestAmount");
            return (Criteria) this;
        }

        public Criteria andInterestAmountLessThanOrEqualTo(Long value) {
            addCriterion("interest_amount <=", value, "interestAmount");
            return (Criteria) this;
        }

        public Criteria andInterestAmountIn(List<Long> values) {
            addCriterion("interest_amount in", values, "interestAmount");
            return (Criteria) this;
        }

        public Criteria andInterestAmountNotIn(List<Long> values) {
            addCriterion("interest_amount not in", values, "interestAmount");
            return (Criteria) this;
        }

        public Criteria andInterestAmountBetween(Long value1, Long value2) {
            addCriterion("interest_amount between", value1, value2, "interestAmount");
            return (Criteria) this;
        }

        public Criteria andInterestAmountNotBetween(Long value1, Long value2) {
            addCriterion("interest_amount not between", value1, value2, "interestAmount");
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

        public Criteria andCurtProvIsNull() {
            addCriterion("curt_prov is null");
            return (Criteria) this;
        }

        public Criteria andCurtProvIsNotNull() {
            addCriterion("curt_prov is not null");
            return (Criteria) this;
        }

        public Criteria andCurtProvEqualTo(Long value) {
            addCriterion("curt_prov =", value, "curtProv");
            return (Criteria) this;
        }

        public Criteria andCurtProvNotEqualTo(Long value) {
            addCriterion("curt_prov <>", value, "curtProv");
            return (Criteria) this;
        }

        public Criteria andCurtProvGreaterThan(Long value) {
            addCriterion("curt_prov >", value, "curtProv");
            return (Criteria) this;
        }

        public Criteria andCurtProvGreaterThanOrEqualTo(Long value) {
            addCriterion("curt_prov >=", value, "curtProv");
            return (Criteria) this;
        }

        public Criteria andCurtProvLessThan(Long value) {
            addCriterion("curt_prov <", value, "curtProv");
            return (Criteria) this;
        }

        public Criteria andCurtProvLessThanOrEqualTo(Long value) {
            addCriterion("curt_prov <=", value, "curtProv");
            return (Criteria) this;
        }

        public Criteria andCurtProvIn(List<Long> values) {
            addCriterion("curt_prov in", values, "curtProv");
            return (Criteria) this;
        }

        public Criteria andCurtProvNotIn(List<Long> values) {
            addCriterion("curt_prov not in", values, "curtProv");
            return (Criteria) this;
        }

        public Criteria andCurtProvBetween(Long value1, Long value2) {
            addCriterion("curt_prov between", value1, value2, "curtProv");
            return (Criteria) this;
        }

        public Criteria andCurtProvNotBetween(Long value1, Long value2) {
            addCriterion("curt_prov not between", value1, value2, "curtProv");
            return (Criteria) this;
        }

        public Criteria andCurtCityIsNull() {
            addCriterion("curt_city is null");
            return (Criteria) this;
        }

        public Criteria andCurtCityIsNotNull() {
            addCriterion("curt_city is not null");
            return (Criteria) this;
        }

        public Criteria andCurtCityEqualTo(Long value) {
            addCriterion("curt_city =", value, "curtCity");
            return (Criteria) this;
        }

        public Criteria andCurtCityNotEqualTo(Long value) {
            addCriterion("curt_city <>", value, "curtCity");
            return (Criteria) this;
        }

        public Criteria andCurtCityGreaterThan(Long value) {
            addCriterion("curt_city >", value, "curtCity");
            return (Criteria) this;
        }

        public Criteria andCurtCityGreaterThanOrEqualTo(Long value) {
            addCriterion("curt_city >=", value, "curtCity");
            return (Criteria) this;
        }

        public Criteria andCurtCityLessThan(Long value) {
            addCriterion("curt_city <", value, "curtCity");
            return (Criteria) this;
        }

        public Criteria andCurtCityLessThanOrEqualTo(Long value) {
            addCriterion("curt_city <=", value, "curtCity");
            return (Criteria) this;
        }

        public Criteria andCurtCityIn(List<Long> values) {
            addCriterion("curt_city in", values, "curtCity");
            return (Criteria) this;
        }

        public Criteria andCurtCityNotIn(List<Long> values) {
            addCriterion("curt_city not in", values, "curtCity");
            return (Criteria) this;
        }

        public Criteria andCurtCityBetween(Long value1, Long value2) {
            addCriterion("curt_city between", value1, value2, "curtCity");
            return (Criteria) this;
        }

        public Criteria andCurtCityNotBetween(Long value1, Long value2) {
            addCriterion("curt_city not between", value1, value2, "curtCity");
            return (Criteria) this;
        }

        public Criteria andCurtCountyIsNull() {
            addCriterion("curt_county is null");
            return (Criteria) this;
        }

        public Criteria andCurtCountyIsNotNull() {
            addCriterion("curt_county is not null");
            return (Criteria) this;
        }

        public Criteria andCurtCountyEqualTo(Long value) {
            addCriterion("curt_county =", value, "curtCounty");
            return (Criteria) this;
        }

        public Criteria andCurtCountyNotEqualTo(Long value) {
            addCriterion("curt_county <>", value, "curtCounty");
            return (Criteria) this;
        }

        public Criteria andCurtCountyGreaterThan(Long value) {
            addCriterion("curt_county >", value, "curtCounty");
            return (Criteria) this;
        }

        public Criteria andCurtCountyGreaterThanOrEqualTo(Long value) {
            addCriterion("curt_county >=", value, "curtCounty");
            return (Criteria) this;
        }

        public Criteria andCurtCountyLessThan(Long value) {
            addCriterion("curt_county <", value, "curtCounty");
            return (Criteria) this;
        }

        public Criteria andCurtCountyLessThanOrEqualTo(Long value) {
            addCriterion("curt_county <=", value, "curtCounty");
            return (Criteria) this;
        }

        public Criteria andCurtCountyIn(List<Long> values) {
            addCriterion("curt_county in", values, "curtCounty");
            return (Criteria) this;
        }

        public Criteria andCurtCountyNotIn(List<Long> values) {
            addCriterion("curt_county not in", values, "curtCounty");
            return (Criteria) this;
        }

        public Criteria andCurtCountyBetween(Long value1, Long value2) {
            addCriterion("curt_county between", value1, value2, "curtCounty");
            return (Criteria) this;
        }

        public Criteria andCurtCountyNotBetween(Long value1, Long value2) {
            addCriterion("curt_county not between", value1, value2, "curtCounty");
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

        public Criteria andBorrowerIsNull() {
            addCriterion("borrower is null");
            return (Criteria) this;
        }

        public Criteria andBorrowerIsNotNull() {
            addCriterion("borrower is not null");
            return (Criteria) this;
        }

        public Criteria andBorrowerEqualTo(String value) {
            addCriterion("borrower =", value, "borrower");
            return (Criteria) this;
        }

        public Criteria andBorrowerNotEqualTo(String value) {
            addCriterion("borrower <>", value, "borrower");
            return (Criteria) this;
        }

        public Criteria andBorrowerGreaterThan(String value) {
            addCriterion("borrower >", value, "borrower");
            return (Criteria) this;
        }

        public Criteria andBorrowerGreaterThanOrEqualTo(String value) {
            addCriterion("borrower >=", value, "borrower");
            return (Criteria) this;
        }

        public Criteria andBorrowerLessThan(String value) {
            addCriterion("borrower <", value, "borrower");
            return (Criteria) this;
        }

        public Criteria andBorrowerLessThanOrEqualTo(String value) {
            addCriterion("borrower <=", value, "borrower");
            return (Criteria) this;
        }

        public Criteria andBorrowerLike(String value) {
            addCriterion("borrower like", value, "borrower");
            return (Criteria) this;
        }

        public Criteria andBorrowerNotLike(String value) {
            addCriterion("borrower not like", value, "borrower");
            return (Criteria) this;
        }

        public Criteria andBorrowerIn(List<String> values) {
            addCriterion("borrower in", values, "borrower");
            return (Criteria) this;
        }

        public Criteria andBorrowerNotIn(List<String> values) {
            addCriterion("borrower not in", values, "borrower");
            return (Criteria) this;
        }

        public Criteria andBorrowerBetween(String value1, String value2) {
            addCriterion("borrower between", value1, value2, "borrower");
            return (Criteria) this;
        }

        public Criteria andBorrowerNotBetween(String value1, String value2) {
            addCriterion("borrower not between", value1, value2, "borrower");
            return (Criteria) this;
        }

        public Criteria andGuaranteeIsNull() {
            addCriterion("guarantee is null");
            return (Criteria) this;
        }

        public Criteria andGuaranteeIsNotNull() {
            addCriterion("guarantee is not null");
            return (Criteria) this;
        }

        public Criteria andGuaranteeEqualTo(String value) {
            addCriterion("guarantee =", value, "guarantee");
            return (Criteria) this;
        }

        public Criteria andGuaranteeNotEqualTo(String value) {
            addCriterion("guarantee <>", value, "guarantee");
            return (Criteria) this;
        }

        public Criteria andGuaranteeGreaterThan(String value) {
            addCriterion("guarantee >", value, "guarantee");
            return (Criteria) this;
        }

        public Criteria andGuaranteeGreaterThanOrEqualTo(String value) {
            addCriterion("guarantee >=", value, "guarantee");
            return (Criteria) this;
        }

        public Criteria andGuaranteeLessThan(String value) {
            addCriterion("guarantee <", value, "guarantee");
            return (Criteria) this;
        }

        public Criteria andGuaranteeLessThanOrEqualTo(String value) {
            addCriterion("guarantee <=", value, "guarantee");
            return (Criteria) this;
        }

        public Criteria andGuaranteeLike(String value) {
            addCriterion("guarantee like", value, "guarantee");
            return (Criteria) this;
        }

        public Criteria andGuaranteeNotLike(String value) {
            addCriterion("guarantee not like", value, "guarantee");
            return (Criteria) this;
        }

        public Criteria andGuaranteeIn(List<String> values) {
            addCriterion("guarantee in", values, "guarantee");
            return (Criteria) this;
        }

        public Criteria andGuaranteeNotIn(List<String> values) {
            addCriterion("guarantee not in", values, "guarantee");
            return (Criteria) this;
        }

        public Criteria andGuaranteeBetween(String value1, String value2) {
            addCriterion("guarantee between", value1, value2, "guarantee");
            return (Criteria) this;
        }

        public Criteria andGuaranteeNotBetween(String value1, String value2) {
            addCriterion("guarantee not between", value1, value2, "guarantee");
            return (Criteria) this;
        }

        public Criteria andZccDebtCodeIsNull() {
            addCriterion("zcc_debt_code is null");
            return (Criteria) this;
        }

        public Criteria andZccDebtCodeIsNotNull() {
            addCriterion("zcc_debt_code is not null");
            return (Criteria) this;
        }

        public Criteria andZccDebtCodeEqualTo(String value) {
            addCriterion("zcc_debt_code =", value, "zccDebtCode");
            return (Criteria) this;
        }

        public Criteria andZccDebtCodeNotEqualTo(String value) {
            addCriterion("zcc_debt_code <>", value, "zccDebtCode");
            return (Criteria) this;
        }

        public Criteria andZccDebtCodeGreaterThan(String value) {
            addCriterion("zcc_debt_code >", value, "zccDebtCode");
            return (Criteria) this;
        }

        public Criteria andZccDebtCodeGreaterThanOrEqualTo(String value) {
            addCriterion("zcc_debt_code >=", value, "zccDebtCode");
            return (Criteria) this;
        }

        public Criteria andZccDebtCodeLessThan(String value) {
            addCriterion("zcc_debt_code <", value, "zccDebtCode");
            return (Criteria) this;
        }

        public Criteria andZccDebtCodeLessThanOrEqualTo(String value) {
            addCriterion("zcc_debt_code <=", value, "zccDebtCode");
            return (Criteria) this;
        }

        public Criteria andZccDebtCodeLike(String value) {
            addCriterion("zcc_debt_code like", value, "zccDebtCode");
            return (Criteria) this;
        }

        public Criteria andZccDebtCodeNotLike(String value) {
            addCriterion("zcc_debt_code not like", value, "zccDebtCode");
            return (Criteria) this;
        }

        public Criteria andZccDebtCodeIn(List<String> values) {
            addCriterion("zcc_debt_code in", values, "zccDebtCode");
            return (Criteria) this;
        }

        public Criteria andZccDebtCodeNotIn(List<String> values) {
            addCriterion("zcc_debt_code not in", values, "zccDebtCode");
            return (Criteria) this;
        }

        public Criteria andZccDebtCodeBetween(String value1, String value2) {
            addCriterion("zcc_debt_code between", value1, value2, "zccDebtCode");
            return (Criteria) this;
        }

        public Criteria andZccDebtCodeNotBetween(String value1, String value2) {
            addCriterion("zcc_debt_code not between", value1, value2, "zccDebtCode");
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

        public Criteria andPublishStateIsNull() {
            addCriterion("publish_state is null");
            return (Criteria) this;
        }

        public Criteria andPublishStateIsNotNull() {
            addCriterion("publish_state is not null");
            return (Criteria) this;
        }

        public Criteria andPublishStateEqualTo(Integer value) {
            addCriterion("publish_state =", value, "publishState");
            return (Criteria) this;
        }

        public Criteria andPublishStateNotEqualTo(Integer value) {
            addCriterion("publish_state <>", value, "publishState");
            return (Criteria) this;
        }

        public Criteria andPublishStateGreaterThan(Integer value) {
            addCriterion("publish_state >", value, "publishState");
            return (Criteria) this;
        }

        public Criteria andPublishStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("publish_state >=", value, "publishState");
            return (Criteria) this;
        }

        public Criteria andPublishStateLessThan(Integer value) {
            addCriterion("publish_state <", value, "publishState");
            return (Criteria) this;
        }

        public Criteria andPublishStateLessThanOrEqualTo(Integer value) {
            addCriterion("publish_state <=", value, "publishState");
            return (Criteria) this;
        }

        public Criteria andPublishStateIn(List<Integer> values) {
            addCriterion("publish_state in", values, "publishState");
            return (Criteria) this;
        }

        public Criteria andPublishStateNotIn(List<Integer> values) {
            addCriterion("publish_state not in", values, "publishState");
            return (Criteria) this;
        }

        public Criteria andPublishStateBetween(Integer value1, Integer value2) {
            addCriterion("publish_state between", value1, value2, "publishState");
            return (Criteria) this;
        }

        public Criteria andPublishStateNotBetween(Integer value1, Integer value2) {
            addCriterion("publish_state not between", value1, value2, "publishState");
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

        public Criteria andLawsuitStateIsNull() {
            addCriterion("lawsuit_state is null");
            return (Criteria) this;
        }

        public Criteria andLawsuitStateIsNotNull() {
            addCriterion("lawsuit_state is not null");
            return (Criteria) this;
        }

        public Criteria andLawsuitStateEqualTo(Integer value) {
            addCriterion("lawsuit_state =", value, "lawsuitState");
            return (Criteria) this;
        }

        public Criteria andLawsuitStateNotEqualTo(Integer value) {
            addCriterion("lawsuit_state <>", value, "lawsuitState");
            return (Criteria) this;
        }

        public Criteria andLawsuitStateGreaterThan(Integer value) {
            addCriterion("lawsuit_state >", value, "lawsuitState");
            return (Criteria) this;
        }

        public Criteria andLawsuitStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("lawsuit_state >=", value, "lawsuitState");
            return (Criteria) this;
        }

        public Criteria andLawsuitStateLessThan(Integer value) {
            addCriterion("lawsuit_state <", value, "lawsuitState");
            return (Criteria) this;
        }

        public Criteria andLawsuitStateLessThanOrEqualTo(Integer value) {
            addCriterion("lawsuit_state <=", value, "lawsuitState");
            return (Criteria) this;
        }

        public Criteria andLawsuitStateIn(List<Integer> values) {
            addCriterion("lawsuit_state in", values, "lawsuitState");
            return (Criteria) this;
        }

        public Criteria andLawsuitStateNotIn(List<Integer> values) {
            addCriterion("lawsuit_state not in", values, "lawsuitState");
            return (Criteria) this;
        }

        public Criteria andLawsuitStateBetween(Integer value1, Integer value2) {
            addCriterion("lawsuit_state between", value1, value2, "lawsuitState");
            return (Criteria) this;
        }

        public Criteria andLawsuitStateNotBetween(Integer value1, Integer value2) {
            addCriterion("lawsuit_state not between", value1, value2, "lawsuitState");
            return (Criteria) this;
        }

        public Criteria andLawsuitStateDescIsNull() {
            addCriterion("lawsuit_state_desc is null");
            return (Criteria) this;
        }

        public Criteria andLawsuitStateDescIsNotNull() {
            addCriterion("lawsuit_state_desc is not null");
            return (Criteria) this;
        }

        public Criteria andLawsuitStateDescEqualTo(String value) {
            addCriterion("lawsuit_state_desc =", value, "lawsuitStateDesc");
            return (Criteria) this;
        }

        public Criteria andLawsuitStateDescNotEqualTo(String value) {
            addCriterion("lawsuit_state_desc <>", value, "lawsuitStateDesc");
            return (Criteria) this;
        }

        public Criteria andLawsuitStateDescGreaterThan(String value) {
            addCriterion("lawsuit_state_desc >", value, "lawsuitStateDesc");
            return (Criteria) this;
        }

        public Criteria andLawsuitStateDescGreaterThanOrEqualTo(String value) {
            addCriterion("lawsuit_state_desc >=", value, "lawsuitStateDesc");
            return (Criteria) this;
        }

        public Criteria andLawsuitStateDescLessThan(String value) {
            addCriterion("lawsuit_state_desc <", value, "lawsuitStateDesc");
            return (Criteria) this;
        }

        public Criteria andLawsuitStateDescLessThanOrEqualTo(String value) {
            addCriterion("lawsuit_state_desc <=", value, "lawsuitStateDesc");
            return (Criteria) this;
        }

        public Criteria andLawsuitStateDescLike(String value) {
            addCriterion("lawsuit_state_desc like", value, "lawsuitStateDesc");
            return (Criteria) this;
        }

        public Criteria andLawsuitStateDescNotLike(String value) {
            addCriterion("lawsuit_state_desc not like", value, "lawsuitStateDesc");
            return (Criteria) this;
        }

        public Criteria andLawsuitStateDescIn(List<String> values) {
            addCriterion("lawsuit_state_desc in", values, "lawsuitStateDesc");
            return (Criteria) this;
        }

        public Criteria andLawsuitStateDescNotIn(List<String> values) {
            addCriterion("lawsuit_state_desc not in", values, "lawsuitStateDesc");
            return (Criteria) this;
        }

        public Criteria andLawsuitStateDescBetween(String value1, String value2) {
            addCriterion("lawsuit_state_desc between", value1, value2, "lawsuitStateDesc");
            return (Criteria) this;
        }

        public Criteria andLawsuitStateDescNotBetween(String value1, String value2) {
            addCriterion("lawsuit_state_desc not between", value1, value2, "lawsuitStateDesc");
            return (Criteria) this;
        }

        public Criteria andValuationIsNull() {
            addCriterion("valuation is null");
            return (Criteria) this;
        }

        public Criteria andValuationIsNotNull() {
            addCriterion("valuation is not null");
            return (Criteria) this;
        }

        public Criteria andValuationEqualTo(Long value) {
            addCriterion("valuation =", value, "valuation");
            return (Criteria) this;
        }

        public Criteria andValuationNotEqualTo(Long value) {
            addCriterion("valuation <>", value, "valuation");
            return (Criteria) this;
        }

        public Criteria andValuationGreaterThan(Long value) {
            addCriterion("valuation >", value, "valuation");
            return (Criteria) this;
        }

        public Criteria andValuationGreaterThanOrEqualTo(Long value) {
            addCriterion("valuation >=", value, "valuation");
            return (Criteria) this;
        }

        public Criteria andValuationLessThan(Long value) {
            addCriterion("valuation <", value, "valuation");
            return (Criteria) this;
        }

        public Criteria andValuationLessThanOrEqualTo(Long value) {
            addCriterion("valuation <=", value, "valuation");
            return (Criteria) this;
        }

        public Criteria andValuationIn(List<Long> values) {
            addCriterion("valuation in", values, "valuation");
            return (Criteria) this;
        }

        public Criteria andValuationNotIn(List<Long> values) {
            addCriterion("valuation not in", values, "valuation");
            return (Criteria) this;
        }

        public Criteria andValuationBetween(Long value1, Long value2) {
            addCriterion("valuation between", value1, value2, "valuation");
            return (Criteria) this;
        }

        public Criteria andValuationNotBetween(Long value1, Long value2) {
            addCriterion("valuation not between", value1, value2, "valuation");
            return (Criteria) this;
        }

        public Criteria andAmcContactorIdIsNull() {
            addCriterion("amc_contactor_id is null");
            return (Criteria) this;
        }

        public Criteria andAmcContactorIdIsNotNull() {
            addCriterion("amc_contactor_id is not null");
            return (Criteria) this;
        }

        public Criteria andAmcContactorIdEqualTo(Long value) {
            addCriterion("amc_contactor_id =", value, "amcContactorId");
            return (Criteria) this;
        }

        public Criteria andAmcContactorIdNotEqualTo(Long value) {
            addCriterion("amc_contactor_id <>", value, "amcContactorId");
            return (Criteria) this;
        }

        public Criteria andAmcContactorIdGreaterThan(Long value) {
            addCriterion("amc_contactor_id >", value, "amcContactorId");
            return (Criteria) this;
        }

        public Criteria andAmcContactorIdGreaterThanOrEqualTo(Long value) {
            addCriterion("amc_contactor_id >=", value, "amcContactorId");
            return (Criteria) this;
        }

        public Criteria andAmcContactorIdLessThan(Long value) {
            addCriterion("amc_contactor_id <", value, "amcContactorId");
            return (Criteria) this;
        }

        public Criteria andAmcContactorIdLessThanOrEqualTo(Long value) {
            addCriterion("amc_contactor_id <=", value, "amcContactorId");
            return (Criteria) this;
        }

        public Criteria andAmcContactorIdIn(List<Long> values) {
            addCriterion("amc_contactor_id in", values, "amcContactorId");
            return (Criteria) this;
        }

        public Criteria andAmcContactorIdNotIn(List<Long> values) {
            addCriterion("amc_contactor_id not in", values, "amcContactorId");
            return (Criteria) this;
        }

        public Criteria andAmcContactorIdBetween(Long value1, Long value2) {
            addCriterion("amc_contactor_id between", value1, value2, "amcContactorId");
            return (Criteria) this;
        }

        public Criteria andAmcContactorIdNotBetween(Long value1, Long value2) {
            addCriterion("amc_contactor_id not between", value1, value2, "amcContactorId");
            return (Criteria) this;
        }

        public Criteria andAmcContactorNameIsNull() {
            addCriterion("amc_contactor_name is null");
            return (Criteria) this;
        }

        public Criteria andAmcContactorNameIsNotNull() {
            addCriterion("amc_contactor_name is not null");
            return (Criteria) this;
        }

        public Criteria andAmcContactorNameEqualTo(String value) {
            addCriterion("amc_contactor_name =", value, "amcContactorName");
            return (Criteria) this;
        }

        public Criteria andAmcContactorNameNotEqualTo(String value) {
            addCriterion("amc_contactor_name <>", value, "amcContactorName");
            return (Criteria) this;
        }

        public Criteria andAmcContactorNameGreaterThan(String value) {
            addCriterion("amc_contactor_name >", value, "amcContactorName");
            return (Criteria) this;
        }

        public Criteria andAmcContactorNameGreaterThanOrEqualTo(String value) {
            addCriterion("amc_contactor_name >=", value, "amcContactorName");
            return (Criteria) this;
        }

        public Criteria andAmcContactorNameLessThan(String value) {
            addCriterion("amc_contactor_name <", value, "amcContactorName");
            return (Criteria) this;
        }

        public Criteria andAmcContactorNameLessThanOrEqualTo(String value) {
            addCriterion("amc_contactor_name <=", value, "amcContactorName");
            return (Criteria) this;
        }

        public Criteria andAmcContactorNameLike(String value) {
            addCriterion("amc_contactor_name like", value, "amcContactorName");
            return (Criteria) this;
        }

        public Criteria andAmcContactorNameNotLike(String value) {
            addCriterion("amc_contactor_name not like", value, "amcContactorName");
            return (Criteria) this;
        }

        public Criteria andAmcContactorNameIn(List<String> values) {
            addCriterion("amc_contactor_name in", values, "amcContactorName");
            return (Criteria) this;
        }

        public Criteria andAmcContactorNameNotIn(List<String> values) {
            addCriterion("amc_contactor_name not in", values, "amcContactorName");
            return (Criteria) this;
        }

        public Criteria andAmcContactorNameBetween(String value1, String value2) {
            addCriterion("amc_contactor_name between", value1, value2, "amcContactorName");
            return (Criteria) this;
        }

        public Criteria andAmcContactorNameNotBetween(String value1, String value2) {
            addCriterion("amc_contactor_name not between", value1, value2, "amcContactorName");
            return (Criteria) this;
        }

        public Criteria andAmcContactorPhoneIsNull() {
            addCriterion("amc_contactor_phone is null");
            return (Criteria) this;
        }

        public Criteria andAmcContactorPhoneIsNotNull() {
            addCriterion("amc_contactor_phone is not null");
            return (Criteria) this;
        }

        public Criteria andAmcContactorPhoneEqualTo(String value) {
            addCriterion("amc_contactor_phone =", value, "amcContactorPhone");
            return (Criteria) this;
        }

        public Criteria andAmcContactorPhoneNotEqualTo(String value) {
            addCriterion("amc_contactor_phone <>", value, "amcContactorPhone");
            return (Criteria) this;
        }

        public Criteria andAmcContactorPhoneGreaterThan(String value) {
            addCriterion("amc_contactor_phone >", value, "amcContactorPhone");
            return (Criteria) this;
        }

        public Criteria andAmcContactorPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("amc_contactor_phone >=", value, "amcContactorPhone");
            return (Criteria) this;
        }

        public Criteria andAmcContactorPhoneLessThan(String value) {
            addCriterion("amc_contactor_phone <", value, "amcContactorPhone");
            return (Criteria) this;
        }

        public Criteria andAmcContactorPhoneLessThanOrEqualTo(String value) {
            addCriterion("amc_contactor_phone <=", value, "amcContactorPhone");
            return (Criteria) this;
        }

        public Criteria andAmcContactorPhoneLike(String value) {
            addCriterion("amc_contactor_phone like", value, "amcContactorPhone");
            return (Criteria) this;
        }

        public Criteria andAmcContactorPhoneNotLike(String value) {
            addCriterion("amc_contactor_phone not like", value, "amcContactorPhone");
            return (Criteria) this;
        }

        public Criteria andAmcContactorPhoneIn(List<String> values) {
            addCriterion("amc_contactor_phone in", values, "amcContactorPhone");
            return (Criteria) this;
        }

        public Criteria andAmcContactorPhoneNotIn(List<String> values) {
            addCriterion("amc_contactor_phone not in", values, "amcContactorPhone");
            return (Criteria) this;
        }

        public Criteria andAmcContactorPhoneBetween(String value1, String value2) {
            addCriterion("amc_contactor_phone between", value1, value2, "amcContactorPhone");
            return (Criteria) this;
        }

        public Criteria andAmcContactorPhoneNotBetween(String value1, String value2) {
            addCriterion("amc_contactor_phone not between", value1, value2, "amcContactorPhone");
            return (Criteria) this;
        }

        public Criteria andAmcContactor2IdIsNull() {
            addCriterion("amc_contactor2_id is null");
            return (Criteria) this;
        }

        public Criteria andAmcContactor2IdIsNotNull() {
            addCriterion("amc_contactor2_id is not null");
            return (Criteria) this;
        }

        public Criteria andAmcContactor2IdEqualTo(Long value) {
            addCriterion("amc_contactor2_id =", value, "amcContactor2Id");
            return (Criteria) this;
        }

        public Criteria andAmcContactor2IdNotEqualTo(Long value) {
            addCriterion("amc_contactor2_id <>", value, "amcContactor2Id");
            return (Criteria) this;
        }

        public Criteria andAmcContactor2IdGreaterThan(Long value) {
            addCriterion("amc_contactor2_id >", value, "amcContactor2Id");
            return (Criteria) this;
        }

        public Criteria andAmcContactor2IdGreaterThanOrEqualTo(Long value) {
            addCriterion("amc_contactor2_id >=", value, "amcContactor2Id");
            return (Criteria) this;
        }

        public Criteria andAmcContactor2IdLessThan(Long value) {
            addCriterion("amc_contactor2_id <", value, "amcContactor2Id");
            return (Criteria) this;
        }

        public Criteria andAmcContactor2IdLessThanOrEqualTo(Long value) {
            addCriterion("amc_contactor2_id <=", value, "amcContactor2Id");
            return (Criteria) this;
        }

        public Criteria andAmcContactor2IdIn(List<Long> values) {
            addCriterion("amc_contactor2_id in", values, "amcContactor2Id");
            return (Criteria) this;
        }

        public Criteria andAmcContactor2IdNotIn(List<Long> values) {
            addCriterion("amc_contactor2_id not in", values, "amcContactor2Id");
            return (Criteria) this;
        }

        public Criteria andAmcContactor2IdBetween(Long value1, Long value2) {
            addCriterion("amc_contactor2_id between", value1, value2, "amcContactor2Id");
            return (Criteria) this;
        }

        public Criteria andAmcContactor2IdNotBetween(Long value1, Long value2) {
            addCriterion("amc_contactor2_id not between", value1, value2, "amcContactor2Id");
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

        public Criteria andGuarantTypeIsNull() {
            addCriterion("guarant_type is null");
            return (Criteria) this;
        }

        public Criteria andGuarantTypeIsNotNull() {
            addCriterion("guarant_type is not null");
            return (Criteria) this;
        }

        public Criteria andGuarantTypeEqualTo(Integer value) {
            addCriterion("guarant_type =", value, "guarantType");
            return (Criteria) this;
        }

        public Criteria andGuarantTypeNotEqualTo(Integer value) {
            addCriterion("guarant_type <>", value, "guarantType");
            return (Criteria) this;
        }

        public Criteria andGuarantTypeGreaterThan(Integer value) {
            addCriterion("guarant_type >", value, "guarantType");
            return (Criteria) this;
        }

        public Criteria andGuarantTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("guarant_type >=", value, "guarantType");
            return (Criteria) this;
        }

        public Criteria andGuarantTypeLessThan(Integer value) {
            addCriterion("guarant_type <", value, "guarantType");
            return (Criteria) this;
        }

        public Criteria andGuarantTypeLessThanOrEqualTo(Integer value) {
            addCriterion("guarant_type <=", value, "guarantType");
            return (Criteria) this;
        }

        public Criteria andGuarantTypeIn(List<Integer> values) {
            addCriterion("guarant_type in", values, "guarantType");
            return (Criteria) this;
        }

        public Criteria andGuarantTypeNotIn(List<Integer> values) {
            addCriterion("guarant_type not in", values, "guarantType");
            return (Criteria) this;
        }

        public Criteria andGuarantTypeBetween(Integer value1, Integer value2) {
            addCriterion("guarant_type between", value1, value2, "guarantType");
            return (Criteria) this;
        }

        public Criteria andGuarantTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("guarant_type not between", value1, value2, "guarantType");
            return (Criteria) this;
        }

        public Criteria andRecommStartDateIsNull() {
            addCriterion("recomm_start_date is null");
            return (Criteria) this;
        }

        public Criteria andRecommStartDateIsNotNull() {
            addCriterion("recomm_start_date is not null");
            return (Criteria) this;
        }

        public Criteria andRecommStartDateEqualTo(Date value) {
            addCriterion("recomm_start_date =", value, "recommStartDate");
            return (Criteria) this;
        }

        public Criteria andRecommStartDateNotEqualTo(Date value) {
            addCriterion("recomm_start_date <>", value, "recommStartDate");
            return (Criteria) this;
        }

        public Criteria andRecommStartDateGreaterThan(Date value) {
            addCriterion("recomm_start_date >", value, "recommStartDate");
            return (Criteria) this;
        }

        public Criteria andRecommStartDateGreaterThanOrEqualTo(Date value) {
            addCriterion("recomm_start_date >=", value, "recommStartDate");
            return (Criteria) this;
        }

        public Criteria andRecommStartDateLessThan(Date value) {
            addCriterion("recomm_start_date <", value, "recommStartDate");
            return (Criteria) this;
        }

        public Criteria andRecommStartDateLessThanOrEqualTo(Date value) {
            addCriterion("recomm_start_date <=", value, "recommStartDate");
            return (Criteria) this;
        }

        public Criteria andRecommStartDateIn(List<Date> values) {
            addCriterion("recomm_start_date in", values, "recommStartDate");
            return (Criteria) this;
        }

        public Criteria andRecommStartDateNotIn(List<Date> values) {
            addCriterion("recomm_start_date not in", values, "recommStartDate");
            return (Criteria) this;
        }

        public Criteria andRecommStartDateBetween(Date value1, Date value2) {
            addCriterion("recomm_start_date between", value1, value2, "recommStartDate");
            return (Criteria) this;
        }

        public Criteria andRecommStartDateNotBetween(Date value1, Date value2) {
            addCriterion("recomm_start_date not between", value1, value2, "recommStartDate");
            return (Criteria) this;
        }

        public Criteria andRecommEndDateIsNull() {
            addCriterion("recomm_end_date is null");
            return (Criteria) this;
        }

        public Criteria andRecommEndDateIsNotNull() {
            addCriterion("recomm_end_date is not null");
            return (Criteria) this;
        }

        public Criteria andRecommEndDateEqualTo(Date value) {
            addCriterion("recomm_end_date =", value, "recommEndDate");
            return (Criteria) this;
        }

        public Criteria andRecommEndDateNotEqualTo(Date value) {
            addCriterion("recomm_end_date <>", value, "recommEndDate");
            return (Criteria) this;
        }

        public Criteria andRecommEndDateGreaterThan(Date value) {
            addCriterion("recomm_end_date >", value, "recommEndDate");
            return (Criteria) this;
        }

        public Criteria andRecommEndDateGreaterThanOrEqualTo(Date value) {
            addCriterion("recomm_end_date >=", value, "recommEndDate");
            return (Criteria) this;
        }

        public Criteria andRecommEndDateLessThan(Date value) {
            addCriterion("recomm_end_date <", value, "recommEndDate");
            return (Criteria) this;
        }

        public Criteria andRecommEndDateLessThanOrEqualTo(Date value) {
            addCriterion("recomm_end_date <=", value, "recommEndDate");
            return (Criteria) this;
        }

        public Criteria andRecommEndDateIn(List<Date> values) {
            addCriterion("recomm_end_date in", values, "recommEndDate");
            return (Criteria) this;
        }

        public Criteria andRecommEndDateNotIn(List<Date> values) {
            addCriterion("recomm_end_date not in", values, "recommEndDate");
            return (Criteria) this;
        }

        public Criteria andRecommEndDateBetween(Date value1, Date value2) {
            addCriterion("recomm_end_date between", value1, value2, "recommEndDate");
            return (Criteria) this;
        }

        public Criteria andRecommEndDateNotBetween(Date value1, Date value2) {
            addCriterion("recomm_end_date not between", value1, value2, "recommEndDate");
            return (Criteria) this;
        }

        public Criteria andOrigCreditorIdIsNull() {
            addCriterion("orig_creditor_id is null");
            return (Criteria) this;
        }

        public Criteria andOrigCreditorIdIsNotNull() {
            addCriterion("orig_creditor_id is not null");
            return (Criteria) this;
        }

        public Criteria andOrigCreditorIdEqualTo(Long value) {
            addCriterion("orig_creditor_id =", value, "origCreditorId");
            return (Criteria) this;
        }

        public Criteria andOrigCreditorIdNotEqualTo(Long value) {
            addCriterion("orig_creditor_id <>", value, "origCreditorId");
            return (Criteria) this;
        }

        public Criteria andOrigCreditorIdGreaterThan(Long value) {
            addCriterion("orig_creditor_id >", value, "origCreditorId");
            return (Criteria) this;
        }

        public Criteria andOrigCreditorIdGreaterThanOrEqualTo(Long value) {
            addCriterion("orig_creditor_id >=", value, "origCreditorId");
            return (Criteria) this;
        }

        public Criteria andOrigCreditorIdLessThan(Long value) {
            addCriterion("orig_creditor_id <", value, "origCreditorId");
            return (Criteria) this;
        }

        public Criteria andOrigCreditorIdLessThanOrEqualTo(Long value) {
            addCriterion("orig_creditor_id <=", value, "origCreditorId");
            return (Criteria) this;
        }

        public Criteria andOrigCreditorIdIn(List<Long> values) {
            addCriterion("orig_creditor_id in", values, "origCreditorId");
            return (Criteria) this;
        }

        public Criteria andOrigCreditorIdNotIn(List<Long> values) {
            addCriterion("orig_creditor_id not in", values, "origCreditorId");
            return (Criteria) this;
        }

        public Criteria andOrigCreditorIdBetween(Long value1, Long value2) {
            addCriterion("orig_creditor_id between", value1, value2, "origCreditorId");
            return (Criteria) this;
        }

        public Criteria andOrigCreditorIdNotBetween(Long value1, Long value2) {
            addCriterion("orig_creditor_id not between", value1, value2, "origCreditorId");
            return (Criteria) this;
        }

        public Criteria andOrigCreditorNameIsNull() {
            addCriterion("orig_creditor_name is null");
            return (Criteria) this;
        }

        public Criteria andOrigCreditorNameIsNotNull() {
            addCriterion("orig_creditor_name is not null");
            return (Criteria) this;
        }

        public Criteria andOrigCreditorNameEqualTo(String value) {
            addCriterion("orig_creditor_name =", value, "origCreditorName");
            return (Criteria) this;
        }

        public Criteria andOrigCreditorNameNotEqualTo(String value) {
            addCriterion("orig_creditor_name <>", value, "origCreditorName");
            return (Criteria) this;
        }

        public Criteria andOrigCreditorNameGreaterThan(String value) {
            addCriterion("orig_creditor_name >", value, "origCreditorName");
            return (Criteria) this;
        }

        public Criteria andOrigCreditorNameGreaterThanOrEqualTo(String value) {
            addCriterion("orig_creditor_name >=", value, "origCreditorName");
            return (Criteria) this;
        }

        public Criteria andOrigCreditorNameLessThan(String value) {
            addCriterion("orig_creditor_name <", value, "origCreditorName");
            return (Criteria) this;
        }

        public Criteria andOrigCreditorNameLessThanOrEqualTo(String value) {
            addCriterion("orig_creditor_name <=", value, "origCreditorName");
            return (Criteria) this;
        }

        public Criteria andOrigCreditorNameLike(String value) {
            addCriterion("orig_creditor_name like", value, "origCreditorName");
            return (Criteria) this;
        }

        public Criteria andOrigCreditorNameNotLike(String value) {
            addCriterion("orig_creditor_name not like", value, "origCreditorName");
            return (Criteria) this;
        }

        public Criteria andOrigCreditorNameIn(List<String> values) {
            addCriterion("orig_creditor_name in", values, "origCreditorName");
            return (Criteria) this;
        }

        public Criteria andOrigCreditorNameNotIn(List<String> values) {
            addCriterion("orig_creditor_name not in", values, "origCreditorName");
            return (Criteria) this;
        }

        public Criteria andOrigCreditorNameBetween(String value1, String value2) {
            addCriterion("orig_creditor_name between", value1, value2, "origCreditorName");
            return (Criteria) this;
        }

        public Criteria andOrigCreditorNameNotBetween(String value1, String value2) {
            addCriterion("orig_creditor_name not between", value1, value2, "origCreditorName");
            return (Criteria) this;
        }

        public Criteria andDebtDescIsNull() {
            addCriterion("debt_desc is null");
            return (Criteria) this;
        }

        public Criteria andDebtDescIsNotNull() {
            addCriterion("debt_desc is not null");
            return (Criteria) this;
        }

        public Criteria andDebtDescEqualTo(String value) {
            addCriterion("debt_desc =", value, "debtDesc");
            return (Criteria) this;
        }

        public Criteria andDebtDescNotEqualTo(String value) {
            addCriterion("debt_desc <>", value, "debtDesc");
            return (Criteria) this;
        }

        public Criteria andDebtDescGreaterThan(String value) {
            addCriterion("debt_desc >", value, "debtDesc");
            return (Criteria) this;
        }

        public Criteria andDebtDescGreaterThanOrEqualTo(String value) {
            addCriterion("debt_desc >=", value, "debtDesc");
            return (Criteria) this;
        }

        public Criteria andDebtDescLessThan(String value) {
            addCriterion("debt_desc <", value, "debtDesc");
            return (Criteria) this;
        }

        public Criteria andDebtDescLessThanOrEqualTo(String value) {
            addCriterion("debt_desc <=", value, "debtDesc");
            return (Criteria) this;
        }

        public Criteria andDebtDescLike(String value) {
            addCriterion("debt_desc like", value, "debtDesc");
            return (Criteria) this;
        }

        public Criteria andDebtDescNotLike(String value) {
            addCriterion("debt_desc not like", value, "debtDesc");
            return (Criteria) this;
        }

        public Criteria andDebtDescIn(List<String> values) {
            addCriterion("debt_desc in", values, "debtDesc");
            return (Criteria) this;
        }

        public Criteria andDebtDescNotIn(List<String> values) {
            addCriterion("debt_desc not in", values, "debtDesc");
            return (Criteria) this;
        }

        public Criteria andDebtDescBetween(String value1, String value2) {
            addCriterion("debt_desc between", value1, value2, "debtDesc");
            return (Criteria) this;
        }

        public Criteria andDebtDescNotBetween(String value1, String value2) {
            addCriterion("debt_desc not between", value1, value2, "debtDesc");
            return (Criteria) this;
        }

        public Criteria andUpdateByIsNull() {
            addCriterion("update_by is null");
            return (Criteria) this;
        }

        public Criteria andUpdateByIsNotNull() {
            addCriterion("update_by is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateByEqualTo(Long value) {
            addCriterion("update_by =", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotEqualTo(Long value) {
            addCriterion("update_by <>", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByGreaterThan(Long value) {
            addCriterion("update_by >", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByGreaterThanOrEqualTo(Long value) {
            addCriterion("update_by >=", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByLessThan(Long value) {
            addCriterion("update_by <", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByLessThanOrEqualTo(Long value) {
            addCriterion("update_by <=", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByIn(List<Long> values) {
            addCriterion("update_by in", values, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotIn(List<Long> values) {
            addCriterion("update_by not in", values, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByBetween(Long value1, Long value2) {
            addCriterion("update_by between", value1, value2, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotBetween(Long value1, Long value2) {
            addCriterion("update_by not between", value1, value2, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIsNull() {
            addCriterion("update_date is null");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIsNotNull() {
            addCriterion("update_date is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateDateEqualTo(Date value) {
            addCriterion("update_date =", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotEqualTo(Date value) {
            addCriterion("update_date <>", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateGreaterThan(Date value) {
            addCriterion("update_date >", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("update_date >=", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateLessThan(Date value) {
            addCriterion("update_date <", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateLessThanOrEqualTo(Date value) {
            addCriterion("update_date <=", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIn(List<Date> values) {
            addCriterion("update_date in", values, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotIn(List<Date> values) {
            addCriterion("update_date not in", values, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateBetween(Date value1, Date value2) {
            addCriterion("update_date between", value1, value2, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotBetween(Date value1, Date value2) {
            addCriterion("update_date not between", value1, value2, "updateDate");
            return (Criteria) this;
        }

        public Criteria andCreatedByIsNull() {
            addCriterion("created_by is null");
            return (Criteria) this;
        }

        public Criteria andCreatedByIsNotNull() {
            addCriterion("created_by is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedByEqualTo(Long value) {
            addCriterion("created_by =", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByNotEqualTo(Long value) {
            addCriterion("created_by <>", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByGreaterThan(Long value) {
            addCriterion("created_by >", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByGreaterThanOrEqualTo(Long value) {
            addCriterion("created_by >=", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByLessThan(Long value) {
            addCriterion("created_by <", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByLessThanOrEqualTo(Long value) {
            addCriterion("created_by <=", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByIn(List<Long> values) {
            addCriterion("created_by in", values, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByNotIn(List<Long> values) {
            addCriterion("created_by not in", values, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByBetween(Long value1, Long value2) {
            addCriterion("created_by between", value1, value2, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByNotBetween(Long value1, Long value2) {
            addCriterion("created_by not between", value1, value2, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedDateIsNull() {
            addCriterion("created_date is null");
            return (Criteria) this;
        }

        public Criteria andCreatedDateIsNotNull() {
            addCriterion("created_date is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedDateEqualTo(Date value) {
            addCriterion("created_date =", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateNotEqualTo(Date value) {
            addCriterion("created_date <>", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateGreaterThan(Date value) {
            addCriterion("created_date >", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateGreaterThanOrEqualTo(Date value) {
            addCriterion("created_date >=", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateLessThan(Date value) {
            addCriterion("created_date <", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateLessThanOrEqualTo(Date value) {
            addCriterion("created_date <=", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateIn(List<Date> values) {
            addCriterion("created_date in", values, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateNotIn(List<Date> values) {
            addCriterion("created_date not in", values, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateBetween(Date value1, Date value2) {
            addCriterion("created_date between", value1, value2, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateNotBetween(Date value1, Date value2) {
            addCriterion("created_date not between", value1, value2, "createdDate");
            return (Criteria) this;
        }

        public Criteria andRowNumIsNull() {
            addCriterion("row_num is null");
            return (Criteria) this;
        }

        public Criteria andRowNumIsNotNull() {
            addCriterion("row_num is not null");
            return (Criteria) this;
        }

        public Criteria andRowNumEqualTo(Integer value) {
            addCriterion("row_num =", value, "rowNum");
            return (Criteria) this;
        }

        public Criteria andRowNumNotEqualTo(Integer value) {
            addCriterion("row_num <>", value, "rowNum");
            return (Criteria) this;
        }

        public Criteria andRowNumGreaterThan(Integer value) {
            addCriterion("row_num >", value, "rowNum");
            return (Criteria) this;
        }

        public Criteria andRowNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("row_num >=", value, "rowNum");
            return (Criteria) this;
        }

        public Criteria andRowNumLessThan(Integer value) {
            addCriterion("row_num <", value, "rowNum");
            return (Criteria) this;
        }

        public Criteria andRowNumLessThanOrEqualTo(Integer value) {
            addCriterion("row_num <=", value, "rowNum");
            return (Criteria) this;
        }

        public Criteria andRowNumIn(List<Integer> values) {
            addCriterion("row_num in", values, "rowNum");
            return (Criteria) this;
        }

        public Criteria andRowNumNotIn(List<Integer> values) {
            addCriterion("row_num not in", values, "rowNum");
            return (Criteria) this;
        }

        public Criteria andRowNumBetween(Integer value1, Integer value2) {
            addCriterion("row_num between", value1, value2, "rowNum");
            return (Criteria) this;
        }

        public Criteria andRowNumNotBetween(Integer value1, Integer value2) {
            addCriterion("row_num not between", value1, value2, "rowNum");
            return (Criteria) this;
        }

        public Criteria andDebtClueIsNull() {
            addCriterion("debt_clue is null");
            return (Criteria) this;
        }

        public Criteria andDebtClueIsNotNull() {
            addCriterion("debt_clue is not null");
            return (Criteria) this;
        }

        public Criteria andDebtClueEqualTo(String value) {
            addCriterion("debt_clue =", value, "debtClue");
            return (Criteria) this;
        }

        public Criteria andDebtClueNotEqualTo(String value) {
            addCriterion("debt_clue <>", value, "debtClue");
            return (Criteria) this;
        }

        public Criteria andDebtClueGreaterThan(String value) {
            addCriterion("debt_clue >", value, "debtClue");
            return (Criteria) this;
        }

        public Criteria andDebtClueGreaterThanOrEqualTo(String value) {
            addCriterion("debt_clue >=", value, "debtClue");
            return (Criteria) this;
        }

        public Criteria andDebtClueLessThan(String value) {
            addCriterion("debt_clue <", value, "debtClue");
            return (Criteria) this;
        }

        public Criteria andDebtClueLessThanOrEqualTo(String value) {
            addCriterion("debt_clue <=", value, "debtClue");
            return (Criteria) this;
        }

        public Criteria andDebtClueLike(String value) {
            addCriterion("debt_clue like", value, "debtClue");
            return (Criteria) this;
        }

        public Criteria andDebtClueNotLike(String value) {
            addCriterion("debt_clue not like", value, "debtClue");
            return (Criteria) this;
        }

        public Criteria andDebtClueIn(List<String> values) {
            addCriterion("debt_clue in", values, "debtClue");
            return (Criteria) this;
        }

        public Criteria andDebtClueNotIn(List<String> values) {
            addCriterion("debt_clue not in", values, "debtClue");
            return (Criteria) this;
        }

        public Criteria andDebtClueBetween(String value1, String value2) {
            addCriterion("debt_clue between", value1, value2, "debtClue");
            return (Criteria) this;
        }

        public Criteria andDebtClueNotBetween(String value1, String value2) {
            addCriterion("debt_clue not between", value1, value2, "debtClue");
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