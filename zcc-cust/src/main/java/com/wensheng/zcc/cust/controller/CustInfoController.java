package com.wensheng.zcc.cust.controller;

import com.google.gson.Gson;
import com.wensheng.zcc.common.params.AmcPage;
import com.wensheng.zcc.common.params.PageReqRepHelper;
import com.wensheng.zcc.cust.config.aop.AddTraceLogId;
import com.wensheng.zcc.cust.config.aop.LogExecutionTime;
import com.wensheng.zcc.cust.config.aop.QueryCheckerCmpy;
import com.wensheng.zcc.cust.config.aop.QueryValidCmpy;
import com.wensheng.zcc.cust.controller.helper.QueryParam;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdCmpy;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdPerson;
import com.wensheng.zcc.cust.module.helper.CustTypeEnum;
import com.wensheng.zcc.cust.module.helper.SelectCustTypeEnum;
import com.wensheng.zcc.cust.module.sync.AddCrawlCmpyDTO;
import com.wensheng.zcc.cust.module.sync.AddCrawlCmpyResultDTO;
import com.wensheng.zcc.cust.module.sync.CmpyBizInfoResult;
import com.wensheng.zcc.cust.module.vo.CustCountVo;
import com.wensheng.zcc.cust.module.vo.CustInfoGeoNear;
import com.wensheng.zcc.cust.module.vo.CustTrdFavorVo;
import com.wensheng.zcc.cust.module.vo.CustTrdInfoExcelVo;
import com.wensheng.zcc.cust.module.vo.CustTrdInfoVo;
import com.wensheng.zcc.cust.module.vo.CustTrdPersonVo;
import com.wensheng.zcc.cust.module.vo.CustsCountByTime;
import com.wensheng.zcc.cust.service.CustInfoService;
import com.wensheng.zcc.cust.service.SyncService;
import com.wensheng.zcc.cust.utils.ExcelGenerator;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author chenwei on 4/15/19
 * @project miniapp-backend
 */
@Controller
@Slf4j
@AddTraceLogId
@RequestMapping("/amc/cust/custinfo")
public class CustInfoController {

  @Autowired
  CustInfoService custInfoService;

  @Autowired
  SyncService syncService;

  @Autowired
  ExcelGenerator excelGenerator;

  @Autowired
  KafkaTemplate crawlSystemKafkaTemplate;

  @RequestMapping(value = "/addCmpy", method = RequestMethod.POST)
  @ResponseBody
  @QueryCheckerCmpy
  public CustTrdCmpy addCompany(@RequestBody CustTrdCmpy custTrdCmpy) throws Exception {

    return custInfoService.addCompany(custTrdCmpy);
  }

  @RequestMapping(value = "/getCmpies", method = RequestMethod.POST)
  @ResponseBody
  public List<CustTrdCmpy> getCmpies(){

      return custInfoService.getCmpies();


  }

  @RequestMapping(value = "/getCmpy", method = RequestMethod.POST)
  @ResponseBody
  public CustTrdCmpy getCmpy(@RequestParam Long companyId){

    CustTrdCmpy custTrdCmpy = custInfoService.getCompany(companyId);
    return custTrdCmpy;
  }

  @RequestMapping(value = "/getCmpyByName", method = RequestMethod.POST)
  @ResponseBody
  public List<CustTrdCmpy> getCmpy(@RequestParam String companyName) throws Exception {

    return custInfoService.getCompanyByName(companyName);

  }

  @RequestMapping(value = "/getAccurateCmpyByName", method = RequestMethod.POST)
  @ResponseBody
  public List<CustTrdCmpy> getAccurateCmpyByName(@RequestParam String companyName) throws Exception {

    return custInfoService.getAccurateCmpyByName(companyName);

  }

  @RequestMapping(value = "/getCustFavor", method = RequestMethod.POST)
  @ResponseBody
  public CustTrdFavorVo getCustFavor(@RequestParam Long custId, @RequestParam Integer custType){

    return custInfoService.getCustFavor(custId, custType);

  }

  @RequestMapping(value = "/updateCmpy", method = RequestMethod.POST)
  @ResponseBody
  @QueryCheckerCmpy
  public void modCmpy(@RequestBody CustTrdCmpy custTrdCmpy) throws Exception {

     custInfoService.updateCompany(custTrdCmpy);

  }

  @RequestMapping(value = "/getPerson", method = RequestMethod.POST)
  @ResponseBody
  public CustTrdPerson getPerson(@RequestParam Long personId){

    return custInfoService.getPerson(personId);

  }

