package com.wensheng.zcc.amc.dao.mysql.mapper.ext;

import com.wensheng.zcc.amc.dao.mysql.mapper.AmcDebtpackMapper;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtpackExample;
import com.wensheng.zcc.amc.module.dao.mysql.auto.ext.AmcDebtpackExt;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface AmcDebtpackExtMapper extends AmcDebtpackMapper {


    List<AmcDebtpackExt> selectByExampleExtWithRowbounds(AmcDebtpackExample example, RowBounds rowBounds);


}