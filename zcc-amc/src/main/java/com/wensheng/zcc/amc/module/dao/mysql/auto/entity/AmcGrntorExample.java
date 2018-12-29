package com.wensheng.zcc.amc.module.dao.mysql.auto.entity;

import java.util.ArrayList;
import java.util.List;

public class AmcGrntorExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AmcGrntorExample() {
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

        public Criteria andGrntctrtIdIsNull() {
            addCriterion("grntctrt_id is null");
            return (Criteria) this;
        }

        public Criteria andGrntctrtIdIsNotNull() {
            addCriterion("grntctrt_id is not null");
            return (Criteria) this;
        }

        public Criteria andGrntctrtIdEqualTo(Long value) {
            addCriterion("grntctrt_id =", value, "grntctrtId");
            return (Criteria) this;
        }

        public Criteria andGrntctrtIdNotEqualTo(Long value) {
            addCriterion("grntctrt_id <>", value, "grntctrtId");
            return (Criteria) this;
        }

        public Criteria andGrntctrtIdGreaterThan(Long value) {
            addCriterion("grntctrt_id >", value, "grntctrtId");
            return (Criteria) this;
        }

        public Criteria andGrntctrtIdGreaterThanOrEqualTo(Long value) {
            addCriterion("grntctrt_id >=", value, "grntctrtId");
            return (Criteria) this;
        }

        public Criteria andGrntctrtIdLessThan(Long value) {
            addCriterion("grntctrt_id <", value, "grntctrtId");
            return (Criteria) this;
        }

        public Criteria andGrntctrtIdLessThanOrEqualTo(Long value) {
            addCriterion("grntctrt_id <=", value, "grntctrtId");
            return (Criteria) this;
        }

        public Criteria andGrntctrtIdIn(List<Long> values) {
            addCriterion("grntctrt_id in", values, "grntctrtId");
            return (Criteria) this;
        }

        public Criteria andGrntctrtIdNotIn(List<Long> values) {
            addCriterion("grntctrt_id not in", values, "grntctrtId");
            return (Criteria) this;
        }

        public Criteria andGrntctrtIdBetween(Long value1, Long value2) {
            addCriterion("grntctrt_id between", value1, value2, "grntctrtId");
            return (Criteria) this;
        }

        public Criteria andGrntctrtIdNotBetween(Long value1, Long value2) {
            addCriterion("grntctrt_id not between", value1, value2, "grntctrtId");
            return (Criteria) this;
        }

        public Criteria andDebtIdIsNull() {
            addCriterion("debt_id is null");
            return (Criteria) this;
        }

        public Criteria andDebtIdIsNotNull() {
            addCriterion("debt_id is not null");
            return (Criteria) this;
        }

        public Criteria andDebtIdEqualTo(Long value) {
            addCriterion("debt_id =", value, "debtId");
            return (Criteria) this;
        }

        public Criteria andDebtIdNotEqualTo(Long value) {
            addCriterion("debt_id <>", value, "debtId");
            return (Criteria) this;
        }

        public Criteria andDebtIdGreaterThan(Long value) {
            addCriterion("debt_id >", value, "debtId");
            return (Criteria) this;
        }

        public Criteria andDebtIdGreaterThanOrEqualTo(Long value) {
            addCriterion("debt_id >=", value, "debtId");
            return (Criteria) this;
        }

        public Criteria andDebtIdLessThan(Long value) {
            addCriterion("debt_id <", value, "debtId");
            return (Criteria) this;
        }

        public Criteria andDebtIdLessThanOrEqualTo(Long value) {
            addCriterion("debt_id <=", value, "debtId");
            return (Criteria) this;
        }

        public Criteria andDebtIdIn(List<Long> values) {
            addCriterion("debt_id in", values, "debtId");
            return (Criteria) this;
        }

        public Criteria andDebtIdNotIn(List<Long> values) {
            addCriterion("debt_id not in", values, "debtId");
            return (Criteria) this;
        }

        public Criteria andDebtIdBetween(Long value1, Long value2) {
            addCriterion("debt_id between", value1, value2, "debtId");
            return (Criteria) this;
        }

