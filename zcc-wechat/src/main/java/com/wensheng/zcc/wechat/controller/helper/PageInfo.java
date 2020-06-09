package com.wensheng.zcc.wechat.controller.helper;

import java.lang.annotation.Annotation;
import java.util.Map;
import lombok.Data;
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
  int page;
  int location = -1;
  Map<String, Direction> sortInfo;

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
    return page;
  }

  @Override
  public String[] sort() {
    return null;
  }

  @Override
  public Direction direction() {
    return Direction.DESC;
  }

  @Override
  public Class<? extends Annotation> annotationType() {
    return null;
  }
}
