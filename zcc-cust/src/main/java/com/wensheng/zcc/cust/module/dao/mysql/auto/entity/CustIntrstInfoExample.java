package com.wensheng.zcc.cust.module.dao.mysql.auto.entity;

import java.util.ArrayList;
import java.util.List;

public class CustIntrstInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CustIntrstInfoExample() {
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

        public Criteria andCustIdIsNull() {
            addCriterion("cust_id is null");
            return (Criteria) this;
        }

        public Criteria andCustIdIsNotNull() {
            addCriterion("cust_id is not null");
            return (Criteria) this;
        }

        public Criteria andCustIdEqualTo(Long value) {
            addCriterion("cust_id =", value, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdNotEqualTo(Long value) {
            addCriterion("cust_id <>", value, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdGreaterThan(Long value) {
            addCriterion("cust_id >", value, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdGreaterThanOrEqualTo(Long value) {
            addCriterion("cust_id >=", value, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdLessThan(Long value) {
            addCriterion("cust_id <", value, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdLessThanOrEqualTo(Long value) {
            addCriterion("cust_id <=", value, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdIn(List<Long> values) {
            addCriterion("cust_id in", values, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdNotIn(List<Long> values) {
            addCriterion("cust_id not in", values, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdBetween(Long value1, Long value2) {
            addCriterion("cust_id between", value1, value2, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdNotBetween(Long value1, Long value2) {
            addCriterion("cust_id not between", value1, value2, "custId");
            return (Criteria) this;
        }

        public Criteria andCustTypeIsNull() {
            addCriterion("cust_type is null");
            return (Criteria) this;
        }

        public Criteria andCustTypeIsNotNull() {
            addCriterion("cust_type is not null");
            return (Criteria) this;
        }

        public Criteria andCustTypeEqualTo(Byte value) {
            addCriterion("cust_type =", value, "custType");
            return (Criteria) this;
        }

        public Criteria andCustTypeNotEqualTo(Byte value) {
            addCriterion("cust_type <>", value, "custType");
            return (Criteria) this;
        }

        public Criteria andCustTypeGreaterThan(Byte value) {
            addCriterion("cust_type >", value, "custType");
            return (Criteria) this;
        }

        public Criteria andCustTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("cust_type >=", value, "custType");
            return (Criteria) this;
        }

        public Criteria andCustTypeLessThan(Byte value) {
            addCriterion("cust_type <", value, "custType");
            return (Criteria) this;
        }

        public Criteria andCustTypeLessThanOrEqualTo(Byte value) {
            addCriterion("cust_type <=", value, "custType");
            return (Criteria) this;
        }

        public Criteria andCustTypeIn(List<Byte> values) {
            addCriterion("cust_type in", values, "custType");
            return (Criteria) this;
        }

        public Criteria andCustTypeNotIn(List<Byte> values) {
            addCriterion("cust_type not in", values, "custType");
            return (Criteria) this;
        }

        public Criteria andCustTypeBetween(Byte value1, Byte value2) {
            addCriterion("cust_type between", value1, value2, "custType");
            return (Criteria) this;
        }

        public Criteria andCustTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("cust_type not between", value1, value2, "custType");
            return (Criteria) this;
        }

        public Criteria andIntrstCityIsNull() {
            addCriterion("intrst_city is null");
            return (Criteria) this;
        }

        public Criteria andIntrstCityIsNotNull() {
            addCriterion("intrst_city is not null");
            return (Criteria) this;
        }

        public Criteria andIntrstCityEqualTo(String value) {
            addCriterion("intrst_city =", value, "intrstCity");
            return (Criteria) this;
        }

        public Criteria andIntrstCityNotEqualTo(String value) {
            addCriterion("intrst_city <>", value, "intrstCity");
            return (Criteria) this;
        }

        public Criteria andIntrstCityGreaterThan(String value) {
            addCriterion("intrst_city >", value, "intrstCity");
            return (Criteria) this;
        }

        public Criteria andIntrstCityGreaterThanOrEqualTo(String value) {
            addCriterion("intrst_city >=", value, "intrstCity");
            return (Criteria) this;
        }

        public Criteria andIntrstCityLessThan(String value) {
            addCriterion("intrst_city <", value, "intrstCity");
            return (Criteria) this;
        }

        public Criteria andIntrstCityLessThanOrEqualTo(String value) {
            addCriterion("intrst_city <=", value, "intrstCity");
            return (Criteria) this;
        }

        public Criteria andIntrstCityLike(String value) {
            addCriterion("intrst_city like", value, "intrstCity");
            return (Criteria) this;
        }

        public Criteria andIntrstCityNotLike(String value) {
            addCriterion("intrst_city not like", value, "intrstCity");
            return (Criteria) this;
        }

        public Criteria andIntrstCityIn(List<String> values) {
            addCriterion("intrst_city in", values, "intrstCity");
            return (Criteria) this;
        }

        public Criteria andIntrstCityNotIn(List<String> values) {
            addCriterion("intrst_city not in", values, "intrstCity");
            return (Criteria) this;
        }

        public Criteria andIntrstCityBetween(String value1, String value2) {
            addCriterion("intrst_city between", value1, value2, "intrstCity");
            return (Criteria) this;
        }

        public Criteria andIntrstCityNotBetween(String value1, String value2) {
            addCriterion("intrst_city not between", value1, value2, "intrstCity");
            return (Criteria) this;
        }

        public Criteria andIntrstTypeIsNull() {
            addCriterion("intrst_type is null");
            return (Criteria) this;
        }

        public Criteria andIntrstTypeIsNotNull() {
            addCriterion("intrst_type is not null");
            return (Criteria) this;
        }

        public Criteria andIntrstTypeEqualTo(Integer value) {
            addCriterion("intrst_type =", value, "intrstType");
            return (Criteria) this;
        }

        public Criteria andIntrstTypeNotEqualTo(Integer value) {
            addCriterion("intrst_type <>", value, "intrstType");
            return (Criteria) this;
        }

        public Criteria andIntrstTypeGreaterThan(Integer value) {
            addCriterion("intrst_type >", value, "intrstType");
            return (Criteria) this;
        }

        public Criteria andIntrstTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("intrst_type >=", value, "intrstType");
            return (Criteria) this;
        }

        public Criteria andIntrstTypeLessThan(Integer value) {
            addCriterion("intrst_type <", value, "intrstType");
            return (Criteria) this;
        }

        public Criteria andIntrstTypeLessThanOrEqualTo(Integer value) {
            addCriterion("intrst_type <=", value, "intrstType");
            return (Criteria) this;
        }

        public Criteria andIntrstTypeIn(List<Integer> values) {
            addCriterion("intrst_type in", values, "intrstType");
            return (Criteria) this;
        }

        public Criteria andIntrstTypeNotIn(List<Integer> values) {
            addCriterion("intrst_type not in", values, "intrstType");
            return (Criteria) this;
        }

        public Criteria andIntrstTypeBetween(Integer value1, Integer value2) {
            addCriterion("intrst_type between", value1, value2, "intrstType");
            return (Criteria) this;
        }

        public Criteria andIntrstTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("intrst_type not between", value1, value2, "intrstType");
            return (Criteria) this;
        }

        public Criteria andInvestScaleIsNull() {
            addCriterion("invest_scale is null");
            return (Criteria) this;
        }

        public Criteria andInvestScaleIsNotNull() {
            addCriterion("invest_scale is not null");
            return (Criteria) this;
        }

        public Criteria andInvestScaleEqualTo(Integer value) {
            addCriterion("invest_scale =", value, "investScale");
            return (Criteria) this;
        }

        public Criteria andInvestScaleNotEqualTo(Integer value) {
            addCriterion("invest_scale <>", value, "investScale");
            return (Criteria) this;
        }

        public Criteria andInvestScaleGreaterThan(Integer value) {
            addCriterion("invest_scale >", value, "investScale");
            return (Criteria) this;
        }

        public Criteria andInvestScaleGreaterThanOrEqualTo(Integer value) {
            addCriterion("invest_scale >=", value, "investScale");
            return (Criteria) this;
        }

        public Criteria andInvestScaleLessThan(Integer value) {
            addCriterion("invest_scale <", value, "investScale");
            return (Criteria) this;
        }

        public Criteria andInvestScaleLessThanOrEqualTo(Integer value) {
            addCriterion("invest_scale <=", value, "investScale");
            return (Criteria) this;
        }

        public Criteria andInvestScaleIn(List<Integer> values) {
            addCriterion("invest_scale in", values, "investScale");
            return (Criteria) this;
        }

        public Criteria andInvestScaleNotIn(List<Integer> values) {
            addCriterion("invest_scale not in", values, "investScale");
            return (Criteria) this;
        }

        public Criteria andInvestScaleBetween(Integer value1, Integer value2) {
            addCriterion("invest_scale between", value1, value2, "investScale");
            return (Criteria) this;
        }

        public Criteria andInvestScaleNotBetween(Integer value1, Integer value2) {
            addCriterion("invest_scale not between", value1, value2, "investScale");
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