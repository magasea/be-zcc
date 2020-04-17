package com.wensheng.zcc.cust.module.sync;

import lombok.Data;

import java.util.List;

@Data
public class TrdInfoFromSync {

    String id;
    int trdType;
    String trdTitle;
    int packCount;
    String debtProvincePrep;
    String debtCityPrep;
    String trdProvincePrep;
    String trdAmount;
    Long trdAmountPrep;
    Long trdDate;
    Long publishDate;
    String trdContentPrep;
    String sellerIdPrep;
    int sellerTypePrep;
    String sellerContactManPrep;
    String sellerContactPhonePrep;
    // 转让方-手机
    String sellerContactMobilePrep;
    String sellerContactEmailPrep;
    String sellerContactAddressPrep;
    String sellerContactIDCardPrep;
    String buyerIdPrep;
    int buyerTypePrep;
    String buyerContactManPrep;
    String buyerContactPhonePrep;
    //受让方-联系手机号
    String buyerContactMobilePrep;
    String buyerContactEmailPrep;
    String buyerContactAddressPrep;
    String buyerContactIDCardPrep;
    Long updateDate;
    String trdContentOss;

}
