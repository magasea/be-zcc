package com.wensheng.zcc.comnfunc.module.dto;

import lombok.Data;
@Data
public class AmcRegion {
    String status;
    AmcRegionItem[] data;
    int total;
}
