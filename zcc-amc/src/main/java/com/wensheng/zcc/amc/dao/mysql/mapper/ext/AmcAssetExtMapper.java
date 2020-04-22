package com.wensheng.zcc.amc.dao.mysql.mapper.ext;

import com.wensheng.zcc.amc.dao.mysql.mapper.AmcAssetMapper;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcAsset;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcAssetExample;
import java.util.List;

public interface AmcAssetExtMapper extends AmcAssetMapper {


    List<AmcAsset> selectAllTitlesByExample(AmcAssetExample example);


}