package com.wensheng.zcc.amc.module.dao.helper;

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
