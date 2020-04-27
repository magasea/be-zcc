package com.wensheng.zcc.cust.dao.mysql.mapper.ext;

import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustAmcCmpycontactor;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustAmcCmpycontactorExample;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdInfo;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdInfoExample;
import com.wensheng.zcc.cust.module.dao.mysql.ext.CustAmcCmpycontactorExt;
import com.wensheng.zcc.cust.module.dao.mysql.ext.CustAmcCmpycontactorExtExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CustAmcCmpycontactorExtMapper {

    List<CustAmcCmpycontactorExt> selectByFilter(CustAmcCmpycontactorExtExample example);

    List<CustAmcCmpycontactor> selectCmpyContactorBymobileList(@Param("company") String company,
                                                   @Param("name") String name,
                                                   @Param("mobileList") List<String> mobileList);

    List<CustAmcCmpycontactor> selectCmpyContactorByPhoneSign(@Param("sign") String sign);
    List<CustAmcCmpycontactor> selectCmpyContactorByRightPhone();
    List<CustAmcCmpycontactor> selectCmpyContactorByUnknowPhone();

}