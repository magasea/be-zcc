package com.wensheng.zcc.amc.module.vo;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class AmcSaleFilter implements Serializable {
  private static final long serialVersionUID = 1L;

  AmcFilterContentAsset filterAsset;
  AmcFilterContentDebt filterDebt;
  AmcFilterContentTag filterTag;


}
