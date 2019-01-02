package com.wensheng.zcc.amc.module.dao.helper;

import com.wensheng.zcc.amc.module.dao.helper.base.EnumUtils;

import java.util.function.Function;

/**
 * @author chenwei on 12/27/18
 * @project zcc-backend
 */
public enum AMCEnum {

    AMC_WENSHENG(2L, "WenSheng", "文盛资产管理公司"),
        ;
    Long id;
    String amcName;
    String amcCnName;
    AMCEnum(Long id, String amcName, String amcCnName){
        this.id = id;
        this.amcName = amcName;
        this.amcCnName = amcCnName;
    }
    private static final Function<String, AMCEnum> func =
            EnumUtils.lookupMap(AMCEnum.class, e -> e.getAmcName());
    public static AMCEnum lookupByDisplayNameUtil(String name) {
        return func.apply(name);
    }


    private static final Function<Long, AMCEnum> funcInt =
        EnumUtils.lookupMap(AMCEnum.class, e -> e.getId());
    public static AMCEnum lookupByIdUtil(Long id) {
        return funcInt.apply(id);
    }

    public Long getId(){
        return this.id;
    }

    public String getAmcName(){
        return this.amcName;
    }

    public String getAmcCnName(){
        return this.amcCnName;
    }
}
