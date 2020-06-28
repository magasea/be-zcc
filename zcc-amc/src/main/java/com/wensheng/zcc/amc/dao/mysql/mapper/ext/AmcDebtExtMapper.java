package com.wensheng.zcc.amc.dao.mysql.mapper.ext;

import com.wensheng.zcc.amc.dao.mysql.mapper.AmcDebtMapper;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebt;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtExample;
import com.wensheng.zcc.amc.module.dao.mysql.auto.ext.AmcDebtExt;
import java.util.List;

public interface AmcDebtExtMapper extends AmcDebtMapper {


    List<AmcDebtExt> selectSimpleByExampleExt(AmcDebtExample example);

    List<AmcDebtExt> selectByPrimaryKeyExt(Long id);

    List<AmcDebt> selectAllTitlesByExample(AmcDebtExample amcDebtExample);

}