  @RequestMapping(value = "/getPersonEditable", method = RequestMethod.POST)
  @ResponseBody
  public CustTrdPersonVo getPersonEditable(@RequestParam Long personId){

    return custInfoService.getPersonEditable(personId);

  }

  @RequestMapping(value = "/custPerson/mod", method = RequestMethod.POST)
  @ResponseBody
  public boolean modCustPerson(@RequestBody CustTrdPersonVo custTrdPersonVo){

    return custInfoService.modCustPerson(custTrdPersonVo);

  }

  @RequestMapping(value = "/getPersons", method = RequestMethod.POST)
  @ResponseBody
  public List<CustTrdPerson> getPersons(){
      return custInfoService.getTrdPersons();

  }

  @RequestMapping(value = "/addPerson", method = RequestMethod.POST)
  @ResponseBody
  public CustTrdPerson addPerson(@RequestBody CustTrdPerson custTrdPerson){

     return custInfoService.addTrdPerson(custTrdPerson);
  }

  @RequestMapping(value = "/doSynchronization", method = RequestMethod.GET)
  @ResponseBody
  public void doSynchronization() throws Exception {
     syncService.syncCustInfo();
  }


  @PreAuthorize("hasAnyRole('AMC_LOCAL_VISITOR','SYSTEM_ADMIN','CO_ADMIN') or hasPermission(null, 'PERM_INVCUST_VIEW')")
  @QueryValidCmpy
  @RequestMapping(value = "/getCustTrdInfo", method = RequestMethod.POST)
  @ResponseBody
  @LogExecutionTime
  public AmcPage<CustTrdInfoVo> getCustTrdInfo(@RequestBody QueryParam queryParam) throws Exception {

    Map<String, Direction> orderByParam = PageReqRepHelper.getOrderParam(queryParam.getPageInfo());
    List<CustTrdInfoVo> queryResults = null;
    Long totalCount = null;
    int offset = PageReqRepHelper.getOffset(queryParam.getPageInfo());
    try{
      if(queryParam.getCustType() == CustTypeEnum.COMPANY.getId()){
        queryResults = custInfoService.queryCmpyTradePage(offset, queryParam.getPageInfo().getSize(), queryParam,
            orderByParam);
        totalCount = custInfoService.getCmpyTradeCount(queryParam);
      }else if(queryParam.getCustType() == CustTypeEnum.PERSON.getId()){
        queryResults = custInfoService.queryPersonTradePage(offset, queryParam.getPageInfo().getSize(), queryParam,
            orderByParam);
        totalCount = custInfoService.getPersonTradeCount(queryParam);
      }

    }catch (Exception ex){
      log.error("got error when query:"+ex.getMessage());
      throw ex;
    }

//    Page<AmcAssetVo> page = PageReqRepHelper.getPageResp(totalCount, queryResults, assetQueryParam.getPageInfo());
    return PageReqRepHelper.getAmcPage(queryResults, totalCount );

  }


  @PreAuthorize("hasAnyRole('AMC_LOCAL_VISITOR','SYSTEM_ADMIN','CO_ADMIN') or hasPermission(null, 'PERM_INVCUST_VIEW')")
  @QueryValidCmpy
  @RequestMapping(value = "/getLatestCustTrdInfo", method = RequestMethod.POST)
  @ResponseBody
  @LogExecutionTime
  public AmcPage<CustTrdInfoVo> getLatestCustTrdInfo(@RequestBody QueryParam queryParam) throws Exception {

    Map<String, Direction> orderByParam = PageReqRepHelper.getOrderParam(queryParam.getPageInfo());

    List<CustTrdInfoVo> queryResults = null;
    Long totalCount = null;
    int offset = PageReqRepHelper.getOffset(queryParam.getPageInfo());
    try{
      if(queryParam.getCustType() == CustTypeEnum.COMPANY.getId()){
        if(CollectionUtils.isEmpty(orderByParam)){
          orderByParam.put("ctc.update_time", Direction.DESC);

        }
        queryResults = custInfoService.queryCmpyTradePage(offset, queryParam.getPageInfo().getSize(), queryParam,
            orderByParam);
        totalCount = custInfoService.getCmpyTradeCount(queryParam);
      }else if(queryParam.getCustType() == CustTypeEnum.PERSON.getId()){

        if(CollectionUtils.isEmpty(orderByParam)){
          orderByParam.put("ctp.update_time", Direction.DESC);
        }
        queryResults = custInfoService.queryPersonTradePage(offset, queryParam.getPageInfo().getSize(), queryParam,
            orderByParam);
        totalCount = custInfoService.getPersonTradeCount(queryParam);
      }

    }catch (Exception ex){
      log.error("got error when query:"+ex.getMessage());
      throw ex;
    }

//    Page<AmcAssetVo> page = PageReqRepHelper.getPageResp(totalCount, queryResults, assetQueryParam.getPageInfo());
    return PageReqRepHelper.getAmcPage(queryResults, totalCount );

  }

