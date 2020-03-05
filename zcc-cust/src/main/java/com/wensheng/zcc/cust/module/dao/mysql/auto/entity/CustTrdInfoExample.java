package com.wensheng.zcc.cust.module.dao.mysql.auto.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class CustTrdInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CustTrdInfoExample() {
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

        public Criteria andItemTypeIsNull() {
            addCriterion("item_type is null");
            return (Criteria) this;
        }

        public Criteria andItemTypeIsNotNull() {
            addCriterion("item_type is not null");
            return (Criteria) this;
        }

        public Criteria andItemTypeEqualTo(Integer value) {
            addCriterion("item_type =", value, "itemType");
            return (Criteria) this;
        }

        public Criteria andItemTypeNotEqualTo(Integer value) {
            addCriterion("item_type <>", value, "itemType");
            return (Criteria) this;
        }

        public Criteria andItemTypeGreaterThan(Integer value) {
            addCriterion("item_type >", value, "itemType");
            return (Criteria) this;
        }

        public Criteria andItemTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("item_type >=", value, "itemType");
            return (Criteria) this;
        }

        public Criteria andItemTypeLessThan(Integer value) {
            addCriterion("item_type <", value, "itemType");
            return (Criteria) this;
        }

        public Criteria andItemTypeLessThanOrEqualTo(Integer value) {
            addCriterion("item_type <=", value, "itemType");
            return (Criteria) this;
        }

        public Criteria andItemTypeIn(List<Integer> values) {
            addCriterion("item_type in", values, "itemType");
            return (Criteria) this;
        }

        public Criteria andItemTypeNotIn(List<Integer> values) {
            addCriterion("item_type not in", values, "itemType");
            return (Criteria) this;
        }

        public Criteria andItemTypeBetween(Integer value1, Integer value2) {
            addCriterion("item_type between", value1, value2, "itemType");
            return (Criteria) this;
        }

        public Criteria andItemTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("item_type not between", value1, value2, "itemType");
            return (Criteria) this;
        }

        public Criteria andItemSubTypeIsNull() {
            addCriterion("item_sub_type is null");
            return (Criteria) this;
        }

        public Criteria andItemSubTypeIsNotNull() {
            addCriterion("item_sub_type is not null");
            return (Criteria) this;
        }

        public Criteria andItemSubTypeEqualTo(Integer value) {
            addCriterion("item_sub_type =", value, "itemSubType");
            return (Criteria) this;
        }

        public Criteria andItemSubTypeNotEqualTo(Integer value) {
            addCriterion("item_sub_type <>", value, "itemSubType");
            return (Criteria) this;
        }

        public Criteria andItemSubTypeGreaterThan(Integer value) {
            addCriterion("item_sub_type >", value, "itemSubType");
            return (Criteria) this;
        }

        public Criteria andItemSubTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("item_sub_type >=", value, "itemSubType");
            return (Criteria) this;
        }

        public Criteria andItemSubTypeLessThan(Integer value) {
            addCriterion("item_sub_type <", value, "itemSubType");
            return (Criteria) this;
        }

        public Criteria andItemSubTypeLessThanOrEqualTo(Integer value) {
            addCriterion("item_sub_type <=", value, "itemSubType");
            return (Criteria) this;
        }

        public Criteria andItemSubTypeIn(List<Integer> values) {
            addCriterion("item_sub_type in", values, "itemSubType");
            return (Criteria) this;
        }

        public Criteria andItemSubTypeNotIn(List<Integer> values) {
            addCriterion("item_sub_type not in", values, "itemSubType");
            return (Criteria) this;
        }

        public Criteria andItemSubTypeBetween(Integer value1, Integer value2) {
            addCriterion("item_sub_type between", value1, value2, "itemSubType");
            return (Criteria) this;
        }

        public Criteria andItemSubTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("item_sub_type not between", value1, value2, "itemSubType");
            return (Criteria) this;
        }

        public Criteria andTrdTypeIsNull() {
            addCriterion("trd_type is null");
            return (Criteria) this;
        }

        public Criteria andTrdTypeIsNotNull() {
            addCriterion("trd_type is not null");
            return (Criteria) this;
        }

        public Criteria andTrdTypeEqualTo(Integer value) {
            addCriterion("trd_type =", value, "trdType");
            return (Criteria) this;
        }

        public Criteria andTrdTypeNotEqualTo(Integer value) {
            addCriterion("trd_type <>", value, "trdType");
            return (Criteria) this;
        }

        public Criteria andTrdTypeGreaterThan(Integer value) {
            addCriterion("trd_type >", value, "trdType");
            return (Criteria) this;
        }

        public Criteria andTrdTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("trd_type >=", value, "trdType");
            return (Criteria) this;
        }

        public Criteria andTrdTypeLessThan(Integer value) {
            addCriterion("trd_type <", value, "trdType");
            return (Criteria) this;
        }

        public Criteria andTrdTypeLessThanOrEqualTo(Integer value) {
            addCriterion("trd_type <=", value, "trdType");
            return (Criteria) this;
        }

        public Criteria andTrdTypeIn(List<Integer> values) {
            addCriterion("trd_type in", values, "trdType");
            return (Criteria) this;
        }

        public Criteria andTrdTypeNotIn(List<Integer> values) {
            addCriterion("trd_type not in", values, "trdType");
            return (Criteria) this;
        }

        public Criteria andTrdTypeBetween(Integer value1, Integer value2) {
            addCriterion("trd_type between", value1, value2, "trdType");
            return (Criteria) this;
        }

        public Criteria andTrdTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("trd_type not between", value1, value2, "trdType");
            return (Criteria) this;
        }

        public Criteria andInfoTitleIsNull() {
            addCriterion("info_title is null");
            return (Criteria) this;
        }

        public Criteria andInfoTitleIsNotNull() {
            addCriterion("info_title is not null");
            return (Criteria) this;
        }

        public Criteria andInfoTitleEqualTo(String value) {
            addCriterion("info_title =", value, "infoTitle");
            return (Criteria) this;
        }

        public Criteria andInfoTitleNotEqualTo(String value) {
            addCriterion("info_title <>", value, "infoTitle");
            return (Criteria) this;
        }

        public Criteria andInfoTitleGreaterThan(String value) {
            addCriterion("info_title >", value, "infoTitle");
            return (Criteria) this;
        }

        public Criteria andInfoTitleGreaterThanOrEqualTo(String value) {
            addCriterion("info_title >=", value, "infoTitle");
            return (Criteria) this;
        }

        public Criteria andInfoTitleLessThan(String value) {
            addCriterion("info_title <", value, "infoTitle");
            return (Criteria) this;
        }

        public Criteria andInfoTitleLessThanOrEqualTo(String value) {
            addCriterion("info_title <=", value, "infoTitle");
            return (Criteria) this;
        }

        public Criteria andInfoTitleLike(String value) {
            addCriterion("info_title like", value, "infoTitle");
            return (Criteria) this;
        }

        public Criteria andInfoTitleNotLike(String value) {
            addCriterion("info_title not like", value, "infoTitle");
            return (Criteria) this;
        }

        public Criteria andInfoTitleIn(List<String> values) {
            addCriterion("info_title in", values, "infoTitle");
            return (Criteria) this;
        }

        public Criteria andInfoTitleNotIn(List<String> values) {
            addCriterion("info_title not in", values, "infoTitle");
            return (Criteria) this;
        }

        public Criteria andInfoTitleBetween(String value1, String value2) {
            addCriterion("info_title between", value1, value2, "infoTitle");
            return (Criteria) this;
        }

        public Criteria andInfoTitleNotBetween(String value1, String value2) {
            addCriterion("info_title not between", value1, value2, "infoTitle");
            return (Criteria) this;
        }

        public Criteria andPackCountIsNull() {
            addCriterion("pack_count is null");
            return (Criteria) this;
        }

        public Criteria andPackCountIsNotNull() {
            addCriterion("pack_count is not null");
            return (Criteria) this;
        }

        public Criteria andPackCountEqualTo(Integer value) {
            addCriterion("pack_count =", value, "packCount");
            return (Criteria) this;
        }

        public Criteria andPackCountNotEqualTo(Integer value) {
            addCriterion("pack_count <>", value, "packCount");
            return (Criteria) this;
        }

        public Criteria andPackCountGreaterThan(Integer value) {
            addCriterion("pack_count >", value, "packCount");
            return (Criteria) this;
        }

        public Criteria andPackCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("pack_count >=", value, "packCount");
            return (Criteria) this;
        }

        public Criteria andPackCountLessThan(Integer value) {
            addCriterion("pack_count <", value, "packCount");
            return (Criteria) this;
        }

        public Criteria andPackCountLessThanOrEqualTo(Integer value) {
            addCriterion("pack_count <=", value, "packCount");
            return (Criteria) this;
        }

        public Criteria andPackCountIn(List<Integer> values) {
            addCriterion("pack_count in", values, "packCount");
            return (Criteria) this;
        }

        public Criteria andPackCountNotIn(List<Integer> values) {
            addCriterion("pack_count not in", values, "packCount");
            return (Criteria) this;
        }

        public Criteria andPackCountBetween(Integer value1, Integer value2) {
            addCriterion("pack_count between", value1, value2, "packCount");
            return (Criteria) this;
        }

        public Criteria andPackCountNotBetween(Integer value1, Integer value2) {
            addCriterion("pack_count not between", value1, value2, "packCount");
            return (Criteria) this;
        }

        public Criteria andInfoIdIsNull() {
            addCriterion("info_id is null");
            return (Criteria) this;
        }

        public Criteria andInfoIdIsNotNull() {
            addCriterion("info_id is not null");
            return (Criteria) this;
        }

        public Criteria andInfoIdEqualTo(String value) {
            addCriterion("info_id =", value, "infoId");
            return (Criteria) this;
        }

        public Criteria andInfoIdNotEqualTo(String value) {
            addCriterion("info_id <>", value, "infoId");
            return (Criteria) this;
        }

        public Criteria andInfoIdGreaterThan(String value) {
            addCriterion("info_id >", value, "infoId");
            return (Criteria) this;
        }

        public Criteria andInfoIdGreaterThanOrEqualTo(String value) {
            addCriterion("info_id >=", value, "infoId");
            return (Criteria) this;
        }

        public Criteria andInfoIdLessThan(String value) {
            addCriterion("info_id <", value, "infoId");
            return (Criteria) this;
        }

        public Criteria andInfoIdLessThanOrEqualTo(String value) {
            addCriterion("info_id <=", value, "infoId");
            return (Criteria) this;
        }

        public Criteria andInfoIdLike(String value) {
            addCriterion("info_id like", value, "infoId");
            return (Criteria) this;
        }

        public Criteria andInfoIdNotLike(String value) {
            addCriterion("info_id not like", value, "infoId");
            return (Criteria) this;
        }

        public Criteria andInfoIdIn(List<String> values) {
            addCriterion("info_id in", values, "infoId");
            return (Criteria) this;
        }

        public Criteria andInfoIdNotIn(List<String> values) {
            addCriterion("info_id not in", values, "infoId");
            return (Criteria) this;
        }

        public Criteria andInfoIdBetween(String value1, String value2) {
            addCriterion("info_id between", value1, value2, "infoId");
            return (Criteria) this;
        }

        public Criteria andInfoIdNotBetween(String value1, String value2) {
            addCriterion("info_id not between", value1, value2, "infoId");
            return (Criteria) this;
        }

        public Criteria andInfoSourceIsNull() {
            addCriterion("info_source is null");
            return (Criteria) this;
        }

        public Criteria andInfoSourceIsNotNull() {
            addCriterion("info_source is not null");
            return (Criteria) this;
        }

        public Criteria andInfoSourceEqualTo(String value) {
            addCriterion("info_source =", value, "infoSource");
            return (Criteria) this;
        }

        public Criteria andInfoSourceNotEqualTo(String value) {
            addCriterion("info_source <>", value, "infoSource");
            return (Criteria) this;
        }

        public Criteria andInfoSourceGreaterThan(String value) {
            addCriterion("info_source >", value, "infoSource");
            return (Criteria) this;
        }

        public Criteria andInfoSourceGreaterThanOrEqualTo(String value) {
            addCriterion("info_source >=", value, "infoSource");
            return (Criteria) this;
        }

        public Criteria andInfoSourceLessThan(String value) {
            addCriterion("info_source <", value, "infoSource");
            return (Criteria) this;
        }

        public Criteria andInfoSourceLessThanOrEqualTo(String value) {
            addCriterion("info_source <=", value, "infoSource");
            return (Criteria) this;
        }

        public Criteria andInfoSourceLike(String value) {
            addCriterion("info_source like", value, "infoSource");
            return (Criteria) this;
        }

        public Criteria andInfoSourceNotLike(String value) {
            addCriterion("info_source not like", value, "infoSource");
            return (Criteria) this;
        }

        public Criteria andInfoSourceIn(List<String> values) {
            addCriterion("info_source in", values, "infoSource");
            return (Criteria) this;
        }

        public Criteria andInfoSourceNotIn(List<String> values) {
            addCriterion("info_source not in", values, "infoSource");
            return (Criteria) this;
        }

        public Criteria andInfoSourceBetween(String value1, String value2) {
            addCriterion("info_source between", value1, value2, "infoSource");
            return (Criteria) this;
        }

        public Criteria andInfoSourceNotBetween(String value1, String value2) {
            addCriterion("info_source not between", value1, value2, "infoSource");
            return (Criteria) this;
        }

        public Criteria andInfoUrlIsNull() {
            addCriterion("info_url is null");
            return (Criteria) this;
        }

        public Criteria andInfoUrlIsNotNull() {
            addCriterion("info_url is not null");
            return (Criteria) this;
        }

        public Criteria andInfoUrlEqualTo(String value) {
            addCriterion("info_url =", value, "infoUrl");
            return (Criteria) this;
        }

        public Criteria andInfoUrlNotEqualTo(String value) {
            addCriterion("info_url <>", value, "infoUrl");
            return (Criteria) this;
        }

        public Criteria andInfoUrlGreaterThan(String value) {
            addCriterion("info_url >", value, "infoUrl");
            return (Criteria) this;
        }

        public Criteria andInfoUrlGreaterThanOrEqualTo(String value) {
            addCriterion("info_url >=", value, "infoUrl");
            return (Criteria) this;
        }

        public Criteria andInfoUrlLessThan(String value) {
            addCriterion("info_url <", value, "infoUrl");
            return (Criteria) this;
        }

        public Criteria andInfoUrlLessThanOrEqualTo(String value) {
            addCriterion("info_url <=", value, "infoUrl");
            return (Criteria) this;
        }

        public Criteria andInfoUrlLike(String value) {
            addCriterion("info_url like", value, "infoUrl");
            return (Criteria) this;
        }

        public Criteria andInfoUrlNotLike(String value) {
            addCriterion("info_url not like", value, "infoUrl");
            return (Criteria) this;
        }

        public Criteria andInfoUrlIn(List<String> values) {
            addCriterion("info_url in", values, "infoUrl");
            return (Criteria) this;
        }

        public Criteria andInfoUrlNotIn(List<String> values) {
            addCriterion("info_url not in", values, "infoUrl");
            return (Criteria) this;
        }

        public Criteria andInfoUrlBetween(String value1, String value2) {
            addCriterion("info_url between", value1, value2, "infoUrl");
            return (Criteria) this;
        }

        public Criteria andInfoUrlNotBetween(String value1, String value2) {
            addCriterion("info_url not between", value1, value2, "infoUrl");
            return (Criteria) this;
        }

        public Criteria andDebtProvinceIsNull() {
            addCriterion("debt_province is null");
            return (Criteria) this;
        }

        public Criteria andDebtProvinceIsNotNull() {
            addCriterion("debt_province is not null");
            return (Criteria) this;
        }

        public Criteria andDebtProvinceEqualTo(String value) {
            addCriterion("debt_province =", value, "debtProvince");
            return (Criteria) this;
        }

        public Criteria andDebtProvinceNotEqualTo(String value) {
            addCriterion("debt_province <>", value, "debtProvince");
            return (Criteria) this;
        }

        public Criteria andDebtProvinceGreaterThan(String value) {
            addCriterion("debt_province >", value, "debtProvince");
            return (Criteria) this;
        }

        public Criteria andDebtProvinceGreaterThanOrEqualTo(String value) {
            addCriterion("debt_province >=", value, "debtProvince");
            return (Criteria) this;
        }

        public Criteria andDebtProvinceLessThan(String value) {
            addCriterion("debt_province <", value, "debtProvince");
            return (Criteria) this;
        }

        public Criteria andDebtProvinceLessThanOrEqualTo(String value) {
            addCriterion("debt_province <=", value, "debtProvince");
            return (Criteria) this;
        }

        public Criteria andDebtProvinceLike(String value) {
            addCriterion("debt_province like", value, "debtProvince");
            return (Criteria) this;
        }

        public Criteria andDebtProvinceNotLike(String value) {
            addCriterion("debt_province not like", value, "debtProvince");
            return (Criteria) this;
        }

        public Criteria andDebtProvinceIn(List<String> values) {
            addCriterion("debt_province in", values, "debtProvince");
            return (Criteria) this;
        }

        public Criteria andDebtProvinceNotIn(List<String> values) {
            addCriterion("debt_province not in", values, "debtProvince");
            return (Criteria) this;
        }

        public Criteria andDebtProvinceBetween(String value1, String value2) {
            addCriterion("debt_province between", value1, value2, "debtProvince");
            return (Criteria) this;
        }

        public Criteria andDebtProvinceNotBetween(String value1, String value2) {
            addCriterion("debt_province not between", value1, value2, "debtProvince");
            return (Criteria) this;
        }

        public Criteria andTrdProvinceIsNull() {
            addCriterion("trd_province is null");
            return (Criteria) this;
        }

        public Criteria andTrdProvinceIsNotNull() {
            addCriterion("trd_province is not null");
            return (Criteria) this;
        }

        public Criteria andTrdProvinceEqualTo(String value) {
            addCriterion("trd_province =", value, "trdProvince");
            return (Criteria) this;
        }

        public Criteria andTrdProvinceNotEqualTo(String value) {
            addCriterion("trd_province <>", value, "trdProvince");
            return (Criteria) this;
        }

        public Criteria andTrdProvinceGreaterThan(String value) {
            addCriterion("trd_province >", value, "trdProvince");
            return (Criteria) this;
        }

        public Criteria andTrdProvinceGreaterThanOrEqualTo(String value) {
            addCriterion("trd_province >=", value, "trdProvince");
            return (Criteria) this;
        }

        public Criteria andTrdProvinceLessThan(String value) {
            addCriterion("trd_province <", value, "trdProvince");
            return (Criteria) this;
        }

        public Criteria andTrdProvinceLessThanOrEqualTo(String value) {
            addCriterion("trd_province <=", value, "trdProvince");
            return (Criteria) this;
        }

        public Criteria andTrdProvinceLike(String value) {
            addCriterion("trd_province like", value, "trdProvince");
            return (Criteria) this;
        }

        public Criteria andTrdProvinceNotLike(String value) {
            addCriterion("trd_province not like", value, "trdProvince");
            return (Criteria) this;
        }

        public Criteria andTrdProvinceIn(List<String> values) {
            addCriterion("trd_province in", values, "trdProvince");
            return (Criteria) this;
        }

        public Criteria andTrdProvinceNotIn(List<String> values) {
            addCriterion("trd_province not in", values, "trdProvince");
            return (Criteria) this;
        }

        public Criteria andTrdProvinceBetween(String value1, String value2) {
            addCriterion("trd_province between", value1, value2, "trdProvince");
            return (Criteria) this;
        }

        public Criteria andTrdProvinceNotBetween(String value1, String value2) {
            addCriterion("trd_province not between", value1, value2, "trdProvince");
            return (Criteria) this;
        }

        public Criteria andDebtCityIsNull() {
            addCriterion("debt_city is null");
            return (Criteria) this;
        }

        public Criteria andDebtCityIsNotNull() {
            addCriterion("debt_city is not null");
            return (Criteria) this;
        }

        public Criteria andDebtCityEqualTo(String value) {
            addCriterion("debt_city =", value, "debtCity");
            return (Criteria) this;
        }

        public Criteria andDebtCityNotEqualTo(String value) {
            addCriterion("debt_city <>", value, "debtCity");
            return (Criteria) this;
        }

        public Criteria andDebtCityGreaterThan(String value) {
            addCriterion("debt_city >", value, "debtCity");
            return (Criteria) this;
        }

        public Criteria andDebtCityGreaterThanOrEqualTo(String value) {
            addCriterion("debt_city >=", value, "debtCity");
            return (Criteria) this;
        }

        public Criteria andDebtCityLessThan(String value) {
            addCriterion("debt_city <", value, "debtCity");
            return (Criteria) this;
        }

        public Criteria andDebtCityLessThanOrEqualTo(String value) {
            addCriterion("debt_city <=", value, "debtCity");
            return (Criteria) this;
        }

        public Criteria andDebtCityLike(String value) {
            addCriterion("debt_city like", value, "debtCity");
            return (Criteria) this;
        }

        public Criteria andDebtCityNotLike(String value) {
            addCriterion("debt_city not like", value, "debtCity");
            return (Criteria) this;
        }

        public Criteria andDebtCityIn(List<String> values) {
            addCriterion("debt_city in", values, "debtCity");
            return (Criteria) this;
        }

        public Criteria andDebtCityNotIn(List<String> values) {
            addCriterion("debt_city not in", values, "debtCity");
            return (Criteria) this;
        }

        public Criteria andDebtCityBetween(String value1, String value2) {
            addCriterion("debt_city between", value1, value2, "debtCity");
            return (Criteria) this;
        }

        public Criteria andDebtCityNotBetween(String value1, String value2) {
            addCriterion("debt_city not between", value1, value2, "debtCity");
            return (Criteria) this;
        }

        public Criteria andTrdAmountOrigIsNull() {
            addCriterion("trd_amount_orig is null");
            return (Criteria) this;
        }

        public Criteria andTrdAmountOrigIsNotNull() {
            addCriterion("trd_amount_orig is not null");
            return (Criteria) this;
        }

        public Criteria andTrdAmountOrigEqualTo(String value) {
            addCriterion("trd_amount_orig =", value, "trdAmountOrig");
            return (Criteria) this;
        }

        public Criteria andTrdAmountOrigNotEqualTo(String value) {
            addCriterion("trd_amount_orig <>", value, "trdAmountOrig");
            return (Criteria) this;
        }

        public Criteria andTrdAmountOrigGreaterThan(String value) {
            addCriterion("trd_amount_orig >", value, "trdAmountOrig");
            return (Criteria) this;
        }

        public Criteria andTrdAmountOrigGreaterThanOrEqualTo(String value) {
            addCriterion("trd_amount_orig >=", value, "trdAmountOrig");
            return (Criteria) this;
        }

        public Criteria andTrdAmountOrigLessThan(String value) {
            addCriterion("trd_amount_orig <", value, "trdAmountOrig");
            return (Criteria) this;
        }

        public Criteria andTrdAmountOrigLessThanOrEqualTo(String value) {
            addCriterion("trd_amount_orig <=", value, "trdAmountOrig");
            return (Criteria) this;
        }

        public Criteria andTrdAmountOrigLike(String value) {
            addCriterion("trd_amount_orig like", value, "trdAmountOrig");
            return (Criteria) this;
        }

        public Criteria andTrdAmountOrigNotLike(String value) {
            addCriterion("trd_amount_orig not like", value, "trdAmountOrig");
            return (Criteria) this;
        }

        public Criteria andTrdAmountOrigIn(List<String> values) {
            addCriterion("trd_amount_orig in", values, "trdAmountOrig");
            return (Criteria) this;
        }

        public Criteria andTrdAmountOrigNotIn(List<String> values) {
            addCriterion("trd_amount_orig not in", values, "trdAmountOrig");
            return (Criteria) this;
        }

        public Criteria andTrdAmountOrigBetween(String value1, String value2) {
            addCriterion("trd_amount_orig between", value1, value2, "trdAmountOrig");
            return (Criteria) this;
        }

        public Criteria andTrdAmountOrigNotBetween(String value1, String value2) {
            addCriterion("trd_amount_orig not between", value1, value2, "trdAmountOrig");
            return (Criteria) this;
        }

        public Criteria andTrdAmountIsNull() {
            addCriterion("trd_amount is null");
            return (Criteria) this;
        }

        public Criteria andTrdAmountIsNotNull() {
            addCriterion("trd_amount is not null");
            return (Criteria) this;
        }

        public Criteria andTrdAmountEqualTo(Long value) {
            addCriterion("trd_amount =", value, "trdAmount");
            return (Criteria) this;
        }

        public Criteria andTrdAmountNotEqualTo(Long value) {
            addCriterion("trd_amount <>", value, "trdAmount");
            return (Criteria) this;
        }

        public Criteria andTrdAmountGreaterThan(Long value) {
            addCriterion("trd_amount >", value, "trdAmount");
            return (Criteria) this;
        }

        public Criteria andTrdAmountGreaterThanOrEqualTo(Long value) {
            addCriterion("trd_amount >=", value, "trdAmount");
            return (Criteria) this;
        }

        public Criteria andTrdAmountLessThan(Long value) {
            addCriterion("trd_amount <", value, "trdAmount");
            return (Criteria) this;
        }

        public Criteria andTrdAmountLessThanOrEqualTo(Long value) {
            addCriterion("trd_amount <=", value, "trdAmount");
            return (Criteria) this;
        }

        public Criteria andTrdAmountIn(List<Long> values) {
            addCriterion("trd_amount in", values, "trdAmount");
            return (Criteria) this;
        }

        public Criteria andTrdAmountNotIn(List<Long> values) {
            addCriterion("trd_amount not in", values, "trdAmount");
            return (Criteria) this;
        }

        public Criteria andTrdAmountBetween(Long value1, Long value2) {
            addCriterion("trd_amount between", value1, value2, "trdAmount");
            return (Criteria) this;
        }

        public Criteria andTrdAmountNotBetween(Long value1, Long value2) {
            addCriterion("trd_amount not between", value1, value2, "trdAmount");
            return (Criteria) this;
        }

        public Criteria andTotalDebtAmountIsNull() {
            addCriterion("total_debt_amount is null");
            return (Criteria) this;
        }

        public Criteria andTotalDebtAmountIsNotNull() {
            addCriterion("total_debt_amount is not null");
            return (Criteria) this;
        }

        public Criteria andTotalDebtAmountEqualTo(Long value) {
            addCriterion("total_debt_amount =", value, "totalDebtAmount");
            return (Criteria) this;
        }

        public Criteria andTotalDebtAmountNotEqualTo(Long value) {
            addCriterion("total_debt_amount <>", value, "totalDebtAmount");
            return (Criteria) this;
        }

        public Criteria andTotalDebtAmountGreaterThan(Long value) {
            addCriterion("total_debt_amount >", value, "totalDebtAmount");
            return (Criteria) this;
        }

        public Criteria andTotalDebtAmountGreaterThanOrEqualTo(Long value) {
            addCriterion("total_debt_amount >=", value, "totalDebtAmount");
            return (Criteria) this;
        }

        public Criteria andTotalDebtAmountLessThan(Long value) {
            addCriterion("total_debt_amount <", value, "totalDebtAmount");
            return (Criteria) this;
        }

        public Criteria andTotalDebtAmountLessThanOrEqualTo(Long value) {
            addCriterion("total_debt_amount <=", value, "totalDebtAmount");
            return (Criteria) this;
        }

        public Criteria andTotalDebtAmountIn(List<Long> values) {
            addCriterion("total_debt_amount in", values, "totalDebtAmount");
            return (Criteria) this;
        }

        public Criteria andTotalDebtAmountNotIn(List<Long> values) {
            addCriterion("total_debt_amount not in", values, "totalDebtAmount");
            return (Criteria) this;
        }

        public Criteria andTotalDebtAmountBetween(Long value1, Long value2) {
            addCriterion("total_debt_amount between", value1, value2, "totalDebtAmount");
            return (Criteria) this;
        }

        public Criteria andTotalDebtAmountNotBetween(Long value1, Long value2) {
            addCriterion("total_debt_amount not between", value1, value2, "totalDebtAmount");
            return (Criteria) this;
        }

        public Criteria andTrdStatusIsNull() {
            addCriterion("trd_status is null");
            return (Criteria) this;
        }

        public Criteria andTrdStatusIsNotNull() {
            addCriterion("trd_status is not null");
            return (Criteria) this;
        }

        public Criteria andTrdStatusEqualTo(Integer value) {
            addCriterion("trd_status =", value, "trdStatus");
            return (Criteria) this;
        }

        public Criteria andTrdStatusNotEqualTo(Integer value) {
            addCriterion("trd_status <>", value, "trdStatus");
            return (Criteria) this;
        }

        public Criteria andTrdStatusGreaterThan(Integer value) {
            addCriterion("trd_status >", value, "trdStatus");
            return (Criteria) this;
        }

        public Criteria andTrdStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("trd_status >=", value, "trdStatus");
            return (Criteria) this;
        }

        public Criteria andTrdStatusLessThan(Integer value) {
            addCriterion("trd_status <", value, "trdStatus");
            return (Criteria) this;
        }

        public Criteria andTrdStatusLessThanOrEqualTo(Integer value) {
            addCriterion("trd_status <=", value, "trdStatus");
            return (Criteria) this;
        }

        public Criteria andTrdStatusIn(List<Integer> values) {
            addCriterion("trd_status in", values, "trdStatus");
            return (Criteria) this;
        }

        public Criteria andTrdStatusNotIn(List<Integer> values) {
            addCriterion("trd_status not in", values, "trdStatus");
            return (Criteria) this;
        }

        public Criteria andTrdStatusBetween(Integer value1, Integer value2) {
            addCriterion("trd_status between", value1, value2, "trdStatus");
            return (Criteria) this;
        }

        public Criteria andTrdStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("trd_status not between", value1, value2, "trdStatus");
            return (Criteria) this;
        }

        public Criteria andTrdDateIsNull() {
            addCriterion("trd_date is null");
            return (Criteria) this;
        }

        public Criteria andTrdDateIsNotNull() {
            addCriterion("trd_date is not null");
            return (Criteria) this;
        }

        public Criteria andTrdDateEqualTo(Date value) {
            addCriterionForJDBCDate("trd_date =", value, "trdDate");
            return (Criteria) this;
        }

        public Criteria andTrdDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("trd_date <>", value, "trdDate");
            return (Criteria) this;
        }

        public Criteria andTrdDateGreaterThan(Date value) {
            addCriterionForJDBCDate("trd_date >", value, "trdDate");
            return (Criteria) this;
        }

        public Criteria andTrdDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("trd_date >=", value, "trdDate");
            return (Criteria) this;
        }

        public Criteria andTrdDateLessThan(Date value) {
            addCriterionForJDBCDate("trd_date <", value, "trdDate");
            return (Criteria) this;
        }

        public Criteria andTrdDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("trd_date <=", value, "trdDate");
            return (Criteria) this;
        }

        public Criteria andTrdDateIn(List<Date> values) {
            addCriterionForJDBCDate("trd_date in", values, "trdDate");
            return (Criteria) this;
        }

        public Criteria andTrdDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("trd_date not in", values, "trdDate");
            return (Criteria) this;
        }

        public Criteria andTrdDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("trd_date between", value1, value2, "trdDate");
            return (Criteria) this;
        }

        public Criteria andTrdDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("trd_date not between", value1, value2, "trdDate");
            return (Criteria) this;
        }

        public Criteria andPubDateIsNull() {
            addCriterion("pub_date is null");
            return (Criteria) this;
        }

        public Criteria andPubDateIsNotNull() {
            addCriterion("pub_date is not null");
            return (Criteria) this;
        }

        public Criteria andPubDateEqualTo(Date value) {
            addCriterion("pub_date =", value, "pubDate");
            return (Criteria) this;
        }

        public Criteria andPubDateNotEqualTo(Date value) {
            addCriterion("pub_date <>", value, "pubDate");
            return (Criteria) this;
        }

        public Criteria andPubDateGreaterThan(Date value) {
            addCriterion("pub_date >", value, "pubDate");
            return (Criteria) this;
        }

        public Criteria andPubDateGreaterThanOrEqualTo(Date value) {
            addCriterion("pub_date >=", value, "pubDate");
            return (Criteria) this;
        }

        public Criteria andPubDateLessThan(Date value) {
            addCriterion("pub_date <", value, "pubDate");
            return (Criteria) this;
        }

        public Criteria andPubDateLessThanOrEqualTo(Date value) {
            addCriterion("pub_date <=", value, "pubDate");
            return (Criteria) this;
        }

        public Criteria andPubDateIn(List<Date> values) {
            addCriterion("pub_date in", values, "pubDate");
            return (Criteria) this;
        }

        public Criteria andPubDateNotIn(List<Date> values) {
            addCriterion("pub_date not in", values, "pubDate");
            return (Criteria) this;
        }

        public Criteria andPubDateBetween(Date value1, Date value2) {
            addCriterion("pub_date between", value1, value2, "pubDate");
            return (Criteria) this;
        }

        public Criteria andPubDateNotBetween(Date value1, Date value2) {
            addCriterion("pub_date not between", value1, value2, "pubDate");
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

        public Criteria andBuyerTypeIsNull() {
            addCriterion("buyer_type is null");
            return (Criteria) this;
        }

        public Criteria andBuyerTypeIsNotNull() {
            addCriterion("buyer_type is not null");
            return (Criteria) this;
        }

        public Criteria andBuyerTypeEqualTo(Integer value) {
            addCriterion("buyer_type =", value, "buyerType");
            return (Criteria) this;
        }

        public Criteria andBuyerTypeNotEqualTo(Integer value) {
            addCriterion("buyer_type <>", value, "buyerType");
            return (Criteria) this;
        }

        public Criteria andBuyerTypeGreaterThan(Integer value) {
            addCriterion("buyer_type >", value, "buyerType");
            return (Criteria) this;
        }

        public Criteria andBuyerTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("buyer_type >=", value, "buyerType");
            return (Criteria) this;
        }

        public Criteria andBuyerTypeLessThan(Integer value) {
            addCriterion("buyer_type <", value, "buyerType");
            return (Criteria) this;
        }

        public Criteria andBuyerTypeLessThanOrEqualTo(Integer value) {
            addCriterion("buyer_type <=", value, "buyerType");
            return (Criteria) this;
        }

        public Criteria andBuyerTypeIn(List<Integer> values) {
            addCriterion("buyer_type in", values, "buyerType");
            return (Criteria) this;
        }

        public Criteria andBuyerTypeNotIn(List<Integer> values) {
            addCriterion("buyer_type not in", values, "buyerType");
            return (Criteria) this;
        }

        public Criteria andBuyerTypeBetween(Integer value1, Integer value2) {
            addCriterion("buyer_type between", value1, value2, "buyerType");
            return (Criteria) this;
        }

        public Criteria andBuyerTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("buyer_type not between", value1, value2, "buyerType");
            return (Criteria) this;
        }

        public Criteria andBuyerIdIsNull() {
            addCriterion("buyer_id is null");
            return (Criteria) this;
        }

        public Criteria andBuyerIdIsNotNull() {
            addCriterion("buyer_id is not null");
            return (Criteria) this;
        }

        public Criteria andBuyerIdEqualTo(Long value) {
            addCriterion("buyer_id =", value, "buyerId");
            return (Criteria) this;
        }

        public Criteria andBuyerIdNotEqualTo(Long value) {
            addCriterion("buyer_id <>", value, "buyerId");
            return (Criteria) this;
        }

        public Criteria andBuyerIdGreaterThan(Long value) {
            addCriterion("buyer_id >", value, "buyerId");
            return (Criteria) this;
        }

        public Criteria andBuyerIdGreaterThanOrEqualTo(Long value) {
            addCriterion("buyer_id >=", value, "buyerId");
            return (Criteria) this;
        }

        public Criteria andBuyerIdLessThan(Long value) {
            addCriterion("buyer_id <", value, "buyerId");
            return (Criteria) this;
        }

        public Criteria andBuyerIdLessThanOrEqualTo(Long value) {
            addCriterion("buyer_id <=", value, "buyerId");
            return (Criteria) this;
        }

        public Criteria andBuyerIdIn(List<Long> values) {
            addCriterion("buyer_id in", values, "buyerId");
            return (Criteria) this;
        }

        public Criteria andBuyerIdNotIn(List<Long> values) {
            addCriterion("buyer_id not in", values, "buyerId");
            return (Criteria) this;
        }

        public Criteria andBuyerIdBetween(Long value1, Long value2) {
            addCriterion("buyer_id between", value1, value2, "buyerId");
            return (Criteria) this;
        }

        public Criteria andBuyerIdNotBetween(Long value1, Long value2) {
            addCriterion("buyer_id not between", value1, value2, "buyerId");
            return (Criteria) this;
        }

        public Criteria andSellerTypeIsNull() {
            addCriterion("seller_type is null");
            return (Criteria) this;
        }

        public Criteria andSellerTypeIsNotNull() {
            addCriterion("seller_type is not null");
            return (Criteria) this;
        }

        public Criteria andSellerTypeEqualTo(Integer value) {
            addCriterion("seller_type =", value, "sellerType");
            return (Criteria) this;
        }

        public Criteria andSellerTypeNotEqualTo(Integer value) {
            addCriterion("seller_type <>", value, "sellerType");
            return (Criteria) this;
        }

        public Criteria andSellerTypeGreaterThan(Integer value) {
            addCriterion("seller_type >", value, "sellerType");
            return (Criteria) this;
        }

        public Criteria andSellerTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("seller_type >=", value, "sellerType");
            return (Criteria) this;
        }

        public Criteria andSellerTypeLessThan(Integer value) {
            addCriterion("seller_type <", value, "sellerType");
            return (Criteria) this;
        }

        public Criteria andSellerTypeLessThanOrEqualTo(Integer value) {
            addCriterion("seller_type <=", value, "sellerType");
            return (Criteria) this;
        }

        public Criteria andSellerTypeIn(List<Integer> values) {
            addCriterion("seller_type in", values, "sellerType");
            return (Criteria) this;
        }

        public Criteria andSellerTypeNotIn(List<Integer> values) {
            addCriterion("seller_type not in", values, "sellerType");
            return (Criteria) this;
        }

        public Criteria andSellerTypeBetween(Integer value1, Integer value2) {
            addCriterion("seller_type between", value1, value2, "sellerType");
            return (Criteria) this;
        }

        public Criteria andSellerTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("seller_type not between", value1, value2, "sellerType");
            return (Criteria) this;
        }

        public Criteria andSellerIdIsNull() {
            addCriterion("seller_id is null");
            return (Criteria) this;
        }

        public Criteria andSellerIdIsNotNull() {
            addCriterion("seller_id is not null");
            return (Criteria) this;
        }

        public Criteria andSellerIdEqualTo(Long value) {
            addCriterion("seller_id =", value, "sellerId");
            return (Criteria) this;
        }

        public Criteria andSellerIdNotEqualTo(Long value) {
            addCriterion("seller_id <>", value, "sellerId");
            return (Criteria) this;
        }

        public Criteria andSellerIdGreaterThan(Long value) {
            addCriterion("seller_id >", value, "sellerId");
            return (Criteria) this;
        }

        public Criteria andSellerIdGreaterThanOrEqualTo(Long value) {
            addCriterion("seller_id >=", value, "sellerId");
            return (Criteria) this;
        }

        public Criteria andSellerIdLessThan(Long value) {
            addCriterion("seller_id <", value, "sellerId");
            return (Criteria) this;
        }

        public Criteria andSellerIdLessThanOrEqualTo(Long value) {
            addCriterion("seller_id <=", value, "sellerId");
            return (Criteria) this;
        }

        public Criteria andSellerIdIn(List<Long> values) {
            addCriterion("seller_id in", values, "sellerId");
            return (Criteria) this;
        }

        public Criteria andSellerIdNotIn(List<Long> values) {
            addCriterion("seller_id not in", values, "sellerId");
            return (Criteria) this;
        }

        public Criteria andSellerIdBetween(Long value1, Long value2) {
            addCriterion("seller_id between", value1, value2, "sellerId");
            return (Criteria) this;
        }

        public Criteria andSellerIdNotBetween(Long value1, Long value2) {
            addCriterion("seller_id not between", value1, value2, "sellerId");
            return (Criteria) this;
        }

        public Criteria andSellerNameIsNull() {
            addCriterion("seller_name is null");
            return (Criteria) this;
        }

        public Criteria andSellerNameIsNotNull() {
            addCriterion("seller_name is not null");
            return (Criteria) this;
        }

        public Criteria andSellerNameEqualTo(String value) {
            addCriterion("seller_name =", value, "sellerName");
            return (Criteria) this;
        }

        public Criteria andSellerNameNotEqualTo(String value) {
            addCriterion("seller_name <>", value, "sellerName");
            return (Criteria) this;
        }

        public Criteria andSellerNameGreaterThan(String value) {
            addCriterion("seller_name >", value, "sellerName");
            return (Criteria) this;
        }

        public Criteria andSellerNameGreaterThanOrEqualTo(String value) {
            addCriterion("seller_name >=", value, "sellerName");
            return (Criteria) this;
        }

        public Criteria andSellerNameLessThan(String value) {
            addCriterion("seller_name <", value, "sellerName");
            return (Criteria) this;
        }

        public Criteria andSellerNameLessThanOrEqualTo(String value) {
            addCriterion("seller_name <=", value, "sellerName");
            return (Criteria) this;
        }

        public Criteria andSellerNameLike(String value) {
            addCriterion("seller_name like", value, "sellerName");
            return (Criteria) this;
        }

        public Criteria andSellerNameNotLike(String value) {
            addCriterion("seller_name not like", value, "sellerName");
            return (Criteria) this;
        }

        public Criteria andSellerNameIn(List<String> values) {
            addCriterion("seller_name in", values, "sellerName");
            return (Criteria) this;
        }

        public Criteria andSellerNameNotIn(List<String> values) {
            addCriterion("seller_name not in", values, "sellerName");
            return (Criteria) this;
        }

        public Criteria andSellerNameBetween(String value1, String value2) {
            addCriterion("seller_name between", value1, value2, "sellerName");
            return (Criteria) this;
        }

        public Criteria andSellerNameNotBetween(String value1, String value2) {
            addCriterion("seller_name not between", value1, value2, "sellerName");
            return (Criteria) this;
        }

        public Criteria andTrdContactorNameIsNull() {
            addCriterion("trd_contactor_name is null");
            return (Criteria) this;
        }

        public Criteria andTrdContactorNameIsNotNull() {
            addCriterion("trd_contactor_name is not null");
            return (Criteria) this;
        }

        public Criteria andTrdContactorNameEqualTo(String value) {
            addCriterion("trd_contactor_name =", value, "trdContactorName");
            return (Criteria) this;
        }

        public Criteria andTrdContactorNameNotEqualTo(String value) {
            addCriterion("trd_contactor_name <>", value, "trdContactorName");
            return (Criteria) this;
        }

        public Criteria andTrdContactorNameGreaterThan(String value) {
            addCriterion("trd_contactor_name >", value, "trdContactorName");
            return (Criteria) this;
        }

        public Criteria andTrdContactorNameGreaterThanOrEqualTo(String value) {
            addCriterion("trd_contactor_name >=", value, "trdContactorName");
            return (Criteria) this;
        }

        public Criteria andTrdContactorNameLessThan(String value) {
            addCriterion("trd_contactor_name <", value, "trdContactorName");
            return (Criteria) this;
        }

        public Criteria andTrdContactorNameLessThanOrEqualTo(String value) {
            addCriterion("trd_contactor_name <=", value, "trdContactorName");
            return (Criteria) this;
        }

        public Criteria andTrdContactorNameLike(String value) {
            addCriterion("trd_contactor_name like", value, "trdContactorName");
            return (Criteria) this;
        }

        public Criteria andTrdContactorNameNotLike(String value) {
            addCriterion("trd_contactor_name not like", value, "trdContactorName");
            return (Criteria) this;
        }

        public Criteria andTrdContactorNameIn(List<String> values) {
            addCriterion("trd_contactor_name in", values, "trdContactorName");
            return (Criteria) this;
        }

        public Criteria andTrdContactorNameNotIn(List<String> values) {
            addCriterion("trd_contactor_name not in", values, "trdContactorName");
            return (Criteria) this;
        }

        public Criteria andTrdContactorNameBetween(String value1, String value2) {
            addCriterion("trd_contactor_name between", value1, value2, "trdContactorName");
            return (Criteria) this;
        }

        public Criteria andTrdContactorNameNotBetween(String value1, String value2) {
            addCriterion("trd_contactor_name not between", value1, value2, "trdContactorName");
            return (Criteria) this;
        }

        public Criteria andTrdContactorAddrIsNull() {
            addCriterion("trd_contactor_addr is null");
            return (Criteria) this;
        }

        public Criteria andTrdContactorAddrIsNotNull() {
            addCriterion("trd_contactor_addr is not null");
            return (Criteria) this;
        }

        public Criteria andTrdContactorAddrEqualTo(String value) {
            addCriterion("trd_contactor_addr =", value, "trdContactorAddr");
            return (Criteria) this;
        }

        public Criteria andTrdContactorAddrNotEqualTo(String value) {
            addCriterion("trd_contactor_addr <>", value, "trdContactorAddr");
            return (Criteria) this;
        }

        public Criteria andTrdContactorAddrGreaterThan(String value) {
            addCriterion("trd_contactor_addr >", value, "trdContactorAddr");
            return (Criteria) this;
        }

        public Criteria andTrdContactorAddrGreaterThanOrEqualTo(String value) {
            addCriterion("trd_contactor_addr >=", value, "trdContactorAddr");
            return (Criteria) this;
        }

        public Criteria andTrdContactorAddrLessThan(String value) {
            addCriterion("trd_contactor_addr <", value, "trdContactorAddr");
            return (Criteria) this;
        }

        public Criteria andTrdContactorAddrLessThanOrEqualTo(String value) {
            addCriterion("trd_contactor_addr <=", value, "trdContactorAddr");
            return (Criteria) this;
        }

        public Criteria andTrdContactorAddrLike(String value) {
            addCriterion("trd_contactor_addr like", value, "trdContactorAddr");
            return (Criteria) this;
        }

        public Criteria andTrdContactorAddrNotLike(String value) {
            addCriterion("trd_contactor_addr not like", value, "trdContactorAddr");
            return (Criteria) this;
        }

        public Criteria andTrdContactorAddrIn(List<String> values) {
            addCriterion("trd_contactor_addr in", values, "trdContactorAddr");
            return (Criteria) this;
        }

        public Criteria andTrdContactorAddrNotIn(List<String> values) {
            addCriterion("trd_contactor_addr not in", values, "trdContactorAddr");
            return (Criteria) this;
        }

        public Criteria andTrdContactorAddrBetween(String value1, String value2) {
            addCriterion("trd_contactor_addr between", value1, value2, "trdContactorAddr");
            return (Criteria) this;
        }

        public Criteria andTrdContactorAddrNotBetween(String value1, String value2) {
            addCriterion("trd_contactor_addr not between", value1, value2, "trdContactorAddr");
            return (Criteria) this;
        }

        public Criteria andTrdContractorPhoneIsNull() {
            addCriterion("trd_contractor_phone is null");
            return (Criteria) this;
        }

        public Criteria andTrdContractorPhoneIsNotNull() {
            addCriterion("trd_contractor_phone is not null");
            return (Criteria) this;
        }

        public Criteria andTrdContractorPhoneEqualTo(String value) {
            addCriterion("trd_contractor_phone =", value, "trdContractorPhone");
            return (Criteria) this;
        }

        public Criteria andTrdContractorPhoneNotEqualTo(String value) {
            addCriterion("trd_contractor_phone <>", value, "trdContractorPhone");
            return (Criteria) this;
        }

        public Criteria andTrdContractorPhoneGreaterThan(String value) {
            addCriterion("trd_contractor_phone >", value, "trdContractorPhone");
            return (Criteria) this;
        }

        public Criteria andTrdContractorPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("trd_contractor_phone >=", value, "trdContractorPhone");
            return (Criteria) this;
        }

        public Criteria andTrdContractorPhoneLessThan(String value) {
            addCriterion("trd_contractor_phone <", value, "trdContractorPhone");
            return (Criteria) this;
        }

        public Criteria andTrdContractorPhoneLessThanOrEqualTo(String value) {
            addCriterion("trd_contractor_phone <=", value, "trdContractorPhone");
            return (Criteria) this;
        }

        public Criteria andTrdContractorPhoneLike(String value) {
            addCriterion("trd_contractor_phone like", value, "trdContractorPhone");
            return (Criteria) this;
        }

        public Criteria andTrdContractorPhoneNotLike(String value) {
            addCriterion("trd_contractor_phone not like", value, "trdContractorPhone");
            return (Criteria) this;
        }

        public Criteria andTrdContractorPhoneIn(List<String> values) {
            addCriterion("trd_contractor_phone in", values, "trdContractorPhone");
            return (Criteria) this;
        }

        public Criteria andTrdContractorPhoneNotIn(List<String> values) {
            addCriterion("trd_contractor_phone not in", values, "trdContractorPhone");
            return (Criteria) this;
        }

        public Criteria andTrdContractorPhoneBetween(String value1, String value2) {
            addCriterion("trd_contractor_phone between", value1, value2, "trdContractorPhone");
            return (Criteria) this;
        }

        public Criteria andTrdContractorPhoneNotBetween(String value1, String value2) {
            addCriterion("trd_contractor_phone not between", value1, value2, "trdContractorPhone");
            return (Criteria) this;
        }

        public Criteria andTrdContactorAddressIsNull() {
            addCriterion("trd_contactor_address is null");
            return (Criteria) this;
        }

        public Criteria andTrdContactorAddressIsNotNull() {
            addCriterion("trd_contactor_address is not null");
            return (Criteria) this;
        }

        public Criteria andTrdContactorAddressEqualTo(String value) {
            addCriterion("trd_contactor_address =", value, "trdContactorAddress");
            return (Criteria) this;
        }

        public Criteria andTrdContactorAddressNotEqualTo(String value) {
            addCriterion("trd_contactor_address <>", value, "trdContactorAddress");
            return (Criteria) this;
        }

        public Criteria andTrdContactorAddressGreaterThan(String value) {
            addCriterion("trd_contactor_address >", value, "trdContactorAddress");
            return (Criteria) this;
        }

        public Criteria andTrdContactorAddressGreaterThanOrEqualTo(String value) {
            addCriterion("trd_contactor_address >=", value, "trdContactorAddress");
            return (Criteria) this;
        }

        public Criteria andTrdContactorAddressLessThan(String value) {
            addCriterion("trd_contactor_address <", value, "trdContactorAddress");
            return (Criteria) this;
        }

        public Criteria andTrdContactorAddressLessThanOrEqualTo(String value) {
            addCriterion("trd_contactor_address <=", value, "trdContactorAddress");
            return (Criteria) this;
        }

        public Criteria andTrdContactorAddressLike(String value) {
            addCriterion("trd_contactor_address like", value, "trdContactorAddress");
            return (Criteria) this;
        }

        public Criteria andTrdContactorAddressNotLike(String value) {
            addCriterion("trd_contactor_address not like", value, "trdContactorAddress");
            return (Criteria) this;
        }

        public Criteria andTrdContactorAddressIn(List<String> values) {
            addCriterion("trd_contactor_address in", values, "trdContactorAddress");
            return (Criteria) this;
        }

        public Criteria andTrdContactorAddressNotIn(List<String> values) {
            addCriterion("trd_contactor_address not in", values, "trdContactorAddress");
            return (Criteria) this;
        }

        public Criteria andTrdContactorAddressBetween(String value1, String value2) {
            addCriterion("trd_contactor_address between", value1, value2, "trdContactorAddress");
            return (Criteria) this;
        }

        public Criteria andTrdContactorAddressNotBetween(String value1, String value2) {
            addCriterion("trd_contactor_address not between", value1, value2, "trdContactorAddress");
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