package com.wensheng.zcc.cust.dao.mysql.mapper.ext;

import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustAmcCmpycontactor;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdPerson;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdPersonExample;
import com.wensheng.zcc.cust.module.dao.mysql.ext.CustTrdPersonExtExample;
import com.wensheng.zcc.cust.module.dao.mysql.ext.CustTrdPersonTrdExt;
import java.util.List;

import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CustTrdPersonExtMapper {


    List<CustTrdPersonTrdExt> selectByExample(CustTrdPersonExample examples);
    List<CustTrdPersonTrdExt> selectByFilter(CustTrdPersonExample example);
    List<Long> selectByPreFilter(CustTrdPersonExample example);
    List<Long> selectByPreFilterAllowNoTrd(CustTrdPersonExample example);
    Long countByFilter(CustTrdPersonExtExample example);
    Long countByFilterAllowNoTrd(CustTrdPersonExtExample example);

    List<CustTrdPerson> selectTrdPersonByPhoneSign(@Param("sign") String sign);
    List<CustTrdPerson> selectTrdPersonByRightPhone();
    List<CustTrdPerson> selectTrdPersonByUnknowPhone();
}