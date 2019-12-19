package com.wensheng.zcc.amc.service;

import java.util.List;

public interface CustRecomService {

  String loadSingleData(Long id, Long type);

  List<String> loadData(Long id, Long type);


}
