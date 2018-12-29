package com.wensheng.zcc.amc.module.dao.helper;

import com.wensheng.zcc.amc.module.dao.helper.base.EnumUtils;

import java.util.function.Function;

/**
 * @author chenwei on 12/27/18
 * @project zcc-backend
 */
public enum AMCEnum {

    AMC_WENSHENG(2, "WenSheng", "文盛资产管理公司"),
        ;
    int id;
    String amcName;
    String amcCnName;
    AMCEnum(int id, String amcName, String amcCnName){
        this.id = id;
        this.amcName = amcName;
        this.amcCnName = amcCnName;
    }
    private static final Function<String, AssetTypeEnum> func =
            EnumUtils.lookupMap(AssetTypeEnum.class, e -> e.getName());
    public static AssetTypeEnum lookupByDisplayNameUtil(String name) {
        return func.apply(name);
    }
}
