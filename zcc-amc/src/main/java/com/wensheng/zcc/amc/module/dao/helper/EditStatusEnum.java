package com.wensheng.zcc.amc.module.dao.helper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wensheng.zcc.amc.module.dao.helper.base.EnumUtils;
import java.util.function.Function;

/**
 * @author chenwei on 12/7/18
 * @project zcc-backend
 */
public enum EditStatusEnum {
    NOTCLEAR(-1, "不确定"),
    PUBLISHED(1, "发布"),
    NOTPUBLISHED(2, "未发布"),
    DELETED(0, "已删除"),
    ABORTED(3, "已放弃"),
    SOLD(4, "已售出"),
    CHECK_WAIT(5, "待审核"),
    DRAFT(6, "草稿"),
    OFF_SHELF(7, "已下架"),
    CHECK_FAILED(8, "审核未通过"),
    INIT(9, "初始化"),
    PUBLISHED1(1, "已发布");

    private int status;
    private String name;
    EditStatusEnum(int status, String name){
        this.status = status;
        this.name = name;
    }
    private static final Function<String, EditStatusEnum> func =
        EnumUtils.lookupMap(EditStatusEnum.class, e -> e.getName());
    public static EditStatusEnum lookupByDisplayNameUtil(String name) {
        return func.apply(name);
    }

    public int getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }




}
