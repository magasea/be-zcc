package com.wensheng.zcc.amc.controller;

import com.wensheng.zcc.amc.aop.LogExecutionTime;
import com.wensheng.zcc.amc.controller.helper.PageInfo;
import com.wensheng.zcc.amc.controller.helper.PageReqRepHelper;
import com.wensheng.zcc.amc.module.dao.helper.DebtorTypeEnum;
import com.wensheng.zcc.amc.module.dao.helper.ImageClassEnum;
import com.wensheng.zcc.amc.module.dao.helper.PublishStateEnum;
import com.wensheng.zcc.amc.module.dao.helper.ImagePathClassEnum;
import com.wensheng.zcc.amc.module.dao.mongo.entity.DebtImage;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcCmpy;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebt;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtContactor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcInfo;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcOrigCreditor;
import com.wensheng.zcc.amc.module.vo.AmcDebtCreateVo;
import com.wensheng.zcc.amc.module.vo.AmcDebtExtVo;
import com.wensheng.zcc.amc.module.vo.AmcDebtVo;
import com.wensheng.zcc.amc.module.vo.base.BaseActionVo;
import com.wensheng.zcc.amc.service.AmcDebtService;
import com.wensheng.zcc.amc.service.AmcDebtpackService;
import com.wensheng.zcc.amc.service.AmcOssFileService;
import com.wensheng.zcc.amc.service.ZccRulesService;
import com.wensheng.zcc.amc.utils.AmcBeanUtils;
import com.wensheng.zcc.amc.utils.AmcNumberUtils;
import com.wensheng.zcc.amc.utils.ExceptionUtils;
import com.wensheng.zcc.amc.utils.ExceptionUtils.AmcExceptions;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

/**
 * @author chenwei on 1/3/19
 * @project zcc-backend
 */
@RestController
@Slf4j
public class AmcDebtController {

  @Autowired
  AmcOssFileService amcOssFileService;

  @Autowired
  AmcDebtService amcDebtService;

  @Autowired
  AmcDebtpackService amcDebtpackService;


  @Autowired
  ZccRulesService zccRulesService;


//  @RequestMapping(value = "/api/amcid/{amcId}/debt/grntcontract/add", method = RequestMethod.POST)
//  @ResponseBody
//  public Long addGrntContract(@PathVariable(name = "amcId") Integer amcId,
//      @RequestBody BaseActionVo<AmcGrntctrct> grntctrctBaseActionVo) throws Exception {
//    checkGrantor(grntctrctBaseActionVo.getContent());
//
//    return amcDebtService.addGrantContract(grntctrctBaseActionVo.getContent());
//  }


//  @RequestMapping(value = "/api/amcid/{amcId}/debt/grntcontract/update", method = RequestMethod.POST)
//  @ResponseBody
//  public AmcGrntctrct updateGrntContract(@PathVariable(name = "amcId") Integer amcId,
//      @RequestBody BaseActionVo<AmcGrntctrct> grntctrctBaseActionVo) throws Exception {
//    checkGrantor(grntctrctBaseActionVo.getContent());
//
//    return amcDebtService.updateGrantContract(grntctrctBaseActionVo.getContent());
//  }


//  private void checkGrantor(AmcGrntctrct grntctrct) throws Exception {
//    if (amcDebtService.isDebtIdExist(grntctrct.getDebtId())) {
//      throw ExceptionUtils.getAmcException(AmcExceptions.NO_AMCDEBT_AVAILABLE, "" + grntctrct
//          .getDebtId());
//    }
//    if (!amcDebtService.isGrntIdExist(grntctrct.getGrantorId(),
//        grntctrct.getType())) {
//      throw ExceptionUtils.getAmcException(AmcExceptions.NO_AMCGRANTOR_AVAILABLE, String.format("grantId:%d, "
//          + "grantorType:%d", grntctrct.getGrantorId(), grntctrct.getType()));
//    }
//  }

  @RequestMapping(value = "/api/amcid/{amcId}/debt/debtor/add", method = RequestMethod.POST)
  @ResponseBody
  public AmcDebtor addAmcDebtor(@PathVariable(name = "amcId") Integer amcId,
      @RequestBody BaseActionVo<AmcDebtor> amcDebtorBaseActionVo) throws Exception {

    return amcDebtService.create(amcDebtorBaseActionVo.getContent());
  }

