package com.wensheng.zcc.cust.module.dao.mysql.auto.entity;

import java.util.ArrayList;
import java.util.List;

public class CustTrdCmpyExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CustTrdCmpyExample() {
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

        public Criteria andCmpyNameIsNull() {
            addCriterion("cmpy_name is null");
            return (Criteria) this;
        }

        public Criteria andCmpyNameIsNotNull() {
            addCriterion("cmpy_name is not null");
            return (Criteria) this;
        }

        public Criteria andCmpyNameEqualTo(String value) {
            addCriterion("cmpy_name =", value, "cmpyName");
            return (Criteria) this;
        }

        public Criteria andCmpyNameNotEqualTo(String value) {
            addCriterion("cmpy_name <>", value, "cmpyName");
            return (Criteria) this;
        }

        public Criteria andCmpyNameGreaterThan(String value) {
            addCriterion("cmpy_name >", value, "cmpyName");
            return (Criteria) this;
        }

        public Criteria andCmpyNameGreaterThanOrEqualTo(String value) {
            addCriterion("cmpy_name >=", value, "cmpyName");
            return (Criteria) this;
        }

        public Criteria andCmpyNameLessThan(String value) {
            addCriterion("cmpy_name <", value, "cmpyName");
            return (Criteria) this;
        }

        public Criteria andCmpyNameLessThanOrEqualTo(String value) {
            addCriterion("cmpy_name <=", value, "cmpyName");
            return (Criteria) this;
        }

        public Criteria andCmpyNameLike(String value) {
            addCriterion("cmpy_name like", value, "cmpyName");
            return (Criteria) this;
        }

        public Criteria andCmpyNameNotLike(String value) {
            addCriterion("cmpy_name not like", value, "cmpyName");
            return (Criteria) this;
        }

        public Criteria andCmpyNameIn(List<String> values) {
            addCriterion("cmpy_name in", values, "cmpyName");
            return (Criteria) this;
        }

        public Criteria andCmpyNameNotIn(List<String> values) {
            addCriterion("cmpy_name not in", values, "cmpyName");
            return (Criteria) this;
        }

        public Criteria andCmpyNameBetween(String value1, String value2) {
            addCriterion("cmpy_name between", value1, value2, "cmpyName");
            return (Criteria) this;
        }

        public Criteria andCmpyNameNotBetween(String value1, String value2) {
            addCriterion("cmpy_name not between", value1, value2, "cmpyName");
            return (Criteria) this;
        }

        public Criteria andUniSocialCodeIsNull() {
            addCriterion("uni_social_code is null");
            return (Criteria) this;
        }

        public Criteria andUniSocialCodeIsNotNull() {
            addCriterion("uni_social_code is not null");
            return (Criteria) this;
        }

        public Criteria andUniSocialCodeEqualTo(String value) {
            addCriterion("uni_social_code =", value, "uniSocialCode");
            return (Criteria) this;
        }

        public Criteria andUniSocialCodeNotEqualTo(String value) {
            addCriterion("uni_social_code <>", value, "uniSocialCode");
            return (Criteria) this;
        }

        public Criteria andUniSocialCodeGreaterThan(String value) {
            addCriterion("uni_social_code >", value, "uniSocialCode");
            return (Criteria) this;
        }

        public Criteria andUniSocialCodeGreaterThanOrEqualTo(String value) {
            addCriterion("uni_social_code >=", value, "uniSocialCode");
            return (Criteria) this;
        }

        public Criteria andUniSocialCodeLessThan(String value) {
            addCriterion("uni_social_code <", value, "uniSocialCode");
            return (Criteria) this;
        }

        public Criteria andUniSocialCodeLessThanOrEqualTo(String value) {
            addCriterion("uni_social_code <=", value, "uniSocialCode");
            return (Criteria) this;
        }

        public Criteria andUniSocialCodeLike(String value) {
            addCriterion("uni_social_code like", value, "uniSocialCode");
            return (Criteria) this;
        }

        public Criteria andUniSocialCodeNotLike(String value) {
            addCriterion("uni_social_code not like", value, "uniSocialCode");
            return (Criteria) this;
        }

