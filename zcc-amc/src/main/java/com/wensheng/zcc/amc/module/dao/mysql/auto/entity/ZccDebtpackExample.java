package com.wensheng.zcc.amc.module.dao.mysql.auto.entity;

import java.util.ArrayList;
import java.util.List;

public class ZccDebtpackExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ZccDebtpackExample() {
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

        public Criteria andPackTitleIsNull() {
            addCriterion("pack_title is null");
            return (Criteria) this;
        }

        public Criteria andPackTitleIsNotNull() {
            addCriterion("pack_title is not null");
            return (Criteria) this;
        }

        public Criteria andPackTitleEqualTo(String value) {
            addCriterion("pack_title =", value, "packTitle");
            return (Criteria) this;
        }

        public Criteria andPackTitleNotEqualTo(String value) {
            addCriterion("pack_title <>", value, "packTitle");
            return (Criteria) this;
        }

        public Criteria andPackTitleGreaterThan(String value) {
            addCriterion("pack_title >", value, "packTitle");
            return (Criteria) this;
        }

        public Criteria andPackTitleGreaterThanOrEqualTo(String value) {
            addCriterion("pack_title >=", value, "packTitle");
            return (Criteria) this;
        }

        public Criteria andPackTitleLessThan(String value) {
            addCriterion("pack_title <", value, "packTitle");
            return (Criteria) this;
        }

        public Criteria andPackTitleLessThanOrEqualTo(String value) {
            addCriterion("pack_title <=", value, "packTitle");
            return (Criteria) this;
        }

        public Criteria andPackTitleLike(String value) {
            addCriterion("pack_title like", value, "packTitle");
            return (Criteria) this;
        }

        public Criteria andPackTitleNotLike(String value) {
            addCriterion("pack_title not like", value, "packTitle");
            return (Criteria) this;
        }

        public Criteria andPackTitleIn(List<String> values) {
            addCriterion("pack_title in", values, "packTitle");
            return (Criteria) this;
        }

        public Criteria andPackTitleNotIn(List<String> values) {
            addCriterion("pack_title not in", values, "packTitle");
            return (Criteria) this;
        }

        public Criteria andPackTitleBetween(String value1, String value2) {
            addCriterion("pack_title between", value1, value2, "packTitle");
            return (Criteria) this;
        }

