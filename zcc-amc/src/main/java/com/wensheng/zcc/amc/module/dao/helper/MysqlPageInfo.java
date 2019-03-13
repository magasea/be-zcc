package com.wensheng.zcc.amc.module.dao.helper;

import lombok.Data;

/**
 * @author chenwei on 3/13/19
 * @project zcc-backend
 */
@Data
public class MysqlPageInfo {
  Integer pageSize;
  Long offset;
}