        public Criteria andDebtIdNotBetween(Long value1, Long value2) {
            addCriterion("debt_id not between", value1, value2, "debtId");
            return (Criteria) this;
        }

        public Criteria andOriginDebtIdIsNull() {
            addCriterion("origin_debt_id is null");
            return (Criteria) this;
        }

        public Criteria andOriginDebtIdIsNotNull() {
            addCriterion("origin_debt_id is not null");
            return (Criteria) this;
        }

        public Criteria andOriginDebtIdEqualTo(Long value) {
            addCriterion("origin_debt_id =", value, "originDebtId");
            return (Criteria) this;
        }

        public Criteria andOriginDebtIdNotEqualTo(Long value) {
            addCriterion("origin_debt_id <>", value, "originDebtId");
            return (Criteria) this;
        }

        public Criteria andOriginDebtIdGreaterThan(Long value) {
            addCriterion("origin_debt_id >", value, "originDebtId");
            return (Criteria) this;
        }

        public Criteria andOriginDebtIdGreaterThanOrEqualTo(Long value) {
            addCriterion("origin_debt_id >=", value, "originDebtId");
            return (Criteria) this;
        }

        public Criteria andOriginDebtIdLessThan(Long value) {
            addCriterion("origin_debt_id <", value, "originDebtId");
            return (Criteria) this;
        }

        public Criteria andOriginDebtIdLessThanOrEqualTo(Long value) {
            addCriterion("origin_debt_id <=", value, "originDebtId");
            return (Criteria) this;
        }

        public Criteria andOriginDebtIdIn(List<Long> values) {
            addCriterion("origin_debt_id in", values, "originDebtId");
            return (Criteria) this;
        }

        public Criteria andOriginDebtIdNotIn(List<Long> values) {
            addCriterion("origin_debt_id not in", values, "originDebtId");
            return (Criteria) this;
        }

        public Criteria andOriginDebtIdBetween(Long value1, Long value2) {
            addCriterion("origin_debt_id between", value1, value2, "originDebtId");
            return (Criteria) this;
        }

        public Criteria andOriginDebtIdNotBetween(Long value1, Long value2) {
            addCriterion("origin_debt_id not between", value1, value2, "originDebtId");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andCmpyIdIsNull() {
            addCriterion("cmpy_id is null");
            return (Criteria) this;
        }

        public Criteria andCmpyIdIsNotNull() {
            addCriterion("cmpy_id is not null");
            return (Criteria) this;
        }

        public Criteria andCmpyIdEqualTo(Long value) {
            addCriterion("cmpy_id =", value, "cmpyId");
            return (Criteria) this;
        }

        public Criteria andCmpyIdNotEqualTo(Long value) {
            addCriterion("cmpy_id <>", value, "cmpyId");
            return (Criteria) this;
        }

        public Criteria andCmpyIdGreaterThan(Long value) {
            addCriterion("cmpy_id >", value, "cmpyId");
            return (Criteria) this;
        }

        public Criteria andCmpyIdGreaterThanOrEqualTo(Long value) {
            addCriterion("cmpy_id >=", value, "cmpyId");
            return (Criteria) this;
        }

        public Criteria andCmpyIdLessThan(Long value) {
            addCriterion("cmpy_id <", value, "cmpyId");
            return (Criteria) this;
        }

        public Criteria andCmpyIdLessThanOrEqualTo(Long value) {
            addCriterion("cmpy_id <=", value, "cmpyId");
            return (Criteria) this;
        }

        public Criteria andCmpyIdIn(List<Long> values) {
            addCriterion("cmpy_id in", values, "cmpyId");
            return (Criteria) this;
        }

        public Criteria andCmpyIdNotIn(List<Long> values) {
            addCriterion("cmpy_id not in", values, "cmpyId");
            return (Criteria) this;
        }

        public Criteria andCmpyIdBetween(Long value1, Long value2) {
            addCriterion("cmpy_id between", value1, value2, "cmpyId");
            return (Criteria) this;
        }

        public Criteria andCmpyIdNotBetween(Long value1, Long value2) {
            addCriterion("cmpy_id not between", value1, value2, "cmpyId");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
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