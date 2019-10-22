package com.wensheng.zcc.amc.controller;

import com.wensheng.zcc.amc.aop.EditActionChecker;
import com.wensheng.zcc.amc.aop.LogExecutionTime;
import com.wensheng.zcc.amc.aop.QueryChecker;
import com.wensheng.zcc.amc.module.vo.AmcDebtUploadImg2WXRlt;
import com.wensheng.zcc.common.params.AmcPage;
import com.wensheng.zcc.common.params.PageInfo;
import com.wensheng.zcc.common.params.PageReqRepHelper;
import com.wensheng.zcc.amc.controller.helper.QueryParam;
import com.wensheng.zcc.amc.module.dao.helper.*;
import com.wensheng.zcc.amc.module.dao.mongo.entity.AmcOperLog;
import com.wensheng.zcc.amc.module.dao.mongo.entity.DebtImage;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcCmpy;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebt;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcInfo;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcOrigCreditor;
import com.wensheng.zcc.amc.module.vo.AmcDebtCreateVo;
import com.wensheng.zcc.amc.module.vo.AmcDebtExtVo;
import com.wensheng.zcc.amc.module.vo.AmcDebtSummary;
import com.wensheng.zcc.amc.module.vo.AmcDebtVo;
import com.wensheng.zcc.amc.module.vo.base.BaseActionVo;
import com.wensheng.zcc.amc.service.AmcDebtService;
import com.wensheng.zcc.amc.service.AmcDebtpackService;
import com.wensheng.zcc.amc.service.AmcOssFileService;
import com.wensheng.zcc.amc.service.ZccRulesService;
import com.wensheng.zcc.amc.utils.SQLUtils;
import com.wensheng.zcc.common.utils.AmcBeanUtils;
import com.wensheng.zcc.common.utils.AmcNumberUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils.AmcExceptions;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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


//  @RequestMapping(value = "/api/amcid/{amcId}/debt/debtor/add", method = RequestMethod.POST)
//  @ResponseBody
//  public AmcDebtor addAmcDebtor(@PathVariable(name = "amcId") Integer amcId,
//      @RequestBody BaseActionVo<AmcDebtor> amcDebtorBaseActionVo) throws Exception {
//
//    return amcDebtService.create(amcDebtorBaseActionVo.getContent());
//  }

  @RequestMapping(value = "/api/amcid/{amcId}/debt/debtors-unasigned", method = RequestMethod.POST)
  @ResponseBody
  public AmcPage<AmcDebtor> getAmcUnasignedDebtors(@RequestBody PageInfo pageable,
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
    return PageReqRepHelper.getAmcPage(queryResults, totalCount);
//
//    Page<AmcDebtor> page = PageReqRepHelper.getPageResp(totalCount, queryResults, pageable);

  }


