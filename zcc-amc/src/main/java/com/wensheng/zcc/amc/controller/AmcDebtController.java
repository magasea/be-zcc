package com.wensheng.zcc.amc.controller;

import com.wensheng.zcc.amc.aop.LogExecutionTime;
import com.wensheng.zcc.amc.controller.helper.PageInfo;
import com.wensheng.zcc.amc.controller.helper.PageReqRepHelper;
import com.wensheng.zcc.amc.module.dao.helper.EditStatusEnum;
import com.wensheng.zcc.amc.module.dao.helper.ImagePathClassEnum;
import com.wensheng.zcc.amc.module.dao.mongo.entity.DebtImage;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcCmpy;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcCreditor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebt;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcGrntctrct;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcGrntor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcOrigCreditor;
import com.wensheng.zcc.amc.module.vo.AmcDebtCreateVo;
import com.wensheng.zcc.amc.module.vo.AmcDebtExtVo;
import com.wensheng.zcc.amc.module.vo.AmcDebtImageVo;
import com.wensheng.zcc.amc.module.vo.AmcDebtVo;
import com.wensheng.zcc.amc.module.vo.base.BaseActionVo;
import com.wensheng.zcc.amc.service.AmcDebtService;
import com.wensheng.zcc.amc.service.AmcDebtpackService;
import com.wensheng.zcc.amc.service.AmcOssFileService;
import com.wensheng.zcc.amc.service.ZccRulesService;
import com.wensheng.zcc.amc.utils.AmcNumberUtils;
import com.wensheng.zcc.amc.utils.ExceptionUtils;
import com.wensheng.zcc.amc.utils.ExceptionUtils.AmcExceptions;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
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


  @RequestMapping(value = "/api/amcid/{amcId}/debt/grntcontract/add", method = RequestMethod.POST)
  @ResponseBody
  public Long addGrntContract(@PathVariable(name = "amcId") Integer amcId,
      BaseActionVo<AmcGrntctrct> grntctrctBaseActionVo) throws Exception {
    checkGrantor(grntctrctBaseActionVo.getContent());

    return amcDebtService.addGrantContract(grntctrctBaseActionVo.getContent());
  }


  @RequestMapping(value = "/api/amcid/{amcId}/debt/grntcontract/update", method = RequestMethod.POST)
  @ResponseBody
  public AmcGrntctrct updateGrntContract(@PathVariable(name = "amcId") Integer amcId,
      BaseActionVo<AmcGrntctrct> grntctrctBaseActionVo) throws Exception {
    checkGrantor(grntctrctBaseActionVo.getContent());

    return amcDebtService.updateGrantContract(grntctrctBaseActionVo.getContent());
  }




  private void checkGrantor(AmcGrntctrct grntctrct) throws Exception {
    if(amcDebtService.isDebtIdExist(grntctrct.getDebtId())){
      throw ExceptionUtils.getAmcException(AmcExceptions.NO_AMCDEBT_AVAILABLE,""+grntctrct
          .getDebtId() );
    }
    if(!amcDebtService.isGrntIdExist(grntctrct.getGrantorId(),
        grntctrct.getType())){
      throw ExceptionUtils.getAmcException(AmcExceptions.NO_AMCGRANTOR_AVAILABLE, String.format("grantId:%d, "
          + "grantorType:%d",grntctrct.getGrantorId(), grntctrct.getType() ));
    }
  }

  @RequestMapping(value = "/api/amcid/{amcId}/debt/creditor/add", method = RequestMethod.POST)
  @ResponseBody
  public AmcCreditor addAmcCreditor(@PathVariable(name = "amcId") Integer amcId,
      BaseActionVo<AmcCreditor> amcCreditorBaseActionVo) throws Exception {


    return amcDebtService.create(amcCreditorBaseActionVo.getContent());
  }


  @RequestMapping(value = "/api/amcid/{amcId}/debt/creditor/update", method = RequestMethod.POST)
  @ResponseBody
  public AmcCreditor updateAmcCreditor(@PathVariable(name = "amcId") Integer amcId,
      BaseActionVo<AmcCreditor> amcCreditorBaseActionVo) throws Exception {

    return amcDebtService.update(amcCreditorBaseActionVo.getContent());
  }

  @RequestMapping(value = "/api/amcid/{amcId}/debt/company/add", method = RequestMethod.POST)
  @ResponseBody
  public AmcCmpy addAmcCompany(@PathVariable(name = "amcId") Integer amcId,
      BaseActionVo<AmcCmpy> amcCmpyBaseActionVo) throws Exception {


    return amcDebtService.create(amcCmpyBaseActionVo.getContent());
  }


  @RequestMapping(value = "/api/amcid/{amcId}/debt/company/update", method = RequestMethod.POST)
  @ResponseBody
  public AmcCmpy updateAmcCompany(@PathVariable(name = "amcId") Integer amcId,
      BaseActionVo<AmcCmpy> amcCmpyBaseActionVo) throws Exception {

    return amcDebtService.create(amcCmpyBaseActionVo.getContent());
  }

  @LogExecutionTime
  @RequestMapping(value = "/api/amcid/{amcId}/debt/image/add", method = RequestMethod.POST, consumes = {"multipart"
      + "/form-data"})
  @ResponseBody
  public String uploadDebtImage(@PathVariable(name = "amcId") Integer amcId,
      @RequestParam("debtId")  Long debtId, @RequestParam("tag")  Integer tag, @RequestParam("desc")  String desc,
      @RequestPart MultipartFile[] uploadingImages) throws Exception {


    if(debtId == null || debtId < 0){
      throw ExceptionUtils.getAmcException(AmcExceptions.MISSING_MUST_PARAM,  String.format("debtId %s is not valid",
          debtId));
    }

//    MultipartFile[] uploadingImages = debtImageBaseActionVo.getContent().getMultipartFiles();
    List<String> filePaths = new ArrayList<>();
    for(MultipartFile uploadedImage : uploadingImages) {
      try {
        String filePath = amcOssFileService.handleMultiPartFile(uploadedImage, debtId, ImagePathClassEnum.DEBT.getName());
        filePaths.add(filePath);
      } catch (Exception e) {
        e.printStackTrace();
        throw new ResponseStatusException(HttpStatus.MULTI_STATUS,e.getStackTrace().toString());
      }
    }
    String prePath = ImagePathClassEnum.DEBT.getName()+"/"+ debtId +"/";

    for(String filePath: filePaths){
      try {
        String ossPath =  amcOssFileService.handleFile2Oss(filePath, prePath);
        amcDebtService.saveImageInfo(ossPath, filePath, debtId, desc, tag);
      } catch (Exception e) {
        e.printStackTrace();
        throw new ResponseStatusException(HttpStatus.MULTI_STATUS,e.getStackTrace().toString());
      }

    }


    return "successed";

  }

  @RequestMapping(value = "/amcid/{amcid}/asset/image/del", method = RequestMethod.POST)
  @ResponseBody
  public void delAmcAssetImage(@RequestBody BaseActionVo<DebtImage> debtImageBaseActionVo ) throws Exception{
    amcOssFileService.delFileInOss(debtImageBaseActionVo.getContent().getOssPath());
    amcDebtService.delImage(debtImageBaseActionVo.getContent());
  }

  @RequestMapping(value = "/api/amcid/{id}/debt/create", method = RequestMethod.POST)
  @ResponseBody
  public String createDebt(@RequestBody BaseActionVo<AmcDebtCreateVo> baseCreateVo) throws Exception {

    AmcDebt amcDebt = new AmcDebt();
    AmcDebtCreateVo createVo = baseCreateVo.getContent();
    BeanUtils.copyProperties(createVo, amcDebt);

    //1. check deptpackId exist
    if(createVo.getDebtpackId() == null || createVo.getDebtpackId() < 0){
      throw ExceptionUtils.getAmcException(AmcExceptions.NO_AMCDEBTPACK_AVAILABLE);
    }
    boolean isExist = amcDebtpackService.exist(createVo.getDebtpackId());
    if( !isExist ){
      throw ExceptionUtils.getAmcException(AmcExceptions.NO_AMCDEBTPACK_AVAILABLE);
    }


    //2. check contact person exist
    if(createVo.getAmcContact1() == null || createVo.getAmcContact1() < 0){
      throw ExceptionUtils.getAmcException(AmcExceptions.NO_AMCGRANTOR_AVAILABLE);
    }
    boolean isAmcContactExist = amcDebtService.isAmcContactexist(createVo.getAmcContact1());
    if( !isAmcContactExist ){
      throw ExceptionUtils.getAmcException(AmcExceptions.NO_AMCCONTACT_AVAILABLE, String.format("no amc person for "
          + "id:%d", createVo.getAmcContact1()));
    }


    //3. create the debt
    AmcDebtVo amcDebtVo = amcDebtService.create(amcDebt);

    //4. make relationship between creditors with debt
    if(CollectionUtils.isEmpty(createVo.getCreditors())){
      amcDebtService.connDebt2Creditors(createVo.getCreditors(), amcDebtVo.getId());
    }

    return "succed";
  }


  @RequestMapping(value = "/api/amcid/{id}/debt/get", method = RequestMethod.POST)
  @ResponseBody
  public AmcDebtExtVo queryDebt(@RequestParam("debtId") Long debtId)
      throws Exception {

    AmcDebtExtVo amcDebtExtVo = new AmcDebtExtVo();

    AmcDebtVo amcDebtVo =  amcDebtService.get(debtId);
    amcDebtExtVo.setAmcDebtVo(amcDebtVo);

    try{
      List<AmcCreditor> creditors = amcDebtService.getCreditors(debtId);

      List<AmcGrntor> amcGrntors = amcDebtService.getGrantors(debtId);

      AmcOrigCreditor origCreditors = amcDebtService.getOriginCreditor(debtId);
      amcDebtExtVo.setCreditors(creditors);
      amcDebtExtVo.setAmcGrntors(amcGrntors);
      amcDebtExtVo.setOrigCreditor(origCreditors);
    }catch (Exception ex){
      log.error("failed to get creditor or grantor",ex);
    }


    return amcDebtExtVo;
  }

  @RequestMapping(value = "/api/amcid/{id}/debt/update", method = RequestMethod.POST)
  @ResponseBody
  public AmcDebtExtVo updateDebt(@RequestBody AmcDebtExtVo amcDebtExtVo)
      throws Exception {




    try{

      AmcDebt amcDebt = new AmcDebt();
      BeanUtils.copyProperties(amcDebtExtVo.getAmcDebtVo(), amcDebt);
      amcDebt.setBaseAmount(AmcNumberUtils.getLongFromDecimalWithMult100(amcDebtExtVo.getAmcDebtVo().getBaseAmount()));
      amcDebt.setEstimatedPrice(AmcNumberUtils.getLongFromDecimalWithMult100(amcDebtExtVo.getAmcDebtVo().getEstimatedPrice()));
      amcDebt.setTotalAmount(AmcNumberUtils.getLongFromDecimalWithMult100(amcDebtExtVo.getAmcDebtVo().getTotalAmount()));
      amcDebtService.update(amcDebt);

      List<Long> creditorIds =
          amcDebtExtVo.getCreditors().stream().map( amcCreditor -> amcCreditor.getId()).collect(Collectors.toList());
      amcDebtService.connDebt2Creditors(creditorIds, amcDebtExtVo.getAmcDebtVo().getId());
    }catch (Exception ex){
      log.error("failed to update debt",ex);
    }


    return amcDebtExtVo;
  }

  @RequestMapping(value = "/api/amcid/{id}/debts", method = RequestMethod.POST)
  @ResponseBody
  public Page<AmcDebtVo> queryDebts(@RequestBody PageInfo pageable)
      throws Exception {
    Map<String, Sort.Direction> orderByParam = PageReqRepHelper.getOrderParam(pageable);
    if(CollectionUtils.isEmpty(orderByParam)){
      orderByParam.put("id", Direction.DESC);
    }


    List<AmcDebtVo> queryResults;
    int offset = PageReqRepHelper.getOffset(pageable);
    try{
      queryResults = amcDebtService.queryAllExt(Long.valueOf(offset), pageable.getSize(), orderByParam);
    }catch (Exception ex){
      log.error("got error when query:"+ex.getMessage());
      throw ex;
    }
    Long totalCount = amcDebtService.getTotalCount();

    Page<AmcDebtVo> page = PageReqRepHelper.getPageResp(totalCount, queryResults, pageable);


    return page;
  }


  @RequestMapping(value = "/api/amcid/{id}/debt/editAble", method = RequestMethod.POST)
  @ResponseBody
  public Boolean editAble(
      @RequestParam("debtStatus") Integer debtStatus)
      {



    return  zccRulesService.editAble(EditStatusEnum.lookupByDisplayStatusUtil(debtStatus));

  }




}
