package com.wensheng.zcc.amc.module.dao.helper;

import com.wensheng.zcc.amc.module.dao.helper.base.EnumUtils;

import java.util.function.Function;

/**
 * @author chenwei on 12/27/18
 * @project zcc-backend
 */
public enum LawstatusEnum {

    NOTSUIT(1, "未诉"),
    SUITED(2, "已诉"),
    SUITED_NTEXEC(2, "已诉未执行"),
    SUITED_EXECED(0, "已诉已执行"),
    NOTCLEAR(3, "不清"),
    NOTARY_EXEC(4, "公证执行"),
    ;

    private int status;
    private String name;
    LawstatusEnum(int status, String name){
        this.status = status;
        this.name = name;
    }
    private static final Function<String, LawstatusEnum> func =
            EnumUtils.lookupMap(LawstatusEnum.class, e -> e.getName());
    public static LawstatusEnum lookupByDisplayNameUtil(String name) {
        return func.apply(name);
    }
    public int getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }
}
