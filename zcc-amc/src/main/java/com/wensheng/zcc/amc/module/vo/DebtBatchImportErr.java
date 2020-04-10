package com.wensheng.zcc.amc.module.vo;

import com.wensheng.zcc.amc.module.dao.helper.DebtPrecheckErrorEnum;
import lombok.Data;

import java.util.List;

@Data
public class DebtBatchImportErr {
    Integer errorCode;
    List<Object> hints;
}
