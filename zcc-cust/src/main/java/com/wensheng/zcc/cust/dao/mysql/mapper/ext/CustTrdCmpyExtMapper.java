package com.wensheng.zcc.cust.dao.mysql.mapper.ext;

import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdCmpy;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdCmpyExample;
import com.wensheng.zcc.cust.module.dao.mysql.ext.CustTrdCmpyTrdExt;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CustTrdCmpyExtMapper {


    List<CustTrdCmpyTrdExt> selectByExampleWithRowbounds(CustTrdCmpyExample example, RowBounds rowBounds);



    List<CustTrdCmpyTrdExt> selectByFilterWithRowbounds(CustTrdCmpyExample example, RowBounds rowBounds);

    Long countByFilter(CustTrdCmpyExample example   );

}