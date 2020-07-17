package com.wensheng.zcc.cust.controller.helper;

import com.wensheng.zcc.common.aop.SQLInjectionSafe;
import com.wensheng.zcc.common.params.PageInfo;
import java.util.List;
import lombok.Data;

@Data
public class QueryParam {
  //查询类型（ALL UPDATE CREATE TRADE）
  String selectCustType;
  //1:个人 2：公司
  int custType;
  //债权所属城市编码
  String city;
  //公司省码
  List<String> custCity;
  //姓名
  String name;
  //类型
  Integer itemType = -1;
  //交易类型：转让，债权
  Integer trdType = -1;
  List<Integer> investDebtScales;
  List<Integer> investTrdScales;
  //分页信息
  PageInfo pageInfo;
  //导出的数量
  int exportSize = -1;
  boolean allowNoTrd = true;
  //默认开始结束时间2020-07-03
  String latestStartDay;
  String latestEndDay;

  //创建时间2020-07-03
  String createStartDay;
  String createEndDay;
  //更新时间2020-07-03
  String updateStartDay;
  String updateEndDay;
}
