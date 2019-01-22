package com.wensheng.zcc.amc.module.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wensheng.zcc.amc.module.dao.mongo.entity.DebtImage;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author chenwei on 1/22/19
 * @project zcc-backend
 */
@Data
public class AmcDebtImageVo {
  @JsonProperty
  DebtImage debtImageParam;
  MultipartFile[] multipartFiles;
}
