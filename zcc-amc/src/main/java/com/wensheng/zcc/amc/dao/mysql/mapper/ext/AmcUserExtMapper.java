package com.wensheng.zcc.amc.dao.mysql.mapper.ext;


import com.wensheng.zcc.amc.dao.mysql.mapper.AmcUserMapper;
import com.wensheng.zcc.amc.module.dao.mysql.auto.ext.AmcUserExt;

/**
 * @author chenwei on 2/1/19
 * @project zcc-backend
 */
public interface AmcUserExtMapper extends AmcUserMapper {
  AmcUserExt selectByExtExample(Long id);

}
