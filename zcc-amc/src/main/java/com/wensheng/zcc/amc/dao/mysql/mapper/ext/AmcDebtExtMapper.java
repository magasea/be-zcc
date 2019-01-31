package com.wensheng.zcc.amc.dao.mysql.mapper.ext;

import com.wensheng.zcc.amc.dao.mysql.mapper.AmcDebtMapper;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcAssetExample;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebt;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtExample;
import com.wensheng.zcc.amc.module.dao.mysql.auto.ext.AmcDebtExt;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface AmcDebtExtMapper extends AmcDebtMapper {


    List<AmcDebtExt> selectByExampleWithRowboundsExt(AmcDebtExample example, RowBounds rowBounds);

    List<AmcDebt> selectAllTitlesByExample(AmcDebtExample amcDebtExample);

}