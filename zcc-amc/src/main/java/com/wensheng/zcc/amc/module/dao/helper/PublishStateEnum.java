package com.wensheng.zcc.amc.module.dao.helper;

import com.wensheng.zcc.common.utils.base.EnumUtils;
import java.util.function.Function;

/**
 * @author chenwei on 12/7/18
 * @project zcc-backend
 */
public enum PublishStateEnum {
    NOTCLEAR(-1, "不确定"),
    PUBLISHED(1, "发布"),
    DELETED(0, "已删除"),
    SOLD(4, "已售出"),
    DRAFT_CHECK_WAIT(5, "草稿待审核"),
    RECORD_CHECK_WAIT(12, "草案待审核"),
    DRAFT(6, "草稿"),
    RECORD(10,"草案"),
    SOLD_OFF_SHELF(7, "售出已下架"),
    UNSOLD_OFF_SHELF(11, "未售出已下架"),
    DRAFT_CHECK_FAILED(8, "草稿审核未通过"),
    RECORD_CHECK_FAILED(13, "草案审核未通过"),
    PUBLISH_HIDED(14, "发布隐藏"),


    ;

    private int status;
    private String name;
    PublishStateEnum(int status, String name){
        this.status = status;
        this.name = name;
    }
    private static final Function<String, PublishStateEnum> func =
        EnumUtils.lookupMap(PublishStateEnum.class, e -> e.getName());
    public static PublishStateEnum lookupByDisplayNameUtil(String name) {
        return func.apply(name);
    }

    private static final Function<Integer, PublishStateEnum> funcStatus =
        EnumUtils.lookupMap(PublishStateEnum.class, e -> e.getStatus());
    public static PublishStateEnum lookupByDisplayStatusUtil(Integer status) {
        return funcStatus.apply(status);
    }

    public int getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }




}
