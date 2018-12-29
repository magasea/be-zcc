package com.wensheng.zcc.amc.module.dao.mysql.auto.entity;

import java.util.ArrayList;
import java.util.List;

public class CurtInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CurtInfoExample() {
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

        public Criteria andCurtNameIsNull() {
            addCriterion("curt_name is null");
            return (Criteria) this;
        }

        public Criteria andCurtNameIsNotNull() {
            addCriterion("curt_name is not null");
            return (Criteria) this;
        }

        public Criteria andCurtNameEqualTo(String value) {
            addCriterion("curt_name =", value, "curtName");
            return (Criteria) this;
        }

        public Criteria andCurtNameNotEqualTo(String value) {
            addCriterion("curt_name <>", value, "curtName");
            return (Criteria) this;
        }

        public Criteria andCurtNameGreaterThan(String value) {
            addCriterion("curt_name >", value, "curtName");
            return (Criteria) this;
        }

        public Criteria andCurtNameGreaterThanOrEqualTo(String value) {
            addCriterion("curt_name >=", value, "curtName");
            return (Criteria) this;
        }

        public Criteria andCurtNameLessThan(String value) {
            addCriterion("curt_name <", value, "curtName");
            return (Criteria) this;
        }

        public Criteria andCurtNameLessThanOrEqualTo(String value) {
            addCriterion("curt_name <=", value, "curtName");
            return (Criteria) this;
        }

        public Criteria andCurtNameLike(String value) {
            addCriterion("curt_name like", value, "curtName");
            return (Criteria) this;
        }

        public Criteria andCurtNameNotLike(String value) {
            addCriterion("curt_name not like", value, "curtName");
            return (Criteria) this;
        }

        public Criteria andCurtNameIn(List<String> values) {
            addCriterion("curt_name in", values, "curtName");
            return (Criteria) this;
        }

        public Criteria andCurtNameNotIn(List<String> values) {
            addCriterion("curt_name not in", values, "curtName");
            return (Criteria) this;
        }

        public Criteria andCurtNameBetween(String value1, String value2) {
            addCriterion("curt_name between", value1, value2, "curtName");
            return (Criteria) this;
        }

        public Criteria andCurtNameNotBetween(String value1, String value2) {
            addCriterion("curt_name not between", value1, value2, "curtName");
            return (Criteria) this;
        }

        public Criteria andCurtProvinceIsNull() {
            addCriterion("curt_province is null");
            return (Criteria) this;
        }

        public Criteria andCurtProvinceIsNotNull() {
            addCriterion("curt_province is not null");
            return (Criteria) this;
        }

        public Criteria andCurtProvinceEqualTo(String value) {
            addCriterion("curt_province =", value, "curtProvince");
            return (Criteria) this;
        }

        public Criteria andCurtProvinceNotEqualTo(String value) {
            addCriterion("curt_province <>", value, "curtProvince");
            return (Criteria) this;
        }

        public Criteria andCurtProvinceGreaterThan(String value) {
            addCriterion("curt_province >", value, "curtProvince");
            return (Criteria) this;
        }

        public Criteria andCurtProvinceGreaterThanOrEqualTo(String value) {
            addCriterion("curt_province >=", value, "curtProvince");
            return (Criteria) this;
        }

        public Criteria andCurtProvinceLessThan(String value) {
            addCriterion("curt_province <", value, "curtProvince");
            return (Criteria) this;
        }

        public Criteria andCurtProvinceLessThanOrEqualTo(String value) {
            addCriterion("curt_province <=", value, "curtProvince");
            return (Criteria) this;
        }

        public Criteria andCurtProvinceLike(String value) {
            addCriterion("curt_province like", value, "curtProvince");
            return (Criteria) this;
        }

        public Criteria andCurtProvinceNotLike(String value) {
            addCriterion("curt_province not like", value, "curtProvince");
            return (Criteria) this;
        }

        public Criteria andCurtProvinceIn(List<String> values) {
            addCriterion("curt_province in", values, "curtProvince");
            return (Criteria) this;
        }

