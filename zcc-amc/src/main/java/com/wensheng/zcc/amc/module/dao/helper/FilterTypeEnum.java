package com.wensheng.zcc.amc.module.dao.helper;

import com.wensheng.zcc.common.utils.base.EnumUtils;
import java.util.function.Function;

/**
 * @author chenwei on 12/10/18
 * @project zcc-backend
 */

public enum FilterTypeEnum {
    DEBT(1, "债权"),
    ASSET(2, "资产"),
    TAG(3, "标签"),


    ;
    FilterTypeEnum(int type, String name){
        this.type = type;
        this.name = name;
    }
    //    住宅，别墅，商铺，写字楼，工业房地产，土地，机器设备，其它
    int type;
    String name;

    private static final Function<String, FilterTypeEnum> func =
        EnumUtils.lookupMap(FilterTypeEnum.class, e -> e.getName());
    public static FilterTypeEnum lookupByDisplayNameUtil(String name) {
        return func.apply(name);
    }
    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
