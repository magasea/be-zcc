package com.wensheng.zcc.amc.utils;

/**
 * @author chenwei on 1/11/19
 * @project zcc-backend
 */
public class ExceptionUtils {

  public static Exception getAmcException(AmcExceptions amcExceptions){
    return new Exception(String.format("%d:%s:%s",amcExceptions.code, amcExceptions.name, amcExceptions.reason));
  }

  public static Exception getAmcException(AmcExceptions amcExceptions, String additional){
    return new Exception(String.format("%d:%s:%s-%s",amcExceptions.code, amcExceptions.name, amcExceptions.reason,
        additional));
  }

  public static enum AmcExceptions{

    NO_AMCDEBTPACK_AVAILABLE(1001, "NO_AMCDEBTPACK_AVAILABLE", "no amcdebtpack available"),
    NO_AMCDEBT_AVAILABLE(1002, "NO_AMCDEBT_AVAILABLE", "no amcdebt available"),
    NO_AMCASSET_AVAILABLE(1003, "NO_AMCASSET_AVAILABLE", "no amcasset available"),

    INVALID_ACTION(1004, "INVALID_ACTION", "violate edit rule"),
    INVALID_ORIG_CREDITOR(1005, "INVALID_ORIG_CREDITOR", "没有该原始债权人"),
    MISSING_MUST_PARAM(1006, "Missing must parameters", "缺少必填参数"),

    INVALID_GRANTORTYPE(1007, "invalid grantor type", "不是合法的担保人类型"),
    NO_AMCGRANTOR_AVAILABLE(1008, "no grantor available", "担保人不存在"),
    INVALID_AMCCONTACTID(1009, "invalid amc contact ", "担保人id非法"),
    NO_AMCCONTACT_AVAILABLE(1010, "no amc contact available ", "担保人不存在"),

    NO_CREDITOR(1011, "no creditor", "没有借款人"),
    INSERT_DB_ERROR(1012, "failed to insert db", "数据插入失败"),
    INVALID_LANDAREA_NUMBER(1013, "invalid landarea number", "非法的土地面积"),
    DIRECTORY_OPER_FAILED(1014, "directory failed to oper", "目录操作失败"),

    ;
    int code;
    String name;
    String reason;
    AmcExceptions(int code, String name, String reason){
        this.code = code;
        this.name = name;
        this.reason = reason;
      }
  }

}
