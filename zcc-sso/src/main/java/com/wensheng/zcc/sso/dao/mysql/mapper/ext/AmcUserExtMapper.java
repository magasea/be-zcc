package com.wensheng.zcc.sso.dao.mysql.mapper.ext;

import com.wensheng.zcc.sso.dao.mysql.mapper.AmcUserMapper;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcUserExample;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.ext.AmcUserExt;
import java.util.List;

/**
 * @author chenwei on 2/1/19
 * @project zcc-backend
 */
public interface AmcUserExtMapper extends AmcUserMapper {
  AmcUserExt  selectByExtExample(Long id);
  List<AmcUserExt> selectByExtWithRowboundsExample(AmcUserExample example);
  List<Long> selectIdsByExtWithRowboundsExample(AmcUserExample example);

}

