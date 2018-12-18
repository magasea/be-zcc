package com.wensheng.zcc.amc.module.dao.helper;

import com.wensheng.zcc.amc.module.dao.helper.base.EnumUtils;
import java.util.function.Function;

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
    MECHANICAL_EQUIP(7, "机器设备"),
    COMMERCIAL_HOUSE(8, "商品房"),
    OTHER(9, "其他"),
    OTHER1(9, "其它"),
    APARTMENT(11, "公寓"),
    FACTORY_BUILDING(12, "厂房"),
    ;
    AssetTypeEnum(int type, String name){
        this.type = type;
        this.name = name;
    }
    //    住宅，别墅，商铺，写字楼，工业房地产，土地，机器设备，其它
    int type;
    String name;

    private static final Function<String, AssetTypeEnum> func =
        EnumUtils.lookupMap(AssetTypeEnum.class, e -> e.getName());
    public static AssetTypeEnum lookupByDisplayNameUtil(String name) {
        return func.apply(name);
    }
    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
