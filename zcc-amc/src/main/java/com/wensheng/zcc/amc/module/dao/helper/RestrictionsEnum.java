package com.wensheng.zcc.amc.module.dao.helper;

import com.wensheng.zcc.amc.module.dao.helper.base.EnumUtils;
import java.util.function.Function;

/**
 * @author chenwei on 12/10/18
 * @project zcc-backend
 */
public enum RestrictionsEnum {
    RESTRICTED(1, "限购"),
    NOTRESTRICTED(2, "不限购"),
    NOTCLEAR(-1, "不明确");

    private int status;
    private String name;
    RestrictionsEnum(int status, String name){
        this.status = status;
        this.name = name;
    }
    private static final Function<String, RestrictionsEnum> func =
        EnumUtils.lookupMap(RestrictionsEnum.class, e -> e.getName());
    public static RestrictionsEnum lookupByDisplayNameUtil(String name) {
        return func.apply(name);
    }
    public int getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

}
