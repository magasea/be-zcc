package com.wensheng.zcc.amc.module.dao.helper;

import com.wensheng.zcc.common.utils.base.EnumUtils;

import java.util.function.Function;

public enum DebtPrecheckErrorEnum {
/*
资产关联的债权名字为：[%s] 但是该债权尚未进入可导入的名单,所以该资产也不能被导入
后台还没设置该土地供应方式枚举值:%s
没有资产名字:%s
后台尚未配置该资产类型:%s
资产类型为空:%s
后台尚未配置该查封类型:%s
后台没找到对应的资产所在省份的编码:%s",sheetAsset.getSheetName(),row.getRowNum(), cellAssetProv));
资产所在省份不能为空",sheetAsset.getSheetName(),row.getRowNum()));
后台没找到对应的资产所在省份的编码:%s",sheetAsset.getSheetName(),row.getRowNum(), cellAssetCity));
资产所在城市不能为空",sheetAsset.getSheetName(),row.getRowNum()));
后台没找到对应的资产所在区县的编码:%s",sheetAsset.getSheetName(),row.getRowNum(), cellAssetCounty));
后台尚未设置该土地用途枚举值:%s",sheetAsset.getSheetName(),row.getRowNum(), cellLandUsage));
有重复的资产名字:%s 在该债权下:%s
联系人不能为空
后台没有找到对应的联系人信息:%s
债权本金不能为空:%s
找不到对应的法律状态:%s
诉讼状态不能为空
后台尚未设置该担保方式枚举值:%s
担保方式不能为空
没有找到联系人所属的地区(分部):%s
法院所属省为空
法院所属城市为空
当前后台系统中没有找到该法院:%s
找不到对应的法院所属省:%s
找不到对应的法院所属市:%s
找不到对应的法院所属区县:%s
该债权在正式表AmcDebt里面已经存在,可以前往债权编辑页面去更新它:%s
担保人不能为空
借款人不能为空
在 %s 中有重复,重复的名字将被去重:
 */

    DEBT_PRECHECK_FAIL(10001,"[%s] [%s] [%s] 债权：[%s]在正式表里已经存在", "debt is already exist in formal table"),
    DEBT_PRECHECK_NOTEXIST(10002,"[%s] [%s] [%s] 债权：[%s]不存在", "debt is not exist "),
    TYPE_NOTAVAIL(10003,"[%s] [%s] [%s] 下拉选项的值跟债查查的值不匹配:[%s] 字段:[%s]", "type  not available in zcc"),
    SPEC_CHAR_ERR(10004, "[%s] [%s] [%s] 特殊字符错误，只允许英文逗号（用作分隔符）", "only , can be used as splitor"),
    FIELD_EMPTY(10005,"[%s] [%s] [%s] 字段:[%s] 为空", "field is empty"),
    LOCATIONCODE_NOTAVAIL(10006,"[%s] [%s] [%s] 没有找到匹配的省市区编码:[%s]", "location code not available"),
    DUPLICATEITEM_IN_DEBET(10007,"[%s] [%s] [%s] 债权[%s]下有重复的元素:[%s]", "duplicate item in debt"),
    DUPLICATENAME_IN_DEBET(10008,"[%s] [%s] [%s] 债权[%s]下有重复的名字:[%s]", "duplicate name in debt"),
    AMCCONTACTOR_INFO_ERR(10009,"[%s] [%s] [%s] 没有找到联系人信息:[%s]", "amc contactor's info not available"),



    ;
    int errorCode;

    String cerrorInfo;
    String errorInfo;

    DebtPrecheckErrorEnum(int errorCode,   String cerrorInfo, String errorInfo){
        this.errorCode = errorCode;

        this.cerrorInfo = cerrorInfo;
        this.errorInfo = errorInfo;
    }

    private static final Function<String, DebtPrecheckErrorEnum> func =
            EnumUtils.lookupMap(DebtPrecheckErrorEnum.class, e -> e.getCerrorInfo());
    public static DebtPrecheckErrorEnum lookupByDisplayNameUtil(String name) {
        return func.apply(name);
    }

    private static final Function<Integer, DebtPrecheckErrorEnum> funcId =
            EnumUtils.lookupMap(DebtPrecheckErrorEnum.class, e -> e.getErrorCode());
    public static DebtPrecheckErrorEnum lookupByDisplayNameUtil(Integer id) {
        return funcId.apply(id);
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getCerrorInfo() {
        return cerrorInfo;
    }

    public String getErrorInfo() {
        return errorInfo;
    }
}