  @RequestMapping(value = "/api/amcid/{amcId}/debt/debtors-unasigned", method = RequestMethod.POST)
  @ResponseBody
  public Page<AmcDebtor> getAmcUnasignedDebtors(@RequestBody PageInfo pageable,
      @RequestParam DebtorTypeEnum typeEnum) throws Exception {

    Map<String, Sort.Direction> orderByParam = PageReqRepHelper.getOrderParam(pageable);
    if (CollectionUtils.isEmpty(orderByParam)) {
      orderByParam.put("id", Direction.DESC);
    }

    List<AmcDebtor> queryResults;
    int offset = PageReqRepHelper.getOffset(pageable);
    try {

      queryResults = amcDebtService.getAllUnasignedDebtors(Long.valueOf(offset), pageable.getSize(), typeEnum.getId(),
          orderByParam);
    } catch (Exception ex) {
      log.error("got error when query:" + ex.getMessage());
      throw ex;
    }
    Long totalCount = amcDebtService.getDebtorCount();

    Page<AmcDebtor> page = PageReqRepHelper.getPageResp(totalCount, queryResults, pageable);

    return page;
  }


  @RequestMapping(value = "/api/amcid/{amcId}/debt/debtor/update", method = RequestMethod.POST)
  @ResponseBody
  public AmcDebtor updateAmcDebtor(@PathVariable(name = "amcId") Integer amcId,
      @RequestBody BaseActionVo<AmcDebtor> amcDebtorBaseActionVo) throws Exception {

    return amcDebtService.update(amcDebtorBaseActionVo.getContent());
  }

  @RequestMapping(value = "/api/amcid/{amcId}/debt/company/add", method = RequestMethod.POST)
  @ResponseBody
  public AmcCmpy addAmcCompany(@PathVariable(name = "amcId") Integer amcId,
      @RequestBody BaseActionVo<AmcCmpy> amcCmpyBaseActionVo) throws Exception {

    return amcDebtService.create(amcCmpyBaseActionVo.getContent());
  }


  @RequestMapping(value = "/api/amcid/{amcId}/debt/company/update", method = RequestMethod.POST)
  @ResponseBody
  public AmcCmpy updateAmcCompany(@PathVariable(name = "amcId") Integer amcId,
      @RequestBody BaseActionVo<AmcCmpy> amcCmpyBaseActionVo) throws Exception {

    return amcDebtService.create(amcCmpyBaseActionVo.getContent());
  }

  @RequestMapping(value = "/api/amcid/{amcId}/debt/companies", method = RequestMethod.POST)
  @ResponseBody
  public Page<AmcCmpy> getAmcCompanies(@RequestBody PageInfo pageable) throws Exception {

    Map<String, Sort.Direction> orderByParam = PageReqRepHelper.getOrderParam(pageable);
    if (CollectionUtils.isEmpty(orderByParam)) {
      orderByParam.put("id", Direction.DESC);
    }

    List<AmcCmpy> queryResults;
    int offset = PageReqRepHelper.getOffset(pageable);
    try {
      queryResults = amcDebtService.getAllCompanies(Long.valueOf(offset), pageable.getSize(), orderByParam);
    } catch (Exception ex) {
      log.error("got error when query:" + ex.getMessage());
      throw ex;
    }
    Long totalCount = amcDebtService.getTotalCompanyCount();

    Page<AmcCmpy> page = PageReqRepHelper.getPageResp(totalCount, queryResults, pageable);

    return page;
  }

