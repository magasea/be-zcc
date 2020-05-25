package com.wensheng.zcc.cust.module.sync;

import java.util.List;
import lombok.Data;

@Data
public class CrawlResultDTO {

  private int pageNum;
  private int pageSize;
  private int size;
  private String orderBy;
  private int startRow;
  private int endRow;
  private int total;
  private int pages;
  private int firstPage;
  private int prePage;
  private int nextPage;
  private int lastPage;
  private boolean isFirstPage;
  private boolean isLastPage;
  private boolean hasPreviousPage;
  private boolean hasNextPage;
  private int navigatePages;
  private List<CmpyBasicBizInfoSync> list;
  private List<Integer> navigatepageNums;

}
