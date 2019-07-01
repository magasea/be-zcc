package com.wensheng.zcc.cust.service;

import java.text.ParseException;

public interface ScriptSysService {

    public void doSynchWithScriptOn(String provinceName) throws Exception;

  void doSynchWithCusts() throws ParseException;

}
