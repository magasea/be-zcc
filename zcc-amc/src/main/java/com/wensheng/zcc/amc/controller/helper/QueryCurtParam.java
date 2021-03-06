package com.wensheng.zcc.amc.controller.helper;

import com.wensheng.zcc.common.aop.SQLInjectionSafe;
import com.wensheng.zcc.common.params.PageInfo;
import lombok.Data;

import java.util.List;

/**
 * @author chenwei on 1/8/19
 * @project zcc-backend
 */
@Data
public class QueryCurtParam {
  String province ;
  String city ;
  String county ;
  String curtName;
  PageInfo pageInfo;



}
