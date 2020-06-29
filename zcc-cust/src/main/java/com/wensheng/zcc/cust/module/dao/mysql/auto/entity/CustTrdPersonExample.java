package com.wensheng.zcc.cust.module.dao.mysql.auto.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustTrdPersonExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CustTrdPersonExample() {
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

        public Criteria andOrigIdIsNull() {
            addCriterion("orig_id is null");
            return (Criteria) this;
        }

        public Criteria andOrigIdIsNotNull() {
            addCriterion("orig_id is not null");
            return (Criteria) this;
        }

        public Criteria andOrigIdEqualTo(String value) {
            addCriterion("orig_id =", value, "origId");
            return (Criteria) this;
        }

        public Criteria andOrigIdNotEqualTo(String value) {
            addCriterion("orig_id <>", value, "origId");
            return (Criteria) this;
        }

        public Criteria andOrigIdGreaterThan(String value) {
            addCriterion("orig_id >", value, "origId");
            return (Criteria) this;
        }

        public Criteria andOrigIdGreaterThanOrEqualTo(String value) {
            addCriterion("orig_id >=", value, "origId");
            return (Criteria) this;
        }

        public Criteria andOrigIdLessThan(String value) {
            addCriterion("orig_id <", value, "origId");
            return (Criteria) this;
        }

        public Criteria andOrigIdLessThanOrEqualTo(String value) {
            addCriterion("orig_id <=", value, "origId");
            return (Criteria) this;
        }

        public Criteria andOrigIdLike(String value) {
            addCriterion("orig_id like", value, "origId");
            return (Criteria) this;
        }

        public Criteria andOrigIdNotLike(String value) {
            addCriterion("orig_id not like", value, "origId");
            return (Criteria) this;
        }

        public Criteria andOrigIdIn(List<String> values) {
            addCriterion("orig_id in", values, "origId");
            return (Criteria) this;
        }

        public Criteria andOrigIdNotIn(List<String> values) {
            addCriterion("orig_id not in", values, "origId");
            return (Criteria) this;
        }

        public Criteria andOrigIdBetween(String value1, String value2) {
            addCriterion("orig_id between", value1, value2, "origId");
            return (Criteria) this;
        }

        public Criteria andOrigIdNotBetween(String value1, String value2) {
            addCriterion("orig_id not between", value1, value2, "origId");
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

        public Criteria andAgeRangeIsNull() {
            addCriterion("age_range is null");
            return (Criteria) this;
        }

        public Criteria andAgeRangeIsNotNull() {
            addCriterion("age_range is not null");
            return (Criteria) this;
        }

        public Criteria andAgeRangeEqualTo(Integer value) {
            addCriterion("age_range =", value, "ageRange");
            return (Criteria) this;
        }

        public Criteria andAgeRangeNotEqualTo(Integer value) {
            addCriterion("age_range <>", value, "ageRange");
            return (Criteria) this;
        }

        public Criteria andAgeRangeGreaterThan(Integer value) {
            addCriterion("age_range >", value, "ageRange");
            return (Criteria) this;
        }

        public Criteria andAgeRangeGreaterThanOrEqualTo(Integer value) {
            addCriterion("age_range >=", value, "ageRange");
            return (Criteria) this;
        }

        public Criteria andAgeRangeLessThan(Integer value) {
            addCriterion("age_range <", value, "ageRange");
            return (Criteria) this;
        }

        public Criteria andAgeRangeLessThanOrEqualTo(Integer value) {
            addCriterion("age_range <=", value, "ageRange");
            return (Criteria) this;
        }

        public Criteria andAgeRangeIn(List<Integer> values) {
            addCriterion("age_range in", values, "ageRange");
            return (Criteria) this;
        }

        public Criteria andAgeRangeNotIn(List<Integer> values) {
            addCriterion("age_range not in", values, "ageRange");
            return (Criteria) this;
        }

        public Criteria andAgeRangeBetween(Integer value1, Integer value2) {
            addCriterion("age_range between", value1, value2, "ageRange");
            return (Criteria) this;
        }

        public Criteria andAgeRangeNotBetween(Integer value1, Integer value2) {
            addCriterion("age_range not between", value1, value2, "ageRange");
            return (Criteria) this;
        }

        public Criteria andGenderIsNull() {
            addCriterion("gender is null");
            return (Criteria) this;
        }

        public Criteria andGenderIsNotNull() {
            addCriterion("gender is not null");
            return (Criteria) this;
        }

        public Criteria andGenderEqualTo(Integer value) {
            addCriterion("gender =", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotEqualTo(Integer value) {
            addCriterion("gender <>", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderGreaterThan(Integer value) {
            addCriterion("gender >", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderGreaterThanOrEqualTo(Integer value) {
            addCriterion("gender >=", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderLessThan(Integer value) {
            addCriterion("gender <", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderLessThanOrEqualTo(Integer value) {
            addCriterion("gender <=", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderIn(List<Integer> values) {
            addCriterion("gender in", values, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotIn(List<Integer> values) {
            addCriterion("gender not in", values, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderBetween(Integer value1, Integer value2) {
            addCriterion("gender between", value1, value2, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotBetween(Integer value1, Integer value2) {
            addCriterion("gender not between", value1, value2, "gender");
            return (Criteria) this;
        }

        public Criteria andMobileUpdateIsNull() {
            addCriterion("mobile_update is null");
            return (Criteria) this;
        }

        public Criteria andMobileUpdateIsNotNull() {
            addCriterion("mobile_update is not null");
            return (Criteria) this;
        }

        public Criteria andMobileUpdateEqualTo(String value) {
            addCriterion("mobile_update =", value, "mobileUpdate");
            return (Criteria) this;
        }

        public Criteria andMobileUpdateNotEqualTo(String value) {
            addCriterion("mobile_update <>", value, "mobileUpdate");
            return (Criteria) this;
        }

        public Criteria andMobileUpdateGreaterThan(String value) {
            addCriterion("mobile_update >", value, "mobileUpdate");
            return (Criteria) this;
        }

        public Criteria andMobileUpdateGreaterThanOrEqualTo(String value) {
            addCriterion("mobile_update >=", value, "mobileUpdate");
            return (Criteria) this;
        }

        public Criteria andMobileUpdateLessThan(String value) {
            addCriterion("mobile_update <", value, "mobileUpdate");
            return (Criteria) this;
        }

        public Criteria andMobileUpdateLessThanOrEqualTo(String value) {
            addCriterion("mobile_update <=", value, "mobileUpdate");
            return (Criteria) this;
        }

        public Criteria andMobileUpdateLike(String value) {
            addCriterion("mobile_update like", value, "mobileUpdate");
            return (Criteria) this;
        }

        public Criteria andMobileUpdateNotLike(String value) {
            addCriterion("mobile_update not like", value, "mobileUpdate");
            return (Criteria) this;
        }

        public Criteria andMobileUpdateIn(List<String> values) {
            addCriterion("mobile_update in", values, "mobileUpdate");
            return (Criteria) this;
        }

        public Criteria andMobileUpdateNotIn(List<String> values) {
            addCriterion("mobile_update not in", values, "mobileUpdate");
            return (Criteria) this;
        }

        public Criteria andMobileUpdateBetween(String value1, String value2) {
            addCriterion("mobile_update between", value1, value2, "mobileUpdate");
            return (Criteria) this;
        }

        public Criteria andMobileUpdateNotBetween(String value1, String value2) {
            addCriterion("mobile_update not between", value1, value2, "mobileUpdate");
            return (Criteria) this;
        }

        public Criteria andMobilePrepIsNull() {
            addCriterion("mobile_prep is null");
            return (Criteria) this;
        }

        public Criteria andMobilePrepIsNotNull() {
            addCriterion("mobile_prep is not null");
            return (Criteria) this;
        }

        public Criteria andMobilePrepEqualTo(String value) {
            addCriterion("mobile_prep =", value, "mobilePrep");
            return (Criteria) this;
        }

        public Criteria andMobilePrepNotEqualTo(String value) {
            addCriterion("mobile_prep <>", value, "mobilePrep");
            return (Criteria) this;
        }

        public Criteria andMobilePrepGreaterThan(String value) {
            addCriterion("mobile_prep >", value, "mobilePrep");
            return (Criteria) this;
        }

        public Criteria andMobilePrepGreaterThanOrEqualTo(String value) {
            addCriterion("mobile_prep >=", value, "mobilePrep");
            return (Criteria) this;
        }

        public Criteria andMobilePrepLessThan(String value) {
            addCriterion("mobile_prep <", value, "mobilePrep");
            return (Criteria) this;
        }

        public Criteria andMobilePrepLessThanOrEqualTo(String value) {
            addCriterion("mobile_prep <=", value, "mobilePrep");
            return (Criteria) this;
        }

        public Criteria andMobilePrepLike(String value) {
            addCriterion("mobile_prep like", value, "mobilePrep");
            return (Criteria) this;
        }

        public Criteria andMobilePrepNotLike(String value) {
            addCriterion("mobile_prep not like", value, "mobilePrep");
            return (Criteria) this;
        }

        public Criteria andMobilePrepIn(List<String> values) {
            addCriterion("mobile_prep in", values, "mobilePrep");
            return (Criteria) this;
        }

        public Criteria andMobilePrepNotIn(List<String> values) {
            addCriterion("mobile_prep not in", values, "mobilePrep");
            return (Criteria) this;
        }

        public Criteria andMobilePrepBetween(String value1, String value2) {
            addCriterion("mobile_prep between", value1, value2, "mobilePrep");
            return (Criteria) this;
        }

        public Criteria andMobilePrepNotBetween(String value1, String value2) {
            addCriterion("mobile_prep not between", value1, value2, "mobilePrep");
            return (Criteria) this;
        }

        public Criteria andMobileHistoryIsNull() {
            addCriterion("mobile_history is null");
            return (Criteria) this;
        }

        public Criteria andMobileHistoryIsNotNull() {
            addCriterion("mobile_history is not null");
            return (Criteria) this;
        }

        public Criteria andMobileHistoryEqualTo(String value) {
            addCriterion("mobile_history =", value, "mobileHistory");
            return (Criteria) this;
        }

        public Criteria andMobileHistoryNotEqualTo(String value) {
            addCriterion("mobile_history <>", value, "mobileHistory");
            return (Criteria) this;
        }

        public Criteria andMobileHistoryGreaterThan(String value) {
            addCriterion("mobile_history >", value, "mobileHistory");
            return (Criteria) this;
        }

        public Criteria andMobileHistoryGreaterThanOrEqualTo(String value) {
            addCriterion("mobile_history >=", value, "mobileHistory");
            return (Criteria) this;
        }

        public Criteria andMobileHistoryLessThan(String value) {
            addCriterion("mobile_history <", value, "mobileHistory");
            return (Criteria) this;
        }

        public Criteria andMobileHistoryLessThanOrEqualTo(String value) {
            addCriterion("mobile_history <=", value, "mobileHistory");
            return (Criteria) this;
        }

        public Criteria andMobileHistoryLike(String value) {
            addCriterion("mobile_history like", value, "mobileHistory");
            return (Criteria) this;
        }

        public Criteria andMobileHistoryNotLike(String value) {
            addCriterion("mobile_history not like", value, "mobileHistory");
            return (Criteria) this;
        }

        public Criteria andMobileHistoryIn(List<String> values) {
            addCriterion("mobile_history in", values, "mobileHistory");
            return (Criteria) this;
        }

        public Criteria andMobileHistoryNotIn(List<String> values) {
            addCriterion("mobile_history not in", values, "mobileHistory");
            return (Criteria) this;
        }

        public Criteria andMobileHistoryBetween(String value1, String value2) {
            addCriterion("mobile_history between", value1, value2, "mobileHistory");
            return (Criteria) this;
        }

        public Criteria andMobileHistoryNotBetween(String value1, String value2) {
            addCriterion("mobile_history not between", value1, value2, "mobileHistory");
            return (Criteria) this;
        }

        public Criteria andPhoneUpdateIsNull() {
            addCriterion("phone_update is null");
            return (Criteria) this;
        }

        public Criteria andPhoneUpdateIsNotNull() {
            addCriterion("phone_update is not null");
            return (Criteria) this;
        }

        public Criteria andPhoneUpdateEqualTo(String value) {
            addCriterion("phone_update =", value, "phoneUpdate");
            return (Criteria) this;
        }

        public Criteria andPhoneUpdateNotEqualTo(String value) {
            addCriterion("phone_update <>", value, "phoneUpdate");
            return (Criteria) this;
        }

        public Criteria andPhoneUpdateGreaterThan(String value) {
            addCriterion("phone_update >", value, "phoneUpdate");
            return (Criteria) this;
        }

        public Criteria andPhoneUpdateGreaterThanOrEqualTo(String value) {
            addCriterion("phone_update >=", value, "phoneUpdate");
            return (Criteria) this;
        }

        public Criteria andPhoneUpdateLessThan(String value) {
            addCriterion("phone_update <", value, "phoneUpdate");
            return (Criteria) this;
        }

        public Criteria andPhoneUpdateLessThanOrEqualTo(String value) {
            addCriterion("phone_update <=", value, "phoneUpdate");
            return (Criteria) this;
        }

        public Criteria andPhoneUpdateLike(String value) {
            addCriterion("phone_update like", value, "phoneUpdate");
            return (Criteria) this;
        }

        public Criteria andPhoneUpdateNotLike(String value) {
            addCriterion("phone_update not like", value, "phoneUpdate");
            return (Criteria) this;
        }

        public Criteria andPhoneUpdateIn(List<String> values) {
            addCriterion("phone_update in", values, "phoneUpdate");
            return (Criteria) this;
        }

        public Criteria andPhoneUpdateNotIn(List<String> values) {
            addCriterion("phone_update not in", values, "phoneUpdate");
            return (Criteria) this;
        }

        public Criteria andPhoneUpdateBetween(String value1, String value2) {
            addCriterion("phone_update between", value1, value2, "phoneUpdate");
            return (Criteria) this;
        }

        public Criteria andPhoneUpdateNotBetween(String value1, String value2) {
            addCriterion("phone_update not between", value1, value2, "phoneUpdate");
            return (Criteria) this;
        }

        public Criteria andPhonePrepIsNull() {
            addCriterion("phone_prep is null");
            return (Criteria) this;
        }

        public Criteria andPhonePrepIsNotNull() {
            addCriterion("phone_prep is not null");
            return (Criteria) this;
        }

        public Criteria andPhonePrepEqualTo(String value) {
            addCriterion("phone_prep =", value, "phonePrep");
            return (Criteria) this;
        }

        public Criteria andPhonePrepNotEqualTo(String value) {
            addCriterion("phone_prep <>", value, "phonePrep");
            return (Criteria) this;
        }

        public Criteria andPhonePrepGreaterThan(String value) {
            addCriterion("phone_prep >", value, "phonePrep");
            return (Criteria) this;
        }

        public Criteria andPhonePrepGreaterThanOrEqualTo(String value) {
            addCriterion("phone_prep >=", value, "phonePrep");
            return (Criteria) this;
        }

        public Criteria andPhonePrepLessThan(String value) {
            addCriterion("phone_prep <", value, "phonePrep");
            return (Criteria) this;
        }

        public Criteria andPhonePrepLessThanOrEqualTo(String value) {
            addCriterion("phone_prep <=", value, "phonePrep");
            return (Criteria) this;
        }

        public Criteria andPhonePrepLike(String value) {
            addCriterion("phone_prep like", value, "phonePrep");
            return (Criteria) this;
        }

        public Criteria andPhonePrepNotLike(String value) {
            addCriterion("phone_prep not like", value, "phonePrep");
            return (Criteria) this;
        }

        public Criteria andPhonePrepIn(List<String> values) {
            addCriterion("phone_prep in", values, "phonePrep");
            return (Criteria) this;
        }

        public Criteria andPhonePrepNotIn(List<String> values) {
            addCriterion("phone_prep not in", values, "phonePrep");
            return (Criteria) this;
        }

        public Criteria andPhonePrepBetween(String value1, String value2) {
            addCriterion("phone_prep between", value1, value2, "phonePrep");
            return (Criteria) this;
        }

        public Criteria andPhonePrepNotBetween(String value1, String value2) {
            addCriterion("phone_prep not between", value1, value2, "phonePrep");
            return (Criteria) this;
        }

        public Criteria andPhoneHistoryIsNull() {
            addCriterion("phone_history is null");
            return (Criteria) this;
        }

        public Criteria andPhoneHistoryIsNotNull() {
            addCriterion("phone_history is not null");
            return (Criteria) this;
        }

        public Criteria andPhoneHistoryEqualTo(String value) {
            addCriterion("phone_history =", value, "phoneHistory");
            return (Criteria) this;
        }

        public Criteria andPhoneHistoryNotEqualTo(String value) {
            addCriterion("phone_history <>", value, "phoneHistory");
            return (Criteria) this;
        }

        public Criteria andPhoneHistoryGreaterThan(String value) {
            addCriterion("phone_history >", value, "phoneHistory");
            return (Criteria) this;
        }

        public Criteria andPhoneHistoryGreaterThanOrEqualTo(String value) {
            addCriterion("phone_history >=", value, "phoneHistory");
            return (Criteria) this;
        }

        public Criteria andPhoneHistoryLessThan(String value) {
            addCriterion("phone_history <", value, "phoneHistory");
            return (Criteria) this;
        }

        public Criteria andPhoneHistoryLessThanOrEqualTo(String value) {
            addCriterion("phone_history <=", value, "phoneHistory");
            return (Criteria) this;
        }

        public Criteria andPhoneHistoryLike(String value) {
            addCriterion("phone_history like", value, "phoneHistory");
            return (Criteria) this;
        }

        public Criteria andPhoneHistoryNotLike(String value) {
            addCriterion("phone_history not like", value, "phoneHistory");
            return (Criteria) this;
        }

        public Criteria andPhoneHistoryIn(List<String> values) {
            addCriterion("phone_history in", values, "phoneHistory");
            return (Criteria) this;
        }

        public Criteria andPhoneHistoryNotIn(List<String> values) {
            addCriterion("phone_history not in", values, "phoneHistory");
            return (Criteria) this;
        }

        public Criteria andPhoneHistoryBetween(String value1, String value2) {
            addCriterion("phone_history between", value1, value2, "phoneHistory");
            return (Criteria) this;
        }

        public Criteria andPhoneHistoryNotBetween(String value1, String value2) {
            addCriterion("phone_history not between", value1, value2, "phoneHistory");
            return (Criteria) this;
        }

        public Criteria andEmailIsNull() {
            addCriterion("email is null");
            return (Criteria) this;
        }

        public Criteria andEmailIsNotNull() {
            addCriterion("email is not null");
            return (Criteria) this;
        }

        public Criteria andEmailEqualTo(String value) {
            addCriterion("email =", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotEqualTo(String value) {
            addCriterion("email <>", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThan(String value) {
            addCriterion("email >", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThanOrEqualTo(String value) {
            addCriterion("email >=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThan(String value) {
            addCriterion("email <", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThanOrEqualTo(String value) {
            addCriterion("email <=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLike(String value) {
            addCriterion("email like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotLike(String value) {
            addCriterion("email not like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailIn(List<String> values) {
            addCriterion("email in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotIn(List<String> values) {
            addCriterion("email not in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailBetween(String value1, String value2) {
            addCriterion("email between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotBetween(String value1, String value2) {
            addCriterion("email not between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andIdCardNumIsNull() {
            addCriterion("id_card_num is null");
            return (Criteria) this;
        }

        public Criteria andIdCardNumIsNotNull() {
            addCriterion("id_card_num is not null");
            return (Criteria) this;
        }

        public Criteria andIdCardNumEqualTo(String value) {
            addCriterion("id_card_num =", value, "idCardNum");
            return (Criteria) this;
        }

        public Criteria andIdCardNumNotEqualTo(String value) {
            addCriterion("id_card_num <>", value, "idCardNum");
            return (Criteria) this;
        }

        public Criteria andIdCardNumGreaterThan(String value) {
            addCriterion("id_card_num >", value, "idCardNum");
            return (Criteria) this;
        }

        public Criteria andIdCardNumGreaterThanOrEqualTo(String value) {
            addCriterion("id_card_num >=", value, "idCardNum");
            return (Criteria) this;
        }

        public Criteria andIdCardNumLessThan(String value) {
            addCriterion("id_card_num <", value, "idCardNum");
            return (Criteria) this;
        }

        public Criteria andIdCardNumLessThanOrEqualTo(String value) {
            addCriterion("id_card_num <=", value, "idCardNum");
            return (Criteria) this;
        }

        public Criteria andIdCardNumLike(String value) {
            addCriterion("id_card_num like", value, "idCardNum");
            return (Criteria) this;
        }

        public Criteria andIdCardNumNotLike(String value) {
            addCriterion("id_card_num not like", value, "idCardNum");
            return (Criteria) this;
        }

        public Criteria andIdCardNumIn(List<String> values) {
            addCriterion("id_card_num in", values, "idCardNum");
            return (Criteria) this;
        }

        public Criteria andIdCardNumNotIn(List<String> values) {
            addCriterion("id_card_num not in", values, "idCardNum");
            return (Criteria) this;
        }

        public Criteria andIdCardNumBetween(String value1, String value2) {
            addCriterion("id_card_num between", value1, value2, "idCardNum");
            return (Criteria) this;
        }

        public Criteria andIdCardNumNotBetween(String value1, String value2) {
            addCriterion("id_card_num not between", value1, value2, "idCardNum");
            return (Criteria) this;
        }

        public Criteria andProvinceIsNull() {
            addCriterion("province is null");
            return (Criteria) this;
        }

        public Criteria andProvinceIsNotNull() {
            addCriterion("province is not null");
            return (Criteria) this;
        }

        public Criteria andProvinceEqualTo(String value) {
            addCriterion("province =", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotEqualTo(String value) {
            addCriterion("province <>", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceGreaterThan(String value) {
            addCriterion("province >", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceGreaterThanOrEqualTo(String value) {
            addCriterion("province >=", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceLessThan(String value) {
            addCriterion("province <", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceLessThanOrEqualTo(String value) {
            addCriterion("province <=", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceLike(String value) {
            addCriterion("province like", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotLike(String value) {
            addCriterion("province not like", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceIn(List<String> values) {
            addCriterion("province in", values, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotIn(List<String> values) {
            addCriterion("province not in", values, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceBetween(String value1, String value2) {
            addCriterion("province between", value1, value2, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotBetween(String value1, String value2) {
            addCriterion("province not between", value1, value2, "province");
            return (Criteria) this;
        }

        public Criteria andCityIsNull() {
            addCriterion("city is null");
            return (Criteria) this;
        }

        public Criteria andCityIsNotNull() {
            addCriterion("city is not null");
            return (Criteria) this;
        }

        public Criteria andCityEqualTo(String value) {
            addCriterion("city =", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotEqualTo(String value) {
            addCriterion("city <>", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityGreaterThan(String value) {
            addCriterion("city >", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityGreaterThanOrEqualTo(String value) {
            addCriterion("city >=", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLessThan(String value) {
            addCriterion("city <", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLessThanOrEqualTo(String value) {
            addCriterion("city <=", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLike(String value) {
            addCriterion("city like", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotLike(String value) {
            addCriterion("city not like", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityIn(List<String> values) {
            addCriterion("city in", values, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotIn(List<String> values) {
            addCriterion("city not in", values, "city");
            return (Criteria) this;
        }

        public Criteria andCityBetween(String value1, String value2) {
            addCriterion("city between", value1, value2, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotBetween(String value1, String value2) {
            addCriterion("city not between", value1, value2, "city");
            return (Criteria) this;
        }

        public Criteria andAddrIsNull() {
            addCriterion("addr is null");
            return (Criteria) this;
        }

        public Criteria andAddrIsNotNull() {
            addCriterion("addr is not null");
            return (Criteria) this;
        }

        public Criteria andAddrEqualTo(String value) {
            addCriterion("addr =", value, "addr");
            return (Criteria) this;
        }

        public Criteria andAddrNotEqualTo(String value) {
            addCriterion("addr <>", value, "addr");
            return (Criteria) this;
        }

        public Criteria andAddrGreaterThan(String value) {
            addCriterion("addr >", value, "addr");
            return (Criteria) this;
        }

        public Criteria andAddrGreaterThanOrEqualTo(String value) {
            addCriterion("addr >=", value, "addr");
            return (Criteria) this;
        }

        public Criteria andAddrLessThan(String value) {
            addCriterion("addr <", value, "addr");
            return (Criteria) this;
        }

        public Criteria andAddrLessThanOrEqualTo(String value) {
            addCriterion("addr <=", value, "addr");
            return (Criteria) this;
        }

        public Criteria andAddrLike(String value) {
            addCriterion("addr like", value, "addr");
            return (Criteria) this;
        }

        public Criteria andAddrNotLike(String value) {
            addCriterion("addr not like", value, "addr");
            return (Criteria) this;
        }

        public Criteria andAddrIn(List<String> values) {
            addCriterion("addr in", values, "addr");
            return (Criteria) this;
        }

        public Criteria andAddrNotIn(List<String> values) {
            addCriterion("addr not in", values, "addr");
            return (Criteria) this;
        }

        public Criteria andAddrBetween(String value1, String value2) {
            addCriterion("addr between", value1, value2, "addr");
            return (Criteria) this;
        }

        public Criteria andAddrNotBetween(String value1, String value2) {
            addCriterion("addr not between", value1, value2, "addr");
            return (Criteria) this;
        }

        public Criteria andHistoryAddrIsNull() {
            addCriterion("history_addr is null");
            return (Criteria) this;
        }

        public Criteria andHistoryAddrIsNotNull() {
            addCriterion("history_addr is not null");
            return (Criteria) this;
        }

        public Criteria andHistoryAddrEqualTo(String value) {
            addCriterion("history_addr =", value, "historyAddr");
            return (Criteria) this;
        }

        public Criteria andHistoryAddrNotEqualTo(String value) {
            addCriterion("history_addr <>", value, "historyAddr");
            return (Criteria) this;
        }

        public Criteria andHistoryAddrGreaterThan(String value) {
            addCriterion("history_addr >", value, "historyAddr");
            return (Criteria) this;
        }

        public Criteria andHistoryAddrGreaterThanOrEqualTo(String value) {
            addCriterion("history_addr >=", value, "historyAddr");
            return (Criteria) this;
        }

        public Criteria andHistoryAddrLessThan(String value) {
            addCriterion("history_addr <", value, "historyAddr");
            return (Criteria) this;
        }

        public Criteria andHistoryAddrLessThanOrEqualTo(String value) {
            addCriterion("history_addr <=", value, "historyAddr");
            return (Criteria) this;
        }

        public Criteria andHistoryAddrLike(String value) {
            addCriterion("history_addr like", value, "historyAddr");
            return (Criteria) this;
        }

        public Criteria andHistoryAddrNotLike(String value) {
            addCriterion("history_addr not like", value, "historyAddr");
            return (Criteria) this;
        }

        public Criteria andHistoryAddrIn(List<String> values) {
            addCriterion("history_addr in", values, "historyAddr");
            return (Criteria) this;
        }

        public Criteria andHistoryAddrNotIn(List<String> values) {
            addCriterion("history_addr not in", values, "historyAddr");
            return (Criteria) this;
        }

        public Criteria andHistoryAddrBetween(String value1, String value2) {
            addCriterion("history_addr between", value1, value2, "historyAddr");
            return (Criteria) this;
        }

        public Criteria andHistoryAddrNotBetween(String value1, String value2) {
            addCriterion("history_addr not between", value1, value2, "historyAddr");
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

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andTrdInfoBakIsNull() {
            addCriterion("trd_info_bak is null");
            return (Criteria) this;
        }

        public Criteria andTrdInfoBakIsNotNull() {
            addCriterion("trd_info_bak is not null");
            return (Criteria) this;
        }

        public Criteria andTrdInfoBakEqualTo(String value) {
            addCriterion("trd_info_bak =", value, "trdInfoBak");
            return (Criteria) this;
        }

        public Criteria andTrdInfoBakNotEqualTo(String value) {
            addCriterion("trd_info_bak <>", value, "trdInfoBak");
            return (Criteria) this;
        }

        public Criteria andTrdInfoBakGreaterThan(String value) {
            addCriterion("trd_info_bak >", value, "trdInfoBak");
            return (Criteria) this;
        }

        public Criteria andTrdInfoBakGreaterThanOrEqualTo(String value) {
            addCriterion("trd_info_bak >=", value, "trdInfoBak");
            return (Criteria) this;
        }

        public Criteria andTrdInfoBakLessThan(String value) {
            addCriterion("trd_info_bak <", value, "trdInfoBak");
            return (Criteria) this;
        }

        public Criteria andTrdInfoBakLessThanOrEqualTo(String value) {
            addCriterion("trd_info_bak <=", value, "trdInfoBak");
            return (Criteria) this;
        }

        public Criteria andTrdInfoBakLike(String value) {
            addCriterion("trd_info_bak like", value, "trdInfoBak");
            return (Criteria) this;
        }

        public Criteria andTrdInfoBakNotLike(String value) {
            addCriterion("trd_info_bak not like", value, "trdInfoBak");
            return (Criteria) this;
        }

        public Criteria andTrdInfoBakIn(List<String> values) {
            addCriterion("trd_info_bak in", values, "trdInfoBak");
            return (Criteria) this;
        }

        public Criteria andTrdInfoBakNotIn(List<String> values) {
            addCriterion("trd_info_bak not in", values, "trdInfoBak");
            return (Criteria) this;
        }

        public Criteria andTrdInfoBakBetween(String value1, String value2) {
            addCriterion("trd_info_bak between", value1, value2, "trdInfoBak");
            return (Criteria) this;
        }

        public Criteria andTrdInfoBakNotBetween(String value1, String value2) {
            addCriterion("trd_info_bak not between", value1, value2, "trdInfoBak");
            return (Criteria) this;
        }

        public Criteria andMobileNumBakIsNull() {
            addCriterion("mobile_num_bak is null");
            return (Criteria) this;
        }

        public Criteria andMobileNumBakIsNotNull() {
            addCriterion("mobile_num_bak is not null");
            return (Criteria) this;
        }

        public Criteria andMobileNumBakEqualTo(String value) {
            addCriterion("mobile_num_bak =", value, "mobileNumBak");
            return (Criteria) this;
        }

        public Criteria andMobileNumBakNotEqualTo(String value) {
            addCriterion("mobile_num_bak <>", value, "mobileNumBak");
            return (Criteria) this;
        }

        public Criteria andMobileNumBakGreaterThan(String value) {
            addCriterion("mobile_num_bak >", value, "mobileNumBak");
            return (Criteria) this;
        }

        public Criteria andMobileNumBakGreaterThanOrEqualTo(String value) {
            addCriterion("mobile_num_bak >=", value, "mobileNumBak");
            return (Criteria) this;
        }

        public Criteria andMobileNumBakLessThan(String value) {
            addCriterion("mobile_num_bak <", value, "mobileNumBak");
            return (Criteria) this;
        }

        public Criteria andMobileNumBakLessThanOrEqualTo(String value) {
            addCriterion("mobile_num_bak <=", value, "mobileNumBak");
            return (Criteria) this;
        }

        public Criteria andMobileNumBakLike(String value) {
            addCriterion("mobile_num_bak like", value, "mobileNumBak");
            return (Criteria) this;
        }

        public Criteria andMobileNumBakNotLike(String value) {
            addCriterion("mobile_num_bak not like", value, "mobileNumBak");
            return (Criteria) this;
        }

        public Criteria andMobileNumBakIn(List<String> values) {
            addCriterion("mobile_num_bak in", values, "mobileNumBak");
            return (Criteria) this;
        }

        public Criteria andMobileNumBakNotIn(List<String> values) {
            addCriterion("mobile_num_bak not in", values, "mobileNumBak");
            return (Criteria) this;
        }

        public Criteria andMobileNumBakBetween(String value1, String value2) {
            addCriterion("mobile_num_bak between", value1, value2, "mobileNumBak");
            return (Criteria) this;
        }

        public Criteria andMobileNumBakNotBetween(String value1, String value2) {
            addCriterion("mobile_num_bak not between", value1, value2, "mobileNumBak");
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