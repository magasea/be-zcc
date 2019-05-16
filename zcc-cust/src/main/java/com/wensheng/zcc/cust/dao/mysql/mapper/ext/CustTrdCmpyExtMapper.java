package com.wensheng.zcc.cust.dao.mysql.mapper.ext;

import com.mysql.cj.result.Row;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdCmpy;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdCmpyExample;
import com.wensheng.zcc.cust.module.dao.mysql.ext.CustTrdCmpyTrdExt;
import com.wensheng.zcc.cust.module.dao.mysql.ext.PreGroupResult;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CustTrdCmpyExtMapper {


    List<CustTrdCmpyTrdExt> selectByExample(CustTrdCmpyExample example);

    List<Long> selectByPreFilter(CustTrdCmpyExample example);

    List<CustTrdCmpyTrdExt> selectByFilter(CustTrdCmpyExample example);

    Long countByFilter(CustTrdCmpyExample example   );

}