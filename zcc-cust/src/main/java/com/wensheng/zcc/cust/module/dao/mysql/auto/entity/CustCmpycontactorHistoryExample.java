package com.wensheng.zcc.cust.module.dao.mysql.auto.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustCmpycontactorHistoryExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CustCmpycontactorHistoryExample() {
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

        public Criteria andCmpycontactorIdIsNull() {
            addCriterion("cmpycontactor_id is null");
            return (Criteria) this;
        }

        public Criteria andCmpycontactorIdIsNotNull() {
            addCriterion("cmpycontactor_id is not null");
            return (Criteria) this;
        }

        public Criteria andCmpycontactorIdEqualTo(Long value) {
            addCriterion("cmpycontactor_id =", value, "cmpycontactorId");
            return (Criteria) this;
        }

        public Criteria andCmpycontactorIdNotEqualTo(Long value) {
            addCriterion("cmpycontactor_id <>", value, "cmpycontactorId");
            return (Criteria) this;
        }

        public Criteria andCmpycontactorIdGreaterThan(Long value) {
            addCriterion("cmpycontactor_id >", value, "cmpycontactorId");
            return (Criteria) this;
        }

        public Criteria andCmpycontactorIdGreaterThanOrEqualTo(Long value) {
            addCriterion("cmpycontactor_id >=", value, "cmpycontactorId");
            return (Criteria) this;
        }

        public Criteria andCmpycontactorIdLessThan(Long value) {
            addCriterion("cmpycontactor_id <", value, "cmpycontactorId");
            return (Criteria) this;
        }

        public Criteria andCmpycontactorIdLessThanOrEqualTo(Long value) {
            addCriterion("cmpycontactor_id <=", value, "cmpycontactorId");
            return (Criteria) this;
        }

        public Criteria andCmpycontactorIdIn(List<Long> values) {
            addCriterion("cmpycontactor_id in", values, "cmpycontactorId");
            return (Criteria) this;
        }

        public Criteria andCmpycontactorIdNotIn(List<Long> values) {
            addCriterion("cmpycontactor_id not in", values, "cmpycontactorId");
            return (Criteria) this;
        }

        public Criteria andCmpycontactorIdBetween(Long value1, Long value2) {
            addCriterion("cmpycontactor_id between", value1, value2, "cmpycontactorId");
            return (Criteria) this;
        }

        public Criteria andCmpycontactorIdNotBetween(Long value1, Long value2) {
            addCriterion("cmpycontactor_id not between", value1, value2, "cmpycontactorId");
            return (Criteria) this;
        }

        public Criteria andUpdateMethodIsNull() {
            addCriterion("update_method is null");
            return (Criteria) this;
        }

        public Criteria andUpdateMethodIsNotNull() {
            addCriterion("update_method is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateMethodEqualTo(String value) {
            addCriterion("update_method =", value, "updateMethod");
            return (Criteria) this;
        }

        public Criteria andUpdateMethodNotEqualTo(String value) {
            addCriterion("update_method <>", value, "updateMethod");
            return (Criteria) this;
        }

        public Criteria andUpdateMethodGreaterThan(String value) {
            addCriterion("update_method >", value, "updateMethod");
            return (Criteria) this;
        }

        public Criteria andUpdateMethodGreaterThanOrEqualTo(String value) {
            addCriterion("update_method >=", value, "updateMethod");
            return (Criteria) this;
        }

        public Criteria andUpdateMethodLessThan(String value) {
            addCriterion("update_method <", value, "updateMethod");
            return (Criteria) this;
        }

        public Criteria andUpdateMethodLessThanOrEqualTo(String value) {
            addCriterion("update_method <=", value, "updateMethod");
            return (Criteria) this;
        }

        public Criteria andUpdateMethodLike(String value) {
            addCriterion("update_method like", value, "updateMethod");
            return (Criteria) this;
        }

        public Criteria andUpdateMethodNotLike(String value) {
            addCriterion("update_method not like", value, "updateMethod");
            return (Criteria) this;
        }

        public Criteria andUpdateMethodIn(List<String> values) {
            addCriterion("update_method in", values, "updateMethod");
            return (Criteria) this;
        }

        public Criteria andUpdateMethodNotIn(List<String> values) {
            addCriterion("update_method not in", values, "updateMethod");
            return (Criteria) this;
        }

        public Criteria andUpdateMethodBetween(String value1, String value2) {
            addCriterion("update_method between", value1, value2, "updateMethod");
            return (Criteria) this;
        }

        public Criteria andUpdateMethodNotBetween(String value1, String value2) {
            addCriterion("update_method not between", value1, value2, "updateMethod");
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

        public Criteria andPhoneIsNull() {
            addCriterion("phone is null");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNotNull() {
            addCriterion("phone is not null");
            return (Criteria) this;
        }

        public Criteria andPhoneEqualTo(String value) {
            addCriterion("phone =", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotEqualTo(String value) {
            addCriterion("phone <>", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThan(String value) {
            addCriterion("phone >", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("phone >=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThan(String value) {
            addCriterion("phone <", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThanOrEqualTo(String value) {
            addCriterion("phone <=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLike(String value) {
            addCriterion("phone like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotLike(String value) {
            addCriterion("phone not like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneIn(List<String> values) {
            addCriterion("phone in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotIn(List<String> values) {
            addCriterion("phone not in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneBetween(String value1, String value2) {
            addCriterion("phone between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotBetween(String value1, String value2) {
            addCriterion("phone not between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andMobileIsNull() {
            addCriterion("mobile is null");
            return (Criteria) this;
        }

        public Criteria andMobileIsNotNull() {
            addCriterion("mobile is not null");
            return (Criteria) this;
        }

        public Criteria andMobileEqualTo(String value) {
            addCriterion("mobile =", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotEqualTo(String value) {
            addCriterion("mobile <>", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThan(String value) {
            addCriterion("mobile >", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThanOrEqualTo(String value) {
            addCriterion("mobile >=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThan(String value) {
            addCriterion("mobile <", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThanOrEqualTo(String value) {
            addCriterion("mobile <=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLike(String value) {
            addCriterion("mobile like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotLike(String value) {
            addCriterion("mobile not like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileIn(List<String> values) {
            addCriterion("mobile in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotIn(List<String> values) {
            addCriterion("mobile not in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileBetween(String value1, String value2) {
            addCriterion("mobile between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotBetween(String value1, String value2) {
            addCriterion("mobile not between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andTrdPhoneIsNull() {
            addCriterion("trd_phone is null");
            return (Criteria) this;
        }

        public Criteria andTrdPhoneIsNotNull() {
            addCriterion("trd_phone is not null");
            return (Criteria) this;
        }

        public Criteria andTrdPhoneEqualTo(String value) {
            addCriterion("trd_phone =", value, "trdPhone");
            return (Criteria) this;
        }

        public Criteria andTrdPhoneNotEqualTo(String value) {
            addCriterion("trd_phone <>", value, "trdPhone");
            return (Criteria) this;
        }

        public Criteria andTrdPhoneGreaterThan(String value) {
            addCriterion("trd_phone >", value, "trdPhone");
            return (Criteria) this;
        }

        public Criteria andTrdPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("trd_phone >=", value, "trdPhone");
            return (Criteria) this;
        }

        public Criteria andTrdPhoneLessThan(String value) {
            addCriterion("trd_phone <", value, "trdPhone");
            return (Criteria) this;
        }

        public Criteria andTrdPhoneLessThanOrEqualTo(String value) {
            addCriterion("trd_phone <=", value, "trdPhone");
            return (Criteria) this;
        }

        public Criteria andTrdPhoneLike(String value) {
            addCriterion("trd_phone like", value, "trdPhone");
            return (Criteria) this;
        }

        public Criteria andTrdPhoneNotLike(String value) {
            addCriterion("trd_phone not like", value, "trdPhone");
            return (Criteria) this;
        }

        public Criteria andTrdPhoneIn(List<String> values) {
            addCriterion("trd_phone in", values, "trdPhone");
            return (Criteria) this;
        }

        public Criteria andTrdPhoneNotIn(List<String> values) {
            addCriterion("trd_phone not in", values, "trdPhone");
            return (Criteria) this;
        }

        public Criteria andTrdPhoneBetween(String value1, String value2) {
            addCriterion("trd_phone between", value1, value2, "trdPhone");
            return (Criteria) this;
        }

        public Criteria andTrdPhoneNotBetween(String value1, String value2) {
            addCriterion("trd_phone not between", value1, value2, "trdPhone");
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

        public Criteria andCountyIsNull() {
            addCriterion("county is null");
            return (Criteria) this;
        }

        public Criteria andCountyIsNotNull() {
            addCriterion("county is not null");
            return (Criteria) this;
        }

        public Criteria andCountyEqualTo(String value) {
            addCriterion("county =", value, "county");
            return (Criteria) this;
        }

        public Criteria andCountyNotEqualTo(String value) {
            addCriterion("county <>", value, "county");
            return (Criteria) this;
        }

        public Criteria andCountyGreaterThan(String value) {
            addCriterion("county >", value, "county");
            return (Criteria) this;
        }

        public Criteria andCountyGreaterThanOrEqualTo(String value) {
            addCriterion("county >=", value, "county");
            return (Criteria) this;
        }

        public Criteria andCountyLessThan(String value) {
            addCriterion("county <", value, "county");
            return (Criteria) this;
        }

        public Criteria andCountyLessThanOrEqualTo(String value) {
            addCriterion("county <=", value, "county");
            return (Criteria) this;
        }

        public Criteria andCountyLike(String value) {
            addCriterion("county like", value, "county");
            return (Criteria) this;
        }

        public Criteria andCountyNotLike(String value) {
            addCriterion("county not like", value, "county");
            return (Criteria) this;
        }

        public Criteria andCountyIn(List<String> values) {
            addCriterion("county in", values, "county");
            return (Criteria) this;
        }

        public Criteria andCountyNotIn(List<String> values) {
            addCriterion("county not in", values, "county");
            return (Criteria) this;
        }

        public Criteria andCountyBetween(String value1, String value2) {
            addCriterion("county between", value1, value2, "county");
            return (Criteria) this;
        }

        public Criteria andCountyNotBetween(String value1, String value2) {
            addCriterion("county not between", value1, value2, "county");
            return (Criteria) this;
        }

        public Criteria andAddressIsNull() {
            addCriterion("address is null");
            return (Criteria) this;
        }

        public Criteria andAddressIsNotNull() {
            addCriterion("address is not null");
            return (Criteria) this;
        }

        public Criteria andAddressEqualTo(String value) {
            addCriterion("address =", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotEqualTo(String value) {
            addCriterion("address <>", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThan(String value) {
            addCriterion("address >", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThanOrEqualTo(String value) {
            addCriterion("address >=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThan(String value) {
            addCriterion("address <", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThanOrEqualTo(String value) {
            addCriterion("address <=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLike(String value) {
            addCriterion("address like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotLike(String value) {
            addCriterion("address not like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressIn(List<String> values) {
            addCriterion("address in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotIn(List<String> values) {
            addCriterion("address not in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressBetween(String value1, String value2) {
            addCriterion("address between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotBetween(String value1, String value2) {
            addCriterion("address not between", value1, value2, "address");
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

        public Criteria andCompanyIsNull() {
            addCriterion("company is null");
            return (Criteria) this;
        }

        public Criteria andCompanyIsNotNull() {
            addCriterion("company is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyEqualTo(String value) {
            addCriterion("company =", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyNotEqualTo(String value) {
            addCriterion("company <>", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyGreaterThan(String value) {
            addCriterion("company >", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyGreaterThanOrEqualTo(String value) {
            addCriterion("company >=", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyLessThan(String value) {
            addCriterion("company <", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyLessThanOrEqualTo(String value) {
            addCriterion("company <=", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyLike(String value) {
            addCriterion("company like", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyNotLike(String value) {
            addCriterion("company not like", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyIn(List<String> values) {
            addCriterion("company in", values, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyNotIn(List<String> values) {
            addCriterion("company not in", values, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyBetween(String value1, String value2) {
            addCriterion("company between", value1, value2, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyNotBetween(String value1, String value2) {
            addCriterion("company not between", value1, value2, "company");
            return (Criteria) this;
        }

        public Criteria andBranchCmpyIsNull() {
            addCriterion("branch_cmpy is null");
            return (Criteria) this;
        }

        public Criteria andBranchCmpyIsNotNull() {
            addCriterion("branch_cmpy is not null");
            return (Criteria) this;
        }

        public Criteria andBranchCmpyEqualTo(String value) {
            addCriterion("branch_cmpy =", value, "branchCmpy");
            return (Criteria) this;
        }

        public Criteria andBranchCmpyNotEqualTo(String value) {
            addCriterion("branch_cmpy <>", value, "branchCmpy");
            return (Criteria) this;
        }

        public Criteria andBranchCmpyGreaterThan(String value) {
            addCriterion("branch_cmpy >", value, "branchCmpy");
            return (Criteria) this;
        }

        public Criteria andBranchCmpyGreaterThanOrEqualTo(String value) {
            addCriterion("branch_cmpy >=", value, "branchCmpy");
            return (Criteria) this;
        }

        public Criteria andBranchCmpyLessThan(String value) {
            addCriterion("branch_cmpy <", value, "branchCmpy");
            return (Criteria) this;
        }

        public Criteria andBranchCmpyLessThanOrEqualTo(String value) {
            addCriterion("branch_cmpy <=", value, "branchCmpy");
            return (Criteria) this;
        }

        public Criteria andBranchCmpyLike(String value) {
            addCriterion("branch_cmpy like", value, "branchCmpy");
            return (Criteria) this;
        }

        public Criteria andBranchCmpyNotLike(String value) {
            addCriterion("branch_cmpy not like", value, "branchCmpy");
            return (Criteria) this;
        }

        public Criteria andBranchCmpyIn(List<String> values) {
            addCriterion("branch_cmpy in", values, "branchCmpy");
            return (Criteria) this;
        }

        public Criteria andBranchCmpyNotIn(List<String> values) {
            addCriterion("branch_cmpy not in", values, "branchCmpy");
            return (Criteria) this;
        }

        public Criteria andBranchCmpyBetween(String value1, String value2) {
            addCriterion("branch_cmpy between", value1, value2, "branchCmpy");
            return (Criteria) this;
        }

        public Criteria andBranchCmpyNotBetween(String value1, String value2) {
            addCriterion("branch_cmpy not between", value1, value2, "branchCmpy");
            return (Criteria) this;
        }

        public Criteria andHistoryCmpyIsNull() {
            addCriterion("history_cmpy is null");
            return (Criteria) this;
        }

        public Criteria andHistoryCmpyIsNotNull() {
            addCriterion("history_cmpy is not null");
            return (Criteria) this;
        }

        public Criteria andHistoryCmpyEqualTo(String value) {
            addCriterion("history_cmpy =", value, "historyCmpy");
            return (Criteria) this;
        }

        public Criteria andHistoryCmpyNotEqualTo(String value) {
            addCriterion("history_cmpy <>", value, "historyCmpy");
            return (Criteria) this;
        }

        public Criteria andHistoryCmpyGreaterThan(String value) {
            addCriterion("history_cmpy >", value, "historyCmpy");
            return (Criteria) this;
        }

        public Criteria andHistoryCmpyGreaterThanOrEqualTo(String value) {
            addCriterion("history_cmpy >=", value, "historyCmpy");
            return (Criteria) this;
        }

        public Criteria andHistoryCmpyLessThan(String value) {
            addCriterion("history_cmpy <", value, "historyCmpy");
            return (Criteria) this;
        }

        public Criteria andHistoryCmpyLessThanOrEqualTo(String value) {
            addCriterion("history_cmpy <=", value, "historyCmpy");
            return (Criteria) this;
        }

        public Criteria andHistoryCmpyLike(String value) {
            addCriterion("history_cmpy like", value, "historyCmpy");
            return (Criteria) this;
        }

        public Criteria andHistoryCmpyNotLike(String value) {
            addCriterion("history_cmpy not like", value, "historyCmpy");
            return (Criteria) this;
        }

        public Criteria andHistoryCmpyIn(List<String> values) {
            addCriterion("history_cmpy in", values, "historyCmpy");
            return (Criteria) this;
        }

        public Criteria andHistoryCmpyNotIn(List<String> values) {
            addCriterion("history_cmpy not in", values, "historyCmpy");
            return (Criteria) this;
        }

        public Criteria andHistoryCmpyBetween(String value1, String value2) {
            addCriterion("history_cmpy between", value1, value2, "historyCmpy");
            return (Criteria) this;
        }

        public Criteria andHistoryCmpyNotBetween(String value1, String value2) {
            addCriterion("history_cmpy not between", value1, value2, "historyCmpy");
            return (Criteria) this;
        }

        public Criteria andRecorderNameIsNull() {
            addCriterion("recorder_name is null");
            return (Criteria) this;
        }

        public Criteria andRecorderNameIsNotNull() {
            addCriterion("recorder_name is not null");
            return (Criteria) this;
        }

        public Criteria andRecorderNameEqualTo(String value) {
            addCriterion("recorder_name =", value, "recorderName");
            return (Criteria) this;
        }

        public Criteria andRecorderNameNotEqualTo(String value) {
            addCriterion("recorder_name <>", value, "recorderName");
            return (Criteria) this;
        }

        public Criteria andRecorderNameGreaterThan(String value) {
            addCriterion("recorder_name >", value, "recorderName");
            return (Criteria) this;
        }

        public Criteria andRecorderNameGreaterThanOrEqualTo(String value) {
            addCriterion("recorder_name >=", value, "recorderName");
            return (Criteria) this;
        }

        public Criteria andRecorderNameLessThan(String value) {
            addCriterion("recorder_name <", value, "recorderName");
            return (Criteria) this;
        }

        public Criteria andRecorderNameLessThanOrEqualTo(String value) {
            addCriterion("recorder_name <=", value, "recorderName");
            return (Criteria) this;
        }

        public Criteria andRecorderNameLike(String value) {
            addCriterion("recorder_name like", value, "recorderName");
            return (Criteria) this;
        }

        public Criteria andRecorderNameNotLike(String value) {
            addCriterion("recorder_name not like", value, "recorderName");
            return (Criteria) this;
        }

        public Criteria andRecorderNameIn(List<String> values) {
            addCriterion("recorder_name in", values, "recorderName");
            return (Criteria) this;
        }

        public Criteria andRecorderNameNotIn(List<String> values) {
            addCriterion("recorder_name not in", values, "recorderName");
            return (Criteria) this;
        }

        public Criteria andRecorderNameBetween(String value1, String value2) {
            addCriterion("recorder_name between", value1, value2, "recorderName");
            return (Criteria) this;
        }

        public Criteria andRecorderNameNotBetween(String value1, String value2) {
            addCriterion("recorder_name not between", value1, value2, "recorderName");
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

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
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