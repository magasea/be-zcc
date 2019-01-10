package com.wensheng.zcc.amc.controller.helper;

import java.lang.annotation.Annotation;
import lombok.Data;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;

/**
 * @author chenwei on 1/8/19
 * @project zcc-backend
 */
@Data
public class PageInfo implements PageableDefault {
  int offset;
  int size;
  Direction direction;
  String[] sort;
  int total;


  @Override
  public int value() {
    return offset;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public int page() {
    if(size > 0){
      int pagePos = offset/size + 1;
    }

    return 0;
  }

  @Override
  public String[] sort() {
    return sort;
  }

  @Override
  public Direction direction() {
    return direction;
  }

  @Override
  public Class<? extends Annotation> annotationType() {
    return null;
  }
}
