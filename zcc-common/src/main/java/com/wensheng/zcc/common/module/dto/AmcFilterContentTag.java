package com.wensheng.zcc.common.module.dto;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class AmcFilterContentTag extends AmcFilterContentItem implements Serializable {
  private static final long serialVersionUID = 1L;

  List<Long> tagIds = null;


}