        public Criteria andCurtProvinceNotIn(List<String> values) {
            addCriterion("curt_province not in", values, "curtProvince");
            return (Criteria) this;
        }

        public Criteria andCurtProvinceBetween(String value1, String value2) {
            addCriterion("curt_province between", value1, value2, "curtProvince");
            return (Criteria) this;
        }

        public Criteria andCurtProvinceNotBetween(String value1, String value2) {
            addCriterion("curt_province not between", value1, value2, "curtProvince");
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

        public Criteria andCurtCityEqualTo(String value) {
            addCriterion("curt_city =", value, "curtCity");
            return (Criteria) this;
        }

        public Criteria andCurtCityNotEqualTo(String value) {
            addCriterion("curt_city <>", value, "curtCity");
            return (Criteria) this;
        }

        public Criteria andCurtCityGreaterThan(String value) {
            addCriterion("curt_city >", value, "curtCity");
            return (Criteria) this;
        }

        public Criteria andCurtCityGreaterThanOrEqualTo(String value) {
            addCriterion("curt_city >=", value, "curtCity");
            return (Criteria) this;
        }

        public Criteria andCurtCityLessThan(String value) {
            addCriterion("curt_city <", value, "curtCity");
            return (Criteria) this;
        }

        public Criteria andCurtCityLessThanOrEqualTo(String value) {
            addCriterion("curt_city <=", value, "curtCity");
            return (Criteria) this;
        }

        public Criteria andCurtCityLike(String value) {
            addCriterion("curt_city like", value, "curtCity");
            return (Criteria) this;
        }

        public Criteria andCurtCityNotLike(String value) {
            addCriterion("curt_city not like", value, "curtCity");
            return (Criteria) this;
        }

        public Criteria andCurtCityIn(List<String> values) {
            addCriterion("curt_city in", values, "curtCity");
            return (Criteria) this;
        }

        public Criteria andCurtCityNotIn(List<String> values) {
            addCriterion("curt_city not in", values, "curtCity");
            return (Criteria) this;
        }

        public Criteria andCurtCityBetween(String value1, String value2) {
            addCriterion("curt_city between", value1, value2, "curtCity");
            return (Criteria) this;
        }

        public Criteria andCurtCityNotBetween(String value1, String value2) {
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

        public Criteria andCurtCountyEqualTo(String value) {
            addCriterion("curt_county =", value, "curtCounty");
            return (Criteria) this;
        }

        public Criteria andCurtCountyNotEqualTo(String value) {
            addCriterion("curt_county <>", value, "curtCounty");
            return (Criteria) this;
        }

        public Criteria andCurtCountyGreaterThan(String value) {
            addCriterion("curt_county >", value, "curtCounty");
            return (Criteria) this;
        }

        public Criteria andCurtCountyGreaterThanOrEqualTo(String value) {
            addCriterion("curt_county >=", value, "curtCounty");
            return (Criteria) this;
        }

        public Criteria andCurtCountyLessThan(String value) {
            addCriterion("curt_county <", value, "curtCounty");
            return (Criteria) this;
        }

        public Criteria andCurtCountyLessThanOrEqualTo(String value) {
            addCriterion("curt_county <=", value, "curtCounty");
            return (Criteria) this;
        }

        public Criteria andCurtCountyLike(String value) {
            addCriterion("curt_county like", value, "curtCounty");
            return (Criteria) this;
        }

        public Criteria andCurtCountyNotLike(String value) {
            addCriterion("curt_county not like", value, "curtCounty");
            return (Criteria) this;
        }

        public Criteria andCurtCountyIn(List<String> values) {
            addCriterion("curt_county in", values, "curtCounty");
            return (Criteria) this;
        }

        public Criteria andCurtCountyNotIn(List<String> values) {
            addCriterion("curt_county not in", values, "curtCounty");
            return (Criteria) this;
        }

        public Criteria andCurtCountyBetween(String value1, String value2) {
            addCriterion("curt_county between", value1, value2, "curtCounty");
            return (Criteria) this;
        }

        public Criteria andCurtCountyNotBetween(String value1, String value2) {
            addCriterion("curt_county not between", value1, value2, "curtCounty");
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