  @PreAuthorize("hasAnyRole('AMC_LOCAL_VISITOR','SYSTEM_ADMIN','CO_ADMIN') or hasPermission(null, 'PERM_INVCUST_VIEW')")
  @QueryValidCmpy
  @RequestMapping(value = "/getCustCount", method = RequestMethod.POST)
  @ResponseBody
  @LogExecutionTime
  public CustCountVo getCustCount(@RequestBody QueryParam queryParam) throws Exception {
    CustCountVo custCountVo = new CustCountVo();
    //查询所有SelectCustTypeEnum
    queryParam.setSelectCustType(SelectCustTypeEnum.ALL.getEname());
    Long allCmpycount = custInfoService.getCmpyTradeCount(queryParam);
    custCountVo.setAllCmpycount(allCmpycount);
    Long allPersonCount = custInfoService.getPersonTradeCount(queryParam);
    custCountVo.setAllPersonCount(allPersonCount);

    //查询最新更新
    queryParam.setSelectCustType(SelectCustTypeEnum.UPDATE.getEname());
    Long updateCmpycount = custInfoService.getCmpyTradeCount(queryParam);
    custCountVo.setUpdateCmpycount(updateCmpycount);
    Long updatePersonCount = custInfoService.getPersonTradeCount(queryParam);
    custCountVo.setUpdatePersonCount(updatePersonCount);

    //最近新增
    queryParam.setSelectCustType(SelectCustTypeEnum.CREATE.getEname());
    Long creatCmpycount = custInfoService.getCmpyTradeCount(queryParam);
    custCountVo.setCreatCmpycount(creatCmpycount);
    Long creatPersonCount = custInfoService.getPersonTradeCount(queryParam);
    custCountVo.setCreatPersonCount(creatPersonCount);

    custCountVo.setTradeCmpycount(400L);
    custCountVo.setTradePersonCount(400L);

    return custCountVo;
  }

//  @PreAuthorize("hasAnyRole('ROLE_AMC_LOCAL_VISITOR','ROLE_AMC_VISITOR')")
  @RequestMapping(value = "/export", method = RequestMethod.POST)
  public ResponseEntity<Resource> excelCustomersReport(@RequestBody QueryParam queryParam) throws Exception {
    Map<String, Direction> orderByParam = PageReqRepHelper.getOrderParam(queryParam.getPageInfo());

    List<CustTrdInfoExcelVo> queryResults = null;
    int offset = PageReqRepHelper.getOffset(queryParam.getPageInfo());
    int size = queryParam.getExportSize() > 0 ? queryParam.getExportSize() : queryParam.getPageInfo().getSize();


      if(queryParam.getCustType() == CustTypeEnum.COMPANY.getId()){
        if(CollectionUtils.isEmpty(orderByParam)){
          orderByParam.put("ctc.data_quality", Direction.DESC);
          orderByParam.put("ctc.id", Direction.DESC);
        }

        queryResults = custInfoService.queryCmpyTrade(offset, size, queryParam,
            orderByParam);
      }else if(queryParam.getCustType() == CustTypeEnum.PERSON.getId()){

        if(CollectionUtils.isEmpty(orderByParam)){
          orderByParam.put("ctp.data_quality", Direction.DESC);
          orderByParam.put("ctp.id", Direction.DESC);
        }
        queryResults = custInfoService.queryPersonTrade(offset, size, queryParam,
            orderByParam);
      }

    File output = excelGenerator.customersToExcel(queryResults);
    Resource resource = new UrlResource(output.toPath().toUri());
    if(resource.exists()) {
      return ResponseEntity.ok()
          .contentType(MediaType.parseMediaType("application/octet-stream"))
          .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
          .body(resource);
    } else {
      throw new Exception("File not found " + output.toPath());
    }


  }


