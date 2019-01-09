package com.wensheng.zcc.amc.controller.helper;

import lombok.Data;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;

/**
 * @author chenwei on 1/8/19
 * @project zcc-backend
 */
@Data
public class PageInfo {
  int offset;
  int size;
  Pageable pageable;

}
