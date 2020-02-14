package com.wensheng.zcc.cust.utils;

import com.wensheng.zcc.cust.module.helper.ItemTypeEnum;
import com.wensheng.zcc.cust.module.helper.sync.BidTypeEnum;
import org.apache.xmlbeans.impl.xb.xsdschema.BlockSet.Member2.Item;

public class TypeConvertUtil {

  public static ItemTypeEnum getItemTypeFromBidtypeEnum(BidTypeEnum bidTypeEnum){
    switch (bidTypeEnum){
      case LAND:
        return ItemTypeEnum.INDUSTRYASSET;
      case MALL:
        return ItemTypeEnum.COMMERIALASSET;
      case RESIDENTIAL:
        return ItemTypeEnum.CIVILASSET;
      case SHOP:
        return ItemTypeEnum.COMMERIALASSET;
      case HOTEL:
        return ItemTypeEnum.COMMERIALASSET;
      case VILLA:
        return ItemTypeEnum.CIVILASSET;
      case COMBINED:
        return ItemTypeEnum.OTHERASSET;
      case OFFICE_BUILDING:
        return ItemTypeEnum.COMMERIALASSET;
      case INDUSTRY_REAL_ESTATE:
        return ItemTypeEnum.INDUSTRYASSET;

        default:
        return ItemTypeEnum.OTHERASSET;

    }
  }

}
