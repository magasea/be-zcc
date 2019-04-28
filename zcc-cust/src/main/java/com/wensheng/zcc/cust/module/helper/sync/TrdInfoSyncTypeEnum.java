package com.wensheng.zcc.cust.module.helper.sync;

public enum TrdInfoSyncTypeEnum {

    DEBTTRANSFER(1, "债权转让"),
    DEBTPROCED(2, "债权处置公告"),
    INVESTMENTAD(3, "招商公告"),
    ;

    private int id;
    private String name;
    TrdInfoSyncTypeEnum(int id, String name){
        this.id = id;
        this.name = name;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
