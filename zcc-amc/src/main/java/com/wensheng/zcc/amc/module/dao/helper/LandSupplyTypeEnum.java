package com.wensheng.zcc.amc.module.dao.helper;

import com.wensheng.zcc.common.utils.base.EnumUtils;

import java.util.function.Function;

public enum LandSupplyTypeEnum {
    ALLOT(1, "划拨"),
    TRANSFER(2, "出让"),

            ;

    private int id;
    private String name;
    LandSupplyTypeEnum(int id, String name){
        this.id = id;
        this.name = name;
    }
    private static final Function<String, LandSupplyTypeEnum> func =
            EnumUtils.lookupMap(LandSupplyTypeEnum.class, e -> e.getName());
    public static LandSupplyTypeEnum lookupByDisplayNameUtil(String name) {
        return func.apply(name);
    }
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
