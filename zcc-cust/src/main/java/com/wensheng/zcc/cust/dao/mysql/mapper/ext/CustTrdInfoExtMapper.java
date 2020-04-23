package com.wensheng.zcc.cust.dao.mysql.mapper.ext;

import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdInfo;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CustTrdInfoExtMapper {
    long countByExample(CustTrdInfoExample example);

    List<CustTrdInfo> selectByExampleWithRowbounds(CustTrdInfoExample example, RowBounds rowBounds);

    List<CustTrdInfo> selectInfoByPhoneSign(@Param("sign") String sign);

    List<CustTrdInfo> selectInfoByRigitPhone();

    List<CustTrdInfo> selectInfoByUnknowPhone();



    List<CustTrdInfo> selectByExample(CustTrdInfoExample example);
    CustTrdInfo selectByPrimaryKey(Long id);


}