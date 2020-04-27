package com.wensheng.zcc.amc.module.dao.helper;

import com.wensheng.zcc.common.utils.base.EnumUtils;
import java.util.function.Function;

/**
 * @author chenwei on 12/7/18
 * @project zcc-backend
 */
public enum FloorPublishStateEnum {
    NOTCLEAR(-1, "不确定"),
    PUBLISHED(1, "上架"),
    UNPUBLISHED(2, "下架"),


    ;

    private int status;
    private String name;
    FloorPublishStateEnum(int status, String name){
        this.status = status;
        this.name = name;
    }
    private static final Function<String, FloorPublishStateEnum> func =
        EnumUtils.lookupMap(FloorPublishStateEnum.class, e -> e.getName());
    public static FloorPublishStateEnum lookupByDisplayNameUtil(String name) {
        return func.apply(name);
    }

    private static final Function<Integer, FloorPublishStateEnum> funcStatus =
        EnumUtils.lookupMap(FloorPublishStateEnum.class, e -> e.getStatus());
    public static FloorPublishStateEnum lookupByDisplayStatusUtil(Integer status) {
        return funcStatus.apply(status);
    }

    public int getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }




}
