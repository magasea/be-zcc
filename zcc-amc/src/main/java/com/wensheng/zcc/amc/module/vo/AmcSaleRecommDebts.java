package com.wensheng.zcc.amc.module.vo;

import com.wensheng.zcc.common.module.dto.AmcFilterContentItem;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class AmcSaleRecommDebts extends AmcFilterContentItem implements Serializable {
  private static final long serialVersionUID = 1L;

  List<Long> debtIds = null;


}
