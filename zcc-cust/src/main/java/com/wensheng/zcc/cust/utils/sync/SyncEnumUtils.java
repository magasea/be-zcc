package com.wensheng.zcc.cust.utils.sync;

import com.wensheng.zcc.common.utils.AmcExceptions;
import com.wensheng.zcc.common.utils.ExceptionUtils;
import com.wensheng.zcc.cust.module.helper.CustTypeEnum;
import com.wensheng.zcc.cust.module.helper.sync.BuyerTypeSyncEnum;

public class SyncEnumUtils {

    public static CustTypeEnum convertTo(BuyerTypeSyncEnum buyerTypeSyncEnum) throws Exception {
        switch (buyerTypeSyncEnum){
            case PERSON:
                return CustTypeEnum.PERSON;
            case BANK:
                return CustTypeEnum.BANK;
            case COMPANY:
                return CustTypeEnum.COMPANY;

        }
        throw ExceptionUtils.getAmcException(
            AmcExceptions.INVALID_ACTION, String.format("buyerTypeSyscEnum:%s", buyerTypeSyncEnum.getName()));
    }
}
