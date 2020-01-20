package com.wensheng.zcc.cust.module.vo.helper;

import java.util.List;
import lombok.Data;

@Data
public class PageResult<T> {
  List<T> results;
}
