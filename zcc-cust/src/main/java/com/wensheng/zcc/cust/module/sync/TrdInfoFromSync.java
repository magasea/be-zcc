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
    String sellerContactEmailPrep;
    String sellerContactAddressPrep;
    String sellerContactIDCardPrep;
    String buyerIdPrep;
    int buyerTypePrep;
    String buyerContactManPrep;
    String buyerContactPhonePrep;
    String buyerContactEmailPrep;
    String buyerContactAddressPrep;
    String buyerContactIDCardPrep;
    Long updateDate;
    String trdContentOss;

}
