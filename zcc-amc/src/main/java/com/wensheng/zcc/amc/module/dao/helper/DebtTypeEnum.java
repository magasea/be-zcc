package com.wensheng.zcc.amc.module.dao.helper;

import com.wensheng.zcc.common.utils.base.EnumUtils;
import java.util.function.Function;

/**
 * @author chenwei on 12/10/18
 * @project zcc-backend
 */

public enum DebtTypeEnum {
    GUARANT(1, "保证类"),
    LOST(2, "损失类"),
    BORROW(3, "拆借类"),
    PLEDGE(4, "质押类"),
    SUSPICIOUS(5, "可疑类"),
    EQUITY(4, "质押类"),


    ;
    DebtTypeEnum(int type, String name){
        this.type = type;
        this.name = name;
    }
    int type;
    String name;

    private static final Function<String, DebtTypeEnum> func =
        EnumUtils.lookupMap(DebtTypeEnum.class, e -> e.getName());
    public static DebtTypeEnum lookupByDisplayNameUtil(String name) {
        return func.apply(name);
    }
    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
