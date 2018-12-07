package com.wensheng.zcc.amc.module.dao.mysql.auto.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AmcCmpyShareholderExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AmcCmpyShareholderExample() {
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

        public Criteria andAmountIsNull() {
            addCriterion("amount is null");
            return (Criteria) this;
        }

        public Criteria andAmountIsNotNull() {
            addCriterion("amount is not null");
            return (Criteria) this;
        }

        public Criteria andAmountEqualTo(String value) {
            addCriterion("amount =", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotEqualTo(String value) {
            addCriterion("amount <>", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThan(String value) {
            addCriterion("amount >", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThanOrEqualTo(String value) {
            addCriterion("amount >=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThan(String value) {
            addCriterion("amount <", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThanOrEqualTo(String value) {
            addCriterion("amount <=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLike(String value) {
            addCriterion("amount like", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotLike(String value) {
            addCriterion("amount not like", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountIn(List<String> values) {
            addCriterion("amount in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotIn(List<String> values) {
            addCriterion("amount not in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountBetween(String value1, String value2) {
            addCriterion("amount between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotBetween(String value1, String value2) {
            addCriterion("amount not between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andPercentageIsNull() {
            addCriterion("percentage is null");
            return (Criteria) this;
        }

        public Criteria andPercentageIsNotNull() {
            addCriterion("percentage is not null");
            return (Criteria) this;
        }

        public Criteria andPercentageEqualTo(String value) {
            addCriterion("percentage =", value, "percentage");
            return (Criteria) this;
        }

        public Criteria andPercentageNotEqualTo(String value) {
            addCriterion("percentage <>", value, "percentage");
            return (Criteria) this;
        }

        public Criteria andPercentageGreaterThan(String value) {
            addCriterion("percentage >", value, "percentage");
            return (Criteria) this;
        }

        public Criteria andPercentageGreaterThanOrEqualTo(String value) {
            addCriterion("percentage >=", value, "percentage");
            return (Criteria) this;
        }

        public Criteria andPercentageLessThan(String value) {
            addCriterion("percentage <", value, "percentage");
            return (Criteria) this;
        }

        public Criteria andPercentageLessThanOrEqualTo(String value) {
            addCriterion("percentage <=", value, "percentage");
            return (Criteria) this;
        }

        public Criteria andPercentageLike(String value) {
            addCriterion("percentage like", value, "percentage");
            return (Criteria) this;
        }

        public Criteria andPercentageNotLike(String value) {
            addCriterion("percentage not like", value, "percentage");
            return (Criteria) this;
        }

        public Criteria andPercentageIn(List<String> values) {
            addCriterion("percentage in", values, "percentage");
            return (Criteria) this;
        }

        public Criteria andPercentageNotIn(List<String> values) {
            addCriterion("percentage not in", values, "percentage");
            return (Criteria) this;
        }

        public Criteria andPercentageBetween(String value1, String value2) {
            addCriterion("percentage between", value1, value2, "percentage");
            return (Criteria) this;
        }

        public Criteria andPercentageNotBetween(String value1, String value2) {
            addCriterion("percentage not between", value1, value2, "percentage");
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

        public Criteria andPersonIdIsNull() {
            addCriterion("person_id is null");
            return (Criteria) this;
        }

        public Criteria andPersonIdIsNotNull() {
            addCriterion("person_id is not null");
            return (Criteria) this;
        }

        public Criteria andPersonIdEqualTo(Long value) {
            addCriterion("person_id =", value, "personId");
            return (Criteria) this;
        }

        public Criteria andPersonIdNotEqualTo(Long value) {
            addCriterion("person_id <>", value, "personId");
            return (Criteria) this;
        }

        public Criteria andPersonIdGreaterThan(Long value) {
            addCriterion("person_id >", value, "personId");
            return (Criteria) this;
        }

        public Criteria andPersonIdGreaterThanOrEqualTo(Long value) {
            addCriterion("person_id >=", value, "personId");
            return (Criteria) this;
        }

        public Criteria andPersonIdLessThan(Long value) {
            addCriterion("person_id <", value, "personId");
            return (Criteria) this;
        }

        public Criteria andPersonIdLessThanOrEqualTo(Long value) {
            addCriterion("person_id <=", value, "personId");
            return (Criteria) this;
        }

        public Criteria andPersonIdIn(List<Long> values) {
            addCriterion("person_id in", values, "personId");
            return (Criteria) this;
        }

        public Criteria andPersonIdNotIn(List<Long> values) {
            addCriterion("person_id not in", values, "personId");
            return (Criteria) this;
        }

        public Criteria andPersonIdBetween(Long value1, Long value2) {
            addCriterion("person_id between", value1, value2, "personId");
            return (Criteria) this;
        }

        public Criteria andPersonIdNotBetween(Long value1, Long value2) {
            addCriterion("person_id not between", value1, value2, "personId");
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

        public Criteria andSubscribedAmountIsNull() {
            addCriterion("subscribed_amount is null");
            return (Criteria) this;
        }

        public Criteria andSubscribedAmountIsNotNull() {
            addCriterion("subscribed_amount is not null");
            return (Criteria) this;
        }

        public Criteria andSubscribedAmountEqualTo(String value) {
            addCriterion("subscribed_amount =", value, "subscribedAmount");
            return (Criteria) this;
        }

        public Criteria andSubscribedAmountNotEqualTo(String value) {
            addCriterion("subscribed_amount <>", value, "subscribedAmount");
            return (Criteria) this;
        }

        public Criteria andSubscribedAmountGreaterThan(String value) {
            addCriterion("subscribed_amount >", value, "subscribedAmount");
            return (Criteria) this;
        }

        public Criteria andSubscribedAmountGreaterThanOrEqualTo(String value) {
            addCriterion("subscribed_amount >=", value, "subscribedAmount");
            return (Criteria) this;
        }

        public Criteria andSubscribedAmountLessThan(String value) {
            addCriterion("subscribed_amount <", value, "subscribedAmount");
            return (Criteria) this;
        }

        public Criteria andSubscribedAmountLessThanOrEqualTo(String value) {
            addCriterion("subscribed_amount <=", value, "subscribedAmount");
            return (Criteria) this;
        }

        public Criteria andSubscribedAmountLike(String value) {
            addCriterion("subscribed_amount like", value, "subscribedAmount");
            return (Criteria) this;
        }

        public Criteria andSubscribedAmountNotLike(String value) {
            addCriterion("subscribed_amount not like", value, "subscribedAmount");
            return (Criteria) this;
        }

        public Criteria andSubscribedAmountIn(List<String> values) {
            addCriterion("subscribed_amount in", values, "subscribedAmount");
            return (Criteria) this;
        }

        public Criteria andSubscribedAmountNotIn(List<String> values) {
            addCriterion("subscribed_amount not in", values, "subscribedAmount");
            return (Criteria) this;
        }

        public Criteria andSubscribedAmountBetween(String value1, String value2) {
            addCriterion("subscribed_amount between", value1, value2, "subscribedAmount");
            return (Criteria) this;
        }

        public Criteria andSubscribedAmountNotBetween(String value1, String value2) {
            addCriterion("subscribed_amount not between", value1, value2, "subscribedAmount");
            return (Criteria) this;
        }

        public Criteria andSubscribedDateIsNull() {
            addCriterion("subscribed_date is null");
            return (Criteria) this;
        }

        public Criteria andSubscribedDateIsNotNull() {
            addCriterion("subscribed_date is not null");
            return (Criteria) this;
        }

        public Criteria andSubscribedDateEqualTo(Date value) {
            addCriterion("subscribed_date =", value, "subscribedDate");
            return (Criteria) this;
        }

        public Criteria andSubscribedDateNotEqualTo(Date value) {
            addCriterion("subscribed_date <>", value, "subscribedDate");
            return (Criteria) this;
        }

        public Criteria andSubscribedDateGreaterThan(Date value) {
            addCriterion("subscribed_date >", value, "subscribedDate");
            return (Criteria) this;
        }

        public Criteria andSubscribedDateGreaterThanOrEqualTo(Date value) {
            addCriterion("subscribed_date >=", value, "subscribedDate");
            return (Criteria) this;
        }

        public Criteria andSubscribedDateLessThan(Date value) {
            addCriterion("subscribed_date <", value, "subscribedDate");
            return (Criteria) this;
        }

        public Criteria andSubscribedDateLessThanOrEqualTo(Date value) {
            addCriterion("subscribed_date <=", value, "subscribedDate");
            return (Criteria) this;
        }

        public Criteria andSubscribedDateIn(List<Date> values) {
            addCriterion("subscribed_date in", values, "subscribedDate");
            return (Criteria) this;
        }

        public Criteria andSubscribedDateNotIn(List<Date> values) {
            addCriterion("subscribed_date not in", values, "subscribedDate");
            return (Criteria) this;
        }

        public Criteria andSubscribedDateBetween(Date value1, Date value2) {
            addCriterion("subscribed_date between", value1, value2, "subscribedDate");
            return (Criteria) this;
        }

        public Criteria andSubscribedDateNotBetween(Date value1, Date value2) {
            addCriterion("subscribed_date not between", value1, value2, "subscribedDate");
            return (Criteria) this;
        }

        public Criteria andPaidAmountIsNull() {
            addCriterion("paid_amount is null");
            return (Criteria) this;
        }

        public Criteria andPaidAmountIsNotNull() {
            addCriterion("paid_amount is not null");
            return (Criteria) this;
        }

        public Criteria andPaidAmountEqualTo(String value) {
            addCriterion("paid_amount =", value, "paidAmount");
            return (Criteria) this;
        }

        public Criteria andPaidAmountNotEqualTo(String value) {
            addCriterion("paid_amount <>", value, "paidAmount");
            return (Criteria) this;
        }

        public Criteria andPaidAmountGreaterThan(String value) {
            addCriterion("paid_amount >", value, "paidAmount");
            return (Criteria) this;
        }

        public Criteria andPaidAmountGreaterThanOrEqualTo(String value) {
            addCriterion("paid_amount >=", value, "paidAmount");
            return (Criteria) this;
        }

        public Criteria andPaidAmountLessThan(String value) {
            addCriterion("paid_amount <", value, "paidAmount");
            return (Criteria) this;
        }

        public Criteria andPaidAmountLessThanOrEqualTo(String value) {
            addCriterion("paid_amount <=", value, "paidAmount");
            return (Criteria) this;
        }

        public Criteria andPaidAmountLike(String value) {
            addCriterion("paid_amount like", value, "paidAmount");
            return (Criteria) this;
        }

        public Criteria andPaidAmountNotLike(String value) {
            addCriterion("paid_amount not like", value, "paidAmount");
            return (Criteria) this;
        }

        public Criteria andPaidAmountIn(List<String> values) {
            addCriterion("paid_amount in", values, "paidAmount");
            return (Criteria) this;
        }

        public Criteria andPaidAmountNotIn(List<String> values) {
            addCriterion("paid_amount not in", values, "paidAmount");
            return (Criteria) this;
        }

        public Criteria andPaidAmountBetween(String value1, String value2) {
            addCriterion("paid_amount between", value1, value2, "paidAmount");
            return (Criteria) this;
        }

        public Criteria andPaidAmountNotBetween(String value1, String value2) {
            addCriterion("paid_amount not between", value1, value2, "paidAmount");
            return (Criteria) this;
        }

        public Criteria andPaidDateIsNull() {
            addCriterion("paid_date is null");
            return (Criteria) this;
        }

        public Criteria andPaidDateIsNotNull() {
            addCriterion("paid_date is not null");
            return (Criteria) this;
        }

        public Criteria andPaidDateEqualTo(Date value) {
            addCriterion("paid_date =", value, "paidDate");
            return (Criteria) this;
        }

        public Criteria andPaidDateNotEqualTo(Date value) {
            addCriterion("paid_date <>", value, "paidDate");
            return (Criteria) this;
        }

        public Criteria andPaidDateGreaterThan(Date value) {
            addCriterion("paid_date >", value, "paidDate");
            return (Criteria) this;
        }

        public Criteria andPaidDateGreaterThanOrEqualTo(Date value) {
            addCriterion("paid_date >=", value, "paidDate");
            return (Criteria) this;
        }

        public Criteria andPaidDateLessThan(Date value) {
            addCriterion("paid_date <", value, "paidDate");
            return (Criteria) this;
        }

        public Criteria andPaidDateLessThanOrEqualTo(Date value) {
            addCriterion("paid_date <=", value, "paidDate");
            return (Criteria) this;
        }

        public Criteria andPaidDateIn(List<Date> values) {
            addCriterion("paid_date in", values, "paidDate");
            return (Criteria) this;
        }

        public Criteria andPaidDateNotIn(List<Date> values) {
            addCriterion("paid_date not in", values, "paidDate");
            return (Criteria) this;
        }

        public Criteria andPaidDateBetween(Date value1, Date value2) {
            addCriterion("paid_date between", value1, value2, "paidDate");
            return (Criteria) this;
        }

        public Criteria andPaidDateNotBetween(Date value1, Date value2) {
            addCriterion("paid_date not between", value1, value2, "paidDate");
            return (Criteria) this;
        }

        public Criteria andParentHolderIdIsNull() {
            addCriterion("parent_holder_id is null");
            return (Criteria) this;
        }

        public Criteria andParentHolderIdIsNotNull() {
            addCriterion("parent_holder_id is not null");
            return (Criteria) this;
        }

        public Criteria andParentHolderIdEqualTo(Long value) {
            addCriterion("parent_holder_id =", value, "parentHolderId");
            return (Criteria) this;
        }

        public Criteria andParentHolderIdNotEqualTo(Long value) {
            addCriterion("parent_holder_id <>", value, "parentHolderId");
            return (Criteria) this;
        }

        public Criteria andParentHolderIdGreaterThan(Long value) {
            addCriterion("parent_holder_id >", value, "parentHolderId");
            return (Criteria) this;
        }

        public Criteria andParentHolderIdGreaterThanOrEqualTo(Long value) {
            addCriterion("parent_holder_id >=", value, "parentHolderId");
            return (Criteria) this;
        }

        public Criteria andParentHolderIdLessThan(Long value) {
            addCriterion("parent_holder_id <", value, "parentHolderId");
            return (Criteria) this;
        }

        public Criteria andParentHolderIdLessThanOrEqualTo(Long value) {
            addCriterion("parent_holder_id <=", value, "parentHolderId");
            return (Criteria) this;
        }

        public Criteria andParentHolderIdIn(List<Long> values) {
            addCriterion("parent_holder_id in", values, "parentHolderId");
            return (Criteria) this;
        }

        public Criteria andParentHolderIdNotIn(List<Long> values) {
            addCriterion("parent_holder_id not in", values, "parentHolderId");
            return (Criteria) this;
        }

        public Criteria andParentHolderIdBetween(Long value1, Long value2) {
            addCriterion("parent_holder_id between", value1, value2, "parentHolderId");
            return (Criteria) this;
        }

        public Criteria andParentHolderIdNotBetween(Long value1, Long value2) {
            addCriterion("parent_holder_id not between", value1, value2, "parentHolderId");
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

        public Criteria andLinkIsNull() {
            addCriterion("link is null");
            return (Criteria) this;
        }

        public Criteria andLinkIsNotNull() {
            addCriterion("link is not null");
            return (Criteria) this;
        }

        public Criteria andLinkEqualTo(String value) {
            addCriterion("link =", value, "link");
            return (Criteria) this;
        }

        public Criteria andLinkNotEqualTo(String value) {
            addCriterion("link <>", value, "link");
            return (Criteria) this;
        }

        public Criteria andLinkGreaterThan(String value) {
            addCriterion("link >", value, "link");
            return (Criteria) this;
        }

        public Criteria andLinkGreaterThanOrEqualTo(String value) {
            addCriterion("link >=", value, "link");
            return (Criteria) this;
        }

        public Criteria andLinkLessThan(String value) {
            addCriterion("link <", value, "link");
            return (Criteria) this;
        }

        public Criteria andLinkLessThanOrEqualTo(String value) {
            addCriterion("link <=", value, "link");
            return (Criteria) this;
        }

        public Criteria andLinkLike(String value) {
            addCriterion("link like", value, "link");
            return (Criteria) this;
        }

        public Criteria andLinkNotLike(String value) {
            addCriterion("link not like", value, "link");
            return (Criteria) this;
        }

        public Criteria andLinkIn(List<String> values) {
            addCriterion("link in", values, "link");
            return (Criteria) this;
        }

        public Criteria andLinkNotIn(List<String> values) {
            addCriterion("link not in", values, "link");
            return (Criteria) this;
        }

        public Criteria andLinkBetween(String value1, String value2) {
            addCriterion("link between", value1, value2, "link");
            return (Criteria) this;
        }

        public Criteria andLinkNotBetween(String value1, String value2) {
            addCriterion("link not between", value1, value2, "link");
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