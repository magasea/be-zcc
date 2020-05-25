package com.wensheng.zcc.amc.module.dao.mysql.auto.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AmcSaleFloorExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AmcSaleFloorExample() {
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

        public Criteria andFloorIsNull() {
            addCriterion("floor is null");
            return (Criteria) this;
        }

        public Criteria andFloorIsNotNull() {
            addCriterion("floor is not null");
            return (Criteria) this;
        }

        public Criteria andFloorEqualTo(Integer value) {
            addCriterion("floor =", value, "floor");
            return (Criteria) this;
        }

        public Criteria andFloorNotEqualTo(Integer value) {
            addCriterion("floor <>", value, "floor");
            return (Criteria) this;
        }

        public Criteria andFloorGreaterThan(Integer value) {
            addCriterion("floor >", value, "floor");
            return (Criteria) this;
        }

        public Criteria andFloorGreaterThanOrEqualTo(Integer value) {
            addCriterion("floor >=", value, "floor");
            return (Criteria) this;
        }

        public Criteria andFloorLessThan(Integer value) {
            addCriterion("floor <", value, "floor");
            return (Criteria) this;
        }

        public Criteria andFloorLessThanOrEqualTo(Integer value) {
            addCriterion("floor <=", value, "floor");
            return (Criteria) this;
        }

        public Criteria andFloorIn(List<Integer> values) {
            addCriterion("floor in", values, "floor");
            return (Criteria) this;
        }

        public Criteria andFloorNotIn(List<Integer> values) {
            addCriterion("floor not in", values, "floor");
            return (Criteria) this;
        }

        public Criteria andFloorBetween(Integer value1, Integer value2) {
            addCriterion("floor between", value1, value2, "floor");
            return (Criteria) this;
        }

        public Criteria andFloorNotBetween(Integer value1, Integer value2) {
            addCriterion("floor not between", value1, value2, "floor");
            return (Criteria) this;
        }

        public Criteria andFloorTypeIsNull() {
            addCriterion("floor_type is null");
            return (Criteria) this;
        }

        public Criteria andFloorTypeIsNotNull() {
            addCriterion("floor_type is not null");
            return (Criteria) this;
        }

        public Criteria andFloorTypeEqualTo(Integer value) {
            addCriterion("floor_type =", value, "floorType");
            return (Criteria) this;
        }

        public Criteria andFloorTypeNotEqualTo(Integer value) {
            addCriterion("floor_type <>", value, "floorType");
            return (Criteria) this;
        }

        public Criteria andFloorTypeGreaterThan(Integer value) {
            addCriterion("floor_type >", value, "floorType");
            return (Criteria) this;
        }

        public Criteria andFloorTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("floor_type >=", value, "floorType");
            return (Criteria) this;
        }

        public Criteria andFloorTypeLessThan(Integer value) {
            addCriterion("floor_type <", value, "floorType");
            return (Criteria) this;
        }

        public Criteria andFloorTypeLessThanOrEqualTo(Integer value) {
            addCriterion("floor_type <=", value, "floorType");
            return (Criteria) this;
        }

        public Criteria andFloorTypeIn(List<Integer> values) {
            addCriterion("floor_type in", values, "floorType");
            return (Criteria) this;
        }

        public Criteria andFloorTypeNotIn(List<Integer> values) {
            addCriterion("floor_type not in", values, "floorType");
            return (Criteria) this;
        }

        public Criteria andFloorTypeBetween(Integer value1, Integer value2) {
            addCriterion("floor_type between", value1, value2, "floorType");
            return (Criteria) this;
        }

        public Criteria andFloorTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("floor_type not between", value1, value2, "floorType");
            return (Criteria) this;
        }

        public Criteria andFloorSeqIsNull() {
            addCriterion("floor_seq is null");
            return (Criteria) this;
        }

        public Criteria andFloorSeqIsNotNull() {
            addCriterion("floor_seq is not null");
            return (Criteria) this;
        }

        public Criteria andFloorSeqEqualTo(Integer value) {
            addCriterion("floor_seq =", value, "floorSeq");
            return (Criteria) this;
        }

        public Criteria andFloorSeqNotEqualTo(Integer value) {
            addCriterion("floor_seq <>", value, "floorSeq");
            return (Criteria) this;
        }

        public Criteria andFloorSeqGreaterThan(Integer value) {
            addCriterion("floor_seq >", value, "floorSeq");
            return (Criteria) this;
        }

        public Criteria andFloorSeqGreaterThanOrEqualTo(Integer value) {
            addCriterion("floor_seq >=", value, "floorSeq");
            return (Criteria) this;
        }

        public Criteria andFloorSeqLessThan(Integer value) {
            addCriterion("floor_seq <", value, "floorSeq");
            return (Criteria) this;
        }

        public Criteria andFloorSeqLessThanOrEqualTo(Integer value) {
            addCriterion("floor_seq <=", value, "floorSeq");
            return (Criteria) this;
        }

        public Criteria andFloorSeqIn(List<Integer> values) {
            addCriterion("floor_seq in", values, "floorSeq");
            return (Criteria) this;
        }

        public Criteria andFloorSeqNotIn(List<Integer> values) {
            addCriterion("floor_seq not in", values, "floorSeq");
            return (Criteria) this;
        }

        public Criteria andFloorSeqBetween(Integer value1, Integer value2) {
            addCriterion("floor_seq between", value1, value2, "floorSeq");
            return (Criteria) this;
        }

        public Criteria andFloorSeqNotBetween(Integer value1, Integer value2) {
            addCriterion("floor_seq not between", value1, value2, "floorSeq");
            return (Criteria) this;
        }

        public Criteria andCssIsNull() {
            addCriterion("css is null");
            return (Criteria) this;
        }

        public Criteria andCssIsNotNull() {
            addCriterion("css is not null");
            return (Criteria) this;
        }

        public Criteria andCssEqualTo(Integer value) {
            addCriterion("css =", value, "css");
            return (Criteria) this;
        }

        public Criteria andCssNotEqualTo(Integer value) {
            addCriterion("css <>", value, "css");
            return (Criteria) this;
        }

        public Criteria andCssGreaterThan(Integer value) {
            addCriterion("css >", value, "css");
            return (Criteria) this;
        }

        public Criteria andCssGreaterThanOrEqualTo(Integer value) {
            addCriterion("css >=", value, "css");
            return (Criteria) this;
        }

        public Criteria andCssLessThan(Integer value) {
            addCriterion("css <", value, "css");
            return (Criteria) this;
        }

        public Criteria andCssLessThanOrEqualTo(Integer value) {
            addCriterion("css <=", value, "css");
            return (Criteria) this;
        }

        public Criteria andCssIn(List<Integer> values) {
            addCriterion("css in", values, "css");
            return (Criteria) this;
        }

        public Criteria andCssNotIn(List<Integer> values) {
            addCriterion("css not in", values, "css");
            return (Criteria) this;
        }

        public Criteria andCssBetween(Integer value1, Integer value2) {
            addCriterion("css between", value1, value2, "css");
            return (Criteria) this;
        }

        public Criteria andCssNotBetween(Integer value1, Integer value2) {
            addCriterion("css not between", value1, value2, "css");
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

        public Criteria andSloganIsNull() {
            addCriterion("slogan is null");
            return (Criteria) this;
        }

        public Criteria andSloganIsNotNull() {
            addCriterion("slogan is not null");
            return (Criteria) this;
        }

        public Criteria andSloganEqualTo(String value) {
            addCriterion("slogan =", value, "slogan");
            return (Criteria) this;
        }

        public Criteria andSloganNotEqualTo(String value) {
            addCriterion("slogan <>", value, "slogan");
            return (Criteria) this;
        }

        public Criteria andSloganGreaterThan(String value) {
            addCriterion("slogan >", value, "slogan");
            return (Criteria) this;
        }

        public Criteria andSloganGreaterThanOrEqualTo(String value) {
            addCriterion("slogan >=", value, "slogan");
            return (Criteria) this;
        }

        public Criteria andSloganLessThan(String value) {
            addCriterion("slogan <", value, "slogan");
            return (Criteria) this;
        }

        public Criteria andSloganLessThanOrEqualTo(String value) {
            addCriterion("slogan <=", value, "slogan");
            return (Criteria) this;
        }

        public Criteria andSloganLike(String value) {
            addCriterion("slogan like", value, "slogan");
            return (Criteria) this;
        }

        public Criteria andSloganNotLike(String value) {
            addCriterion("slogan not like", value, "slogan");
            return (Criteria) this;
        }

        public Criteria andSloganIn(List<String> values) {
            addCriterion("slogan in", values, "slogan");
            return (Criteria) this;
        }

        public Criteria andSloganNotIn(List<String> values) {
            addCriterion("slogan not in", values, "slogan");
            return (Criteria) this;
        }

        public Criteria andSloganBetween(String value1, String value2) {
            addCriterion("slogan between", value1, value2, "slogan");
            return (Criteria) this;
        }

        public Criteria andSloganNotBetween(String value1, String value2) {
            addCriterion("slogan not between", value1, value2, "slogan");
            return (Criteria) this;
        }

        public Criteria andPageImgUrlIsNull() {
            addCriterion("page_img_url is null");
            return (Criteria) this;
        }

        public Criteria andPageImgUrlIsNotNull() {
            addCriterion("page_img_url is not null");
            return (Criteria) this;
        }

        public Criteria andPageImgUrlEqualTo(String value) {
            addCriterion("page_img_url =", value, "pageImgUrl");
            return (Criteria) this;
        }

        public Criteria andPageImgUrlNotEqualTo(String value) {
            addCriterion("page_img_url <>", value, "pageImgUrl");
            return (Criteria) this;
        }

        public Criteria andPageImgUrlGreaterThan(String value) {
            addCriterion("page_img_url >", value, "pageImgUrl");
            return (Criteria) this;
        }

        public Criteria andPageImgUrlGreaterThanOrEqualTo(String value) {
            addCriterion("page_img_url >=", value, "pageImgUrl");
            return (Criteria) this;
        }

        public Criteria andPageImgUrlLessThan(String value) {
            addCriterion("page_img_url <", value, "pageImgUrl");
            return (Criteria) this;
        }

        public Criteria andPageImgUrlLessThanOrEqualTo(String value) {
            addCriterion("page_img_url <=", value, "pageImgUrl");
            return (Criteria) this;
        }

        public Criteria andPageImgUrlLike(String value) {
            addCriterion("page_img_url like", value, "pageImgUrl");
            return (Criteria) this;
        }

        public Criteria andPageImgUrlNotLike(String value) {
            addCriterion("page_img_url not like", value, "pageImgUrl");
            return (Criteria) this;
        }

        public Criteria andPageImgUrlIn(List<String> values) {
            addCriterion("page_img_url in", values, "pageImgUrl");
            return (Criteria) this;
        }

        public Criteria andPageImgUrlNotIn(List<String> values) {
            addCriterion("page_img_url not in", values, "pageImgUrl");
            return (Criteria) this;
        }

        public Criteria andPageImgUrlBetween(String value1, String value2) {
            addCriterion("page_img_url between", value1, value2, "pageImgUrl");
            return (Criteria) this;
        }

        public Criteria andPageImgUrlNotBetween(String value1, String value2) {
            addCriterion("page_img_url not between", value1, value2, "pageImgUrl");
            return (Criteria) this;
        }

        public Criteria andFloorStartTimeIsNull() {
            addCriterion("floor_start_time is null");
            return (Criteria) this;
        }

        public Criteria andFloorStartTimeIsNotNull() {
            addCriterion("floor_start_time is not null");
            return (Criteria) this;
        }

        public Criteria andFloorStartTimeEqualTo(Date value) {
            addCriterion("floor_start_time =", value, "floorStartTime");
            return (Criteria) this;
        }

        public Criteria andFloorStartTimeNotEqualTo(Date value) {
            addCriterion("floor_start_time <>", value, "floorStartTime");
            return (Criteria) this;
        }

        public Criteria andFloorStartTimeGreaterThan(Date value) {
            addCriterion("floor_start_time >", value, "floorStartTime");
            return (Criteria) this;
        }

        public Criteria andFloorStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("floor_start_time >=", value, "floorStartTime");
            return (Criteria) this;
        }

        public Criteria andFloorStartTimeLessThan(Date value) {
            addCriterion("floor_start_time <", value, "floorStartTime");
            return (Criteria) this;
        }

        public Criteria andFloorStartTimeLessThanOrEqualTo(Date value) {
            addCriterion("floor_start_time <=", value, "floorStartTime");
            return (Criteria) this;
        }

        public Criteria andFloorStartTimeIn(List<Date> values) {
            addCriterion("floor_start_time in", values, "floorStartTime");
            return (Criteria) this;
        }

        public Criteria andFloorStartTimeNotIn(List<Date> values) {
            addCriterion("floor_start_time not in", values, "floorStartTime");
            return (Criteria) this;
        }

        public Criteria andFloorStartTimeBetween(Date value1, Date value2) {
            addCriterion("floor_start_time between", value1, value2, "floorStartTime");
            return (Criteria) this;
        }

        public Criteria andFloorStartTimeNotBetween(Date value1, Date value2) {
            addCriterion("floor_start_time not between", value1, value2, "floorStartTime");
            return (Criteria) this;
        }

        public Criteria andFloorEndTimeIsNull() {
            addCriterion("floor_end_time is null");
            return (Criteria) this;
        }

        public Criteria andFloorEndTimeIsNotNull() {
            addCriterion("floor_end_time is not null");
            return (Criteria) this;
        }

        public Criteria andFloorEndTimeEqualTo(Date value) {
            addCriterion("floor_end_time =", value, "floorEndTime");
            return (Criteria) this;
        }

        public Criteria andFloorEndTimeNotEqualTo(Date value) {
            addCriterion("floor_end_time <>", value, "floorEndTime");
            return (Criteria) this;
        }

        public Criteria andFloorEndTimeGreaterThan(Date value) {
            addCriterion("floor_end_time >", value, "floorEndTime");
            return (Criteria) this;
        }

        public Criteria andFloorEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("floor_end_time >=", value, "floorEndTime");
            return (Criteria) this;
        }

        public Criteria andFloorEndTimeLessThan(Date value) {
            addCriterion("floor_end_time <", value, "floorEndTime");
            return (Criteria) this;
        }

        public Criteria andFloorEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("floor_end_time <=", value, "floorEndTime");
            return (Criteria) this;
        }

        public Criteria andFloorEndTimeIn(List<Date> values) {
            addCriterion("floor_end_time in", values, "floorEndTime");
            return (Criteria) this;
        }

        public Criteria andFloorEndTimeNotIn(List<Date> values) {
            addCriterion("floor_end_time not in", values, "floorEndTime");
            return (Criteria) this;
        }

        public Criteria andFloorEndTimeBetween(Date value1, Date value2) {
            addCriterion("floor_end_time between", value1, value2, "floorEndTime");
            return (Criteria) this;
        }

        public Criteria andFloorEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("floor_end_time not between", value1, value2, "floorEndTime");
            return (Criteria) this;
        }

        public Criteria andManualEndTimeIsNull() {
            addCriterion("manual_end_time is null");
            return (Criteria) this;
        }

        public Criteria andManualEndTimeIsNotNull() {
            addCriterion("manual_end_time is not null");
            return (Criteria) this;
        }

        public Criteria andManualEndTimeEqualTo(Date value) {
            addCriterion("manual_end_time =", value, "manualEndTime");
            return (Criteria) this;
        }

        public Criteria andManualEndTimeNotEqualTo(Date value) {
            addCriterion("manual_end_time <>", value, "manualEndTime");
            return (Criteria) this;
        }

        public Criteria andManualEndTimeGreaterThan(Date value) {
            addCriterion("manual_end_time >", value, "manualEndTime");
            return (Criteria) this;
        }

        public Criteria andManualEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("manual_end_time >=", value, "manualEndTime");
            return (Criteria) this;
        }

        public Criteria andManualEndTimeLessThan(Date value) {
            addCriterion("manual_end_time <", value, "manualEndTime");
            return (Criteria) this;
        }

        public Criteria andManualEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("manual_end_time <=", value, "manualEndTime");
            return (Criteria) this;
        }

        public Criteria andManualEndTimeIn(List<Date> values) {
            addCriterion("manual_end_time in", values, "manualEndTime");
            return (Criteria) this;
        }

        public Criteria andManualEndTimeNotIn(List<Date> values) {
            addCriterion("manual_end_time not in", values, "manualEndTime");
            return (Criteria) this;
        }

        public Criteria andManualEndTimeBetween(Date value1, Date value2) {
            addCriterion("manual_end_time between", value1, value2, "manualEndTime");
            return (Criteria) this;
        }

        public Criteria andManualEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("manual_end_time not between", value1, value2, "manualEndTime");
            return (Criteria) this;
        }

        public Criteria andFilterContentIsNull() {
            addCriterion("filter_content is null");
            return (Criteria) this;
        }

        public Criteria andFilterContentIsNotNull() {
            addCriterion("filter_content is not null");
            return (Criteria) this;
        }

        public Criteria andFilterContentEqualTo(String value) {
            addCriterion("filter_content =", value, "filterContent");
            return (Criteria) this;
        }

        public Criteria andFilterContentNotEqualTo(String value) {
            addCriterion("filter_content <>", value, "filterContent");
            return (Criteria) this;
        }

        public Criteria andFilterContentGreaterThan(String value) {
            addCriterion("filter_content >", value, "filterContent");
            return (Criteria) this;
        }

        public Criteria andFilterContentGreaterThanOrEqualTo(String value) {
            addCriterion("filter_content >=", value, "filterContent");
            return (Criteria) this;
        }

        public Criteria andFilterContentLessThan(String value) {
            addCriterion("filter_content <", value, "filterContent");
            return (Criteria) this;
        }

        public Criteria andFilterContentLessThanOrEqualTo(String value) {
            addCriterion("filter_content <=", value, "filterContent");
            return (Criteria) this;
        }

        public Criteria andFilterContentLike(String value) {
            addCriterion("filter_content like", value, "filterContent");
            return (Criteria) this;
        }

        public Criteria andFilterContentNotLike(String value) {
            addCriterion("filter_content not like", value, "filterContent");
            return (Criteria) this;
        }

        public Criteria andFilterContentIn(List<String> values) {
            addCriterion("filter_content in", values, "filterContent");
            return (Criteria) this;
        }

        public Criteria andFilterContentNotIn(List<String> values) {
            addCriterion("filter_content not in", values, "filterContent");
            return (Criteria) this;
        }

        public Criteria andFilterContentBetween(String value1, String value2) {
            addCriterion("filter_content between", value1, value2, "filterContent");
            return (Criteria) this;
        }

        public Criteria andFilterContentNotBetween(String value1, String value2) {
            addCriterion("filter_content not between", value1, value2, "filterContent");
            return (Criteria) this;
        }

        public Criteria andRecomItemsIsNull() {
            addCriterion("recom_items is null");
            return (Criteria) this;
        }

        public Criteria andRecomItemsIsNotNull() {
            addCriterion("recom_items is not null");
            return (Criteria) this;
        }

        public Criteria andRecomItemsEqualTo(String value) {
            addCriterion("recom_items =", value, "recomItems");
            return (Criteria) this;
        }

        public Criteria andRecomItemsNotEqualTo(String value) {
            addCriterion("recom_items <>", value, "recomItems");
            return (Criteria) this;
        }

        public Criteria andRecomItemsGreaterThan(String value) {
            addCriterion("recom_items >", value, "recomItems");
            return (Criteria) this;
        }

        public Criteria andRecomItemsGreaterThanOrEqualTo(String value) {
            addCriterion("recom_items >=", value, "recomItems");
            return (Criteria) this;
        }

        public Criteria andRecomItemsLessThan(String value) {
            addCriterion("recom_items <", value, "recomItems");
            return (Criteria) this;
        }

        public Criteria andRecomItemsLessThanOrEqualTo(String value) {
            addCriterion("recom_items <=", value, "recomItems");
            return (Criteria) this;
        }

        public Criteria andRecomItemsLike(String value) {
            addCriterion("recom_items like", value, "recomItems");
            return (Criteria) this;
        }

        public Criteria andRecomItemsNotLike(String value) {
            addCriterion("recom_items not like", value, "recomItems");
            return (Criteria) this;
        }

        public Criteria andRecomItemsIn(List<String> values) {
            addCriterion("recom_items in", values, "recomItems");
            return (Criteria) this;
        }

        public Criteria andRecomItemsNotIn(List<String> values) {
            addCriterion("recom_items not in", values, "recomItems");
            return (Criteria) this;
        }

        public Criteria andRecomItemsBetween(String value1, String value2) {
            addCriterion("recom_items between", value1, value2, "recomItems");
            return (Criteria) this;
        }

        public Criteria andRecomItemsNotBetween(String value1, String value2) {
            addCriterion("recom_items not between", value1, value2, "recomItems");
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