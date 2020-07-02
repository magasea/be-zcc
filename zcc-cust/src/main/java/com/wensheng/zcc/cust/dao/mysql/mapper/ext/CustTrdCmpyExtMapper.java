package com.wensheng.zcc.cust.dao.mysql.mapper.ext;

import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdCmpy;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdCmpyExample;
import com.wensheng.zcc.cust.module.dao.mysql.ext.CustTrdCmpyTrdExt;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface CustTrdCmpyExtMapper {


    List<CustTrdCmpyTrdExt> selectByExample(CustTrdCmpyExample example);

    List<Long> selectByPreFilter(CustTrdCmpyExample example);
    List<Long> selectByPreFilterAllowNoTrd(CustTrdCmpyExample example);

    List<CustTrdCmpyTrdExt> selectByFilter(CustTrdCmpyExample example);

    Long countByFilter(CustTrdCmpyExample example   );
    Long countByFilterAllowNoTrd(CustTrdCmpyExample example   );

    List<Map> selectDuplicateNameCmpy();

    List<CustTrdCmpy> selectNewCmpyByProvince(@Param("updateStartTime") Date updateStartTime,
                                              @Param("updateEndTime") Date updateEndTime,
                                              @Param("provinceList") List<String> provinceList);

}