        public Criteria andUniSocialCodeIn(List<String> values) {
            addCriterion("uni_social_code in", values, "uniSocialCode");
            return (Criteria) this;
        }

        public Criteria andUniSocialCodeNotIn(List<String> values) {
            addCriterion("uni_social_code not in", values, "uniSocialCode");
            return (Criteria) this;
        }

        public Criteria andUniSocialCodeBetween(String value1, String value2) {
            addCriterion("uni_social_code between", value1, value2, "uniSocialCode");
            return (Criteria) this;
        }

        public Criteria andUniSocialCodeNotBetween(String value1, String value2) {
            addCriterion("uni_social_code not between", value1, value2, "uniSocialCode");
            return (Criteria) this;
        }

        public Criteria andLegalReptiveIsNull() {
            addCriterion("legal_reptive is null");
            return (Criteria) this;
        }

        public Criteria andLegalReptiveIsNotNull() {
            addCriterion("legal_reptive is not null");
            return (Criteria) this;
        }

        public Criteria andLegalReptiveEqualTo(String value) {
            addCriterion("legal_reptive =", value, "legalReptive");
            return (Criteria) this;
        }

        public Criteria andLegalReptiveNotEqualTo(String value) {
            addCriterion("legal_reptive <>", value, "legalReptive");
            return (Criteria) this;
        }

        public Criteria andLegalReptiveGreaterThan(String value) {
            addCriterion("legal_reptive >", value, "legalReptive");
            return (Criteria) this;
        }

        public Criteria andLegalReptiveGreaterThanOrEqualTo(String value) {
            addCriterion("legal_reptive >=", value, "legalReptive");
            return (Criteria) this;
        }

        public Criteria andLegalReptiveLessThan(String value) {
            addCriterion("legal_reptive <", value, "legalReptive");
            return (Criteria) this;
        }

        public Criteria andLegalReptiveLessThanOrEqualTo(String value) {
            addCriterion("legal_reptive <=", value, "legalReptive");
            return (Criteria) this;
        }

        public Criteria andLegalReptiveLike(String value) {
            addCriterion("legal_reptive like", value, "legalReptive");
            return (Criteria) this;
        }

        public Criteria andLegalReptiveNotLike(String value) {
            addCriterion("legal_reptive not like", value, "legalReptive");
            return (Criteria) this;
        }

        public Criteria andLegalReptiveIn(List<String> values) {
            addCriterion("legal_reptive in", values, "legalReptive");
            return (Criteria) this;
        }

        public Criteria andLegalReptiveNotIn(List<String> values) {
            addCriterion("legal_reptive not in", values, "legalReptive");
            return (Criteria) this;
        }

        public Criteria andLegalReptiveBetween(String value1, String value2) {
            addCriterion("legal_reptive between", value1, value2, "legalReptive");
            return (Criteria) this;
        }

        public Criteria andLegalReptiveNotBetween(String value1, String value2) {
            addCriterion("legal_reptive not between", value1, value2, "legalReptive");
            return (Criteria) this;
        }

        public Criteria andCmpyPhoneIsNull() {
            addCriterion("cmpy_phone is null");
            return (Criteria) this;
        }

        public Criteria andCmpyPhoneIsNotNull() {
            addCriterion("cmpy_phone is not null");
            return (Criteria) this;
        }

        public Criteria andCmpyPhoneEqualTo(String value) {
            addCriterion("cmpy_phone =", value, "cmpyPhone");
            return (Criteria) this;
        }

        public Criteria andCmpyPhoneNotEqualTo(String value) {
            addCriterion("cmpy_phone <>", value, "cmpyPhone");
            return (Criteria) this;
        }

        public Criteria andCmpyPhoneGreaterThan(String value) {
            addCriterion("cmpy_phone >", value, "cmpyPhone");
            return (Criteria) this;
        }

