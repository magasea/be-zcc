package com.wensheng.zcc.amc.controller.helper;

import com.wensheng.zcc.amc.module.vo.AmcDebtVo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

/**
 * @author chenwei on 1/10/19
 * @project zcc-backend
 */
public class PageReqRepHelper {
  public static Map<String, Direction> getOrderParam(PageInfo pageable){
    Map<String, Direction> orderByParam = new HashMap<>();
    if(pageable.getSort() != null && pageable.getSort().length > 0){

      for(String orderBy : pageable.getSort()){

        orderByParam.put( orderBy, pageable.direction());
      }
    }
    return orderByParam;
  }

  public static int getOffset(PageInfo pageable){
    return pageable.getOffset() > 0 ? pageable.getOffset(): pageable.getPage() > 0?
        (pageable.getPage()-1)*pageable.getSize(): 0;
  }

  public static <T> Page<T> getPageRespdep(Long total, List<T> list, PageInfo pageable){
    PageRequest pageRequest = PageRequest.of(pageable.page(), pageable.getSize());

    PageImpl page = new PageImpl<T>(list, pageRequest, total );
    return page;

  }

  public static <T> AmcPage <T> getAmcPage(List<T> queryResults, Long total){
    AmcPage<T> amcPage = new AmcPage<>();
    amcPage.setContent(queryResults);
    amcPage.setTotal(total);
    return amcPage;
  }
}
