package com.wensheng.zcc.amc.dao.mysql.mapper.ext;

import com.wensheng.zcc.amc.dao.mysql.mapper.AmcAssetMapper;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcAsset;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcAssetExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface AmcAssetExtMapper extends AmcAssetMapper {


    List<String> selectAllTitlesByExample(AmcAssetExample example);


}