        public Criteria andCmpyPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("cmpy_phone >=", value, "cmpyPhone");
            return (Criteria) this;
        }

        public Criteria andCmpyPhoneLessThan(String value) {
            addCriterion("cmpy_phone <", value, "cmpyPhone");
            return (Criteria) this;
        }

        public Criteria andCmpyPhoneLessThanOrEqualTo(String value) {
            addCriterion("cmpy_phone <=", value, "cmpyPhone");
            return (Criteria) this;
        }

        public Criteria andCmpyPhoneLike(String value) {
            addCriterion("cmpy_phone like", value, "cmpyPhone");
            return (Criteria) this;
        }

        public Criteria andCmpyPhoneNotLike(String value) {
            addCriterion("cmpy_phone not like", value, "cmpyPhone");
            return (Criteria) this;
        }

        public Criteria andCmpyPhoneIn(List<String> values) {
            addCriterion("cmpy_phone in", values, "cmpyPhone");
            return (Criteria) this;
        }

        public Criteria andCmpyPhoneNotIn(List<String> values) {
            addCriterion("cmpy_phone not in", values, "cmpyPhone");
            return (Criteria) this;
        }

        public Criteria andCmpyPhoneBetween(String value1, String value2) {
            addCriterion("cmpy_phone between", value1, value2, "cmpyPhone");
            return (Criteria) this;
        }

        public Criteria andCmpyPhoneNotBetween(String value1, String value2) {
            addCriterion("cmpy_phone not between", value1, value2, "cmpyPhone");
            return (Criteria) this;
        }

        public Criteria andCmpyAddrIsNull() {
            addCriterion("cmpy_addr is null");
            return (Criteria) this;
        }

        public Criteria andCmpyAddrIsNotNull() {
            addCriterion("cmpy_addr is not null");
            return (Criteria) this;
        }

        public Criteria andCmpyAddrEqualTo(String value) {
            addCriterion("cmpy_addr =", value, "cmpyAddr");
            return (Criteria) this;
        }

        public Criteria andCmpyAddrNotEqualTo(String value) {
            addCriterion("cmpy_addr <>", value, "cmpyAddr");
            return (Criteria) this;
        }

        public Criteria andCmpyAddrGreaterThan(String value) {
            addCriterion("cmpy_addr >", value, "cmpyAddr");
            return (Criteria) this;
        }

        public Criteria andCmpyAddrGreaterThanOrEqualTo(String value) {
            addCriterion("cmpy_addr >=", value, "cmpyAddr");
            return (Criteria) this;
        }

        public Criteria andCmpyAddrLessThan(String value) {
            addCriterion("cmpy_addr <", value, "cmpyAddr");
            return (Criteria) this;
        }

        public Criteria andCmpyAddrLessThanOrEqualTo(String value) {
            addCriterion("cmpy_addr <=", value, "cmpyAddr");
            return (Criteria) this;
        }

        public Criteria andCmpyAddrLike(String value) {
            addCriterion("cmpy_addr like", value, "cmpyAddr");
            return (Criteria) this;
        }

        public Criteria andCmpyAddrNotLike(String value) {
            addCriterion("cmpy_addr not like", value, "cmpyAddr");
            return (Criteria) this;
        }

        public Criteria andCmpyAddrIn(List<String> values) {
            addCriterion("cmpy_addr in", values, "cmpyAddr");
            return (Criteria) this;
        }

        public Criteria andCmpyAddrNotIn(List<String> values) {
            addCriterion("cmpy_addr not in", values, "cmpyAddr");
            return (Criteria) this;
        }

        public Criteria andCmpyAddrBetween(String value1, String value2) {
            addCriterion("cmpy_addr between", value1, value2, "cmpyAddr");
            return (Criteria) this;
        }

        public Criteria andCmpyAddrNotBetween(String value1, String value2) {
            addCriterion("cmpy_addr not between", value1, value2, "cmpyAddr");
            return (Criteria) this;
        }

        public Criteria andAnnuReptPhoneIsNull() {
            addCriterion("annu_rept_phone is null");
            return (Criteria) this;
        }

        public Criteria andAnnuReptPhoneIsNotNull() {
            addCriterion("annu_rept_phone is not null");
            return (Criteria) this;
        }

        public Criteria andAnnuReptPhoneEqualTo(String value) {
            addCriterion("annu_rept_phone =", value, "annuReptPhone");
            return (Criteria) this;
        }

        public Criteria andAnnuReptPhoneNotEqualTo(String value) {
            addCriterion("annu_rept_phone <>", value, "annuReptPhone");
            return (Criteria) this;
        }

        public Criteria andAnnuReptPhoneGreaterThan(String value) {
            addCriterion("annu_rept_phone >", value, "annuReptPhone");
            return (Criteria) this;
        }

        public Criteria andAnnuReptPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("annu_rept_phone >=", value, "annuReptPhone");
            return (Criteria) this;
        }

        public Criteria andAnnuReptPhoneLessThan(String value) {
            addCriterion("annu_rept_phone <", value, "annuReptPhone");
            return (Criteria) this;
        }

        public Criteria andAnnuReptPhoneLessThanOrEqualTo(String value) {
            addCriterion("annu_rept_phone <=", value, "annuReptPhone");
            return (Criteria) this;
        }

        public Criteria andAnnuReptPhoneLike(String value) {
            addCriterion("annu_rept_phone like", value, "annuReptPhone");
            return (Criteria) this;
        }

        public Criteria andAnnuReptPhoneNotLike(String value) {
            addCriterion("annu_rept_phone not like", value, "annuReptPhone");
            return (Criteria) this;
        }

        public Criteria andAnnuReptPhoneIn(List<String> values) {
            addCriterion("annu_rept_phone in", values, "annuReptPhone");
            return (Criteria) this;
        }

        public Criteria andAnnuReptPhoneNotIn(List<String> values) {
            addCriterion("annu_rept_phone not in", values, "annuReptPhone");
            return (Criteria) this;
        }

        public Criteria andAnnuReptPhoneBetween(String value1, String value2) {
            addCriterion("annu_rept_phone between", value1, value2, "annuReptPhone");
            return (Criteria) this;
        }

        public Criteria andAnnuReptPhoneNotBetween(String value1, String value2) {
            addCriterion("annu_rept_phone not between", value1, value2, "annuReptPhone");
            return (Criteria) this;
        }

        public Criteria andAnnuReptAddrIsNull() {
            addCriterion("annu_rept_addr is null");
            return (Criteria) this;
        }

        public Criteria andAnnuReptAddrIsNotNull() {
            addCriterion("annu_rept_addr is not null");
            return (Criteria) this;
        }

        public Criteria andAnnuReptAddrEqualTo(String value) {
            addCriterion("annu_rept_addr =", value, "annuReptAddr");
            return (Criteria) this;
        }

        public Criteria andAnnuReptAddrNotEqualTo(String value) {
            addCriterion("annu_rept_addr <>", value, "annuReptAddr");
            return (Criteria) this;
        }

        public Criteria andAnnuReptAddrGreaterThan(String value) {
            addCriterion("annu_rept_addr >", value, "annuReptAddr");
            return (Criteria) this;
        }

        public Criteria andAnnuReptAddrGreaterThanOrEqualTo(String value) {
            addCriterion("annu_rept_addr >=", value, "annuReptAddr");
            return (Criteria) this;
        }

        public Criteria andAnnuReptAddrLessThan(String value) {
            addCriterion("annu_rept_addr <", value, "annuReptAddr");
            return (Criteria) this;
        }

        public Criteria andAnnuReptAddrLessThanOrEqualTo(String value) {
            addCriterion("annu_rept_addr <=", value, "annuReptAddr");
            return (Criteria) this;
        }

        public Criteria andAnnuReptAddrLike(String value) {
            addCriterion("annu_rept_addr like", value, "annuReptAddr");
            return (Criteria) this;
        }

        public Criteria andAnnuReptAddrNotLike(String value) {
            addCriterion("annu_rept_addr not like", value, "annuReptAddr");
            return (Criteria) this;
        }

        public Criteria andAnnuReptAddrIn(List<String> values) {
            addCriterion("annu_rept_addr in", values, "annuReptAddr");
            return (Criteria) this;
        }

        public Criteria andAnnuReptAddrNotIn(List<String> values) {
            addCriterion("annu_rept_addr not in", values, "annuReptAddr");
            return (Criteria) this;
        }

        public Criteria andAnnuReptAddrBetween(String value1, String value2) {
            addCriterion("annu_rept_addr between", value1, value2, "annuReptAddr");
            return (Criteria) this;
        }

        public Criteria andAnnuReptAddrNotBetween(String value1, String value2) {
            addCriterion("annu_rept_addr not between", value1, value2, "annuReptAddr");
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