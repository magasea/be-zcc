package com.wensheng.zcc.cust.module.vo.recom;

import com.google.gson.annotations.SerializedName;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class RecomQueryAssetRlt {
    @SerializedName("bizType")
    String objectType;
    @SerializedName("bizId")
    Long assetId;
    String type;
    @SerializedName("rawId")
    Long custId;
    BigDecimal score;
}
