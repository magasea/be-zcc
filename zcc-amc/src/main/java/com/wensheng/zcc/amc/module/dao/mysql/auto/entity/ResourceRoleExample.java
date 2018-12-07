package com.wensheng.zcc.amc.module.dao.mysql.auto.entity;

import java.util.ArrayList;
import java.util.List;

public class ResourceRoleExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ResourceRoleExample() {
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

        public Criteria andAssetTypeIsNull() {
            addCriterion("asset_type is null");
            return (Criteria) this;
        }

        public Criteria andAssetTypeIsNotNull() {
            addCriterion("asset_type is not null");
            return (Criteria) this;
        }

        public Criteria andAssetTypeEqualTo(Integer value) {
            addCriterion("asset_type =", value, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeNotEqualTo(Integer value) {
            addCriterion("asset_type <>", value, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeGreaterThan(Integer value) {
            addCriterion("asset_type >", value, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("asset_type >=", value, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeLessThan(Integer value) {
            addCriterion("asset_type <", value, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeLessThanOrEqualTo(Integer value) {
            addCriterion("asset_type <=", value, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeIn(List<Integer> values) {
            addCriterion("asset_type in", values, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeNotIn(List<Integer> values) {
            addCriterion("asset_type not in", values, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeBetween(Integer value1, Integer value2) {
            addCriterion("asset_type between", value1, value2, "assetType");
            return (Criteria) this;
        }

        public Criteria andAssetTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("asset_type not between", value1, value2, "assetType");
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

        public Criteria andAccessRoleIsNull() {
            addCriterion("access_role is null");
            return (Criteria) this;
        }

        public Criteria andAccessRoleIsNotNull() {
            addCriterion("access_role is not null");
            return (Criteria) this;
        }

        public Criteria andAccessRoleEqualTo(Integer value) {
            addCriterion("access_role =", value, "accessRole");
            return (Criteria) this;
        }

        public Criteria andAccessRoleNotEqualTo(Integer value) {
            addCriterion("access_role <>", value, "accessRole");
            return (Criteria) this;
        }

        public Criteria andAccessRoleGreaterThan(Integer value) {
            addCriterion("access_role >", value, "accessRole");
            return (Criteria) this;
        }

        public Criteria andAccessRoleGreaterThanOrEqualTo(Integer value) {
            addCriterion("access_role >=", value, "accessRole");
            return (Criteria) this;
        }

        public Criteria andAccessRoleLessThan(Integer value) {
            addCriterion("access_role <", value, "accessRole");
            return (Criteria) this;
        }

        public Criteria andAccessRoleLessThanOrEqualTo(Integer value) {
            addCriterion("access_role <=", value, "accessRole");
            return (Criteria) this;
        }

        public Criteria andAccessRoleIn(List<Integer> values) {
            addCriterion("access_role in", values, "accessRole");
            return (Criteria) this;
        }

        public Criteria andAccessRoleNotIn(List<Integer> values) {
            addCriterion("access_role not in", values, "accessRole");
            return (Criteria) this;
        }

        public Criteria andAccessRoleBetween(Integer value1, Integer value2) {
            addCriterion("access_role between", value1, value2, "accessRole");
            return (Criteria) this;
        }

        public Criteria andAccessRoleNotBetween(Integer value1, Integer value2) {
            addCriterion("access_role not between", value1, value2, "accessRole");
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