        public Criteria andPackTitleNotBetween(String value1, String value2) {
            addCriterion("pack_title not between", value1, value2, "packTitle");
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

        public Criteria andAmcDebtpackCodeIsNull() {
            addCriterion("amc_debtpack_code is null");
            return (Criteria) this;
        }

        public Criteria andAmcDebtpackCodeIsNotNull() {
            addCriterion("amc_debtpack_code is not null");
            return (Criteria) this;
        }

        public Criteria andAmcDebtpackCodeEqualTo(String value) {
            addCriterion("amc_debtpack_code =", value, "amcDebtpackCode");
            return (Criteria) this;
        }

        public Criteria andAmcDebtpackCodeNotEqualTo(String value) {
            addCriterion("amc_debtpack_code <>", value, "amcDebtpackCode");
            return (Criteria) this;
        }

        public Criteria andAmcDebtpackCodeGreaterThan(String value) {
            addCriterion("amc_debtpack_code >", value, "amcDebtpackCode");
            return (Criteria) this;
        }

        public Criteria andAmcDebtpackCodeGreaterThanOrEqualTo(String value) {
            addCriterion("amc_debtpack_code >=", value, "amcDebtpackCode");
            return (Criteria) this;
        }

        public Criteria andAmcDebtpackCodeLessThan(String value) {
            addCriterion("amc_debtpack_code <", value, "amcDebtpackCode");
            return (Criteria) this;
        }

        public Criteria andAmcDebtpackCodeLessThanOrEqualTo(String value) {
            addCriterion("amc_debtpack_code <=", value, "amcDebtpackCode");
            return (Criteria) this;
        }

        public Criteria andAmcDebtpackCodeLike(String value) {
            addCriterion("amc_debtpack_code like", value, "amcDebtpackCode");
            return (Criteria) this;
        }

        public Criteria andAmcDebtpackCodeNotLike(String value) {
            addCriterion("amc_debtpack_code not like", value, "amcDebtpackCode");
            return (Criteria) this;
        }

        public Criteria andAmcDebtpackCodeIn(List<String> values) {
            addCriterion("amc_debtpack_code in", values, "amcDebtpackCode");
            return (Criteria) this;
        }

        public Criteria andAmcDebtpackCodeNotIn(List<String> values) {
            addCriterion("amc_debtpack_code not in", values, "amcDebtpackCode");
            return (Criteria) this;
        }

        public Criteria andAmcDebtpackCodeBetween(String value1, String value2) {
            addCriterion("amc_debtpack_code between", value1, value2, "amcDebtpackCode");
            return (Criteria) this;
        }

        public Criteria andAmcDebtpackCodeNotBetween(String value1, String value2) {
            addCriterion("amc_debtpack_code not between", value1, value2, "amcDebtpackCode");
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

        public Criteria andAmcCmpyIdIsNull() {
            addCriterion("amc_cmpy_id is null");
            return (Criteria) this;
        }

        public Criteria andAmcCmpyIdIsNotNull() {
            addCriterion("amc_cmpy_id is not null");
            return (Criteria) this;
        }

        public Criteria andAmcCmpyIdEqualTo(Long value) {
            addCriterion("amc_cmpy_id =", value, "amcCmpyId");
            return (Criteria) this;
        }

        public Criteria andAmcCmpyIdNotEqualTo(Long value) {
            addCriterion("amc_cmpy_id <>", value, "amcCmpyId");
            return (Criteria) this;
        }

        public Criteria andAmcCmpyIdGreaterThan(Long value) {
            addCriterion("amc_cmpy_id >", value, "amcCmpyId");
            return (Criteria) this;
        }

        public Criteria andAmcCmpyIdGreaterThanOrEqualTo(Long value) {
            addCriterion("amc_cmpy_id >=", value, "amcCmpyId");
            return (Criteria) this;
        }

        public Criteria andAmcCmpyIdLessThan(Long value) {
            addCriterion("amc_cmpy_id <", value, "amcCmpyId");
            return (Criteria) this;
        }

        public Criteria andAmcCmpyIdLessThanOrEqualTo(Long value) {
            addCriterion("amc_cmpy_id <=", value, "amcCmpyId");
            return (Criteria) this;
        }

        public Criteria andAmcCmpyIdIn(List<Long> values) {
            addCriterion("amc_cmpy_id in", values, "amcCmpyId");
            return (Criteria) this;
        }

        public Criteria andAmcCmpyIdNotIn(List<Long> values) {
            addCriterion("amc_cmpy_id not in", values, "amcCmpyId");
            return (Criteria) this;
        }

        public Criteria andAmcCmpyIdBetween(Long value1, Long value2) {
            addCriterion("amc_cmpy_id between", value1, value2, "amcCmpyId");
            return (Criteria) this;
        }

        public Criteria andAmcCmpyIdNotBetween(Long value1, Long value2) {
            addCriterion("amc_cmpy_id not between", value1, value2, "amcCmpyId");
            return (Criteria) this;
        }

        public Criteria andAreaIsNull() {
            addCriterion("area is null");
            return (Criteria) this;
        }

        public Criteria andAreaIsNotNull() {
            addCriterion("area is not null");
            return (Criteria) this;
        }

        public Criteria andAreaEqualTo(Integer value) {
            addCriterion("area =", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotEqualTo(Integer value) {
            addCriterion("area <>", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaGreaterThan(Integer value) {
            addCriterion("area >", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaGreaterThanOrEqualTo(Integer value) {
            addCriterion("area >=", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLessThan(Integer value) {
            addCriterion("area <", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLessThanOrEqualTo(Integer value) {
            addCriterion("area <=", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaIn(List<Integer> values) {
            addCriterion("area in", values, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotIn(List<Integer> values) {
            addCriterion("area not in", values, "area");
            return (Criteria) this;
        }

        public Criteria andAreaBetween(Integer value1, Integer value2) {
            addCriterion("area between", value1, value2, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotBetween(Integer value1, Integer value2) {
            addCriterion("area not between", value1, value2, "area");
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