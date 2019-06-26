package com.wensheng.zcc.wechat.module.dao.mysql.auto.entity;

import java.util.ArrayList;
import java.util.List;

public class WechatMsgCkitemExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public WechatMsgCkitemExample() {
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

        public Criteria andMsgIdIsNull() {
            addCriterion("msg_id is null");
            return (Criteria) this;
        }

        public Criteria andMsgIdIsNotNull() {
            addCriterion("msg_id is not null");
            return (Criteria) this;
        }

        public Criteria andMsgIdEqualTo(Long value) {
            addCriterion("msg_id =", value, "msgId");
            return (Criteria) this;
        }

        public Criteria andMsgIdNotEqualTo(Long value) {
            addCriterion("msg_id <>", value, "msgId");
            return (Criteria) this;
        }

        public Criteria andMsgIdGreaterThan(Long value) {
            addCriterion("msg_id >", value, "msgId");
            return (Criteria) this;
        }

        public Criteria andMsgIdGreaterThanOrEqualTo(Long value) {
            addCriterion("msg_id >=", value, "msgId");
            return (Criteria) this;
        }

        public Criteria andMsgIdLessThan(Long value) {
            addCriterion("msg_id <", value, "msgId");
            return (Criteria) this;
        }

        public Criteria andMsgIdLessThanOrEqualTo(Long value) {
            addCriterion("msg_id <=", value, "msgId");
            return (Criteria) this;
        }

        public Criteria andMsgIdIn(List<Long> values) {
            addCriterion("msg_id in", values, "msgId");
            return (Criteria) this;
        }

        public Criteria andMsgIdNotIn(List<Long> values) {
            addCriterion("msg_id not in", values, "msgId");
            return (Criteria) this;
        }

        public Criteria andMsgIdBetween(Long value1, Long value2) {
            addCriterion("msg_id between", value1, value2, "msgId");
            return (Criteria) this;
        }

        public Criteria andMsgIdNotBetween(Long value1, Long value2) {
            addCriterion("msg_id not between", value1, value2, "msgId");
            return (Criteria) this;
        }

        public Criteria andArticleIdxIsNull() {
            addCriterion("article_idx is null");
            return (Criteria) this;
        }

        public Criteria andArticleIdxIsNotNull() {
            addCriterion("article_idx is not null");
            return (Criteria) this;
        }

        public Criteria andArticleIdxEqualTo(Long value) {
            addCriterion("article_idx =", value, "articleIdx");
            return (Criteria) this;
        }

        public Criteria andArticleIdxNotEqualTo(Long value) {
            addCriterion("article_idx <>", value, "articleIdx");
            return (Criteria) this;
        }

        public Criteria andArticleIdxGreaterThan(Long value) {
            addCriterion("article_idx >", value, "articleIdx");
            return (Criteria) this;
        }

        public Criteria andArticleIdxGreaterThanOrEqualTo(Long value) {
            addCriterion("article_idx >=", value, "articleIdx");
            return (Criteria) this;
        }

        public Criteria andArticleIdxLessThan(Long value) {
            addCriterion("article_idx <", value, "articleIdx");
            return (Criteria) this;
        }

        public Criteria andArticleIdxLessThanOrEqualTo(Long value) {
            addCriterion("article_idx <=", value, "articleIdx");
            return (Criteria) this;
        }

        public Criteria andArticleIdxIn(List<Long> values) {
            addCriterion("article_idx in", values, "articleIdx");
            return (Criteria) this;
        }

        public Criteria andArticleIdxNotIn(List<Long> values) {
            addCriterion("article_idx not in", values, "articleIdx");
            return (Criteria) this;
        }

        public Criteria andArticleIdxBetween(Long value1, Long value2) {
            addCriterion("article_idx between", value1, value2, "articleIdx");
            return (Criteria) this;
        }

        public Criteria andArticleIdxNotBetween(Long value1, Long value2) {
            addCriterion("article_idx not between", value1, value2, "articleIdx");
            return (Criteria) this;
        }

        public Criteria andUserDeclareStateIsNull() {
            addCriterion("user_declare_state is null");
            return (Criteria) this;
        }

        public Criteria andUserDeclareStateIsNotNull() {
            addCriterion("user_declare_state is not null");
            return (Criteria) this;
        }

        public Criteria andUserDeclareStateEqualTo(Integer value) {
            addCriterion("user_declare_state =", value, "userDeclareState");
            return (Criteria) this;
        }

        public Criteria andUserDeclareStateNotEqualTo(Integer value) {
            addCriterion("user_declare_state <>", value, "userDeclareState");
            return (Criteria) this;
        }

