package com.wensheng.zcc.amc.mapper;

import com.wensheng.zcc.amc.module.dao.mysql.AmcAsset;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author chenwei on 12/5/18
 * @project zcc-backend
 */
public interface AmcAssetMapper {

    @Select("SELECT * FROM AMC_ASSET")
    List<AmcAsset> getAll();

}
