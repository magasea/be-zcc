package com.wensheng.zcc.cust.module.sync;

import java.util.List;
import lombok.Data;

@Data
public class PageWrapperBidResp<T> {

    String id;
    Integer page;
    Integer size;
    Integer total;
    Integer totalPage;
    List pages;
    Integer firstPage;
    Integer prePage;
    PageItem nextPage;
    PageItem upPage;


    List<T> dataList;
    @Data
    class PageItem{
        String id;
        String lastId;
        Integer page;
        Integer pageKey;
    }
}

