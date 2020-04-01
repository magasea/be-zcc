package com.wensheng.zcc.amc.module.dao.helper;

import com.wensheng.zcc.common.utils.base.EnumUtils;

import java.util.function.Function;

public enum RenovationEnum {
//    精装修-1，普通装修-2，毛坯-3
    EXQUISITE(1, "精装修"),
    ORDINARY(2, "普通装修"),
    ROUGHCAST(3, "毛坯"),

            ;

    private int id;
    private String name;
    RenovationEnum(int id, String name){
        this.id = id;
        this.name = name;
    }
    private static final Function<String, RenovationEnum> func =
            EnumUtils.lookupMap(RenovationEnum.class, e -> e.getName());
    public static RenovationEnum lookupByDisplayNameUtil(String name) {
        return func.apply(name);
    }

    private static final Function<Integer, RenovationEnum> funcId =
            EnumUtils.lookupMap(RenovationEnum.class, e -> e.getId());
    public static RenovationEnum lookupByDisplayIdUtil(Integer id) {
        return funcId.apply(id);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }



}