        public Criteria andUserDeclareStateGreaterThan(Integer value) {
            addCriterion("user_declare_state >", value, "userDeclareState");
            return (Criteria) this;
        }

        public Criteria andUserDeclareStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_declare_state >=", value, "userDeclareState");
            return (Criteria) this;
        }

        public Criteria andUserDeclareStateLessThan(Integer value) {
            addCriterion("user_declare_state <", value, "userDeclareState");
            return (Criteria) this;
        }

        public Criteria andUserDeclareStateLessThanOrEqualTo(Integer value) {
            addCriterion("user_declare_state <=", value, "userDeclareState");
            return (Criteria) this;
        }

        public Criteria andUserDeclareStateIn(List<Integer> values) {
            addCriterion("user_declare_state in", values, "userDeclareState");
            return (Criteria) this;
        }

        public Criteria andUserDeclareStateNotIn(List<Integer> values) {
            addCriterion("user_declare_state not in", values, "userDeclareState");
            return (Criteria) this;
        }

        public Criteria andUserDeclareStateBetween(Integer value1, Integer value2) {
            addCriterion("user_declare_state between", value1, value2, "userDeclareState");
            return (Criteria) this;
        }

        public Criteria andUserDeclareStateNotBetween(Integer value1, Integer value2) {
            addCriterion("user_declare_state not between", value1, value2, "userDeclareState");
            return (Criteria) this;
        }

        public Criteria andAuditStateIsNull() {
            addCriterion("audit_state is null");
            return (Criteria) this;
        }

        public Criteria andAuditStateIsNotNull() {
            addCriterion("audit_state is not null");
            return (Criteria) this;
        }

        public Criteria andAuditStateEqualTo(Integer value) {
            addCriterion("audit_state =", value, "auditState");
            return (Criteria) this;
        }

        public Criteria andAuditStateNotEqualTo(Integer value) {
            addCriterion("audit_state <>", value, "auditState");
            return (Criteria) this;
        }

        public Criteria andAuditStateGreaterThan(Integer value) {
            addCriterion("audit_state >", value, "auditState");
            return (Criteria) this;
        }

        public Criteria andAuditStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("audit_state >=", value, "auditState");
            return (Criteria) this;
        }

        public Criteria andAuditStateLessThan(Integer value) {
            addCriterion("audit_state <", value, "auditState");
            return (Criteria) this;
        }

        public Criteria andAuditStateLessThanOrEqualTo(Integer value) {
            addCriterion("audit_state <=", value, "auditState");
            return (Criteria) this;
        }

        public Criteria andAuditStateIn(List<Integer> values) {
            addCriterion("audit_state in", values, "auditState");
            return (Criteria) this;
        }

        public Criteria andAuditStateNotIn(List<Integer> values) {
            addCriterion("audit_state not in", values, "auditState");
            return (Criteria) this;
        }

        public Criteria andAuditStateBetween(Integer value1, Integer value2) {
            addCriterion("audit_state between", value1, value2, "auditState");
            return (Criteria) this;
        }

        public Criteria andAuditStateNotBetween(Integer value1, Integer value2) {
            addCriterion("audit_state not between", value1, value2, "auditState");
            return (Criteria) this;
        }

        public Criteria andOriginalArticleUrlIsNull() {
            addCriterion("original_article_url is null");
            return (Criteria) this;
        }

        public Criteria andOriginalArticleUrlIsNotNull() {
            addCriterion("original_article_url is not null");
            return (Criteria) this;
        }

        public Criteria andOriginalArticleUrlEqualTo(String value) {
            addCriterion("original_article_url =", value, "originalArticleUrl");
            return (Criteria) this;
        }

        public Criteria andOriginalArticleUrlNotEqualTo(String value) {
            addCriterion("original_article_url <>", value, "originalArticleUrl");
            return (Criteria) this;
        }

        public Criteria andOriginalArticleUrlGreaterThan(String value) {
            addCriterion("original_article_url >", value, "originalArticleUrl");
            return (Criteria) this;
        }

        public Criteria andOriginalArticleUrlGreaterThanOrEqualTo(String value) {
            addCriterion("original_article_url >=", value, "originalArticleUrl");
            return (Criteria) this;
        }

        public Criteria andOriginalArticleUrlLessThan(String value) {
            addCriterion("original_article_url <", value, "originalArticleUrl");
            return (Criteria) this;
        }

        public Criteria andOriginalArticleUrlLessThanOrEqualTo(String value) {
            addCriterion("original_article_url <=", value, "originalArticleUrl");
            return (Criteria) this;
        }

        public Criteria andOriginalArticleUrlLike(String value) {
            addCriterion("original_article_url like", value, "originalArticleUrl");
            return (Criteria) this;
        }

        public Criteria andOriginalArticleUrlNotLike(String value) {
            addCriterion("original_article_url not like", value, "originalArticleUrl");
            return (Criteria) this;
        }

        public Criteria andOriginalArticleUrlIn(List<String> values) {
            addCriterion("original_article_url in", values, "originalArticleUrl");
            return (Criteria) this;
        }

        public Criteria andOriginalArticleUrlNotIn(List<String> values) {
            addCriterion("original_article_url not in", values, "originalArticleUrl");
            return (Criteria) this;
        }

        public Criteria andOriginalArticleUrlBetween(String value1, String value2) {
            addCriterion("original_article_url between", value1, value2, "originalArticleUrl");
            return (Criteria) this;
        }

        public Criteria andOriginalArticleUrlNotBetween(String value1, String value2) {
            addCriterion("original_article_url not between", value1, value2, "originalArticleUrl");
            return (Criteria) this;
        }

        public Criteria andOriginalArticleTypeIsNull() {
            addCriterion("original_article_type is null");
            return (Criteria) this;
        }

        public Criteria andOriginalArticleTypeIsNotNull() {
            addCriterion("original_article_type is not null");
            return (Criteria) this;
        }

        public Criteria andOriginalArticleTypeEqualTo(Integer value) {
            addCriterion("original_article_type =", value, "originalArticleType");
            return (Criteria) this;
        }

        public Criteria andOriginalArticleTypeNotEqualTo(Integer value) {
            addCriterion("original_article_type <>", value, "originalArticleType");
            return (Criteria) this;
        }

        public Criteria andOriginalArticleTypeGreaterThan(Integer value) {
            addCriterion("original_article_type >", value, "originalArticleType");
            return (Criteria) this;
        }

        public Criteria andOriginalArticleTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("original_article_type >=", value, "originalArticleType");
            return (Criteria) this;
        }

        public Criteria andOriginalArticleTypeLessThan(Integer value) {
            addCriterion("original_article_type <", value, "originalArticleType");
            return (Criteria) this;
        }

        public Criteria andOriginalArticleTypeLessThanOrEqualTo(Integer value) {
            addCriterion("original_article_type <=", value, "originalArticleType");
            return (Criteria) this;
        }

        public Criteria andOriginalArticleTypeIn(List<Integer> values) {
            addCriterion("original_article_type in", values, "originalArticleType");
            return (Criteria) this;
        }

        public Criteria andOriginalArticleTypeNotIn(List<Integer> values) {
            addCriterion("original_article_type not in", values, "originalArticleType");
            return (Criteria) this;
        }

        public Criteria andOriginalArticleTypeBetween(Integer value1, Integer value2) {
            addCriterion("original_article_type between", value1, value2, "originalArticleType");
            return (Criteria) this;
        }

        public Criteria andOriginalArticleTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("original_article_type not between", value1, value2, "originalArticleType");
            return (Criteria) this;
        }

        public Criteria andCanReprintIsNull() {
            addCriterion("can_reprint is null");
            return (Criteria) this;
        }

        public Criteria andCanReprintIsNotNull() {
            addCriterion("can_reprint is not null");
            return (Criteria) this;
        }

        public Criteria andCanReprintEqualTo(Integer value) {
            addCriterion("can_reprint =", value, "canReprint");
            return (Criteria) this;
        }

        public Criteria andCanReprintNotEqualTo(Integer value) {
            addCriterion("can_reprint <>", value, "canReprint");
            return (Criteria) this;
        }

        public Criteria andCanReprintGreaterThan(Integer value) {
            addCriterion("can_reprint >", value, "canReprint");
            return (Criteria) this;
        }

        public Criteria andCanReprintGreaterThanOrEqualTo(Integer value) {
            addCriterion("can_reprint >=", value, "canReprint");
            return (Criteria) this;
        }

        public Criteria andCanReprintLessThan(Integer value) {
            addCriterion("can_reprint <", value, "canReprint");
            return (Criteria) this;
        }

        public Criteria andCanReprintLessThanOrEqualTo(Integer value) {
            addCriterion("can_reprint <=", value, "canReprint");
            return (Criteria) this;
        }

        public Criteria andCanReprintIn(List<Integer> values) {
            addCriterion("can_reprint in", values, "canReprint");
            return (Criteria) this;
        }

        public Criteria andCanReprintNotIn(List<Integer> values) {
            addCriterion("can_reprint not in", values, "canReprint");
            return (Criteria) this;
        }

        public Criteria andCanReprintBetween(Integer value1, Integer value2) {
            addCriterion("can_reprint between", value1, value2, "canReprint");
            return (Criteria) this;
        }

        public Criteria andCanReprintNotBetween(Integer value1, Integer value2) {
            addCriterion("can_reprint not between", value1, value2, "canReprint");
            return (Criteria) this;
        }

        public Criteria andNeedReplaceContentIsNull() {
            addCriterion("need_replace_content is null");
            return (Criteria) this;
        }

        public Criteria andNeedReplaceContentIsNotNull() {
            addCriterion("need_replace_content is not null");
            return (Criteria) this;
        }

        public Criteria andNeedReplaceContentEqualTo(Integer value) {
            addCriterion("need_replace_content =", value, "needReplaceContent");
            return (Criteria) this;
        }

        public Criteria andNeedReplaceContentNotEqualTo(Integer value) {
            addCriterion("need_replace_content <>", value, "needReplaceContent");
            return (Criteria) this;
        }

        public Criteria andNeedReplaceContentGreaterThan(Integer value) {
            addCriterion("need_replace_content >", value, "needReplaceContent");
            return (Criteria) this;
        }

        public Criteria andNeedReplaceContentGreaterThanOrEqualTo(Integer value) {
            addCriterion("need_replace_content >=", value, "needReplaceContent");
            return (Criteria) this;
        }

        public Criteria andNeedReplaceContentLessThan(Integer value) {
            addCriterion("need_replace_content <", value, "needReplaceContent");
            return (Criteria) this;
        }

        public Criteria andNeedReplaceContentLessThanOrEqualTo(Integer value) {
            addCriterion("need_replace_content <=", value, "needReplaceContent");
            return (Criteria) this;
        }

        public Criteria andNeedReplaceContentIn(List<Integer> values) {
            addCriterion("need_replace_content in", values, "needReplaceContent");
            return (Criteria) this;
        }

        public Criteria andNeedReplaceContentNotIn(List<Integer> values) {
            addCriterion("need_replace_content not in", values, "needReplaceContent");
            return (Criteria) this;
        }

        public Criteria andNeedReplaceContentBetween(Integer value1, Integer value2) {
            addCriterion("need_replace_content between", value1, value2, "needReplaceContent");
            return (Criteria) this;
        }

        public Criteria andNeedReplaceContentNotBetween(Integer value1, Integer value2) {
            addCriterion("need_replace_content not between", value1, value2, "needReplaceContent");
            return (Criteria) this;
        }

        public Criteria andNeedShowReprintSourceIsNull() {
            addCriterion("need_show_reprint_source is null");
            return (Criteria) this;
        }

        public Criteria andNeedShowReprintSourceIsNotNull() {
            addCriterion("need_show_reprint_source is not null");
            return (Criteria) this;
        }

        public Criteria andNeedShowReprintSourceEqualTo(Integer value) {
            addCriterion("need_show_reprint_source =", value, "needShowReprintSource");
            return (Criteria) this;
        }

        public Criteria andNeedShowReprintSourceNotEqualTo(Integer value) {
            addCriterion("need_show_reprint_source <>", value, "needShowReprintSource");
            return (Criteria) this;
        }

        public Criteria andNeedShowReprintSourceGreaterThan(Integer value) {
            addCriterion("need_show_reprint_source >", value, "needShowReprintSource");
            return (Criteria) this;
        }

        public Criteria andNeedShowReprintSourceGreaterThanOrEqualTo(Integer value) {
            addCriterion("need_show_reprint_source >=", value, "needShowReprintSource");
            return (Criteria) this;
        }

        public Criteria andNeedShowReprintSourceLessThan(Integer value) {
            addCriterion("need_show_reprint_source <", value, "needShowReprintSource");
            return (Criteria) this;
        }

        public Criteria andNeedShowReprintSourceLessThanOrEqualTo(Integer value) {
            addCriterion("need_show_reprint_source <=", value, "needShowReprintSource");
            return (Criteria) this;
        }

        public Criteria andNeedShowReprintSourceIn(List<Integer> values) {
            addCriterion("need_show_reprint_source in", values, "needShowReprintSource");
            return (Criteria) this;
        }

        public Criteria andNeedShowReprintSourceNotIn(List<Integer> values) {
            addCriterion("need_show_reprint_source not in", values, "needShowReprintSource");
            return (Criteria) this;
        }

        public Criteria andNeedShowReprintSourceBetween(Integer value1, Integer value2) {
            addCriterion("need_show_reprint_source between", value1, value2, "needShowReprintSource");
            return (Criteria) this;
        }

        public Criteria andNeedShowReprintSourceNotBetween(Integer value1, Integer value2) {
            addCriterion("need_show_reprint_source not between", value1, value2, "needShowReprintSource");
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