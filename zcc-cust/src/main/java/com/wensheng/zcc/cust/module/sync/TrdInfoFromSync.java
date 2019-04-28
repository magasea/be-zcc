package com.wensheng.zcc.cust.module.sync;

import lombok.Data;

import java.util.List;

@Data
public class TrdInfoFromSync {
    Integer pageNum;
    Integer pageSize;
    Integer size;
    String orderBy;
    Integer startRow;
    Integer endRow;
    Integer total;
    Integer pages;
    Integer firstPage;
    Integer prePage;
    Integer nextPage;
    Integer lastPage;
    boolean isFirstPage;
    boolean isLastPage;
    boolean hasPreviousPage;
    boolean hasNextPage;
    Integer navigatePages;
    List<TrdItem> list;

    @Data
    public static class TrdItem{
        String area;
        Integer areaId;
        String asset;
        Long assetOrder;
        String buyer;
        Integer buyerType;
        String buyerTypeName;
        Long createTime;
        String debtBref;
        String id;
        String jsonData;
        String linkMan;
        String linkPhone;
        String number;
        Integer page;
        Integer rows;
        String seller;
        Integer sellerType;
        String sellerTypeName;
        String site;
        String source;
        Integer status;
        String timeEnd;
        String timeStart;
        String title;
        Integer type;
        String typeName;
        Long updateTime;
        String url;
    }
}
