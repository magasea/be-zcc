package com.wensheng.zcc.amc.module.dao.helper;

import com.wensheng.zcc.common.utils.base.EnumUtils;
import java.util.function.Function;

/**
 * @author chenwei on 12/10/18
 * @project zcc-backend
 */
//未查封 首封 轮候第一顺位 轮候第二顺位 轮候其他顺位
public enum SealStateEnum {
    NOTCLEAR(-1, "不确定"),
    NOTSEALED(1, "未查封"),
    FIRST_LIEN(2, "首封"),
    SECOND_LIEN(3, "轮候第一顺位"),
    THIRD_LIEN(4, "轮候第二顺位"),
    OTHER_LIEN(5, "轮候"),

    ;


    private int status;
    private String name;

    private static final Function<String, SealStateEnum> func =
        EnumUtils.lookupMap(SealStateEnum.class, e -> e.getName());
    public static SealStateEnum lookupByDisplayNameUtil(String name) {
        return func.apply(name);
    }
    SealStateEnum(int status, String name){
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
