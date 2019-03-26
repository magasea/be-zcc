package com.wensheng.zcc.amc.module.dao.helper;

import com.wensheng.zcc.common.utils.base.EnumUtils;

import java.util.function.Function;

/**
 * @author chenwei on 12/27/18
 * @project zcc-backend
 */
public enum LawstateEnum {

    CLOSED(1, "已结案"),
    NOTYETSUED(2, "未诉"),
    INTRIAL(3, "已诉未判"),
    JUDGEMENT(4, "已判"),
    EXECNOTAPPLIED(5, "未申请执行"),
    EXECAPPLIED(6, "已申请执行"),
    INENFORCEENT(7, "执行中"),
    BANKRUPTED(8, "破产"),
    MIXED(9, "混合"),
    ;

    private int status;
    private String name;
    LawstateEnum(int status, String name){
        this.status = status;
        this.name = name;
    }
    private static final Function<String, LawstateEnum> func =
            EnumUtils.lookupMap(LawstateEnum.class, e -> e.getName());
    public static LawstateEnum lookupByDisplayNameUtil(String name) {
        return func.apply(name);
    }
    public int getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }
}
