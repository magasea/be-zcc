package com.wensheng.zcc.comnfunc.module.vo.base;

import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class GaodeGeoQueryResp extends GaodeGeneralResp{

  List< GaodeGeoQueryVal> geocodes;

}


