package com.wensheng.zcc.cust.service;

import java.text.ParseException;

public interface ScriptSysService {

    public void doSynchWithScriptOn(String province) throws Exception;

  void doSynchWithCusts() throws ParseException;

}
