package com.wensheng.zcc.cust.module.dao.mysql.auto.entity;

import java.util.ArrayList;
import java.util.Date;
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

        public Criteria andHistoryCmpyPhoneIsNull() {
            addCriterion("history_cmpy_phone is null");
            return (Criteria) this;
        }

        public Criteria andHistoryCmpyPhoneIsNotNull() {
            addCriterion("history_cmpy_phone is not null");
            return (Criteria) this;
        }

        public Criteria andHistoryCmpyPhoneEqualTo(String value) {
            addCriterion("history_cmpy_phone =", value, "historyCmpyPhone");
            return (Criteria) this;
        }

        public Criteria andHistoryCmpyPhoneNotEqualTo(String value) {
            addCriterion("history_cmpy_phone <>", value, "historyCmpyPhone");
            return (Criteria) this;
        }

        public Criteria andHistoryCmpyPhoneGreaterThan(String value) {
            addCriterion("history_cmpy_phone >", value, "historyCmpyPhone");
            return (Criteria) this;
        }

        public Criteria andHistoryCmpyPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("history_cmpy_phone >=", value, "historyCmpyPhone");
            return (Criteria) this;
        }

        public Criteria andHistoryCmpyPhoneLessThan(String value) {
            addCriterion("history_cmpy_phone <", value, "historyCmpyPhone");
            return (Criteria) this;
        }

        public Criteria andHistoryCmpyPhoneLessThanOrEqualTo(String value) {
            addCriterion("history_cmpy_phone <=", value, "historyCmpyPhone");
            return (Criteria) this;
        }

        public Criteria andHistoryCmpyPhoneLike(String value) {
            addCriterion("history_cmpy_phone like", value, "historyCmpyPhone");
            return (Criteria) this;
        }

        public Criteria andHistoryCmpyPhoneNotLike(String value) {
            addCriterion("history_cmpy_phone not like", value, "historyCmpyPhone");
            return (Criteria) this;
        }

        public Criteria andHistoryCmpyPhoneIn(List<String> values) {
            addCriterion("history_cmpy_phone in", values, "historyCmpyPhone");
            return (Criteria) this;
        }

        public Criteria andHistoryCmpyPhoneNotIn(List<String> values) {
            addCriterion("history_cmpy_phone not in", values, "historyCmpyPhone");
            return (Criteria) this;
        }

        public Criteria andHistoryCmpyPhoneBetween(String value1, String value2) {
            addCriterion("history_cmpy_phone between", value1, value2, "historyCmpyPhone");
            return (Criteria) this;
        }

        public Criteria andHistoryCmpyPhoneNotBetween(String value1, String value2) {
            addCriterion("history_cmpy_phone not between", value1, value2, "historyCmpyPhone");
            return (Criteria) this;
        }

        public Criteria andCmpyProvinceIsNull() {
            addCriterion("cmpy_province is null");
            return (Criteria) this;
        }

        public Criteria andCmpyProvinceIsNotNull() {
            addCriterion("cmpy_province is not null");
            return (Criteria) this;
        }

        public Criteria andCmpyProvinceEqualTo(String value) {
            addCriterion("cmpy_province =", value, "cmpyProvince");
            return (Criteria) this;
        }

        public Criteria andCmpyProvinceNotEqualTo(String value) {
            addCriterion("cmpy_province <>", value, "cmpyProvince");
            return (Criteria) this;
        }

        public Criteria andCmpyProvinceGreaterThan(String value) {
            addCriterion("cmpy_province >", value, "cmpyProvince");
            return (Criteria) this;
        }

        public Criteria andCmpyProvinceGreaterThanOrEqualTo(String value) {
            addCriterion("cmpy_province >=", value, "cmpyProvince");
            return (Criteria) this;
        }

        public Criteria andCmpyProvinceLessThan(String value) {
            addCriterion("cmpy_province <", value, "cmpyProvince");
            return (Criteria) this;
        }

        public Criteria andCmpyProvinceLessThanOrEqualTo(String value) {
            addCriterion("cmpy_province <=", value, "cmpyProvince");
            return (Criteria) this;
        }

        public Criteria andCmpyProvinceLike(String value) {
            addCriterion("cmpy_province like", value, "cmpyProvince");
            return (Criteria) this;
        }

        public Criteria andCmpyProvinceNotLike(String value) {
            addCriterion("cmpy_province not like", value, "cmpyProvince");
            return (Criteria) this;
        }

        public Criteria andCmpyProvinceIn(List<String> values) {
            addCriterion("cmpy_province in", values, "cmpyProvince");
            return (Criteria) this;
        }

        public Criteria andCmpyProvinceNotIn(List<String> values) {
            addCriterion("cmpy_province not in", values, "cmpyProvince");
            return (Criteria) this;
        }

        public Criteria andCmpyProvinceBetween(String value1, String value2) {
            addCriterion("cmpy_province between", value1, value2, "cmpyProvince");
            return (Criteria) this;
        }

        public Criteria andCmpyProvinceNotBetween(String value1, String value2) {
            addCriterion("cmpy_province not between", value1, value2, "cmpyProvince");
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

        public Criteria andHistoryCmpyAddrIsNull() {
            addCriterion("history_cmpy_addr is null");
            return (Criteria) this;
        }

        public Criteria andHistoryCmpyAddrIsNotNull() {
            addCriterion("history_cmpy_addr is not null");
            return (Criteria) this;
        }

        public Criteria andHistoryCmpyAddrEqualTo(String value) {
            addCriterion("history_cmpy_addr =", value, "historyCmpyAddr");
            return (Criteria) this;
        }

        public Criteria andHistoryCmpyAddrNotEqualTo(String value) {
            addCriterion("history_cmpy_addr <>", value, "historyCmpyAddr");
            return (Criteria) this;
        }

        public Criteria andHistoryCmpyAddrGreaterThan(String value) {
            addCriterion("history_cmpy_addr >", value, "historyCmpyAddr");
            return (Criteria) this;
        }

        public Criteria andHistoryCmpyAddrGreaterThanOrEqualTo(String value) {
            addCriterion("history_cmpy_addr >=", value, "historyCmpyAddr");
            return (Criteria) this;
        }

        public Criteria andHistoryCmpyAddrLessThan(String value) {
            addCriterion("history_cmpy_addr <", value, "historyCmpyAddr");
            return (Criteria) this;
        }

        public Criteria andHistoryCmpyAddrLessThanOrEqualTo(String value) {
            addCriterion("history_cmpy_addr <=", value, "historyCmpyAddr");
            return (Criteria) this;
        }

        public Criteria andHistoryCmpyAddrLike(String value) {
            addCriterion("history_cmpy_addr like", value, "historyCmpyAddr");
            return (Criteria) this;
        }

        public Criteria andHistoryCmpyAddrNotLike(String value) {
            addCriterion("history_cmpy_addr not like", value, "historyCmpyAddr");
            return (Criteria) this;
        }

        public Criteria andHistoryCmpyAddrIn(List<String> values) {
            addCriterion("history_cmpy_addr in", values, "historyCmpyAddr");
            return (Criteria) this;
        }

        public Criteria andHistoryCmpyAddrNotIn(List<String> values) {
            addCriterion("history_cmpy_addr not in", values, "historyCmpyAddr");
            return (Criteria) this;
        }

        public Criteria andHistoryCmpyAddrBetween(String value1, String value2) {
            addCriterion("history_cmpy_addr between", value1, value2, "historyCmpyAddr");
            return (Criteria) this;
        }

        public Criteria andHistoryCmpyAddrNotBetween(String value1, String value2) {
            addCriterion("history_cmpy_addr not between", value1, value2, "historyCmpyAddr");
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

        public Criteria andFavoriteCityUpdateIsNull() {
            addCriterion("favorite_city_update is null");
            return (Criteria) this;
        }

        public Criteria andFavoriteCityUpdateIsNotNull() {
            addCriterion("favorite_city_update is not null");
            return (Criteria) this;
        }

        public Criteria andFavoriteCityUpdateEqualTo(String value) {
            addCriterion("favorite_city_update =", value, "favoriteCityUpdate");
            return (Criteria) this;
        }

        public Criteria andFavoriteCityUpdateNotEqualTo(String value) {
            addCriterion("favorite_city_update <>", value, "favoriteCityUpdate");
            return (Criteria) this;
        }

        public Criteria andFavoriteCityUpdateGreaterThan(String value) {
            addCriterion("favorite_city_update >", value, "favoriteCityUpdate");
            return (Criteria) this;
        }

        public Criteria andFavoriteCityUpdateGreaterThanOrEqualTo(String value) {
            addCriterion("favorite_city_update >=", value, "favoriteCityUpdate");
            return (Criteria) this;
        }

        public Criteria andFavoriteCityUpdateLessThan(String value) {
            addCriterion("favorite_city_update <", value, "favoriteCityUpdate");
            return (Criteria) this;
        }

        public Criteria andFavoriteCityUpdateLessThanOrEqualTo(String value) {
            addCriterion("favorite_city_update <=", value, "favoriteCityUpdate");
            return (Criteria) this;
        }

        public Criteria andFavoriteCityUpdateLike(String value) {
            addCriterion("favorite_city_update like", value, "favoriteCityUpdate");
            return (Criteria) this;
        }

        public Criteria andFavoriteCityUpdateNotLike(String value) {
            addCriterion("favorite_city_update not like", value, "favoriteCityUpdate");
            return (Criteria) this;
        }

        public Criteria andFavoriteCityUpdateIn(List<String> values) {
            addCriterion("favorite_city_update in", values, "favoriteCityUpdate");
            return (Criteria) this;
        }

        public Criteria andFavoriteCityUpdateNotIn(List<String> values) {
            addCriterion("favorite_city_update not in", values, "favoriteCityUpdate");
            return (Criteria) this;
        }

        public Criteria andFavoriteCityUpdateBetween(String value1, String value2) {
            addCriterion("favorite_city_update between", value1, value2, "favoriteCityUpdate");
            return (Criteria) this;
        }

        public Criteria andFavoriteCityUpdateNotBetween(String value1, String value2) {
            addCriterion("favorite_city_update not between", value1, value2, "favoriteCityUpdate");
            return (Criteria) this;
        }

        public Criteria andFavoriteTypeUpdateIsNull() {
            addCriterion("favorite_type_update is null");
            return (Criteria) this;
        }

        public Criteria andFavoriteTypeUpdateIsNotNull() {
            addCriterion("favorite_type_update is not null");
            return (Criteria) this;
        }

        public Criteria andFavoriteTypeUpdateEqualTo(String value) {
            addCriterion("favorite_type_update =", value, "favoriteTypeUpdate");
            return (Criteria) this;
        }

        public Criteria andFavoriteTypeUpdateNotEqualTo(String value) {
            addCriterion("favorite_type_update <>", value, "favoriteTypeUpdate");
            return (Criteria) this;
        }

        public Criteria andFavoriteTypeUpdateGreaterThan(String value) {
            addCriterion("favorite_type_update >", value, "favoriteTypeUpdate");
            return (Criteria) this;
        }

        public Criteria andFavoriteTypeUpdateGreaterThanOrEqualTo(String value) {
            addCriterion("favorite_type_update >=", value, "favoriteTypeUpdate");
            return (Criteria) this;
        }

        public Criteria andFavoriteTypeUpdateLessThan(String value) {
            addCriterion("favorite_type_update <", value, "favoriteTypeUpdate");
            return (Criteria) this;
        }

        public Criteria andFavoriteTypeUpdateLessThanOrEqualTo(String value) {
            addCriterion("favorite_type_update <=", value, "favoriteTypeUpdate");
            return (Criteria) this;
        }

        public Criteria andFavoriteTypeUpdateLike(String value) {
            addCriterion("favorite_type_update like", value, "favoriteTypeUpdate");
            return (Criteria) this;
        }

        public Criteria andFavoriteTypeUpdateNotLike(String value) {
            addCriterion("favorite_type_update not like", value, "favoriteTypeUpdate");
            return (Criteria) this;
        }

        public Criteria andFavoriteTypeUpdateIn(List<String> values) {
            addCriterion("favorite_type_update in", values, "favoriteTypeUpdate");
            return (Criteria) this;
        }

        public Criteria andFavoriteTypeUpdateNotIn(List<String> values) {
            addCriterion("favorite_type_update not in", values, "favoriteTypeUpdate");
            return (Criteria) this;
        }

        public Criteria andFavoriteTypeUpdateBetween(String value1, String value2) {
            addCriterion("favorite_type_update between", value1, value2, "favoriteTypeUpdate");
            return (Criteria) this;
        }

        public Criteria andFavoriteTypeUpdateNotBetween(String value1, String value2) {
            addCriterion("favorite_type_update not between", value1, value2, "favoriteTypeUpdate");
            return (Criteria) this;
        }

        public Criteria andFavTypeNoteIsNull() {
            addCriterion("fav_type_note is null");
            return (Criteria) this;
        }

        public Criteria andFavTypeNoteIsNotNull() {
            addCriterion("fav_type_note is not null");
            return (Criteria) this;
        }

        public Criteria andFavTypeNoteEqualTo(String value) {
            addCriterion("fav_type_note =", value, "favTypeNote");
            return (Criteria) this;
        }

        public Criteria andFavTypeNoteNotEqualTo(String value) {
            addCriterion("fav_type_note <>", value, "favTypeNote");
            return (Criteria) this;
        }

        public Criteria andFavTypeNoteGreaterThan(String value) {
            addCriterion("fav_type_note >", value, "favTypeNote");
            return (Criteria) this;
        }

        public Criteria andFavTypeNoteGreaterThanOrEqualTo(String value) {
            addCriterion("fav_type_note >=", value, "favTypeNote");
            return (Criteria) this;
        }

        public Criteria andFavTypeNoteLessThan(String value) {
            addCriterion("fav_type_note <", value, "favTypeNote");
            return (Criteria) this;
        }

        public Criteria andFavTypeNoteLessThanOrEqualTo(String value) {
            addCriterion("fav_type_note <=", value, "favTypeNote");
            return (Criteria) this;
        }

        public Criteria andFavTypeNoteLike(String value) {
            addCriterion("fav_type_note like", value, "favTypeNote");
            return (Criteria) this;
        }

        public Criteria andFavTypeNoteNotLike(String value) {
            addCriterion("fav_type_note not like", value, "favTypeNote");
            return (Criteria) this;
        }

        public Criteria andFavTypeNoteIn(List<String> values) {
            addCriterion("fav_type_note in", values, "favTypeNote");
            return (Criteria) this;
        }

        public Criteria andFavTypeNoteNotIn(List<String> values) {
            addCriterion("fav_type_note not in", values, "favTypeNote");
            return (Criteria) this;
        }

        public Criteria andFavTypeNoteBetween(String value1, String value2) {
            addCriterion("fav_type_note between", value1, value2, "favTypeNote");
            return (Criteria) this;
        }

        public Criteria andFavTypeNoteNotBetween(String value1, String value2) {
            addCriterion("fav_type_note not between", value1, value2, "favTypeNote");
            return (Criteria) this;
        }

        public Criteria andCmpyTypeIsNull() {
            addCriterion("cmpy_type is null");
            return (Criteria) this;
        }

        public Criteria andCmpyTypeIsNotNull() {
            addCriterion("cmpy_type is not null");
            return (Criteria) this;
        }

        public Criteria andCmpyTypeEqualTo(Integer value) {
            addCriterion("cmpy_type =", value, "cmpyType");
            return (Criteria) this;
        }

        public Criteria andCmpyTypeNotEqualTo(Integer value) {
            addCriterion("cmpy_type <>", value, "cmpyType");
            return (Criteria) this;
        }

        public Criteria andCmpyTypeGreaterThan(Integer value) {
            addCriterion("cmpy_type >", value, "cmpyType");
            return (Criteria) this;
        }

        public Criteria andCmpyTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("cmpy_type >=", value, "cmpyType");
            return (Criteria) this;
        }

        public Criteria andCmpyTypeLessThan(Integer value) {
            addCriterion("cmpy_type <", value, "cmpyType");
            return (Criteria) this;
        }

        public Criteria andCmpyTypeLessThanOrEqualTo(Integer value) {
            addCriterion("cmpy_type <=", value, "cmpyType");
            return (Criteria) this;
        }

        public Criteria andCmpyTypeIn(List<Integer> values) {
            addCriterion("cmpy_type in", values, "cmpyType");
            return (Criteria) this;
        }

        public Criteria andCmpyTypeNotIn(List<Integer> values) {
            addCriterion("cmpy_type not in", values, "cmpyType");
            return (Criteria) this;
        }

        public Criteria andCmpyTypeBetween(Integer value1, Integer value2) {
            addCriterion("cmpy_type between", value1, value2, "cmpyType");
            return (Criteria) this;
        }

        public Criteria andCmpyTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("cmpy_type not between", value1, value2, "cmpyType");
            return (Criteria) this;
        }

        public Criteria andNoteIsNull() {
            addCriterion("note is null");
            return (Criteria) this;
        }

        public Criteria andNoteIsNotNull() {
            addCriterion("note is not null");
            return (Criteria) this;
        }

        public Criteria andNoteEqualTo(String value) {
            addCriterion("note =", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotEqualTo(String value) {
            addCriterion("note <>", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteGreaterThan(String value) {
            addCriterion("note >", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteGreaterThanOrEqualTo(String value) {
            addCriterion("note >=", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteLessThan(String value) {
            addCriterion("note <", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteLessThanOrEqualTo(String value) {
            addCriterion("note <=", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteLike(String value) {
            addCriterion("note like", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotLike(String value) {
            addCriterion("note not like", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteIn(List<String> values) {
            addCriterion("note in", values, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotIn(List<String> values) {
            addCriterion("note not in", values, "note");
            return (Criteria) this;
        }

        public Criteria andNoteBetween(String value1, String value2) {
            addCriterion("note between", value1, value2, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotBetween(String value1, String value2) {
            addCriterion("note not between", value1, value2, "note");
            return (Criteria) this;
        }

        public Criteria andDataStatusIsNull() {
            addCriterion("data_status is null");
            return (Criteria) this;
        }

        public Criteria andDataStatusIsNotNull() {
            addCriterion("data_status is not null");
            return (Criteria) this;
        }

        public Criteria andDataStatusEqualTo(Integer value) {
            addCriterion("data_status =", value, "dataStatus");
            return (Criteria) this;
        }

        public Criteria andDataStatusNotEqualTo(Integer value) {
            addCriterion("data_status <>", value, "dataStatus");
            return (Criteria) this;
        }

        public Criteria andDataStatusGreaterThan(Integer value) {
            addCriterion("data_status >", value, "dataStatus");
            return (Criteria) this;
        }

        public Criteria andDataStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("data_status >=", value, "dataStatus");
            return (Criteria) this;
        }

        public Criteria andDataStatusLessThan(Integer value) {
            addCriterion("data_status <", value, "dataStatus");
            return (Criteria) this;
        }

        public Criteria andDataStatusLessThanOrEqualTo(Integer value) {
            addCriterion("data_status <=", value, "dataStatus");
            return (Criteria) this;
        }

        public Criteria andDataStatusIn(List<Integer> values) {
            addCriterion("data_status in", values, "dataStatus");
            return (Criteria) this;
        }

        public Criteria andDataStatusNotIn(List<Integer> values) {
            addCriterion("data_status not in", values, "dataStatus");
            return (Criteria) this;
        }

        public Criteria andDataStatusBetween(Integer value1, Integer value2) {
            addCriterion("data_status between", value1, value2, "dataStatus");
            return (Criteria) this;
        }

        public Criteria andDataStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("data_status not between", value1, value2, "dataStatus");
            return (Criteria) this;
        }

        public Criteria andDataQualityIsNull() {
            addCriterion("data_quality is null");
            return (Criteria) this;
        }

        public Criteria andDataQualityIsNotNull() {
            addCriterion("data_quality is not null");
            return (Criteria) this;
        }

        public Criteria andDataQualityEqualTo(Integer value) {
            addCriterion("data_quality =", value, "dataQuality");
            return (Criteria) this;
        }

        public Criteria andDataQualityNotEqualTo(Integer value) {
            addCriterion("data_quality <>", value, "dataQuality");
            return (Criteria) this;
        }

        public Criteria andDataQualityGreaterThan(Integer value) {
            addCriterion("data_quality >", value, "dataQuality");
            return (Criteria) this;
        }

        public Criteria andDataQualityGreaterThanOrEqualTo(Integer value) {
            addCriterion("data_quality >=", value, "dataQuality");
            return (Criteria) this;
        }

        public Criteria andDataQualityLessThan(Integer value) {
            addCriterion("data_quality <", value, "dataQuality");
            return (Criteria) this;
        }

        public Criteria andDataQualityLessThanOrEqualTo(Integer value) {
            addCriterion("data_quality <=", value, "dataQuality");
            return (Criteria) this;
        }

        public Criteria andDataQualityIn(List<Integer> values) {
            addCriterion("data_quality in", values, "dataQuality");
            return (Criteria) this;
        }

        public Criteria andDataQualityNotIn(List<Integer> values) {
            addCriterion("data_quality not in", values, "dataQuality");
            return (Criteria) this;
        }

        public Criteria andDataQualityBetween(Integer value1, Integer value2) {
            addCriterion("data_quality between", value1, value2, "dataQuality");
            return (Criteria) this;
        }

        public Criteria andDataQualityNotBetween(Integer value1, Integer value2) {
            addCriterion("data_quality not between", value1, value2, "dataQuality");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
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

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateByIsNull() {
            addCriterion("create_by is null");
            return (Criteria) this;
        }

        public Criteria andCreateByIsNotNull() {
            addCriterion("create_by is not null");
            return (Criteria) this;
        }

        public Criteria andCreateByEqualTo(Long value) {
            addCriterion("create_by =", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotEqualTo(Long value) {
            addCriterion("create_by <>", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByGreaterThan(Long value) {
            addCriterion("create_by >", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByGreaterThanOrEqualTo(Long value) {
            addCriterion("create_by >=", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLessThan(Long value) {
            addCriterion("create_by <", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLessThanOrEqualTo(Long value) {
            addCriterion("create_by <=", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByIn(List<Long> values) {
            addCriterion("create_by in", values, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotIn(List<Long> values) {
            addCriterion("create_by not in", values, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByBetween(Long value1, Long value2) {
            addCriterion("create_by between", value1, value2, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotBetween(Long value1, Long value2) {
            addCriterion("create_by not between", value1, value2, "createBy");
            return (Criteria) this;
        }

        public Criteria andSyncTimeIsNull() {
            addCriterion("sync_time is null");
            return (Criteria) this;
        }

        public Criteria andSyncTimeIsNotNull() {
            addCriterion("sync_time is not null");
            return (Criteria) this;
        }

        public Criteria andSyncTimeEqualTo(Date value) {
            addCriterion("sync_time =", value, "syncTime");
            return (Criteria) this;
        }

        public Criteria andSyncTimeNotEqualTo(Date value) {
            addCriterion("sync_time <>", value, "syncTime");
            return (Criteria) this;
        }

        public Criteria andSyncTimeGreaterThan(Date value) {
            addCriterion("sync_time >", value, "syncTime");
            return (Criteria) this;
        }

        public Criteria andSyncTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("sync_time >=", value, "syncTime");
            return (Criteria) this;
        }

        public Criteria andSyncTimeLessThan(Date value) {
            addCriterion("sync_time <", value, "syncTime");
            return (Criteria) this;
        }

        public Criteria andSyncTimeLessThanOrEqualTo(Date value) {
            addCriterion("sync_time <=", value, "syncTime");
            return (Criteria) this;
        }

        public Criteria andSyncTimeIn(List<Date> values) {
            addCriterion("sync_time in", values, "syncTime");
            return (Criteria) this;
        }

        public Criteria andSyncTimeNotIn(List<Date> values) {
            addCriterion("sync_time not in", values, "syncTime");
            return (Criteria) this;
        }

        public Criteria andSyncTimeBetween(Date value1, Date value2) {
            addCriterion("sync_time between", value1, value2, "syncTime");
            return (Criteria) this;
        }

        public Criteria andSyncTimeNotBetween(Date value1, Date value2) {
            addCriterion("sync_time not between", value1, value2, "syncTime");
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