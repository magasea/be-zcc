package com.wensheng.zcc.amc.module.dao.helper;

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
    SOLD(4, "已售出");

    private int status;
    private String name;
    EditStatusEnum(int status, String name){
        this.status = status;
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }


}
