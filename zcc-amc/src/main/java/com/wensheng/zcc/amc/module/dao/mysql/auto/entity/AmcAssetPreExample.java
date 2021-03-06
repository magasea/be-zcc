package com.wensheng.zcc.amc.module.dao.mysql.auto.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class AmcAssetPreExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AmcAssetPreExample() {
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

        public Criteria andDebtTitleIsNull() {
            addCriterion("debt_title is null");
            return (Criteria) this;
        }

        public Criteria andDebtTitleIsNotNull() {
            addCriterion("debt_title is not null");
            return (Criteria) this;
        }

        public Criteria andDebtTitleEqualTo(String value) {
            addCriterion("debt_title =", value, "debtTitle");
            return (Criteria) this;
        }

        public Criteria andDebtTitleNotEqualTo(String value) {
            addCriterion("debt_title <>", value, "debtTitle");
            return (Criteria) this;
        }

        public Criteria andDebtTitleGreaterThan(String value) {
            addCriterion("debt_title >", value, "debtTitle");
            return (Criteria) this;
        }

        public Criteria andDebtTitleGreaterThanOrEqualTo(String value) {
            addCriterion("debt_title >=", value, "debtTitle");
            return (Criteria) this;
        }

        public Criteria andDebtTitleLessThan(String value) {
            addCriterion("debt_title <", value, "debtTitle");
            return (Criteria) this;
        }

        public Criteria andDebtTitleLessThanOrEqualTo(String value) {
            addCriterion("debt_title <=", value, "debtTitle");
            return (Criteria) this;
        }

        public Criteria andDebtTitleLike(String value) {
            addCriterion("debt_title like", value, "debtTitle");
            return (Criteria) this;
        }

        public Criteria andDebtTitleNotLike(String value) {
            addCriterion("debt_title not like", value, "debtTitle");
            return (Criteria) this;
        }

        public Criteria andDebtTitleIn(List<String> values) {
            addCriterion("debt_title in", values, "debtTitle");
            return (Criteria) this;
        }

        public Criteria andDebtTitleNotIn(List<String> values) {
            addCriterion("debt_title not in", values, "debtTitle");
            return (Criteria) this;
        }

        public Criteria andDebtTitleBetween(String value1, String value2) {
            addCriterion("debt_title between", value1, value2, "debtTitle");
            return (Criteria) this;
        }

        public Criteria andDebtTitleNotBetween(String value1, String value2) {
            addCriterion("debt_title not between", value1, value2, "debtTitle");
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

        public Criteria andSealedStateIsNull() {
            addCriterion("sealed_state is null");
            return (Criteria) this;
        }

        public Criteria andSealedStateIsNotNull() {
            addCriterion("sealed_state is not null");
            return (Criteria) this;
        }

        public Criteria andSealedStateEqualTo(Integer value) {
            addCriterion("sealed_state =", value, "sealedState");
            return (Criteria) this;
        }

        public Criteria andSealedStateNotEqualTo(Integer value) {
            addCriterion("sealed_state <>", value, "sealedState");
            return (Criteria) this;
        }

        public Criteria andSealedStateGreaterThan(Integer value) {
            addCriterion("sealed_state >", value, "sealedState");
            return (Criteria) this;
        }

        public Criteria andSealedStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("sealed_state >=", value, "sealedState");
            return (Criteria) this;
        }

        public Criteria andSealedStateLessThan(Integer value) {
            addCriterion("sealed_state <", value, "sealedState");
            return (Criteria) this;
        }

        public Criteria andSealedStateLessThanOrEqualTo(Integer value) {
            addCriterion("sealed_state <=", value, "sealedState");
            return (Criteria) this;
        }

        public Criteria andSealedStateIn(List<Integer> values) {
            addCriterion("sealed_state in", values, "sealedState");
            return (Criteria) this;
        }

        public Criteria andSealedStateNotIn(List<Integer> values) {
            addCriterion("sealed_state not in", values, "sealedState");
            return (Criteria) this;
        }

        public Criteria andSealedStateBetween(Integer value1, Integer value2) {
            addCriterion("sealed_state between", value1, value2, "sealedState");
            return (Criteria) this;
        }

        public Criteria andSealedStateNotBetween(Integer value1, Integer value2) {
            addCriterion("sealed_state not between", value1, value2, "sealedState");
            return (Criteria) this;
        }

        public Criteria andAssetNatureIsNull() {
            addCriterion("asset_nature is null");
            return (Criteria) this;
        }

        public Criteria andAssetNatureIsNotNull() {
            addCriterion("asset_nature is not null");
            return (Criteria) this;
        }

        public Criteria andAssetNatureEqualTo(Integer value) {
            addCriterion("asset_nature =", value, "assetNature");
            return (Criteria) this;
        }

        public Criteria andAssetNatureNotEqualTo(Integer value) {
            addCriterion("asset_nature <>", value, "assetNature");
            return (Criteria) this;
        }

        public Criteria andAssetNatureGreaterThan(Integer value) {
            addCriterion("asset_nature >", value, "assetNature");
            return (Criteria) this;
        }

        public Criteria andAssetNatureGreaterThanOrEqualTo(Integer value) {
            addCriterion("asset_nature >=", value, "assetNature");
            return (Criteria) this;
        }

        public Criteria andAssetNatureLessThan(Integer value) {
            addCriterion("asset_nature <", value, "assetNature");
            return (Criteria) this;
        }

        public Criteria andAssetNatureLessThanOrEqualTo(Integer value) {
            addCriterion("asset_nature <=", value, "assetNature");
            return (Criteria) this;
        }

        public Criteria andAssetNatureIn(List<Integer> values) {
            addCriterion("asset_nature in", values, "assetNature");
            return (Criteria) this;
        }

        public Criteria andAssetNatureNotIn(List<Integer> values) {
            addCriterion("asset_nature not in", values, "assetNature");
            return (Criteria) this;
        }

        public Criteria andAssetNatureBetween(Integer value1, Integer value2) {
            addCriterion("asset_nature between", value1, value2, "assetNature");
            return (Criteria) this;
        }

        public Criteria andAssetNatureNotBetween(Integer value1, Integer value2) {
            addCriterion("asset_nature not between", value1, value2, "assetNature");
            return (Criteria) this;
        }

        public Criteria andPublishStateIsNull() {
            addCriterion("publish_state is null");
            return (Criteria) this;
        }

        public Criteria andPublishStateIsNotNull() {
            addCriterion("publish_state is not null");
            return (Criteria) this;
        }

        public Criteria andPublishStateEqualTo(Integer value) {
            addCriterion("publish_state =", value, "publishState");
            return (Criteria) this;
        }

        public Criteria andPublishStateNotEqualTo(Integer value) {
            addCriterion("publish_state <>", value, "publishState");
            return (Criteria) this;
        }

        public Criteria andPublishStateGreaterThan(Integer value) {
            addCriterion("publish_state >", value, "publishState");
            return (Criteria) this;
        }

        public Criteria andPublishStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("publish_state >=", value, "publishState");
            return (Criteria) this;
        }

        public Criteria andPublishStateLessThan(Integer value) {
            addCriterion("publish_state <", value, "publishState");
            return (Criteria) this;
        }

        public Criteria andPublishStateLessThanOrEqualTo(Integer value) {
            addCriterion("publish_state <=", value, "publishState");
            return (Criteria) this;
        }

        public Criteria andPublishStateIn(List<Integer> values) {
            addCriterion("publish_state in", values, "publishState");
            return (Criteria) this;
        }

        public Criteria andPublishStateNotIn(List<Integer> values) {
            addCriterion("publish_state not in", values, "publishState");
            return (Criteria) this;
        }

        public Criteria andPublishStateBetween(Integer value1, Integer value2) {
            addCriterion("publish_state between", value1, value2, "publishState");
            return (Criteria) this;
        }

        public Criteria andPublishStateNotBetween(Integer value1, Integer value2) {
            addCriterion("publish_state not between", value1, value2, "publishState");
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

        public Criteria andTotalValuationIsNull() {
            addCriterion("total_valuation is null");
            return (Criteria) this;
        }

        public Criteria andTotalValuationIsNotNull() {
            addCriterion("total_valuation is not null");
            return (Criteria) this;
        }

        public Criteria andTotalValuationEqualTo(Long value) {
            addCriterion("total_valuation =", value, "totalValuation");
            return (Criteria) this;
        }

        public Criteria andTotalValuationNotEqualTo(Long value) {
            addCriterion("total_valuation <>", value, "totalValuation");
            return (Criteria) this;
        }

        public Criteria andTotalValuationGreaterThan(Long value) {
            addCriterion("total_valuation >", value, "totalValuation");
            return (Criteria) this;
        }

        public Criteria andTotalValuationGreaterThanOrEqualTo(Long value) {
            addCriterion("total_valuation >=", value, "totalValuation");
            return (Criteria) this;
        }

        public Criteria andTotalValuationLessThan(Long value) {
            addCriterion("total_valuation <", value, "totalValuation");
            return (Criteria) this;
        }

        public Criteria andTotalValuationLessThanOrEqualTo(Long value) {
            addCriterion("total_valuation <=", value, "totalValuation");
            return (Criteria) this;
        }

        public Criteria andTotalValuationIn(List<Long> values) {
            addCriterion("total_valuation in", values, "totalValuation");
            return (Criteria) this;
        }

        public Criteria andTotalValuationNotIn(List<Long> values) {
            addCriterion("total_valuation not in", values, "totalValuation");
            return (Criteria) this;
        }

        public Criteria andTotalValuationBetween(Long value1, Long value2) {
            addCriterion("total_valuation between", value1, value2, "totalValuation");
            return (Criteria) this;
        }

        public Criteria andTotalValuationNotBetween(Long value1, Long value2) {
            addCriterion("total_valuation not between", value1, value2, "totalValuation");
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

        public Criteria andBuildingAreaIsNull() {
            addCriterion("building_area is null");
            return (Criteria) this;
        }

        public Criteria andBuildingAreaIsNotNull() {
            addCriterion("building_area is not null");
            return (Criteria) this;
        }

        public Criteria andBuildingAreaEqualTo(Long value) {
            addCriterion("building_area =", value, "buildingArea");
            return (Criteria) this;
        }

        public Criteria andBuildingAreaNotEqualTo(Long value) {
            addCriterion("building_area <>", value, "buildingArea");
            return (Criteria) this;
        }

        public Criteria andBuildingAreaGreaterThan(Long value) {
            addCriterion("building_area >", value, "buildingArea");
            return (Criteria) this;
        }

        public Criteria andBuildingAreaGreaterThanOrEqualTo(Long value) {
            addCriterion("building_area >=", value, "buildingArea");
            return (Criteria) this;
        }

        public Criteria andBuildingAreaLessThan(Long value) {
            addCriterion("building_area <", value, "buildingArea");
            return (Criteria) this;
        }

        public Criteria andBuildingAreaLessThanOrEqualTo(Long value) {
            addCriterion("building_area <=", value, "buildingArea");
            return (Criteria) this;
        }

        public Criteria andBuildingAreaIn(List<Long> values) {
            addCriterion("building_area in", values, "buildingArea");
            return (Criteria) this;
        }

        public Criteria andBuildingAreaNotIn(List<Long> values) {
            addCriterion("building_area not in", values, "buildingArea");
            return (Criteria) this;
        }

        public Criteria andBuildingAreaBetween(Long value1, Long value2) {
            addCriterion("building_area between", value1, value2, "buildingArea");
            return (Criteria) this;
        }

        public Criteria andBuildingAreaNotBetween(Long value1, Long value2) {
            addCriterion("building_area not between", value1, value2, "buildingArea");
            return (Criteria) this;
        }

        public Criteria andBuildingUnitPriceIsNull() {
            addCriterion("building_unit_price is null");
            return (Criteria) this;
        }

        public Criteria andBuildingUnitPriceIsNotNull() {
            addCriterion("building_unit_price is not null");
            return (Criteria) this;
        }

        public Criteria andBuildingUnitPriceEqualTo(Long value) {
            addCriterion("building_unit_price =", value, "buildingUnitPrice");
            return (Criteria) this;
        }

        public Criteria andBuildingUnitPriceNotEqualTo(Long value) {
            addCriterion("building_unit_price <>", value, "buildingUnitPrice");
            return (Criteria) this;
        }

        public Criteria andBuildingUnitPriceGreaterThan(Long value) {
            addCriterion("building_unit_price >", value, "buildingUnitPrice");
            return (Criteria) this;
        }

        public Criteria andBuildingUnitPriceGreaterThanOrEqualTo(Long value) {
            addCriterion("building_unit_price >=", value, "buildingUnitPrice");
            return (Criteria) this;
        }

        public Criteria andBuildingUnitPriceLessThan(Long value) {
            addCriterion("building_unit_price <", value, "buildingUnitPrice");
            return (Criteria) this;
        }

        public Criteria andBuildingUnitPriceLessThanOrEqualTo(Long value) {
            addCriterion("building_unit_price <=", value, "buildingUnitPrice");
            return (Criteria) this;
        }

        public Criteria andBuildingUnitPriceIn(List<Long> values) {
            addCriterion("building_unit_price in", values, "buildingUnitPrice");
            return (Criteria) this;
        }

        public Criteria andBuildingUnitPriceNotIn(List<Long> values) {
            addCriterion("building_unit_price not in", values, "buildingUnitPrice");
            return (Criteria) this;
        }

        public Criteria andBuildingUnitPriceBetween(Long value1, Long value2) {
            addCriterion("building_unit_price between", value1, value2, "buildingUnitPrice");
            return (Criteria) this;
        }

        public Criteria andBuildingUnitPriceNotBetween(Long value1, Long value2) {
            addCriterion("building_unit_price not between", value1, value2, "buildingUnitPrice");
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

        public Criteria andLandAreaUnitIsNull() {
            addCriterion("land_area_unit is null");
            return (Criteria) this;
        }

        public Criteria andLandAreaUnitIsNotNull() {
            addCriterion("land_area_unit is not null");
            return (Criteria) this;
        }

        public Criteria andLandAreaUnitEqualTo(Integer value) {
            addCriterion("land_area_unit =", value, "landAreaUnit");
            return (Criteria) this;
        }

        public Criteria andLandAreaUnitNotEqualTo(Integer value) {
            addCriterion("land_area_unit <>", value, "landAreaUnit");
            return (Criteria) this;
        }

        public Criteria andLandAreaUnitGreaterThan(Integer value) {
            addCriterion("land_area_unit >", value, "landAreaUnit");
            return (Criteria) this;
        }

        public Criteria andLandAreaUnitGreaterThanOrEqualTo(Integer value) {
            addCriterion("land_area_unit >=", value, "landAreaUnit");
            return (Criteria) this;
        }

        public Criteria andLandAreaUnitLessThan(Integer value) {
            addCriterion("land_area_unit <", value, "landAreaUnit");
            return (Criteria) this;
        }

        public Criteria andLandAreaUnitLessThanOrEqualTo(Integer value) {
            addCriterion("land_area_unit <=", value, "landAreaUnit");
            return (Criteria) this;
        }

        public Criteria andLandAreaUnitIn(List<Integer> values) {
            addCriterion("land_area_unit in", values, "landAreaUnit");
            return (Criteria) this;
        }

        public Criteria andLandAreaUnitNotIn(List<Integer> values) {
            addCriterion("land_area_unit not in", values, "landAreaUnit");
            return (Criteria) this;
        }

        public Criteria andLandAreaUnitBetween(Integer value1, Integer value2) {
            addCriterion("land_area_unit between", value1, value2, "landAreaUnit");
            return (Criteria) this;
        }

        public Criteria andLandAreaUnitNotBetween(Integer value1, Integer value2) {
            addCriterion("land_area_unit not between", value1, value2, "landAreaUnit");
            return (Criteria) this;
        }

        public Criteria andLandusageIsNull() {
            addCriterion("landusage is null");
            return (Criteria) this;
        }

        public Criteria andLandusageIsNotNull() {
            addCriterion("landusage is not null");
            return (Criteria) this;
        }

        public Criteria andLandusageEqualTo(Integer value) {
            addCriterion("landusage =", value, "landusage");
            return (Criteria) this;
        }

        public Criteria andLandusageNotEqualTo(Integer value) {
            addCriterion("landusage <>", value, "landusage");
            return (Criteria) this;
        }

        public Criteria andLandusageGreaterThan(Integer value) {
            addCriterion("landusage >", value, "landusage");
            return (Criteria) this;
        }

        public Criteria andLandusageGreaterThanOrEqualTo(Integer value) {
            addCriterion("landusage >=", value, "landusage");
            return (Criteria) this;
        }

        public Criteria andLandusageLessThan(Integer value) {
            addCriterion("landusage <", value, "landusage");
            return (Criteria) this;
        }

        public Criteria andLandusageLessThanOrEqualTo(Integer value) {
            addCriterion("landusage <=", value, "landusage");
            return (Criteria) this;
        }

        public Criteria andLandusageIn(List<Integer> values) {
            addCriterion("landusage in", values, "landusage");
            return (Criteria) this;
        }

        public Criteria andLandusageNotIn(List<Integer> values) {
            addCriterion("landusage not in", values, "landusage");
            return (Criteria) this;
        }

        public Criteria andLandusageBetween(Integer value1, Integer value2) {
            addCriterion("landusage between", value1, value2, "landusage");
            return (Criteria) this;
        }

        public Criteria andLandusageNotBetween(Integer value1, Integer value2) {
            addCriterion("landusage not between", value1, value2, "landusage");
            return (Criteria) this;
        }

        public Criteria andLandsupplyIsNull() {
            addCriterion("landsupply is null");
            return (Criteria) this;
        }

        public Criteria andLandsupplyIsNotNull() {
            addCriterion("landsupply is not null");
            return (Criteria) this;
        }

        public Criteria andLandsupplyEqualTo(Integer value) {
            addCriterion("landsupply =", value, "landsupply");
            return (Criteria) this;
        }

        public Criteria andLandsupplyNotEqualTo(Integer value) {
            addCriterion("landsupply <>", value, "landsupply");
            return (Criteria) this;
        }

        public Criteria andLandsupplyGreaterThan(Integer value) {
            addCriterion("landsupply >", value, "landsupply");
            return (Criteria) this;
        }

        public Criteria andLandsupplyGreaterThanOrEqualTo(Integer value) {
            addCriterion("landsupply >=", value, "landsupply");
            return (Criteria) this;
        }

        public Criteria andLandsupplyLessThan(Integer value) {
            addCriterion("landsupply <", value, "landsupply");
            return (Criteria) this;
        }

        public Criteria andLandsupplyLessThanOrEqualTo(Integer value) {
            addCriterion("landsupply <=", value, "landsupply");
            return (Criteria) this;
        }

        public Criteria andLandsupplyIn(List<Integer> values) {
            addCriterion("landsupply in", values, "landsupply");
            return (Criteria) this;
        }

        public Criteria andLandsupplyNotIn(List<Integer> values) {
            addCriterion("landsupply not in", values, "landsupply");
            return (Criteria) this;
        }

        public Criteria andLandsupplyBetween(Integer value1, Integer value2) {
            addCriterion("landsupply between", value1, value2, "landsupply");
            return (Criteria) this;
        }

        public Criteria andLandsupplyNotBetween(Integer value1, Integer value2) {
            addCriterion("landsupply not between", value1, value2, "landsupply");
            return (Criteria) this;
        }

        public Criteria andOwnerIsNull() {
            addCriterion("owner is null");
            return (Criteria) this;
        }

        public Criteria andOwnerIsNotNull() {
            addCriterion("owner is not null");
            return (Criteria) this;
        }

        public Criteria andOwnerEqualTo(String value) {
            addCriterion("owner =", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerNotEqualTo(String value) {
            addCriterion("owner <>", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerGreaterThan(String value) {
            addCriterion("owner >", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerGreaterThanOrEqualTo(String value) {
            addCriterion("owner >=", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerLessThan(String value) {
            addCriterion("owner <", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerLessThanOrEqualTo(String value) {
            addCriterion("owner <=", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerLike(String value) {
            addCriterion("owner like", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerNotLike(String value) {
            addCriterion("owner not like", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerIn(List<String> values) {
            addCriterion("owner in", values, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerNotIn(List<String> values) {
            addCriterion("owner not in", values, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerBetween(String value1, String value2) {
            addCriterion("owner between", value1, value2, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerNotBetween(String value1, String value2) {
            addCriterion("owner not between", value1, value2, "owner");
            return (Criteria) this;
        }

        public Criteria andWarrantIsNull() {
            addCriterion("warrant is null");
            return (Criteria) this;
        }

        public Criteria andWarrantIsNotNull() {
            addCriterion("warrant is not null");
            return (Criteria) this;
        }

        public Criteria andWarrantEqualTo(String value) {
            addCriterion("warrant =", value, "warrant");
            return (Criteria) this;
        }

        public Criteria andWarrantNotEqualTo(String value) {
            addCriterion("warrant <>", value, "warrant");
            return (Criteria) this;
        }

        public Criteria andWarrantGreaterThan(String value) {
            addCriterion("warrant >", value, "warrant");
            return (Criteria) this;
        }

        public Criteria andWarrantGreaterThanOrEqualTo(String value) {
            addCriterion("warrant >=", value, "warrant");
            return (Criteria) this;
        }

        public Criteria andWarrantLessThan(String value) {
            addCriterion("warrant <", value, "warrant");
            return (Criteria) this;
        }

        public Criteria andWarrantLessThanOrEqualTo(String value) {
            addCriterion("warrant <=", value, "warrant");
            return (Criteria) this;
        }

        public Criteria andWarrantLike(String value) {
            addCriterion("warrant like", value, "warrant");
            return (Criteria) this;
        }

        public Criteria andWarrantNotLike(String value) {
            addCriterion("warrant not like", value, "warrant");
            return (Criteria) this;
        }

        public Criteria andWarrantIn(List<String> values) {
            addCriterion("warrant in", values, "warrant");
            return (Criteria) this;
        }

        public Criteria andWarrantNotIn(List<String> values) {
            addCriterion("warrant not in", values, "warrant");
            return (Criteria) this;
        }

        public Criteria andWarrantBetween(String value1, String value2) {
            addCriterion("warrant between", value1, value2, "warrant");
            return (Criteria) this;
        }

        public Criteria andWarrantNotBetween(String value1, String value2) {
            addCriterion("warrant not between", value1, value2, "warrant");
            return (Criteria) this;
        }

        public Criteria andLandUnitPriceIsNull() {
            addCriterion("land_unit_price is null");
            return (Criteria) this;
        }

        public Criteria andLandUnitPriceIsNotNull() {
            addCriterion("land_unit_price is not null");
            return (Criteria) this;
        }

        public Criteria andLandUnitPriceEqualTo(Long value) {
            addCriterion("land_unit_price =", value, "landUnitPrice");
            return (Criteria) this;
        }

        public Criteria andLandUnitPriceNotEqualTo(Long value) {
            addCriterion("land_unit_price <>", value, "landUnitPrice");
            return (Criteria) this;
        }

        public Criteria andLandUnitPriceGreaterThan(Long value) {
            addCriterion("land_unit_price >", value, "landUnitPrice");
            return (Criteria) this;
        }

        public Criteria andLandUnitPriceGreaterThanOrEqualTo(Long value) {
            addCriterion("land_unit_price >=", value, "landUnitPrice");
            return (Criteria) this;
        }

        public Criteria andLandUnitPriceLessThan(Long value) {
            addCriterion("land_unit_price <", value, "landUnitPrice");
            return (Criteria) this;
        }

        public Criteria andLandUnitPriceLessThanOrEqualTo(Long value) {
            addCriterion("land_unit_price <=", value, "landUnitPrice");
            return (Criteria) this;
        }

        public Criteria andLandUnitPriceIn(List<Long> values) {
            addCriterion("land_unit_price in", values, "landUnitPrice");
            return (Criteria) this;
        }

        public Criteria andLandUnitPriceNotIn(List<Long> values) {
            addCriterion("land_unit_price not in", values, "landUnitPrice");
            return (Criteria) this;
        }

        public Criteria andLandUnitPriceBetween(Long value1, Long value2) {
            addCriterion("land_unit_price between", value1, value2, "landUnitPrice");
            return (Criteria) this;
        }

        public Criteria andLandUnitPriceNotBetween(Long value1, Long value2) {
            addCriterion("land_unit_price not between", value1, value2, "landUnitPrice");
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

        public Criteria andUpdateDateIsNull() {
            addCriterion("update_date is null");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIsNotNull() {
            addCriterion("update_date is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateDateEqualTo(Date value) {
            addCriterion("update_date =", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotEqualTo(Date value) {
            addCriterion("update_date <>", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateGreaterThan(Date value) {
            addCriterion("update_date >", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("update_date >=", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateLessThan(Date value) {
            addCriterion("update_date <", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateLessThanOrEqualTo(Date value) {
            addCriterion("update_date <=", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIn(List<Date> values) {
            addCriterion("update_date in", values, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotIn(List<Date> values) {
            addCriterion("update_date not in", values, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateBetween(Date value1, Date value2) {
            addCriterion("update_date between", value1, value2, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotBetween(Date value1, Date value2) {
            addCriterion("update_date not between", value1, value2, "updateDate");
            return (Criteria) this;
        }

        public Criteria andCreatedByIsNull() {
            addCriterion("created_by is null");
            return (Criteria) this;
        }

        public Criteria andCreatedByIsNotNull() {
            addCriterion("created_by is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedByEqualTo(Long value) {
            addCriterion("created_by =", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByNotEqualTo(Long value) {
            addCriterion("created_by <>", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByGreaterThan(Long value) {
            addCriterion("created_by >", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByGreaterThanOrEqualTo(Long value) {
            addCriterion("created_by >=", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByLessThan(Long value) {
            addCriterion("created_by <", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByLessThanOrEqualTo(Long value) {
            addCriterion("created_by <=", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByIn(List<Long> values) {
            addCriterion("created_by in", values, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByNotIn(List<Long> values) {
            addCriterion("created_by not in", values, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByBetween(Long value1, Long value2) {
            addCriterion("created_by between", value1, value2, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByNotBetween(Long value1, Long value2) {
            addCriterion("created_by not between", value1, value2, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedDateIsNull() {
            addCriterion("created_date is null");
            return (Criteria) this;
        }

        public Criteria andCreatedDateIsNotNull() {
            addCriterion("created_date is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedDateEqualTo(Date value) {
            addCriterion("created_date =", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateNotEqualTo(Date value) {
            addCriterion("created_date <>", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateGreaterThan(Date value) {
            addCriterion("created_date >", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateGreaterThanOrEqualTo(Date value) {
            addCriterion("created_date >=", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateLessThan(Date value) {
            addCriterion("created_date <", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateLessThanOrEqualTo(Date value) {
            addCriterion("created_date <=", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateIn(List<Date> values) {
            addCriterion("created_date in", values, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateNotIn(List<Date> values) {
            addCriterion("created_date not in", values, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateBetween(Date value1, Date value2) {
            addCriterion("created_date between", value1, value2, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateNotBetween(Date value1, Date value2) {
            addCriterion("created_date not between", value1, value2, "createdDate");
            return (Criteria) this;
        }

        public Criteria andAmcContactorIdIsNull() {
            addCriterion("amc_contactor_id is null");
            return (Criteria) this;
        }

        public Criteria andAmcContactorIdIsNotNull() {
            addCriterion("amc_contactor_id is not null");
            return (Criteria) this;
        }

        public Criteria andAmcContactorIdEqualTo(Long value) {
            addCriterion("amc_contactor_id =", value, "amcContactorId");
            return (Criteria) this;
        }

        public Criteria andAmcContactorIdNotEqualTo(Long value) {
            addCriterion("amc_contactor_id <>", value, "amcContactorId");
            return (Criteria) this;
        }

        public Criteria andAmcContactorIdGreaterThan(Long value) {
            addCriterion("amc_contactor_id >", value, "amcContactorId");
            return (Criteria) this;
        }

        public Criteria andAmcContactorIdGreaterThanOrEqualTo(Long value) {
            addCriterion("amc_contactor_id >=", value, "amcContactorId");
            return (Criteria) this;
        }

        public Criteria andAmcContactorIdLessThan(Long value) {
            addCriterion("amc_contactor_id <", value, "amcContactorId");
            return (Criteria) this;
        }

        public Criteria andAmcContactorIdLessThanOrEqualTo(Long value) {
            addCriterion("amc_contactor_id <=", value, "amcContactorId");
            return (Criteria) this;
        }

        public Criteria andAmcContactorIdIn(List<Long> values) {
            addCriterion("amc_contactor_id in", values, "amcContactorId");
            return (Criteria) this;
        }

        public Criteria andAmcContactorIdNotIn(List<Long> values) {
            addCriterion("amc_contactor_id not in", values, "amcContactorId");
            return (Criteria) this;
        }

        public Criteria andAmcContactorIdBetween(Long value1, Long value2) {
            addCriterion("amc_contactor_id between", value1, value2, "amcContactorId");
            return (Criteria) this;
        }

        public Criteria andAmcContactorIdNotBetween(Long value1, Long value2) {
            addCriterion("amc_contactor_id not between", value1, value2, "amcContactorId");
            return (Criteria) this;
        }

        public Criteria andAmcContactorNameIsNull() {
            addCriterion("amc_contactor_name is null");
            return (Criteria) this;
        }

        public Criteria andAmcContactorNameIsNotNull() {
            addCriterion("amc_contactor_name is not null");
            return (Criteria) this;
        }

        public Criteria andAmcContactorNameEqualTo(String value) {
            addCriterion("amc_contactor_name =", value, "amcContactorName");
            return (Criteria) this;
        }

        public Criteria andAmcContactorNameNotEqualTo(String value) {
            addCriterion("amc_contactor_name <>", value, "amcContactorName");
            return (Criteria) this;
        }

        public Criteria andAmcContactorNameGreaterThan(String value) {
            addCriterion("amc_contactor_name >", value, "amcContactorName");
            return (Criteria) this;
        }

        public Criteria andAmcContactorNameGreaterThanOrEqualTo(String value) {
            addCriterion("amc_contactor_name >=", value, "amcContactorName");
            return (Criteria) this;
        }

        public Criteria andAmcContactorNameLessThan(String value) {
            addCriterion("amc_contactor_name <", value, "amcContactorName");
            return (Criteria) this;
        }

        public Criteria andAmcContactorNameLessThanOrEqualTo(String value) {
            addCriterion("amc_contactor_name <=", value, "amcContactorName");
            return (Criteria) this;
        }

        public Criteria andAmcContactorNameLike(String value) {
            addCriterion("amc_contactor_name like", value, "amcContactorName");
            return (Criteria) this;
        }

        public Criteria andAmcContactorNameNotLike(String value) {
            addCriterion("amc_contactor_name not like", value, "amcContactorName");
            return (Criteria) this;
        }

        public Criteria andAmcContactorNameIn(List<String> values) {
            addCriterion("amc_contactor_name in", values, "amcContactorName");
            return (Criteria) this;
        }

        public Criteria andAmcContactorNameNotIn(List<String> values) {
            addCriterion("amc_contactor_name not in", values, "amcContactorName");
            return (Criteria) this;
        }

        public Criteria andAmcContactorNameBetween(String value1, String value2) {
            addCriterion("amc_contactor_name between", value1, value2, "amcContactorName");
            return (Criteria) this;
        }

        public Criteria andAmcContactorNameNotBetween(String value1, String value2) {
            addCriterion("amc_contactor_name not between", value1, value2, "amcContactorName");
            return (Criteria) this;
        }

        public Criteria andAmcContactorPhoneIsNull() {
            addCriterion("amc_contactor_phone is null");
            return (Criteria) this;
        }

        public Criteria andAmcContactorPhoneIsNotNull() {
            addCriterion("amc_contactor_phone is not null");
            return (Criteria) this;
        }

        public Criteria andAmcContactorPhoneEqualTo(String value) {
            addCriterion("amc_contactor_phone =", value, "amcContactorPhone");
            return (Criteria) this;
        }

        public Criteria andAmcContactorPhoneNotEqualTo(String value) {
            addCriterion("amc_contactor_phone <>", value, "amcContactorPhone");
            return (Criteria) this;
        }

        public Criteria andAmcContactorPhoneGreaterThan(String value) {
            addCriterion("amc_contactor_phone >", value, "amcContactorPhone");
            return (Criteria) this;
        }

        public Criteria andAmcContactorPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("amc_contactor_phone >=", value, "amcContactorPhone");
            return (Criteria) this;
        }

        public Criteria andAmcContactorPhoneLessThan(String value) {
            addCriterion("amc_contactor_phone <", value, "amcContactorPhone");
            return (Criteria) this;
        }

        public Criteria andAmcContactorPhoneLessThanOrEqualTo(String value) {
            addCriterion("amc_contactor_phone <=", value, "amcContactorPhone");
            return (Criteria) this;
        }

        public Criteria andAmcContactorPhoneLike(String value) {
            addCriterion("amc_contactor_phone like", value, "amcContactorPhone");
            return (Criteria) this;
        }

        public Criteria andAmcContactorPhoneNotLike(String value) {
            addCriterion("amc_contactor_phone not like", value, "amcContactorPhone");
            return (Criteria) this;
        }

        public Criteria andAmcContactorPhoneIn(List<String> values) {
            addCriterion("amc_contactor_phone in", values, "amcContactorPhone");
            return (Criteria) this;
        }

        public Criteria andAmcContactorPhoneNotIn(List<String> values) {
            addCriterion("amc_contactor_phone not in", values, "amcContactorPhone");
            return (Criteria) this;
        }

        public Criteria andAmcContactorPhoneBetween(String value1, String value2) {
            addCriterion("amc_contactor_phone between", value1, value2, "amcContactorPhone");
            return (Criteria) this;
        }

        public Criteria andAmcContactorPhoneNotBetween(String value1, String value2) {
            addCriterion("amc_contactor_phone not between", value1, value2, "amcContactorPhone");
            return (Criteria) this;
        }

        public Criteria andAssetNoteIsNull() {
            addCriterion("asset_note is null");
            return (Criteria) this;
        }

        public Criteria andAssetNoteIsNotNull() {
            addCriterion("asset_note is not null");
            return (Criteria) this;
        }

        public Criteria andAssetNoteEqualTo(String value) {
            addCriterion("asset_note =", value, "assetNote");
            return (Criteria) this;
        }

        public Criteria andAssetNoteNotEqualTo(String value) {
            addCriterion("asset_note <>", value, "assetNote");
            return (Criteria) this;
        }

        public Criteria andAssetNoteGreaterThan(String value) {
            addCriterion("asset_note >", value, "assetNote");
            return (Criteria) this;
        }

        public Criteria andAssetNoteGreaterThanOrEqualTo(String value) {
            addCriterion("asset_note >=", value, "assetNote");
            return (Criteria) this;
        }

        public Criteria andAssetNoteLessThan(String value) {
            addCriterion("asset_note <", value, "assetNote");
            return (Criteria) this;
        }

        public Criteria andAssetNoteLessThanOrEqualTo(String value) {
            addCriterion("asset_note <=", value, "assetNote");
            return (Criteria) this;
        }

        public Criteria andAssetNoteLike(String value) {
            addCriterion("asset_note like", value, "assetNote");
            return (Criteria) this;
        }

        public Criteria andAssetNoteNotLike(String value) {
            addCriterion("asset_note not like", value, "assetNote");
            return (Criteria) this;
        }

        public Criteria andAssetNoteIn(List<String> values) {
            addCriterion("asset_note in", values, "assetNote");
            return (Criteria) this;
        }

        public Criteria andAssetNoteNotIn(List<String> values) {
            addCriterion("asset_note not in", values, "assetNote");
            return (Criteria) this;
        }

        public Criteria andAssetNoteBetween(String value1, String value2) {
            addCriterion("asset_note between", value1, value2, "assetNote");
            return (Criteria) this;
        }

        public Criteria andAssetNoteNotBetween(String value1, String value2) {
            addCriterion("asset_note not between", value1, value2, "assetNote");
            return (Criteria) this;
        }

        public Criteria andRowNumIsNull() {
            addCriterion("row_num is null");
            return (Criteria) this;
        }

        public Criteria andRowNumIsNotNull() {
            addCriterion("row_num is not null");
            return (Criteria) this;
        }

        public Criteria andRowNumEqualTo(Integer value) {
            addCriterion("row_num =", value, "rowNum");
            return (Criteria) this;
        }

        public Criteria andRowNumNotEqualTo(Integer value) {
            addCriterion("row_num <>", value, "rowNum");
            return (Criteria) this;
        }

        public Criteria andRowNumGreaterThan(Integer value) {
            addCriterion("row_num >", value, "rowNum");
            return (Criteria) this;
        }

        public Criteria andRowNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("row_num >=", value, "rowNum");
            return (Criteria) this;
        }

        public Criteria andRowNumLessThan(Integer value) {
            addCriterion("row_num <", value, "rowNum");
            return (Criteria) this;
        }

        public Criteria andRowNumLessThanOrEqualTo(Integer value) {
            addCriterion("row_num <=", value, "rowNum");
            return (Criteria) this;
        }

        public Criteria andRowNumIn(List<Integer> values) {
            addCriterion("row_num in", values, "rowNum");
            return (Criteria) this;
        }

        public Criteria andRowNumNotIn(List<Integer> values) {
            addCriterion("row_num not in", values, "rowNum");
            return (Criteria) this;
        }

        public Criteria andRowNumBetween(Integer value1, Integer value2) {
            addCriterion("row_num between", value1, value2, "rowNum");
            return (Criteria) this;
        }

        public Criteria andRowNumNotBetween(Integer value1, Integer value2) {
            addCriterion("row_num not between", value1, value2, "rowNum");
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