  @LogExecutionTime
  @RequestMapping(value = "/api/amcid/{amcId}/debt/image/add", headers = "Content-Type= multipart/form-data",method =
      RequestMethod.POST)
  @ResponseBody
  public List<DebtImage> uploadDebtImage(@PathVariable(name = "amcId") Integer amcId,
      @RequestParam("debtId") Long debtId,
      @RequestParam("desc") String desc,
      @RequestParam("images") MultipartFile[] uploadingImages) throws Exception {
    if (debtId == null || debtId < 0) {
      throw ExceptionUtils.getAmcException(AmcExceptions.MISSING_MUST_PARAM, String.format("debtId %s is not valid",
          debtId));
    }

//    MultipartFile[] uploadingImages = debtImageBaseActionVo.getContent().getMultipartFiles();
    List<String> filePaths = new ArrayList<>();
    for (MultipartFile uploadingImage : uploadingImages) {
      try {
        String filePath = amcOssFileService
            .handleMultiPartFile(uploadingImage, debtId, ImagePathClassEnum.DEBT.getName());
        filePaths.add(filePath);
      } catch (Exception e) {
        e.printStackTrace();
        throw new ResponseStatusException(HttpStatus.MULTI_STATUS, e.getStackTrace().toString());
      }
    }
    String prePath = ImagePathClassEnum.DEBT.getName() + "/" + debtId + "/";
    List<DebtImage> debtImages = new ArrayList<>();
    String ossPath;
    for (String filePath : filePaths) {
      try {
        ossPath = amcOssFileService.handleFile2Oss(filePath, prePath);

      } catch (Exception e) {
        e.printStackTrace();
        throw e;
      }
      try{
        debtImages.add(amcDebtService.saveImageInfo(ossPath, filePath, debtId, desc,
            ImageClassEnum.MAIN));
      }catch (Exception e) {
        e.printStackTrace();
        if(e.getMessage().contains("duplicate")){
          throw ExceptionUtils.getAmcException(AmcExceptions.DUPLICATE_IMAGE_ERROR, ossPath);
        }
        throw e;
      }

    }
    log.info("begin handle upload files 3");


    return debtImages;

  }

  @RequestMapping(value = "/api/amcid/{amcid}/debt/image/del", method = RequestMethod.POST)
  @ResponseBody
  @Transactional
  public void delAmcDebtImage(@RequestBody BaseActionVo<DebtImage> debtImageBaseActionVo) throws Exception {
    amcOssFileService.delFileInOss(debtImageBaseActionVo.getContent().getOssPath());
    amcDebtService.delImage(debtImageBaseActionVo.getContent());
  }

  @RequestMapping(value = "/api/amcid/{amcid}/debt/del", method = RequestMethod.POST)
  @ResponseBody
  public void delAmcDebt(@RequestBody BaseActionVo<Long> debtIdBaseActionVo) throws Exception {
    amcDebtService.del(debtIdBaseActionVo.getContent());
  }

