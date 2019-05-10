package com.wensheng.zcc.cust.dao.mysql.mapper.ext;

import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdPerson;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdPersonExample;
import com.wensheng.zcc.cust.module.dao.mysql.ext.CustTrdPersonExtExample;
import com.wensheng.zcc.cust.module.dao.mysql.ext.CustTrdPersonTrdExt;
import java.util.List;

import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CustTrdPersonExtMapper {


    List<CustTrdPersonTrdExt> selectByExampleWithRowbounds(CustTrdPersonExample example, RowBounds rowBounds);
    List<CustTrdPersonTrdExt> selectByFilterWithRowbounds(CustTrdPersonExample example, RowBounds rowBounds);
    Long countByFilter(CustTrdPersonExtExample example);

}