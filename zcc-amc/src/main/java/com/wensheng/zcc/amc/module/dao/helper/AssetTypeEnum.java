package com.wensheng.zcc.amc.module.dao.helper;

/**
 * @author chenwei on 12/10/18
 * @project zcc-backend
 */

public enum AssetTypeEnum {
    RESIDENTIAL(1, "住宅"),
    VILLA(2, "别墅"),
    SHOP(3, "商铺"),
    OFFICE_BUILDING(4, "写字楼"),
    INDUSTRY_REAL_ESTATE(5, "工业房地产"),
    LAND(6, "土地"),
    MECHANICAL_EQUIP(7, "机器设备");

    AssetTypeEnum(int type, String name){
        this.type = type;
        this.name = name;
    }
    //    住宅，别墅，商铺，写字楼，工业房地产，土地，机器设备，其它
    int type;
    String name;

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