  @RequestMapping(value = "/api/amcid/{id}/debt/create", method = RequestMethod.POST)
  @ResponseBody
  public String createDebt(@RequestBody BaseActionVo<AmcDebtCreateVo> baseCreateVo) throws Exception {

    AmcDebt amcDebt = new AmcDebt();
    AmcDebtCreateVo createVo = baseCreateVo.getContent();
    AmcBeanUtils.copyProperties(createVo, amcDebt);

    //1. check deptpackId exist
    if (createVo.getDebtpackId() == null || createVo.getDebtpackId() < 0) {
      throw ExceptionUtils.getAmcException(AmcExceptions.NO_AMCDEBTPACK_AVAILABLE);
    }
    boolean isExist = amcDebtpackService.exist(createVo.getDebtpackId());
    if (!isExist) {
      throw ExceptionUtils.getAmcException(AmcExceptions.NO_AMCDEBTPACK_AVAILABLE);
    }

    //2. check contact person exist
    if (createVo.getAmcContactorId() == null || createVo.getAmcContactorId() < 0) {
      throw ExceptionUtils.getAmcException(AmcExceptions.NO_AMCGRANTOR_AVAILABLE);
    }
    boolean isAmcContactExist = amcDebtService.isAmcContactexist(createVo.getAmcContactorId());
    if (!isAmcContactExist) {
      throw ExceptionUtils.getAmcException(AmcExceptions.NO_AMCCONTACT_AVAILABLE, String.format("no amc person for "
          + "id:%d", createVo.getAmcContactorId()));
    } else {
      amcDebt.setAmcContactorId(createVo.getAmcContactorId());
    }

    if (createVo.getAmcContactor2Id() == null || createVo.getAmcContactor2Id() < 0) {
      log.info("no amc contactor2");
    }else{
      isAmcContactExist = amcDebtService.isAmcContactexist(createVo.getAmcContactor2Id());
      if (!isAmcContactExist) {
        throw ExceptionUtils.getAmcException(AmcExceptions.NO_AMCCONTACT_AVAILABLE, String.format("no amc person for "
            + "id:%d", createVo.getAmcContactor2Id()));
      } else {
        amcDebt.setAmcContactor2Id(createVo.getAmcContactor2Id());
      }
    }
    if(createVo.getTotalAmount() != null){
      amcDebt.setTotalAmount(AmcNumberUtils.getLongFromDecimalWithMult100(createVo.getTotalAmount()));
    }
    if(createVo.getBaseAmount() != null){
      amcDebt.setBaseAmount(AmcNumberUtils.getLongFromDecimalWithMult100(createVo.getBaseAmount()));
    }
    if(createVo.getValuation() != null){
      amcDebt.setValuation(AmcNumberUtils.getLongFromDecimalWithMult100(createVo.getValuation()));
    }

    //3. create the debt
      AmcDebtVo amcDebtVo = amcDebtService.create(amcDebt);

    //4. make relationship between debtors with debt
    if (!CollectionUtils.isEmpty(createVo.getDebtors())) {
      amcDebtService.connDebt2Debtors(createVo.getDebtors(), amcDebtVo.getId());
    }
    if(createVo.getDebtAdditional() != null && createVo.getDebtAdditional().getDesc() != null){
      amcDebtService.saveDebtDesc(createVo.getDebtAdditional().getDesc(), amcDebtVo.getId());
    }

    return "succed";
  }


  @RequestMapping(value = "/api/amcid/{id}/debt/get", method = RequestMethod.POST)
  @ResponseBody
  public AmcDebtExtVo queryDebt(@RequestParam("debtId") Long debtId)
      throws Exception {


    AmcDebtExtVo amcDebtExtVo = amcDebtService.get(debtId);

    try {
      AmcInfo amcInfo = amcDebtService.getAmcInfo(debtId);

      List<AmcDebtor> amcDebtors = amcDebtService.getDebtors(debtId);


      AmcOrigCreditor origCreditor = amcDebtService.getOriginCreditor(debtId);
      amcDebtExtVo.setAmcInfos(amcInfo);
      amcDebtExtVo.setAmcDebtors(amcDebtors);
      amcDebtExtVo.setOrigCreditor(origCreditor);

    } catch (Exception ex) {
      log.error("failed to get creditor or grantor", ex);
    }

    return amcDebtExtVo;
  }

  @RequestMapping(value = "/api/amcid/{id}/debt/update", method = RequestMethod.POST)
  @ResponseBody
  public AmcDebtCreateVo updateDebt(@RequestBody AmcDebtCreateVo amcDebtVo)
      throws Exception {

    try {

      AmcDebt amcDebt = new AmcDebt();
      AmcBeanUtils.copyProperties(amcDebtVo, amcDebt);
      log.info("amcDebtVo.getBaseAmount()"+ amcDebtVo.getBaseAmount());
      if( null != amcDebtVo.getBaseAmount()){
        amcDebt.setBaseAmount(AmcNumberUtils.getLongFromDecimalWithMult100(amcDebtVo.getBaseAmount()));

      }else{
        log.error("baseAmount empty"+amcDebtVo.getBaseAmount());
      }
      if(null != amcDebtVo.getValuation()){
        amcDebt.setValuation(
            AmcNumberUtils.getLongFromDecimalWithMult100(amcDebtVo.getValuation()));
      }else{
        log.error("amcDebtVo.getValuation()"+ amcDebtVo.getValuation());
      }
      if(null != amcDebtVo.getTotalAmount()){
        amcDebt
            .setTotalAmount(AmcNumberUtils.getLongFromDecimalWithMult100(amcDebtVo.getTotalAmount()));
      }else{
        log.error("amcDebtVo.getTotalAmount()"+ amcDebtVo.getTotalAmount());
      }

      amcDebtService.update(amcDebt);
      if (!CollectionUtils.isEmpty(amcDebtVo.getDebtors())) {
        amcDebtService.connDebt2Debtors(amcDebtVo.getDebtors(), amcDebtVo.getId());
      }
      if(amcDebtVo.getDebtAdditional() != null  && amcDebtVo.getDebtAdditional().getDesc() != null && !StringUtils.isEmpty(amcDebtVo.getDebtAdditional().getDesc())){
        amcDebtService.saveDebtDesc(amcDebtVo.getDebtAdditional().getDesc(), amcDebtVo.getId());
      }
    } catch (Exception ex) {
      log.error("failed to update debt", ex);
    }

    return amcDebtVo;
  }

