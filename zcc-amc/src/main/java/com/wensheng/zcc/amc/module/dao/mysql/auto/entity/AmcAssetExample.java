package com.wensheng.zcc.amc.module.dao.mysql.auto.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class AmcAssetExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AmcAssetExample() {
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

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
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

        public Criteria andStateIsNull() {
            addCriterion("state is null");
            return (Criteria) this;
        }

        public Criteria andStateIsNotNull() {
            addCriterion("state is not null");
            return (Criteria) this;
        }

        public Criteria andStateEqualTo(Integer value) {
            addCriterion("state =", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotEqualTo(Integer value) {
            addCriterion("state <>", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThan(Integer value) {
            addCriterion("state >", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("state >=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThan(Integer value) {
            addCriterion("state <", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThanOrEqualTo(Integer value) {
            addCriterion("state <=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateIn(List<Integer> values) {
            addCriterion("state in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotIn(List<Integer> values) {
            addCriterion("state not in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateBetween(Integer value1, Integer value2) {
            addCriterion("state between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotBetween(Integer value1, Integer value2) {
            addCriterion("state not between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andEditStatusIsNull() {
            addCriterion("edit_status is null");
            return (Criteria) this;
        }

        public Criteria andEditStatusIsNotNull() {
            addCriterion("edit_status is not null");
            return (Criteria) this;
        }

        public Criteria andEditStatusEqualTo(Integer value) {
            addCriterion("edit_status =", value, "editStatus");
            return (Criteria) this;
        }

        public Criteria andEditStatusNotEqualTo(Integer value) {
            addCriterion("edit_status <>", value, "editStatus");
            return (Criteria) this;
        }

        public Criteria andEditStatusGreaterThan(Integer value) {
            addCriterion("edit_status >", value, "editStatus");
            return (Criteria) this;
        }

        public Criteria andEditStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("edit_status >=", value, "editStatus");
            return (Criteria) this;
        }

        public Criteria andEditStatusLessThan(Integer value) {
            addCriterion("edit_status <", value, "editStatus");
            return (Criteria) this;
        }

        public Criteria andEditStatusLessThanOrEqualTo(Integer value) {
            addCriterion("edit_status <=", value, "editStatus");
            return (Criteria) this;
        }

        public Criteria andEditStatusIn(List<Integer> values) {
            addCriterion("edit_status in", values, "editStatus");
            return (Criteria) this;
        }

        public Criteria andEditStatusNotIn(List<Integer> values) {
            addCriterion("edit_status not in", values, "editStatus");
            return (Criteria) this;
        }

        public Criteria andEditStatusBetween(Integer value1, Integer value2) {
            addCriterion("edit_status between", value1, value2, "editStatus");
            return (Criteria) this;
        }

        public Criteria andEditStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("edit_status not between", value1, value2, "editStatus");
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

        public Criteria andAmcAssetCodeIsNull() {
            addCriterion("amc_asset_code is null");
            return (Criteria) this;
        }

        public Criteria andAmcAssetCodeIsNotNull() {
            addCriterion("amc_asset_code is not null");
            return (Criteria) this;
        }

        public Criteria andAmcAssetCodeEqualTo(String value) {
            addCriterion("amc_asset_code =", value, "amcAssetCode");
            return (Criteria) this;
        }

        public Criteria andAmcAssetCodeNotEqualTo(String value) {
            addCriterion("amc_asset_code <>", value, "amcAssetCode");
            return (Criteria) this;
        }

        public Criteria andAmcAssetCodeGreaterThan(String value) {
            addCriterion("amc_asset_code >", value, "amcAssetCode");
            return (Criteria) this;
        }

        public Criteria andAmcAssetCodeGreaterThanOrEqualTo(String value) {
            addCriterion("amc_asset_code >=", value, "amcAssetCode");
            return (Criteria) this;
        }

        public Criteria andAmcAssetCodeLessThan(String value) {
            addCriterion("amc_asset_code <", value, "amcAssetCode");
            return (Criteria) this;
        }

        public Criteria andAmcAssetCodeLessThanOrEqualTo(String value) {
            addCriterion("amc_asset_code <=", value, "amcAssetCode");
            return (Criteria) this;
        }

        public Criteria andAmcAssetCodeLike(String value) {
            addCriterion("amc_asset_code like", value, "amcAssetCode");
            return (Criteria) this;
        }

        public Criteria andAmcAssetCodeNotLike(String value) {
            addCriterion("amc_asset_code not like", value, "amcAssetCode");
            return (Criteria) this;
        }

        public Criteria andAmcAssetCodeIn(List<String> values) {
            addCriterion("amc_asset_code in", values, "amcAssetCode");
            return (Criteria) this;
        }

        public Criteria andAmcAssetCodeNotIn(List<String> values) {
            addCriterion("amc_asset_code not in", values, "amcAssetCode");
            return (Criteria) this;
        }

        public Criteria andAmcAssetCodeBetween(String value1, String value2) {
            addCriterion("amc_asset_code between", value1, value2, "amcAssetCode");
            return (Criteria) this;
        }

        public Criteria andAmcAssetCodeNotBetween(String value1, String value2) {
            addCriterion("amc_asset_code not between", value1, value2, "amcAssetCode");
            return (Criteria) this;
        }

        public Criteria andZccAssetCodeIsNull() {
            addCriterion("zcc_asset_code is null");
            return (Criteria) this;
        }

        public Criteria andZccAssetCodeIsNotNull() {
            addCriterion("zcc_asset_code is not null");
            return (Criteria) this;
        }

        public Criteria andZccAssetCodeEqualTo(String value) {
            addCriterion("zcc_asset_code =", value, "zccAssetCode");
            return (Criteria) this;
        }

        public Criteria andZccAssetCodeNotEqualTo(String value) {
            addCriterion("zcc_asset_code <>", value, "zccAssetCode");
            return (Criteria) this;
        }

        public Criteria andZccAssetCodeGreaterThan(String value) {
            addCriterion("zcc_asset_code >", value, "zccAssetCode");
            return (Criteria) this;
        }

        public Criteria andZccAssetCodeGreaterThanOrEqualTo(String value) {
            addCriterion("zcc_asset_code >=", value, "zccAssetCode");
            return (Criteria) this;
        }

        public Criteria andZccAssetCodeLessThan(String value) {
            addCriterion("zcc_asset_code <", value, "zccAssetCode");
            return (Criteria) this;
        }

        public Criteria andZccAssetCodeLessThanOrEqualTo(String value) {
            addCriterion("zcc_asset_code <=", value, "zccAssetCode");
            return (Criteria) this;
        }

        public Criteria andZccAssetCodeLike(String value) {
            addCriterion("zcc_asset_code like", value, "zccAssetCode");
            return (Criteria) this;
        }

        public Criteria andZccAssetCodeNotLike(String value) {
            addCriterion("zcc_asset_code not like", value, "zccAssetCode");
            return (Criteria) this;
        }

        public Criteria andZccAssetCodeIn(List<String> values) {
            addCriterion("zcc_asset_code in", values, "zccAssetCode");
            return (Criteria) this;
        }

        public Criteria andZccAssetCodeNotIn(List<String> values) {
            addCriterion("zcc_asset_code not in", values, "zccAssetCode");
            return (Criteria) this;
        }

        public Criteria andZccAssetCodeBetween(String value1, String value2) {
            addCriterion("zcc_asset_code between", value1, value2, "zccAssetCode");
            return (Criteria) this;
        }

        public Criteria andZccAssetCodeNotBetween(String value1, String value2) {
            addCriterion("zcc_asset_code not between", value1, value2, "zccAssetCode");
            return (Criteria) this;
        }

        public Criteria andEstmPriceIsNull() {
            addCriterion("estm_price is null");
            return (Criteria) this;
        }

        public Criteria andEstmPriceIsNotNull() {
            addCriterion("estm_price is not null");
            return (Criteria) this;
        }

        public Criteria andEstmPriceEqualTo(Long value) {
            addCriterion("estm_price =", value, "estmPrice");
            return (Criteria) this;
        }

        public Criteria andEstmPriceNotEqualTo(Long value) {
            addCriterion("estm_price <>", value, "estmPrice");
            return (Criteria) this;
        }

        public Criteria andEstmPriceGreaterThan(Long value) {
            addCriterion("estm_price >", value, "estmPrice");
            return (Criteria) this;
        }

        public Criteria andEstmPriceGreaterThanOrEqualTo(Long value) {
            addCriterion("estm_price >=", value, "estmPrice");
            return (Criteria) this;
        }

        public Criteria andEstmPriceLessThan(Long value) {
            addCriterion("estm_price <", value, "estmPrice");
            return (Criteria) this;
        }

        public Criteria andEstmPriceLessThanOrEqualTo(Long value) {
            addCriterion("estm_price <=", value, "estmPrice");
            return (Criteria) this;
        }

        public Criteria andEstmPriceIn(List<Long> values) {
            addCriterion("estm_price in", values, "estmPrice");
            return (Criteria) this;
        }

        public Criteria andEstmPriceNotIn(List<Long> values) {
            addCriterion("estm_price not in", values, "estmPrice");
            return (Criteria) this;
        }

        public Criteria andEstmPriceBetween(Long value1, Long value2) {
            addCriterion("estm_price between", value1, value2, "estmPrice");
            return (Criteria) this;
        }

        public Criteria andEstmPriceNotBetween(Long value1, Long value2) {
            addCriterion("estm_price not between", value1, value2, "estmPrice");
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

        public Criteria andInitPriceIsNull() {
            addCriterion("init_price is null");
            return (Criteria) this;
        }

        public Criteria andInitPriceIsNotNull() {
            addCriterion("init_price is not null");
            return (Criteria) this;
        }

        public Criteria andInitPriceEqualTo(Long value) {
            addCriterion("init_price =", value, "initPrice");
            return (Criteria) this;
        }

        public Criteria andInitPriceNotEqualTo(Long value) {
            addCriterion("init_price <>", value, "initPrice");
            return (Criteria) this;
        }

        public Criteria andInitPriceGreaterThan(Long value) {
            addCriterion("init_price >", value, "initPrice");
            return (Criteria) this;
        }

        public Criteria andInitPriceGreaterThanOrEqualTo(Long value) {
            addCriterion("init_price >=", value, "initPrice");
            return (Criteria) this;
        }

        public Criteria andInitPriceLessThan(Long value) {
            addCriterion("init_price <", value, "initPrice");
            return (Criteria) this;
        }

        public Criteria andInitPriceLessThanOrEqualTo(Long value) {
            addCriterion("init_price <=", value, "initPrice");
            return (Criteria) this;
        }

        public Criteria andInitPriceIn(List<Long> values) {
            addCriterion("init_price in", values, "initPrice");
            return (Criteria) this;
        }

        public Criteria andInitPriceNotIn(List<Long> values) {
            addCriterion("init_price not in", values, "initPrice");
            return (Criteria) this;
        }

        public Criteria andInitPriceBetween(Long value1, Long value2) {
            addCriterion("init_price between", value1, value2, "initPrice");
            return (Criteria) this;
        }

        public Criteria andInitPriceNotBetween(Long value1, Long value2) {
            addCriterion("init_price not between", value1, value2, "initPrice");
            return (Criteria) this;
        }

        public Criteria andRestrictionsIsNull() {
            addCriterion("restrictions is null");
            return (Criteria) this;
        }

        public Criteria andRestrictionsIsNotNull() {
            addCriterion("restrictions is not null");
            return (Criteria) this;
        }

        public Criteria andRestrictionsEqualTo(Integer value) {
            addCriterion("restrictions =", value, "restrictions");
            return (Criteria) this;
        }

        public Criteria andRestrictionsNotEqualTo(Integer value) {
            addCriterion("restrictions <>", value, "restrictions");
            return (Criteria) this;
        }

        public Criteria andRestrictionsGreaterThan(Integer value) {
            addCriterion("restrictions >", value, "restrictions");
            return (Criteria) this;
        }

        public Criteria andRestrictionsGreaterThanOrEqualTo(Integer value) {
            addCriterion("restrictions >=", value, "restrictions");
            return (Criteria) this;
        }

        public Criteria andRestrictionsLessThan(Integer value) {
            addCriterion("restrictions <", value, "restrictions");
            return (Criteria) this;
        }

        public Criteria andRestrictionsLessThanOrEqualTo(Integer value) {
            addCriterion("restrictions <=", value, "restrictions");
            return (Criteria) this;
        }

        public Criteria andRestrictionsIn(List<Integer> values) {
            addCriterion("restrictions in", values, "restrictions");
            return (Criteria) this;
        }

        public Criteria andRestrictionsNotIn(List<Integer> values) {
            addCriterion("restrictions not in", values, "restrictions");
            return (Criteria) this;
        }

        public Criteria andRestrictionsBetween(Integer value1, Integer value2) {
            addCriterion("restrictions between", value1, value2, "restrictions");
            return (Criteria) this;
        }

        public Criteria andRestrictionsNotBetween(Integer value1, Integer value2) {
            addCriterion("restrictions not between", value1, value2, "restrictions");
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

        public Criteria andAreaEqualTo(Long value) {
            addCriterion("area =", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotEqualTo(Long value) {
            addCriterion("area <>", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaGreaterThan(Long value) {
            addCriterion("area >", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaGreaterThanOrEqualTo(Long value) {
            addCriterion("area >=", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLessThan(Long value) {
            addCriterion("area <", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLessThanOrEqualTo(Long value) {
            addCriterion("area <=", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaIn(List<Long> values) {
            addCriterion("area in", values, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotIn(List<Long> values) {
            addCriterion("area not in", values, "area");
            return (Criteria) this;
        }

        public Criteria andAreaBetween(Long value1, Long value2) {
            addCriterion("area between", value1, value2, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotBetween(Long value1, Long value2) {
            addCriterion("area not between", value1, value2, "area");
            return (Criteria) this;
        }

        public Criteria andLandAreaIsNull() {
            addCriterion("land_area is null");
            return (Criteria) this;
        }

        public Criteria andLandAreaIsNotNull() {
            addCriterion("land_area is not null");
            return (Criteria) this;
        }

        public Criteria andLandAreaEqualTo(Long value) {
            addCriterion("land_area =", value, "landArea");
            return (Criteria) this;
        }

        public Criteria andLandAreaNotEqualTo(Long value) {
            addCriterion("land_area <>", value, "landArea");
            return (Criteria) this;
        }

        public Criteria andLandAreaGreaterThan(Long value) {
            addCriterion("land_area >", value, "landArea");
            return (Criteria) this;
        }

        public Criteria andLandAreaGreaterThanOrEqualTo(Long value) {
            addCriterion("land_area >=", value, "landArea");
            return (Criteria) this;
        }

        public Criteria andLandAreaLessThan(Long value) {
            addCriterion("land_area <", value, "landArea");
            return (Criteria) this;
        }

        public Criteria andLandAreaLessThanOrEqualTo(Long value) {
            addCriterion("land_area <=", value, "landArea");
            return (Criteria) this;
        }

        public Criteria andLandAreaIn(List<Long> values) {
            addCriterion("land_area in", values, "landArea");
            return (Criteria) this;
        }

        public Criteria andLandAreaNotIn(List<Long> values) {
            addCriterion("land_area not in", values, "landArea");
            return (Criteria) this;
        }

        public Criteria andLandAreaBetween(Long value1, Long value2) {
            addCriterion("land_area between", value1, value2, "landArea");
            return (Criteria) this;
        }

        public Criteria andLandAreaNotBetween(Long value1, Long value2) {
            addCriterion("land_area not between", value1, value2, "landArea");
            return (Criteria) this;
        }

        public Criteria andPublishDateIsNull() {
            addCriterion("publish_date is null");
            return (Criteria) this;
        }

        public Criteria andPublishDateIsNotNull() {
            addCriterion("publish_date is not null");
            return (Criteria) this;
        }

        public Criteria andPublishDateEqualTo(Date value) {
            addCriterionForJDBCDate("publish_date =", value, "publishDate");
            return (Criteria) this;
        }

        public Criteria andPublishDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("publish_date <>", value, "publishDate");
            return (Criteria) this;
        }

        public Criteria andPublishDateGreaterThan(Date value) {
            addCriterionForJDBCDate("publish_date >", value, "publishDate");
            return (Criteria) this;
        }

        public Criteria andPublishDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("publish_date >=", value, "publishDate");
            return (Criteria) this;
        }

        public Criteria andPublishDateLessThan(Date value) {
            addCriterionForJDBCDate("publish_date <", value, "publishDate");
            return (Criteria) this;
        }

        public Criteria andPublishDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("publish_date <=", value, "publishDate");
            return (Criteria) this;
        }

        public Criteria andPublishDateIn(List<Date> values) {
            addCriterionForJDBCDate("publish_date in", values, "publishDate");
            return (Criteria) this;
        }

        public Criteria andPublishDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("publish_date not in", values, "publishDate");
            return (Criteria) this;
        }

        public Criteria andPublishDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("publish_date between", value1, value2, "publishDate");
            return (Criteria) this;
        }

        public Criteria andPublishDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("publish_date not between", value1, value2, "publishDate");
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

        public Criteria andStreetIsNull() {
            addCriterion("street is null");
            return (Criteria) this;
        }

        public Criteria andStreetIsNotNull() {
            addCriterion("street is not null");
            return (Criteria) this;
        }

        public Criteria andStreetEqualTo(String value) {
            addCriterion("street =", value, "street");
            return (Criteria) this;
        }

        public Criteria andStreetNotEqualTo(String value) {
            addCriterion("street <>", value, "street");
            return (Criteria) this;
        }

        public Criteria andStreetGreaterThan(String value) {
            addCriterion("street >", value, "street");
            return (Criteria) this;
        }

        public Criteria andStreetGreaterThanOrEqualTo(String value) {
            addCriterion("street >=", value, "street");
            return (Criteria) this;
        }

        public Criteria andStreetLessThan(String value) {
            addCriterion("street <", value, "street");
            return (Criteria) this;
        }

        public Criteria andStreetLessThanOrEqualTo(String value) {
            addCriterion("street <=", value, "street");
            return (Criteria) this;
        }

        public Criteria andStreetLike(String value) {
            addCriterion("street like", value, "street");
            return (Criteria) this;
        }

        public Criteria andStreetNotLike(String value) {
            addCriterion("street not like", value, "street");
            return (Criteria) this;
        }

        public Criteria andStreetIn(List<String> values) {
            addCriterion("street in", values, "street");
            return (Criteria) this;
        }

        public Criteria andStreetNotIn(List<String> values) {
            addCriterion("street not in", values, "street");
            return (Criteria) this;
        }

        public Criteria andStreetBetween(String value1, String value2) {
            addCriterion("street between", value1, value2, "street");
            return (Criteria) this;
        }

        public Criteria andStreetNotBetween(String value1, String value2) {
            addCriterion("street not between", value1, value2, "street");
            return (Criteria) this;
        }

        public Criteria andBuildingNameIsNull() {
            addCriterion("building_name is null");
            return (Criteria) this;
        }

        public Criteria andBuildingNameIsNotNull() {
            addCriterion("building_name is not null");
            return (Criteria) this;
        }

        public Criteria andBuildingNameEqualTo(String value) {
            addCriterion("building_name =", value, "buildingName");
            return (Criteria) this;
        }

        public Criteria andBuildingNameNotEqualTo(String value) {
            addCriterion("building_name <>", value, "buildingName");
            return (Criteria) this;
        }

        public Criteria andBuildingNameGreaterThan(String value) {
            addCriterion("building_name >", value, "buildingName");
            return (Criteria) this;
        }

        public Criteria andBuildingNameGreaterThanOrEqualTo(String value) {
            addCriterion("building_name >=", value, "buildingName");
            return (Criteria) this;
        }

        public Criteria andBuildingNameLessThan(String value) {
            addCriterion("building_name <", value, "buildingName");
            return (Criteria) this;
        }

        public Criteria andBuildingNameLessThanOrEqualTo(String value) {
            addCriterion("building_name <=", value, "buildingName");
            return (Criteria) this;
        }

        public Criteria andBuildingNameLike(String value) {
            addCriterion("building_name like", value, "buildingName");
            return (Criteria) this;
        }

        public Criteria andBuildingNameNotLike(String value) {
            addCriterion("building_name not like", value, "buildingName");
            return (Criteria) this;
        }

        public Criteria andBuildingNameIn(List<String> values) {
            addCriterion("building_name in", values, "buildingName");
            return (Criteria) this;
        }

        public Criteria andBuildingNameNotIn(List<String> values) {
            addCriterion("building_name not in", values, "buildingName");
            return (Criteria) this;
        }

        public Criteria andBuildingNameBetween(String value1, String value2) {
            addCriterion("building_name between", value1, value2, "buildingName");
            return (Criteria) this;
        }

        public Criteria andBuildingNameNotBetween(String value1, String value2) {
            addCriterion("building_name not between", value1, value2, "buildingName");
            return (Criteria) this;
        }

        public Criteria andGpsLngIsNull() {
            addCriterion("gps_lng is null");
            return (Criteria) this;
        }

        public Criteria andGpsLngIsNotNull() {
            addCriterion("gps_lng is not null");
            return (Criteria) this;
        }

        public Criteria andGpsLngEqualTo(String value) {
            addCriterion("gps_lng =", value, "gpsLng");
            return (Criteria) this;
        }

        public Criteria andGpsLngNotEqualTo(String value) {
            addCriterion("gps_lng <>", value, "gpsLng");
            return (Criteria) this;
        }

        public Criteria andGpsLngGreaterThan(String value) {
            addCriterion("gps_lng >", value, "gpsLng");
            return (Criteria) this;
        }

        public Criteria andGpsLngGreaterThanOrEqualTo(String value) {
            addCriterion("gps_lng >=", value, "gpsLng");
            return (Criteria) this;
        }

        public Criteria andGpsLngLessThan(String value) {
            addCriterion("gps_lng <", value, "gpsLng");
            return (Criteria) this;
        }

        public Criteria andGpsLngLessThanOrEqualTo(String value) {
            addCriterion("gps_lng <=", value, "gpsLng");
            return (Criteria) this;
        }

        public Criteria andGpsLngLike(String value) {
            addCriterion("gps_lng like", value, "gpsLng");
            return (Criteria) this;
        }

        public Criteria andGpsLngNotLike(String value) {
            addCriterion("gps_lng not like", value, "gpsLng");
            return (Criteria) this;
        }

        public Criteria andGpsLngIn(List<String> values) {
            addCriterion("gps_lng in", values, "gpsLng");
            return (Criteria) this;
        }

        public Criteria andGpsLngNotIn(List<String> values) {
            addCriterion("gps_lng not in", values, "gpsLng");
            return (Criteria) this;
        }

        public Criteria andGpsLngBetween(String value1, String value2) {
            addCriterion("gps_lng between", value1, value2, "gpsLng");
            return (Criteria) this;
        }

        public Criteria andGpsLngNotBetween(String value1, String value2) {
            addCriterion("gps_lng not between", value1, value2, "gpsLng");
            return (Criteria) this;
        }

        public Criteria andGpsLatIsNull() {
            addCriterion("gps_lat is null");
            return (Criteria) this;
        }

        public Criteria andGpsLatIsNotNull() {
            addCriterion("gps_lat is not null");
            return (Criteria) this;
        }

        public Criteria andGpsLatEqualTo(String value) {
            addCriterion("gps_lat =", value, "gpsLat");
            return (Criteria) this;
        }

        public Criteria andGpsLatNotEqualTo(String value) {
            addCriterion("gps_lat <>", value, "gpsLat");
            return (Criteria) this;
        }

        public Criteria andGpsLatGreaterThan(String value) {
            addCriterion("gps_lat >", value, "gpsLat");
            return (Criteria) this;
        }

        public Criteria andGpsLatGreaterThanOrEqualTo(String value) {
            addCriterion("gps_lat >=", value, "gpsLat");
            return (Criteria) this;
        }

        public Criteria andGpsLatLessThan(String value) {
            addCriterion("gps_lat <", value, "gpsLat");
            return (Criteria) this;
        }

        public Criteria andGpsLatLessThanOrEqualTo(String value) {
            addCriterion("gps_lat <=", value, "gpsLat");
            return (Criteria) this;
        }

        public Criteria andGpsLatLike(String value) {
            addCriterion("gps_lat like", value, "gpsLat");
            return (Criteria) this;
        }

        public Criteria andGpsLatNotLike(String value) {
            addCriterion("gps_lat not like", value, "gpsLat");
            return (Criteria) this;
        }

        public Criteria andGpsLatIn(List<String> values) {
            addCriterion("gps_lat in", values, "gpsLat");
            return (Criteria) this;
        }

        public Criteria andGpsLatNotIn(List<String> values) {
            addCriterion("gps_lat not in", values, "gpsLat");
            return (Criteria) this;
        }

        public Criteria andGpsLatBetween(String value1, String value2) {
            addCriterion("gps_lat between", value1, value2, "gpsLat");
            return (Criteria) this;
        }

        public Criteria andGpsLatNotBetween(String value1, String value2) {
            addCriterion("gps_lat not between", value1, value2, "gpsLat");
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

        public Criteria andOriginDebtIdEqualTo(String value) {
            addCriterion("origin_debt_id =", value, "originDebtId");
            return (Criteria) this;
        }

        public Criteria andOriginDebtIdNotEqualTo(String value) {
            addCriterion("origin_debt_id <>", value, "originDebtId");
            return (Criteria) this;
        }

        public Criteria andOriginDebtIdGreaterThan(String value) {
            addCriterion("origin_debt_id >", value, "originDebtId");
            return (Criteria) this;
        }

        public Criteria andOriginDebtIdGreaterThanOrEqualTo(String value) {
            addCriterion("origin_debt_id >=", value, "originDebtId");
            return (Criteria) this;
        }

        public Criteria andOriginDebtIdLessThan(String value) {
            addCriterion("origin_debt_id <", value, "originDebtId");
            return (Criteria) this;
        }

        public Criteria andOriginDebtIdLessThanOrEqualTo(String value) {
            addCriterion("origin_debt_id <=", value, "originDebtId");
            return (Criteria) this;
        }

        public Criteria andOriginDebtIdLike(String value) {
            addCriterion("origin_debt_id like", value, "originDebtId");
            return (Criteria) this;
        }

        public Criteria andOriginDebtIdNotLike(String value) {
            addCriterion("origin_debt_id not like", value, "originDebtId");
            return (Criteria) this;
        }

        public Criteria andOriginDebtIdIn(List<String> values) {
            addCriterion("origin_debt_id in", values, "originDebtId");
            return (Criteria) this;
        }

        public Criteria andOriginDebtIdNotIn(List<String> values) {
            addCriterion("origin_debt_id not in", values, "originDebtId");
            return (Criteria) this;
        }

        public Criteria andOriginDebtIdBetween(String value1, String value2) {
            addCriterion("origin_debt_id between", value1, value2, "originDebtId");
            return (Criteria) this;
        }

        public Criteria andOriginDebtIdNotBetween(String value1, String value2) {
            addCriterion("origin_debt_id not between", value1, value2, "originDebtId");
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