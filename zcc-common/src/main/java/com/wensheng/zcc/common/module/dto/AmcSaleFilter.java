package com.wensheng.zcc.common.module.dto;

import com.wensheng.zcc.common.module.dto.AmcFilterContentAsset;
import com.wensheng.zcc.common.module.dto.AmcFilterContentDebt;
import com.wensheng.zcc.common.module.dto.AmcFilterContentTag;
import java.io.Serializable;
import lombok.Data;

@Data
public class AmcSaleFilter implements Serializable {
  private static final long serialVersionUID = 1L;

  AmcFilterContentAsset filterAsset;
  AmcFilterContentDebt filterDebt;
  AmcFilterContentTag filterTag;


}
