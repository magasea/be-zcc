package com.wensheng.zcc.amc.module.vo.base;

import com.wensheng.zcc.amc.module.dao.helper.EditStatusEnum;
import lombok.Data;

/**
 * @author chenwei on 1/15/19
 * @project zcc-backend
 */
@Data
public class BaseActionVo<T> {
  Integer editActionId;
  T content;
}
