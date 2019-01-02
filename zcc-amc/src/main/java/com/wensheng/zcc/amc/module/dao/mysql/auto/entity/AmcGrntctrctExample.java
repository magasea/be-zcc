package com.wensheng.zcc.amc.module.dao.mysql.auto.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AmcGrntctrctExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AmcGrntctrctExample() {
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

        public Criteria andOriginContractIdIsNull() {
            addCriterion("origin_contract_id is null");
            return (Criteria) this;
        }

        public Criteria andOriginContractIdIsNotNull() {
            addCriterion("origin_contract_id is not null");
            return (Criteria) this;
        }

        public Criteria andOriginContractIdEqualTo(Long value) {
            addCriterion("origin_contract_id =", value, "originContractId");
            return (Criteria) this;
        }

        public Criteria andOriginContractIdNotEqualTo(Long value) {
            addCriterion("origin_contract_id <>", value, "originContractId");
            return (Criteria) this;
        }

        public Criteria andOriginContractIdGreaterThan(Long value) {
            addCriterion("origin_contract_id >", value, "originContractId");
            return (Criteria) this;
        }

        public Criteria andOriginContractIdGreaterThanOrEqualTo(Long value) {
            addCriterion("origin_contract_id >=", value, "originContractId");
            return (Criteria) this;
        }

        public Criteria andOriginContractIdLessThan(Long value) {
            addCriterion("origin_contract_id <", value, "originContractId");
            return (Criteria) this;
        }

        public Criteria andOriginContractIdLessThanOrEqualTo(Long value) {
            addCriterion("origin_contract_id <=", value, "originContractId");
            return (Criteria) this;
        }

        public Criteria andOriginContractIdIn(List<Long> values) {
            addCriterion("origin_contract_id in", values, "originContractId");
            return (Criteria) this;
        }

        public Criteria andOriginContractIdNotIn(List<Long> values) {
            addCriterion("origin_contract_id not in", values, "originContractId");
            return (Criteria) this;
        }

        public Criteria andOriginContractIdBetween(Long value1, Long value2) {
            addCriterion("origin_contract_id between", value1, value2, "originContractId");
            return (Criteria) this;
        }

        public Criteria andOriginContractIdNotBetween(Long value1, Long value2) {
            addCriterion("origin_contract_id not between", value1, value2, "originContractId");
            return (Criteria) this;
        }

        public Criteria andAmountIsNull() {
            addCriterion("amount is null");
            return (Criteria) this;
        }

        public Criteria andAmountIsNotNull() {
            addCriterion("amount is not null");
            return (Criteria) this;
        }

        public Criteria andAmountEqualTo(Long value) {
            addCriterion("amount =", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotEqualTo(Long value) {
            addCriterion("amount <>", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThan(Long value) {
            addCriterion("amount >", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThanOrEqualTo(Long value) {
            addCriterion("amount >=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThan(Long value) {
            addCriterion("amount <", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThanOrEqualTo(Long value) {
            addCriterion("amount <=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountIn(List<Long> values) {
            addCriterion("amount in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotIn(List<Long> values) {
            addCriterion("amount not in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountBetween(Long value1, Long value2) {
            addCriterion("amount between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotBetween(Long value1, Long value2) {
            addCriterion("amount not between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andOriginGrantorIdIsNull() {
            addCriterion("origin_grantor_id is null");
            return (Criteria) this;
        }

        public Criteria andOriginGrantorIdIsNotNull() {
            addCriterion("origin_grantor_id is not null");
            return (Criteria) this;
        }

        public Criteria andOriginGrantorIdEqualTo(Long value) {
            addCriterion("origin_grantor_id =", value, "originGrantorId");
            return (Criteria) this;
        }

        public Criteria andOriginGrantorIdNotEqualTo(Long value) {
            addCriterion("origin_grantor_id <>", value, "originGrantorId");
            return (Criteria) this;
        }

        public Criteria andOriginGrantorIdGreaterThan(Long value) {
            addCriterion("origin_grantor_id >", value, "originGrantorId");
            return (Criteria) this;
        }

        public Criteria andOriginGrantorIdGreaterThanOrEqualTo(Long value) {
            addCriterion("origin_grantor_id >=", value, "originGrantorId");
            return (Criteria) this;
        }

        public Criteria andOriginGrantorIdLessThan(Long value) {
            addCriterion("origin_grantor_id <", value, "originGrantorId");
            return (Criteria) this;
        }

        public Criteria andOriginGrantorIdLessThanOrEqualTo(Long value) {
            addCriterion("origin_grantor_id <=", value, "originGrantorId");
            return (Criteria) this;
        }

        public Criteria andOriginGrantorIdIn(List<Long> values) {
            addCriterion("origin_grantor_id in", values, "originGrantorId");
            return (Criteria) this;
        }

        public Criteria andOriginGrantorIdNotIn(List<Long> values) {
            addCriterion("origin_grantor_id not in", values, "originGrantorId");
            return (Criteria) this;
        }

        public Criteria andOriginGrantorIdBetween(Long value1, Long value2) {
            addCriterion("origin_grantor_id between", value1, value2, "originGrantorId");
            return (Criteria) this;
        }

        public Criteria andOriginGrantorIdNotBetween(Long value1, Long value2) {
            addCriterion("origin_grantor_id not between", value1, value2, "originGrantorId");
            return (Criteria) this;
        }

        public Criteria andGrantorIdIsNull() {
            addCriterion("grantor_id is null");
            return (Criteria) this;
        }

        public Criteria andGrantorIdIsNotNull() {
            addCriterion("grantor_id is not null");
            return (Criteria) this;
        }

        public Criteria andGrantorIdEqualTo(Long value) {
            addCriterion("grantor_id =", value, "grantorId");
            return (Criteria) this;
        }

        public Criteria andGrantorIdNotEqualTo(Long value) {
            addCriterion("grantor_id <>", value, "grantorId");
            return (Criteria) this;
        }

        public Criteria andGrantorIdGreaterThan(Long value) {
            addCriterion("grantor_id >", value, "grantorId");
            return (Criteria) this;
        }

        public Criteria andGrantorIdGreaterThanOrEqualTo(Long value) {
            addCriterion("grantor_id >=", value, "grantorId");
            return (Criteria) this;
        }

        public Criteria andGrantorIdLessThan(Long value) {
            addCriterion("grantor_id <", value, "grantorId");
            return (Criteria) this;
        }

        public Criteria andGrantorIdLessThanOrEqualTo(Long value) {
            addCriterion("grantor_id <=", value, "grantorId");
            return (Criteria) this;
        }

        public Criteria andGrantorIdIn(List<Long> values) {
            addCriterion("grantor_id in", values, "grantorId");
            return (Criteria) this;
        }

        public Criteria andGrantorIdNotIn(List<Long> values) {
            addCriterion("grantor_id not in", values, "grantorId");
            return (Criteria) this;
        }

        public Criteria andGrantorIdBetween(Long value1, Long value2) {
            addCriterion("grantor_id between", value1, value2, "grantorId");
            return (Criteria) this;
        }

        public Criteria andGrantorIdNotBetween(Long value1, Long value2) {
            addCriterion("grantor_id not between", value1, value2, "grantorId");
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

        public Criteria andContractNumberIsNull() {
            addCriterion("contract_number is null");
            return (Criteria) this;
        }

        public Criteria andContractNumberIsNotNull() {
            addCriterion("contract_number is not null");
            return (Criteria) this;
        }

        public Criteria andContractNumberEqualTo(String value) {
            addCriterion("contract_number =", value, "contractNumber");
            return (Criteria) this;
        }

        public Criteria andContractNumberNotEqualTo(String value) {
            addCriterion("contract_number <>", value, "contractNumber");
            return (Criteria) this;
        }

        public Criteria andContractNumberGreaterThan(String value) {
            addCriterion("contract_number >", value, "contractNumber");
            return (Criteria) this;
        }

        public Criteria andContractNumberGreaterThanOrEqualTo(String value) {
            addCriterion("contract_number >=", value, "contractNumber");
            return (Criteria) this;
        }

        public Criteria andContractNumberLessThan(String value) {
            addCriterion("contract_number <", value, "contractNumber");
            return (Criteria) this;
        }

        public Criteria andContractNumberLessThanOrEqualTo(String value) {
            addCriterion("contract_number <=", value, "contractNumber");
            return (Criteria) this;
        }

        public Criteria andContractNumberLike(String value) {
            addCriterion("contract_number like", value, "contractNumber");
            return (Criteria) this;
        }

        public Criteria andContractNumberNotLike(String value) {
            addCriterion("contract_number not like", value, "contractNumber");
            return (Criteria) this;
        }

        public Criteria andContractNumberIn(List<String> values) {
            addCriterion("contract_number in", values, "contractNumber");
            return (Criteria) this;
        }

        public Criteria andContractNumberNotIn(List<String> values) {
            addCriterion("contract_number not in", values, "contractNumber");
            return (Criteria) this;
        }

        public Criteria andContractNumberBetween(String value1, String value2) {
            addCriterion("contract_number between", value1, value2, "contractNumber");
            return (Criteria) this;
        }

        public Criteria andContractNumberNotBetween(String value1, String value2) {
            addCriterion("contract_number not between", value1, value2, "contractNumber");
            return (Criteria) this;
        }

        public Criteria andSignDateIsNull() {
            addCriterion("sign_date is null");
            return (Criteria) this;
        }

        public Criteria andSignDateIsNotNull() {
            addCriterion("sign_date is not null");
            return (Criteria) this;
        }

        public Criteria andSignDateEqualTo(Date value) {
            addCriterion("sign_date =", value, "signDate");
            return (Criteria) this;
        }

        public Criteria andSignDateNotEqualTo(Date value) {
            addCriterion("sign_date <>", value, "signDate");
            return (Criteria) this;
        }

        public Criteria andSignDateGreaterThan(Date value) {
            addCriterion("sign_date >", value, "signDate");
            return (Criteria) this;
        }

        public Criteria andSignDateGreaterThanOrEqualTo(Date value) {
            addCriterion("sign_date >=", value, "signDate");
            return (Criteria) this;
        }

        public Criteria andSignDateLessThan(Date value) {
            addCriterion("sign_date <", value, "signDate");
            return (Criteria) this;
        }

        public Criteria andSignDateLessThanOrEqualTo(Date value) {
            addCriterion("sign_date <=", value, "signDate");
            return (Criteria) this;
        }

        public Criteria andSignDateIn(List<Date> values) {
            addCriterion("sign_date in", values, "signDate");
            return (Criteria) this;
        }

        public Criteria andSignDateNotIn(List<Date> values) {
            addCriterion("sign_date not in", values, "signDate");
            return (Criteria) this;
        }

        public Criteria andSignDateBetween(Date value1, Date value2) {
            addCriterion("sign_date between", value1, value2, "signDate");
            return (Criteria) this;
        }

        public Criteria andSignDateNotBetween(Date value1, Date value2) {
            addCriterion("sign_date not between", value1, value2, "signDate");
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