//  @RequestMapping(value = "/api/amcid/{amcId}/debt/debtor/update", method = RequestMethod.POST)
//  @ResponseBody
//  public AmcDebtor updateAmcDebtor(@PathVariable(name = "amcId") Integer amcId,
//      @RequestBody BaseActionVo<AmcDebtor> amcDebtorBaseActionVo) throws Exception {
//
//    return amcDebtService.update(amcDebtorBaseActionVo.getContent());
//  }

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

    return amcDebtService.update(amcCmpyBaseActionVo.getContent());
  }

  @RequestMapping(value = "/api/amcid/{amcId}/debt/companies", method = RequestMethod.POST)
  @ResponseBody
  public AmcPage<AmcCmpy> getAmcCompanies(@RequestBody PageInfo pageable) throws Exception {

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
    return PageReqRepHelper.getAmcPage(queryResults, totalCount);
//    Page<AmcCmpy> page = PageReqRepHelper.getPageResp(totalCount, queryResults, pageable);

//    return queryResults;
  }

  @EditActionChecker
  @LogExecutionTime
  @PreAuthorize("hasAnyRole('SYSTEM_ADMIN','CO_ADMIN') or hasPermission(#amcid, 'PERM_DEBTASSET_MOD') or hasPermission"
      + "(#amcid, 'PERM_AMC_CRUD')")
  @RequestMapping(value = "/api/amcid/{amcid}/debt/image/add", headers = "Content-Type= multipart/form-data",method =
      RequestMethod.POST)
  @ResponseBody
  public List<DebtImage> uploadDebtImage(@PathVariable Long amcid,
      @RequestParam("debtId") Long debtId,
      @RequestParam("desc") String desc,
      @RequestParam("images") MultipartFile uploadingImage) throws Exception {
    if (debtId == null || debtId < 0) {
      throw ExceptionUtils.getAmcException(AmcExceptions.MISSING_MUST_PARAM, String.format("debtId %s is not valid",
          debtId));
    }

//    MultipartFile[] uploadingImages = debtImageBaseActionVo.getContent().getMultipartFiles();
    List<String> filePaths = new ArrayList<>();

      try {
        String filePath = amcOssFileService
            .handleMultiPartFile(uploadingImage, debtId, ImagePathClassEnum.DEBT.getName());
        filePaths.add(filePath);
      } catch (Exception e) {
        e.printStackTrace();
        throw new ResponseStatusException(HttpStatus.MULTI_STATUS, e.getStackTrace().toString());
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



    return debtImages;

  }
  @PreAuthorize("hasAnyRole('SYSTEM_ADMIN','CO_ADMIN')  or hasPermission(#amcid, 'PERM_AMC_CRUD') or hasPermission(#amcid, 'PERM_AMC_CRUD')")
  @RequestMapping(value = "/api/amcid/{amcid}/debt/image/del", method = RequestMethod.POST)
  @ResponseBody
  @Transactional
  public void delAmcDebtImage(@RequestBody BaseActionVo<DebtImage> debtImageBaseActionVo, @PathVariable Long amcid) throws Exception {
    amcOssFileService.delFileInOss(debtImageBaseActionVo.getContent().getOssPath());
    amcDebtService.delImage(debtImageBaseActionVo.getContent());
  }

  @EditActionChecker
  @PreAuthorize("hasAnyRole('SYSTEM_ADMIN','CO_ADMIN') ")
  @RequestMapping(value = "/api/amcid/{amcid}/debt/del", method = RequestMethod.POST)
  @ResponseBody
  public void delAmcDebt(@RequestBody BaseActionVo<Long> debtIdBaseActionVo) throws Exception {
    amcDebtService.del(debtIdBaseActionVo.getContent());
  }

  @EditActionChecker
  @RequestMapping(value = "/api/amcid/{id}/debt/create", method = RequestMethod.POST)
  @PreAuthorize("hasAnyRole('SYSTEM_ADMIN','CO_ADMIN') or hasPermission(#amcid, 'PERM_DEBTASSET_MOD') or hasPermission(#amcid, 'PERM_AMC_CRUD')")
  @ResponseBody
  public AmcDebtVo createDebt(@RequestBody BaseActionVo<AmcDebtCreateVo> baseCreateVo, @PathVariable Long id) throws Exception {

    AmcDebt amcDebt = new AmcDebt();
    AmcDebtCreateVo createVo = baseCreateVo.getContent();
    AmcBeanUtils.copyProperties(createVo, amcDebt);

    //1. check deptpackId exist
    if (createVo.getDebtpackId() == null || createVo.getDebtpackId() < 0) {
//      throw ExceptionUtils.getAmcException(AmcExceptions.NO_AMCDEBTPACK_AVAILABLE);
      log.error("there is no debtpack info for create the debt:" + createVo.getTitle());
    }
    boolean isExist = amcDebtpackService.exist(createVo.getDebtpackId());
    if (!isExist) {
//      throw ExceptionUtils.getAmcException(AmcExceptions.NO_AMCDEBTPACK_AVAILABLE);
      log.error("deptpackId:"+createVo.getDebtpackId()+" not exist");
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
    amcDebt.setPublishState(PublishStateEnum.DRAFT.getStatus());
      AmcDebtVo amcDebtVo = amcDebtService.create(amcDebt);

    //4. make relationship between debtors with debt
    handleDebtors(createVo, amcDebtVo.getId());


    if(createVo.getDebtAdditional() != null && createVo.getDebtAdditional().getDesc() != null){
      amcDebtService.saveDebtDesc(createVo.getDebtAdditional().getDesc(), amcDebtVo.getId());
    }

    return amcDebtVo;
  }

  @RequestMapping(value = "/api/amcid/{id}/debt/getIds", method = RequestMethod.POST)
  @ResponseBody
  public List<AmcDebtExtVo> queryDebtByIds(@RequestParam("debtId") List<Long> debtIds)
      throws Exception {

    if(CollectionUtils.isEmpty(debtIds)){
      throw ExceptionUtils.getAmcException(AmcExceptions.MISSING_MUST_PARAM, String.format("debtIds is empty"));
    }
    List<AmcDebtExtVo>  amcDebtExtVoes = amcDebtService.getByIds(debtIds);



    return amcDebtExtVoes;
  }


  @RequestMapping(value = "/api/amcid/{id}/debt/get", method = RequestMethod.POST)
  @ResponseBody
  public AmcDebtExtVo queryDebt(@RequestParam("debtId") Long debtId)
      throws Exception {


    AmcDebtExtVo amcDebtExtVo = amcDebtService.get(debtId);



    return amcDebtExtVo;
  }

  @PreAuthorize("hasRole('SYSTEM_ADMIN') or hasPermission(#id, 'PERM_DEBTASSET_MOD')")
  @EditActionChecker
  @RequestMapping(value = "/api/amcid/{id}/debt/update", method = RequestMethod.POST)
  @ResponseBody
  public AmcDebtCreateVo updateDebt(@RequestBody BaseActionVo<AmcDebtCreateVo> amcDebtUpdateAct, @PathVariable Long id)
      throws Exception {
    AmcDebtCreateVo amcDebtVo = amcDebtUpdateAct.getContent();

    try {

      AmcDebt amcDebt = new AmcDebt();
      initDebt(amcDebtVo, amcDebt);


      amcDebtService.update(amcDebt);
      handleDebtors(amcDebtVo, amcDebtVo.getId());
//      amcDebtService.saveOperLog(amcDebtUpdateAct,"");
      if(amcDebtVo.getDebtAdditional() != null  && amcDebtVo.getDebtAdditional().getDesc() != null && !StringUtils.isEmpty(amcDebtVo.getDebtAdditional().getDesc())){
        amcDebtService.saveDebtDesc(amcDebtVo.getDebtAdditional().getDesc(), amcDebtVo.getId());
      }

    } catch (Exception ex) {
      log.error("failed to update debt", ex);
      throw ex;
    }

    return amcDebtVo;
  }

  @PreAuthorize("hasRole('SYSTEM_ADMIN') or hasPermission(#id, 'PERM_DEBT_RECOM')")
  @RequestMapping(value = "/api/amcid/{id}/debt/recommDebt", method = RequestMethod.POST)
  @ResponseBody
  public void recommDebt(@RequestBody BaseActionVo<List<Long>> amcDebtUpdateRecommdAct, @PathVariable Long id, @RequestParam IsRecommandEnum isRecommandEnum)
          throws Exception {



      if(CollectionUtils.isEmpty(amcDebtUpdateRecommdAct.getContent())){
        return;
      }

      amcDebtService.setRecomm(amcDebtUpdateRecommdAct.getContent(), isRecommandEnum.getId());




  }



  private void initDebt(AmcDebtCreateVo amcDebtVo, AmcDebt amcDebt){
    AmcBeanUtils.copyProperties(amcDebtVo, amcDebt);
    AmcBeanUtils.fillNullObjects(amcDebt);
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
  }



  private void handleDebtors(AmcDebtCreateVo amcDebtCreateVo, Long amcDebtId){
    if (!CollectionUtils.isEmpty(amcDebtCreateVo.getDebtors())) {
      amcDebtService.connDebt2Debtors(amcDebtCreateVo.getDebtors(), amcDebtId);
    }

    if(!CollectionUtils.isEmpty(amcDebtCreateVo.getNewCompanies())){
      amcDebtService.connDebt2Cmpys(amcDebtCreateVo.getNewCompanies(), amcDebtId);
    }

    if(!CollectionUtils.isEmpty(amcDebtCreateVo.getNewPersons())){
      amcDebtService.connDebt2Persons(amcDebtCreateVo.getNewPersons(), amcDebtId);
    }
  }
  @PreAuthorize("hasAnyRole('SYSTEM_ADMIN','CO_ADMIN') or hasPermission(#id, 'PERM_DEBTASSET_MOD')")
  @EditActionChecker
  @RequestMapping(value = "/api/amcid/{id}/debt/updateState", method = RequestMethod.POST)
  @ResponseBody
  public AmcDebtVo updateDebtState(@RequestBody BaseActionVo<AmcDebtVo> debtVoBaseActionVo , @PathVariable Long id)
      throws Exception {

    AmcDebt amcDebt = new AmcDebt();
    if(debtVoBaseActionVo.getEditActionId() == EditActionEnum.ACT_REVIEW_FAIL.getId()){
      throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_ACTION, "Cannot do review fail oper in"
          + " updateDebtState inteerface");
    }
    amcDebt.setPublishState(debtVoBaseActionVo.getContent().getPublishState());
    amcDebt.setId(debtVoBaseActionVo.getContent().getId());
    amcDebt.setUpdateBy(debtVoBaseActionVo.getContent().getUpdateBy());
//    amcDebtService.saveOperLog(debtVoBaseActionVo,"");
    return amcDebtService.updatePublishState(amcDebt);
  }


  @PreAuthorize("hasAnyRole('SYSTEM_ADMIN','CO_ADMIN')")
  @EditActionChecker
  @RequestMapping(value = "/api/amcid/{id}/debt/reviewDebt", method = RequestMethod.POST)
  @ResponseBody
  public String reviewDebt(@RequestBody BaseActionVo<AmcDebtVo> debtVoBaseActionVo ,
      @RequestParam(required = false) String reviewComment,  @PathVariable Long id)
      throws Exception {
    AmcDebt amcDebt = new AmcDebt();
    amcDebt.setPublishState(debtVoBaseActionVo.getContent().getPublishState());
    amcDebt.setId(debtVoBaseActionVo.getContent().getId());
    amcDebt.setUpdateBy(debtVoBaseActionVo.getContent().getUpdateBy());
    amcDebtService.updatePublishState(amcDebt);
//    amcDebtService.saveOperLog(debtVoBaseActionVo, reviewComment);
    return "success";

  }


  @PreAuthorize("hasAnyRole('SYSTEM_ADMIN','CO_ADMIN')")
  @EditActionChecker
  @RequestMapping(value = "/api/amcid/{id}/debt/reviewBatchDebt", method = RequestMethod.POST)
  @ResponseBody
  public String reviewBatchDebt(@RequestBody BaseActionVo<List<AmcDebtVo>> debtVoBaseActionVo ,
      @RequestParam(required = false) String reviewComment,  @PathVariable Long id)
      throws Exception {
    AmcDebt amcDebt = new AmcDebt();
    if(CollectionUtils.isEmpty(debtVoBaseActionVo.getContent())){
      throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_ACTION,"got empty list for amcDebtVo");
    }
    for(AmcDebtVo amcDebtVo: debtVoBaseActionVo.getContent()){
      amcDebt.setPublishState(amcDebtVo.getPublishState());
      amcDebt.setId(amcDebtVo.getId());
      amcDebt.setUpdateBy(amcDebtVo.getUpdateBy());
      amcDebtService.updatePublishState(amcDebt);

    }
//    amcDebtService.saveOperLog(debtVoBaseActionVo, reviewComment);
    return "success";

  }


  @RequestMapping(value = "/api/amcid/{id}/debt/oplog/get", method = RequestMethod.POST)
  @ResponseBody
  public List<AmcOperLog> getOperLog(@RequestParam Long debtId , @PathVariable Long id,
      @RequestParam(required = false, defaultValue = "-1") Integer actionId) throws Exception {


    return amcDebtService.getOperLog(debtId, actionId);

  }

  @RequestMapping(value = "/api/amcid/{id}/debts/total", method = RequestMethod.POST)
  @ResponseBody
  public AmcDebtSummary queryDebtsSummary()
      throws Exception {

    AmcDebtSummary amcDebtSummary = amcDebtService.getSummaryInfo();
    return amcDebtSummary;
  }


  @PreAuthorize("hasAnyRole('SYSTEM_ADMIN','CO_ADMIN')")
  @RequestMapping(value = "/api/amcid/{id}/debtsOfCurrUser", method = RequestMethod.POST)
  @ResponseBody
  @LogExecutionTime
  public List<Long> queryDebtsOfUser( @RequestParam  Long userId)
      throws Exception {



    List<AmcDebt>  queryResults = amcDebtService.queryAllByUserId(userId);
    if(CollectionUtils.isEmpty(queryResults)){
      return new ArrayList<>();
    }
    return queryResults.stream().map(item -> item.getId()).collect(Collectors.toList());

  }



  @RequestMapping(value = "/api/amcid/{id}/debts", method = RequestMethod.POST)
  @ResponseBody
  @QueryChecker
  @LogExecutionTime
  public AmcPage<AmcDebtVo> queryDebts( @RequestBody  QueryParam queryParam)
      throws Exception {
    Map<String, Sort.Direction> orderByParam = PageReqRepHelper.getOrderParam(queryParam.getPageInfo());
    if (CollectionUtils.isEmpty(orderByParam)) {
      orderByParam.put("id", Direction.DESC);
    }
    Map<String, Object> queryParamMap =  SQLUtils.getQueryParams(queryParam);

    List<AmcDebtVo> queryResults;
    int offset = PageReqRepHelper.getOffset(queryParam.getPageInfo());
    try {
      queryResults = amcDebtService.queryAllExt(Long.valueOf(offset), queryParam.getPageInfo().getSize(), orderByParam,
          queryParamMap);
    } catch (Exception ex) {
      log.error("got error when query:" + ex.getMessage());
      throw ex;
    }
    Long totalCount = amcDebtService.getTotalCount(queryParamMap);
    return PageReqRepHelper.getAmcPage(queryResults, totalCount);
//
//    Page<AmcDebtVo> page = PageReqRepHelper.getPageResp(totalCount, queryResults, pageable);

//    return queryResults;
  }




  @RequestMapping(value = "/api/amcid/{id}/debt/stateEditAble", method = RequestMethod.POST)
  @ResponseBody
  public PublishStateEnum editAble(@RequestParam("debtStatus") Integer currentDebtStatus,
      @RequestParam("actionId") Integer actionId) throws Exception {

    return zccRulesService.runActionAndStatus(EditActionEnum.lookupByDisplayIdUtil(actionId),
        PublishStateEnum.lookupByDisplayStatusUtil(currentDebtStatus));

  }


  @RequestMapping(value = "/api/amcid/{id}/debt/editAble", method = RequestMethod.POST)
  @ResponseBody
  public PublishStateEnum editAble(@RequestParam("debtId") Long debtId,
      @RequestParam("actionId") Integer actionId) throws Exception {


    return zccRulesService.runActionAndStatus(EditActionEnum.lookupByDisplayIdUtil(actionId),
        PublishStateEnum.lookupByDisplayStatusUtil(amcDebtService.getDebt(debtId).getPublishState()));

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
  public AmcPage<AmcOrigCreditor> getAmcCreditor(@RequestBody PageInfo pageable) throws Exception {

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
//    Page<AmcOrigCreditor> amcOrigCreditorPage = PageReqRepHelper.getPageResp(totalCount, amcOrigCreditors, pageable);
    return PageReqRepHelper.getAmcPage(amcOrigCreditors, totalCount);
//    return amcOrigCreditors;
  }

  @RequestMapping(value = "/api/amcid/{amcId}/debt/allTitles", method = RequestMethod.POST)
  @ResponseBody
  public Map<String, List<Long>> getAllDebtTitles() throws Exception {

    return amcDebtService.getAllTitles();

  }

  @RequestMapping(value = "/api/amcid/{amcId}/debt/upload2wx", method = RequestMethod.POST)
  @ResponseBody
  public List<AmcDebtUploadImg2WXRlt> upload2Wx(@RequestBody List<Long> debtIds) throws Exception {

    return amcDebtService.uploadAmcDebtImage2WechatByIds(debtIds);

  }
}
