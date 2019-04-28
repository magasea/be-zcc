package com.wensheng.zcc.cust.module.sync;

import lombok.Data;

import java.util.List;
@Data
public class    PageWrapperResp <T> {

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
    List<T> list;
}
