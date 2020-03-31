package com.wensheng.zcc.amc.module.dao.helper;

import com.wensheng.zcc.common.utils.base.EnumUtils;

import java.util.function.Function;

public enum LandUsageTypeEnum {
    RESIDENTIAL_LAND(1, "住宅用地"),
    BUSINESS_LAND(2, "商服用地"),
    INDUSTRY_LAND(3, "工业用地"),
    MINING_LAND(4, "采矿用地"),
    WAREHOUSE_LAND(5, "仓储用地"),
    OTHER_LAND(6, "其他用地"),

            ;

    private int id;
    private String name;
    LandUsageTypeEnum(int id, String name){
        this.id = id;
        this.name = name;
    }
    private static final Function<String, LandUsageTypeEnum> func =
            EnumUtils.lookupMap(LandUsageTypeEnum.class, e -> e.getName());
    public static LandUsageTypeEnum lookupByDisplayNameUtil(String name) {
        return func.apply(name);
    }
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
