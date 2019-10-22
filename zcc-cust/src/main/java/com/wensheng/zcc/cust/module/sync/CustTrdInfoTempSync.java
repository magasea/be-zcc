package com.wensheng.zcc.cust.module.sync;

import java.util.Date;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class CustTrdInfoTempSync {

    Integer areaId;
    Long assetOrder;
    Integer buyerType;
    Integer invType;
    Integer page;
    Integer rows;
    Integer sellerType;
    Integer status;
    Integer type;
    Long createTime;
    Long updateTime;
    String area;
    String asset;
    String buyer;
    String buyerAddress;
    String buyerEmail;
    String buyerId;
    String buyerMan;
    String buyerPhone;
    String buyerPrep;
    String buyerTypeName;
    String cityCode;
    String cityName;
    String debtBref;
    String id;
    String jsonData;
    String linkMan;
    String linkPhone;
    String number;
    String provinceCode;
    String provinceName;
    String seller;
    String sellerAddress;
    String sellerEmail;
    String sellerId;
    String sellerMan;
    String sellerPhone;
    String sellerPrep;
    String sellerTypeName;
    String site;
    String source;
    String timeEnd;
    String timeStart;
    String title;
    String typeName;
    String url;
}