package com.wensheng.zcc.cust.module.sync;

import lombok.Data;

@Data
public class CustCmpyInfoFromSync {
  String id;
  Integer page;
  Integer rows;
  Long createTime;
  Long updateTime;
  String cmpyName;
  String cmpyProvince;
  String uniSocialCode;
  String legalReptive;
  String cmpyPhone;
  String cmpyAddr;
  String annuReptPhone;
  String annuReptAddr;
  String notes;
  String dataStatus;
  String provinceCode;
  String provinceName;
  String cityCode;
  String cityName;
  String updateFromDate;
  String updateToDate;
  String updateFrom;
  String updateTo;

  @Override
  public String toString() {
    return "CustCmpyInfoFromSync{" +
        "id='" + id + '\'' +
        ", page=" + page +
        ", rows=" + rows +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", cmpyName='" + cmpyName + '\'' +
        ", uniSocialCode='" + uniSocialCode + '\'' +
        ", legalReptive='" + legalReptive + '\'' +
        ", cmpyPhone='" + cmpyPhone + '\'' +
        ", cmpyAddr='" + cmpyAddr + '\'' +
        ", annuReptPhone='" + annuReptPhone + '\'' +
        ", annuReptAddr='" + annuReptAddr + '\'' +
        ", notes='" + notes + '\'' +
        ", dataStatus='" + dataStatus + '\'' +
        ", provinceCode='" + provinceCode + '\'' +
        ", provinceName='" + provinceName + '\'' +
        ", cityCode='" + cityCode + '\'' +
        ", cityName='" + cityName + '\'' +
        ", updateFromDate='" + updateFromDate + '\'' +
        ", updateToDate='" + updateToDate + '\'' +
        ", updateFrom='" + updateFrom + '\'' +
        ", updateTo='" + updateTo + '\'' +
        '}';
  }
}
