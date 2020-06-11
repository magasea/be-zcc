package com.wensheng.zcc.common.module.dto;

import java.io.Serializable;
import lombok.Data;

@Data
public class AmcSaleFilter implements Serializable {
  private static final long serialVersionUID = 1L;

  AmcFilterContentAsset filterAsset;
  AmcFilterContentDebt filterDebt;
  AmcFilterContentTag filterTag;


}
