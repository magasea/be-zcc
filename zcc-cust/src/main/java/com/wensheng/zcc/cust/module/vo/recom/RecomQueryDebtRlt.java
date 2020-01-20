package com.wensheng.zcc.cust.module.vo.recom;

import com.google.gson.annotations.SerializedName;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class RecomQueryDebtRlt {
    @SerializedName("bizType")
    String custType;
    @SerializedName("bizId")
    Long debtId;
    String type;
    @SerializedName("rawId")
    Long custId;
    BigDecimal score;
}