  @PreAuthorize("hasAnyRole('ROLE_AMC_LOCAL_VISITOR','ROLE_SYSTEM_ADMIN', 'ROLE_CO_ADMIN') or hasPermission(null, "
      + "'PERM_INVCUST_VIEW')")
  @RequestMapping(value = "/getCustInfoGeoNear", method = RequestMethod.POST)
  @ResponseBody
  public List<CustInfoGeoNear> getCustInfoGeoNear(@RequestBody GeoJsonPoint geoJsonPoint) throws Exception {
    return custInfoService.queryAllNearByCusts(geoJsonPoint);
  }

  @RequestMapping(value = "/getCustCountByTimeStart", method = RequestMethod.POST)
  @ResponseBody
  public CustsCountByTime getCustCountByTime(@RequestBody LocalDateTime startTime) throws Exception {
    return custInfoService.getCustCountByTime(startTime);
  }

  @RequestMapping(value = "/patchCustInfo4DupCmpyName", method = RequestMethod.POST)
  @ResponseBody
  public void patchCustInfo4DupCmpyName() throws Exception {
     custInfoService.patchDuplicateCmpyName();
  }

  @RequestMapping(value = "/patchCmpyProvince", method = RequestMethod.POST)
  @ResponseBody
  public void patchCmpyProvince(@RequestParam(required = false) String province) throws Exception {
    custInfoService.patchCmpyProvince(province);
  }

  @RequestMapping(value = "/patchAddCmpyProvince", method = RequestMethod.POST)
  @ResponseBody
  public void patchAddCmpyProvince() throws Exception {
    custInfoService.patchAddCmpyProvince();
  }

  @RequestMapping(value = "/addCrawlCmpyKafkaTest", method = RequestMethod.POST)
  @ResponseBody
  public String addCrawlCmpy(@RequestParam String cmpyNames, @RequestParam String batchName,
      @RequestParam String responseType) throws Exception {
    final String TOPIC = "cmpy_biz_info";
    AddCrawlCmpyDTO addCrawlCmpyDTO = new AddCrawlCmpyDTO();
    addCrawlCmpyDTO.setAppName("zcc");
    addCrawlCmpyDTO.setCmpyNames(cmpyNames);
    addCrawlCmpyDTO.setBatchName(batchName);
    addCrawlCmpyDTO.setBatchId(batchName);
    String[] cmpyNameArray = cmpyNames.split(",");
    addCrawlCmpyDTO.setCmpyCount(cmpyNameArray.length);
    addCrawlCmpyDTO.setResponseType(responseType);
    String addCrawlCmpyJson = new Gson().toJson(addCrawlCmpyDTO);
    try {
      crawlSystemKafkaTemplate.send(TOPIC, addCrawlCmpyJson);
      log.info("请求添加爬取公司信息kafka发送成功,addCrawlCmpyJson:{}", addCrawlCmpyJson);
    } catch (Exception e) {
      log.error("请求添加爬取公司信息失败，,addCrawlCmpyJson:{}", addCrawlCmpyJson);
    }
    return "success";
  }

  @RequestMapping(value = "/addCrawlCmpyResulKafkaTest", method = RequestMethod.POST)
  @ResponseBody
  public String addCrawlCmpyResult(@RequestParam String cmpyNames) throws Exception {
    final String TOPIC = "crawler_response_zcc";
    AddCrawlCmpyResultDTO addCrawlCmpyResultDTO = new AddCrawlCmpyResultDTO();
    addCrawlCmpyResultDTO.setAppName("zcc");
    List<CmpyBizInfoResult> cmpyBizInfoResultList = new ArrayList<>();
    CmpyBizInfoResult cmpyBizInfoResult = new CmpyBizInfoResult();
    cmpyBizInfoResult.setCmpyName(cmpyNames);
    cmpyBizInfoResult.setStatus("1");
    cmpyBizInfoResult.setErrorMsg(null);
    cmpyBizInfoResultList.add(cmpyBizInfoResult);
    addCrawlCmpyResultDTO.setCmpyBizInfoResults(cmpyBizInfoResultList);
    String addCrawlCmpyResultDTOJson = new Gson().toJson(addCrawlCmpyResultDTO);
    try {
      crawlSystemKafkaTemplate.send(TOPIC, addCrawlCmpyResultDTOJson);
      log.info("请求添加爬取公司信息kafka发送成功,addCrawlCmpyJson:{}", addCrawlCmpyResultDTOJson);
    } catch (Exception e) {
      log.error("请求添加爬取公司信息失败，,addCrawlCmpyJson:{}", addCrawlCmpyResultDTOJson);
    }
    return "success";
  }

}
