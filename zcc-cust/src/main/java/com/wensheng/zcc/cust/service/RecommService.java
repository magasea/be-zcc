package com.wensheng.zcc.cust.service;

import java.util.List;

public interface RecommService {
  List<Object> queryDebtCusts(List<Long> debtIds);
}