  @RequestMapping(value = "/api/amcid/{id}/debts", method = RequestMethod.POST)
  @ResponseBody
  public Page<AmcDebtVo> queryDebts(@RequestBody PageInfo pageable)
      throws Exception {
    Map<String, Sort.Direction> orderByParam = PageReqRepHelper.getOrderParam(pageable);
    if (CollectionUtils.isEmpty(orderByParam)) {
      orderByParam.put("id", Direction.DESC);
    }

    List<AmcDebtVo> queryResults;
    int offset = PageReqRepHelper.getOffset(pageable);
    try {
      queryResults = amcDebtService.queryAllDebt(Long.valueOf(offset), pageable.getSize(), orderByParam);
    } catch (Exception ex) {
      log.error("got error when query:" + ex.getMessage());
      throw ex;
    }
    Long totalCount = amcDebtService.getTotalCount();

    Page<AmcDebtVo> page = PageReqRepHelper.getPageResp(totalCount, queryResults, pageable);

    return page;
  }


  @RequestMapping(value = "/api/amcid/{id}/debt/editAble", method = RequestMethod.POST)
  @ResponseBody
  public Boolean editAble(@RequestParam("debtStatus") Integer debtStatus) {

    return zccRulesService.editAble(PublishStateEnum.lookupByDisplayStatusUtil(debtStatus));

  }

  @RequestMapping(value = "/api/amcid/{amcId}/debt/origcreditor/add", method = RequestMethod.POST)
  @ResponseBody
  public AmcOrigCreditor createAmcCreditor(@RequestBody AmcOrigCreditor amcOrigCreditor) throws Exception {
    if (StringUtils.isEmpty(amcOrigCreditor.getBankName())) {
      throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_ORIG_CREDITOR, "bankname is empty");
    }

    AmcOrigCreditor amcOrigCreditorResult;
    amcOrigCreditorResult = amcDebtService.createOrigCreditor(amcOrigCreditor);
    return amcOrigCreditorResult;
  }

  @RequestMapping(value = "/api/amcid/{amcId}/debt/origcreditors", method = RequestMethod.POST)
  @ResponseBody
  public Page<AmcOrigCreditor> getAmcCreditor(@RequestBody PageInfo pageable) throws Exception {

    Map<String, Sort.Direction> orderByParam = PageReqRepHelper.getOrderParam(pageable);
    if (CollectionUtils.isEmpty(orderByParam)) {
      orderByParam.put("id", Direction.DESC);
    }

    int offset = PageReqRepHelper.getOffset(pageable);
    List<AmcOrigCreditor> amcOrigCreditors;
    try {
      amcOrigCreditors = amcDebtService.getAllOrigCreditors(offset, pageable.getSize(), orderByParam);

    } catch (Exception ex) {
      log.error("got error when query:" + ex.getMessage());
      throw ex;
    }
    Long totalCount = amcDebtService.getCreditorsCount();
    Page<AmcOrigCreditor> amcOrigCreditorPage = PageReqRepHelper.getPageResp(totalCount, amcOrigCreditors, pageable);

    return amcOrigCreditorPage;
  }

  @RequestMapping(value = "/api/amcid/{amcId}/debt/allTitles", method = RequestMethod.POST)
  @ResponseBody
  public Map<String, List<Long>> getAllDebtTitles() throws Exception {

    return amcDebtService.getAllTitles();

  }
}
