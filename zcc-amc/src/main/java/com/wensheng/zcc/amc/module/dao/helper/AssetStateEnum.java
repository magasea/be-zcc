package com.wensheng.zcc.amc.module.dao.helper;

import com.wensheng.zcc.amc.module.dao.helper.base.EnumUtils;
import java.util.function.Function;

/**
 * @author chenwei on 12/10/18
 * @project zcc-backend
 */
public enum AssetStateEnum {
    NOTCLEAR(-1, "不确定"),
    PLEDGE(1, "质押"),
    FROZEN(2, "冻结"),
    MORTGAGE(3, "抵押"),
    SEAL(4, "查封"),
    WAITING_SEAL(5, "轮候查封"),
    ;


    private int status;
    private String name;

    private static final Function<String, AssetStateEnum> func =
        EnumUtils.lookupMap(AssetStateEnum.class, e -> e.getName());
    public static AssetStateEnum lookupByDisplayNameUtil(String name) {
        return func.apply(name);
    }
    AssetStateEnum(int status, String name){
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
