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
    String debtProvinceNamePrep;
    String debtCityPrep;
    String debtCityNamePrep;
    String trdAmount;
    Long trdAmountPrep;
    Double baseAmount;
    Double interestAmount;
    Double totalAmount;
    Long trdDate;
    String trdContentPrep;
    String linkMan;
    String linkAddress;
    String sellerIdPrep;
    int sellerTypePrep;
    String buyerIdPrep;
    int buyerTypePrep;
    Long updateDate;
    String url;

}
