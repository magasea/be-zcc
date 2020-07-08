package com.wensheng.zcc.cust.service.impl;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.wensheng.zcc.common.utils.AmcBeanUtils;
import com.wensheng.zcc.common.utils.AmcDateUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils.AmcExceptions;
import com.wensheng.zcc.cust.controller.helper.QueryParam;
import com.wensheng.zcc.cust.dao.mysql.mapper.CustRegionDetailMapper;
import com.wensheng.zcc.cust.dao.mysql.mapper.CustRegionMapper;
import com.wensheng.zcc.cust.dao.mysql.mapper.CustTrdCmpyHistoryMapper;
import com.wensheng.zcc.cust.dao.mysql.mapper.CustTrdCmpyMapper;
import com.wensheng.zcc.cust.dao.mysql.mapper.CustTrdInfoMapper;
import com.wensheng.zcc.cust.dao.mysql.mapper.CustTrdPersonMapper;
import com.wensheng.zcc.cust.dao.mysql.mapper.ext.CustTrdCmpyExtMapper;
import com.wensheng.zcc.cust.dao.mysql.mapper.ext.CustTrdPersonExtMapper;
import com.wensheng.zcc.cust.module.dao.mongo.CustTrdGeo;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustRegionDetail;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdCmpy;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdCmpyExample;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdCmpyHistory;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdInfo;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdInfoExample;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdPerson;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdPersonExample;
import com.wensheng.zcc.cust.module.dao.mysql.ext.CustTrdCmpyExtExample;
import com.wensheng.zcc.cust.module.dao.mysql.ext.CustTrdCmpyTrdExt;
import com.wensheng.zcc.cust.module.dao.mysql.ext.CustTrdPersonExtExample;
import com.wensheng.zcc.cust.module.dao.mysql.ext.CustTrdPersonTrdExt;
import com.wensheng.zcc.cust.module.helper.CustTypeEnum;
import com.wensheng.zcc.cust.module.helper.ItemTypeEnum;
import com.wensheng.zcc.cust.module.helper.PresonStatusEnum;
import com.wensheng.zcc.cust.module.sync.AddCrawlCmpyDTO;
import com.wensheng.zcc.cust.module.sync.AdressResp;
import com.wensheng.zcc.cust.module.sync.CmpyBasicBizInfoSync;
import com.wensheng.zcc.cust.module.sync.CrawlResultDTO;
import com.wensheng.zcc.cust.module.sync.CustCmpyInfoFromSync;
import com.wensheng.zcc.cust.module.vo.CustInfoGeoNear;
import com.wensheng.zcc.cust.module.vo.CustTrdCmpyExtVo;
import com.wensheng.zcc.cust.module.vo.CustTrdFavorVo;
import com.wensheng.zcc.cust.module.vo.CustTrdInfoExcelVo;
import com.wensheng.zcc.cust.module.vo.CustTrdInfoVo;
import com.wensheng.zcc.cust.module.vo.CustTrdPersonExtVo;
import com.wensheng.zcc.cust.module.vo.CustTrdPersonVo;
import com.wensheng.zcc.cust.module.vo.CustsCountByTime;
import com.wensheng.zcc.cust.service.CustInfoService;
import com.wensheng.zcc.cust.service.SyncService;
import com.wensheng.zcc.cust.utils.SQLUtils;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.http.HttpMethod;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

/**
 * @author chenwei on 4/17/19
 * @project miniapp-backend
 */
@Service
@Slf4j
public class CustInfoServiceImpl implements CustInfoService {

  @Autowired
  CustTrdCmpyMapper custTrdCmpyMapper;

  @Autowired
  CustTrdPersonMapper custTrdPersonMapper;

  @Autowired
  CustTrdCmpyExtMapper custTrdCmpyExtMapper;

  @Autowired
  CustTrdPersonExtMapper custTrdPersonExtMapper;

  @Autowired
  CustRegionMapper custRegionMapper;

  @Autowired
  CustRegionDetailMapper custRegionDetailMapper;

  @Autowired
  CustTrdInfoMapper custTrdInfoMapper;

  @Autowired
  KafkaTemplate crawlSystemKafkaTemplate;

  @Autowired
  CommonHandler commonHandler;

  @Value("${cust.syncUrls.getAddressCodeByAddress}")
  private String getAddressCodeByAddress;

  @Value("${cust.syncUrls.getCompanyBasicBizInfo}")
  String getCompanyBasicBizInfo;

  @Autowired
  SyncService syncService;

  @Value("${env.image-repo}")
  String fileBase;
  @Value("${cust.syncUrls.crawledCompany}")
  private String crawledCompany;
  @Value("${cust.syncUrls.getCompanyInfoByName}")
  String getCompanyInfoByNameUrl;

  ThreadLocal<String> provinceToHandle;
  final String APP_NAME = "zcc";
  final String ADD_CRAWL_CMPY_TOPIC = "cmpy_biz_info";

  static final String SEP_INDICATOR_EDITABLE = "|$|";
  static final String SEP_INDICATOR_ORIGINAL = "|#|";

  static final String SEP_INDICATOR_EDITABLE_USED = "\\|\\$\\|";
  static final String SEP_INDICATOR_ORIGINAL_USED = "\\|#\\|";
  @Autowired
  MongoTemplate mongoTemplate;

  Gson gson = new Gson();

  @Override
  public CustTrdCmpy addCompany(CustTrdCmpy custTrdCmpy) throws Exception {
    List<CustTrdCmpy> custTrdCmpies = commonHandler.queryCmpyByName(custTrdCmpy.getCmpyName());
    if (!CollectionUtils.isEmpty(custTrdCmpies)){
      throw ExceptionUtils.getAmcException(AmcExceptions.DUPLICATE_RECORD_INSERT_ERROR ,
              String.format("已存在该公司，名称是：%s",custTrdCmpies.get(0).getCmpyName()));
    }
    //判断基础库是否有数据
    RestTemplate restTemplate = CommonHandler.getRestTemplate();
    String url = String.format(getCompanyBasicBizInfo, custTrdCmpy.getCmpyName());
    CrawlResultDTO crawlResultDTO = restTemplate.getForEntity(
        url, CrawlResultDTO.class).getBody();
    log.info("查询爬虫基础库中信息url:{},查询结果crawlResultDTO：{}", url, crawlResultDTO);

    //模糊匹配改精确匹配
    CmpyBasicBizInfoSync cmpyBasicBizInfoSyncNew = null;
    List<CmpyBasicBizInfoSync>  cmpyBasicBizInfoSyncArrayList= crawlResultDTO.getList();
    if(!CollectionUtils.isEmpty(cmpyBasicBizInfoSyncArrayList)){
      for (CmpyBasicBizInfoSync cmpyBasicBizInfoSync : cmpyBasicBizInfoSyncArrayList){
        if(custTrdCmpy.getCmpyName().equals(cmpyBasicBizInfoSync.getName())){
          cmpyBasicBizInfoSyncNew = cmpyBasicBizInfoSync;
        }
      }
    }

    //判断基础库是否为空
    if(null == cmpyBasicBizInfoSyncNew ||  null == cmpyBasicBizInfoSyncNew.getEntAddress()){
      addCrawlCmpy(custTrdCmpy.getCmpyName());
      log.info("人工新增公司custTrdCmpy：{}",custTrdCmpy);
      custTrdCmpyMapper.insertSelective(custTrdCmpy);
      return custTrdCmpy;
    }

    //保存信息
    custTrdCmpy.setCmpyName(cmpyBasicBizInfoSyncNew.getName());
    custTrdCmpy.setUniSocialCode(cmpyBasicBizInfoSyncNew.getSocialCode());
    if(null != cmpyBasicBizInfoSyncNew.getHistoryName()){
      custTrdCmpy.setCmpyNameHistory(cmpyBasicBizInfoSyncNew.getHistoryName().replace(",",";"));
    }
    custTrdCmpy.setCmpyPhone(cmpyBasicBizInfoSyncNew.getEntPhone());
    custTrdCmpy.setCmpyAddr(cmpyBasicBizInfoSyncNew.getEntAddress());
    custTrdCmpy.setAnnuReptPhone(cmpyBasicBizInfoSyncNew.getReportPhone());
    custTrdCmpy.setAnnuReptAddr(cmpyBasicBizInfoSyncNew.getReportAddress());
    if(null != cmpyBasicBizInfoSyncNew.getRegionCode()){
      custTrdCmpy.setCmpyProvince(cmpyBasicBizInfoSyncNew.getRegionCode().substring(0,6));
    }
    custTrdCmpy.setLegalReptive(cmpyBasicBizInfoSyncNew.getLegalPerson());
    custTrdCmpy.setSyncTime(new Date());
    custTrdCmpy.setUpdateTime(new Date());
    log.info("根据基础库数据新增公司信息custTrdCmpy：{}",custTrdCmpy);
    custTrdCmpyMapper.insertSelective(custTrdCmpy);
    return custTrdCmpy;
  }

  private void addCrawlCmpy(String cmpyName) {
    AddCrawlCmpyDTO addCrawlCmpyDTO = new AddCrawlCmpyDTO();
    addCrawlCmpyDTO.setAppName(APP_NAME);
    addCrawlCmpyDTO.setCmpyNames(cmpyName);
    addCrawlCmpyDTO.setCmpyCount(1);
    String addCrawlCmpyJson = new Gson().toJson(addCrawlCmpyDTO);
    try {
      log.info("请求添加爬取公司信息kafka");
      crawlSystemKafkaTemplate.send(ADD_CRAWL_CMPY_TOPIC, addCrawlCmpyJson);
      log.info("请求添加爬取公司信息kafka发送成功,addCrawlCmpyJson:{}",addCrawlCmpyJson);
    }catch (Exception e){
      log.error("请求添加爬取公司信息失败，,addCrawlCmpyJson:{}",addCrawlCmpyJson);
    }
  }

  @Override
  public void updateCompany(CustTrdCmpy custTrdCmpy) throws Exception {

    CustTrdCmpyExtExample custTrdCmpyExtExample = new CustTrdCmpyExtExample();
    custTrdCmpyExtExample.createCriteria().andIdEqualTo(custTrdCmpy.getId());
    List<CustTrdCmpy> custTrdCmpies= custTrdCmpyMapper.selectByExample(custTrdCmpyExtExample);
    CustTrdCmpy custTrdCmpyOriginal= custTrdCmpies.get(0);

    //判断公司名称是否更改
    if(null == custTrdCmpy.getCmpyNameUpdate() || "-1".equals(custTrdCmpy.getCmpyNameUpdate())){
      log.info("没有修改公司名称，则根据修改信息跟新数据库：custTrdCmpy：{}",custTrdCmpy);
      //保存公司修改记录
      commonHandler.creatCmpyHistory(custTrdCmpy.getUpdateBy(),"updateCompany",
          "人工修改基础信息", custTrdCmpyOriginal);
      //不修改名称则直接修改公司
      custTrdCmpyMapper.updateByPrimaryKeySelective(custTrdCmpy);
      return;
    }

    //修改公司名称，先判断是否已有该公司
    List<CustTrdCmpy> custTrdCmpieList = commonHandler.queryCmpyByName(custTrdCmpy.getCmpyNameUpdate());
    if (!CollectionUtils.isEmpty(custTrdCmpieList)){
      throw ExceptionUtils.getAmcException(AmcExceptions.DUPLICATE_RECORD_UPDATE_ERROR ,
          String.format("已存在该公司，名称是：%s",custTrdCmpieList.get(0).getCmpyName()));
    }

    //查询爬虫基础库中信息
    RestTemplate restTemplate = CommonHandler.getRestTemplate();
    String url = String.format(getCompanyBasicBizInfo, custTrdCmpy.getCmpyNameUpdate());
    CrawlResultDTO crawlResultDTO = restTemplate.getForEntity(
        url, CrawlResultDTO.class).getBody();
    log.info("查询爬虫基础库中信息url:{},查询结果crawlResultDTO：{}", url, crawlResultDTO);

    //查到数据比较爬虫数据的历史数据是否有原公司名称，按照基础库数据更新公司名称和曾用名，状态为-1
    //模糊匹配改精确匹配
    CmpyBasicBizInfoSync cmpyBasicBizInfoSync = null;
    List<CmpyBasicBizInfoSync>  cmpyBasicBizInfoSyncArrayList= crawlResultDTO.getList();
    if(!CollectionUtils.isEmpty(cmpyBasicBizInfoSyncArrayList)){
      for (CmpyBasicBizInfoSync cmpyBasicBizInfoSyncNew : cmpyBasicBizInfoSyncArrayList){
        if(custTrdCmpy.getCmpyNameUpdate().equals(cmpyBasicBizInfoSyncNew.getName())){
          cmpyBasicBizInfoSync = cmpyBasicBizInfoSyncNew;
        }
      }
    }

    //判断基础库是否为空
    if(null == cmpyBasicBizInfoSync ||  null == cmpyBasicBizInfoSync.getEntAddress()){
      log.info("人工修改公司名称,，添加爬取公司信息任务");
      //保存公司修改记录,添加爬取公司数据任务
      commonHandler.creatCmpyHistory(custTrdCmpy.getUpdateBy(),"updateCompany",
          "人工修改公司名称，添加爬取公司信息任务", custTrdCmpyOriginal);
      //没有查到数据则添加爬取公司数据任务，状态为1
      custTrdCmpy.setCrawledStatus("1");
      addCrawlCmpy(custTrdCmpy.getCmpyNameUpdate());
      custTrdCmpyMapper.updateByPrimaryKeySelective(custTrdCmpy);
      return;
    }

//    CmpyBasicBizInfoSync cmpyBasicBizInfoSync = cmpyBasicBizInfoSyncNew;
    String cmpyHistoryName = cmpyBasicBizInfoSync.getHistoryName();
    String[] cmpyHistoryArry = cmpyHistoryName.split(",");
    boolean match = false;
    for (int i = 0; i < cmpyHistoryArry.length; i++) {
      if(custTrdCmpyOriginal.getCmpyName().equals(cmpyHistoryArry[i])){
        match = true;
      }
    }

    if(!match){
      log.error("根据修改公司名称查询到基础库信息与被修改公司信息不匹配，被修改公司名称为：{}，基础库公司cmpyBasicBizInfoSync：{}",
          custTrdCmpyOriginal.getCmpyName(),cmpyBasicBizInfoSync);
      throw ExceptionUtils.getAmcException(AmcExceptions.COMPANY_UPDATE_ERROR,
          String.format("根据修改公司名称查询到基础库信息与被修改公司信息不匹配"));
    }

    //保存公司修改记录
    commonHandler.creatCmpyHistory(custTrdCmpy.getUpdateBy(),"updateCompany",
        "人工修改公司名称，查询基础库符合", custTrdCmpyOriginal);
    log.info("人工修改公司名称，查询基础库符合,根据基础库内容更新公司：cmpyBasicBizInfoSync{}",cmpyBasicBizInfoSync);
    custTrdCmpy.setCmpyName(cmpyBasicBizInfoSync.getName());
    custTrdCmpy.setUniSocialCode(cmpyBasicBizInfoSync.getSocialCode());
    if(null != cmpyBasicBizInfoSync.getHistoryName()){
      custTrdCmpy.setCmpyNameHistory(cmpyBasicBizInfoSync.getHistoryName().replace(",",";"));
    }
    custTrdCmpy.setCmpyPhone(cmpyBasicBizInfoSync.getEntPhone());
    custTrdCmpy.setCmpyAddr(cmpyBasicBizInfoSync.getEntAddress());
    custTrdCmpy.setAnnuReptPhone(cmpyBasicBizInfoSync.getReportPhone());
    custTrdCmpy.setAnnuReptAddr(cmpyBasicBizInfoSync.getReportAddress());
    if(null != cmpyBasicBizInfoSync.getRegionCode()){
      custTrdCmpy.setCmpyProvince(cmpyBasicBizInfoSync.getRegionCode().substring(0,6));
    }
    custTrdCmpy.setLegalReptive(cmpyBasicBizInfoSync.getLegalPerson());
    custTrdCmpy.setSyncTime(new Date());
    custTrdCmpy.setUpdateTime(new Date());
    custTrdCmpyMapper.updateByPrimaryKeySelective(custTrdCmpy);
  }

  @Override
  public CustTrdPerson addTrdPerson(CustTrdPerson custTrdPerson) {
    custTrdPersonMapper.insertSelective(custTrdPerson);
    return custTrdPerson;
  }

  @Override
  public List<CustTrdPerson> getTrdPersons() {
    return custTrdPersonMapper.selectByExample(null);
  }

  @Override
  public List<CustTrdCmpy> getCmpies() {
    return custTrdCmpyMapper.selectByExample(null);
  }

  @Override
  public List<CustTrdInfoVo> queryCmpyTradePage(int offset, int size, QueryParam queryParam,
      Map<String, Direction> orderByParam) throws Exception {
    List<CustTrdCmpyTrdExt> custTrdCmpyTrdExts = queryCmpy(offset, size, queryParam, orderByParam);
    return convertCmpyToVoes(custTrdCmpyTrdExts);
  }

  private List<CustTrdCmpyTrdExt> queryCmpy(int offset, int size, QueryParam queryParam,
      Map<String, Direction> orderByParam) throws Exception {

    String orderBy = SQLUtils.getOrderBy(orderByParam);
//   CustTrdCmpyExtExample custTrdCmpyExtExample = SQLUtils.getCustCmpyTrdExample(queryParam);
    CustTrdCmpyExtExample custTrdCmpyExtExample = new CustTrdCmpyExtExample();
    custTrdCmpyExtExample.setOrderByClause(orderBy);

    String whereClause = SQLUtils.getTrdCmpyExtWhereClause(queryParam);
    if(!StringUtils.isEmpty(whereClause)){
      custTrdCmpyExtExample.setWhereClause(whereClause);
    }
    String filterBy = SQLUtils.getFilterByForCustTrd(queryParam);
//    filterBy = filterBy + " and ctc.cmpy_phone > -1 ";
    List<CustTrdCmpyTrdExt> custTrdCmpyTrdExts = new ArrayList<>();
    custTrdCmpyExtExample.setLimitByClause(String.format(" %d , %d ", offset, size));
    List<Long> preGroupResults = new ArrayList<>();
    if(!StringUtils.isEmpty(filterBy)){
      custTrdCmpyExtExample.setFilterByClause(filterBy);
      preGroupResults =
          custTrdCmpyExtMapper.selectByPreFilter(custTrdCmpyExtExample);
      log.info("preGroupResults:{}", gson.toJson(preGroupResults));

    }else{
      if(queryParam.isAllowNoTrd()){
        preGroupResults =
            custTrdCmpyExtMapper.selectByPreFilterAllowNoTrd(custTrdCmpyExtExample);
      }else{
        preGroupResults =
            custTrdCmpyExtMapper.selectByPreFilter(custTrdCmpyExtExample);
      }
      log.info("preGroupResults:{}", gson.toJson(preGroupResults));
//      CustTrdCmpyExtExample.Criteria criteria = custTrdCmpyExtExample.createCriteria();
//      criteria.andIdIn(preGroupResults);
//      custTrdCmpyExtExample.getOredCriteria().add(criteria);

    }
    if(CollectionUtils.isEmpty(preGroupResults)){
      log.error("Failed to get results by filter:{}", filterBy);
      return custTrdCmpyTrdExts;
    }
    StringBuilder sb = new StringBuilder(" ctc.id in ( ");
    preGroupResults.forEach(item -> sb.append(item).append(","));
    sb.setLength(sb.length() -1);
    sb.append(")");
    custTrdCmpyExtExample.setWhereClause(sb.toString());
    custTrdCmpyExtExample.setFilterByClause(null);
    custTrdCmpyTrdExts = custTrdCmpyExtMapper.selectByExample(custTrdCmpyExtExample);
    //custTrdCmpyTrdExts 转map
    Map<Long, CustTrdCmpyTrdExt> cmpyTrdExtMap = custTrdCmpyTrdExts.stream().collect(Collectors.toMap(CustTrdCmpyTrdExt::getId, custTrdCmpyTrdExt -> custTrdCmpyTrdExt));
    //根据id重新排列
    List<CustTrdCmpyTrdExt> custTrdCmpyTrdExtNewList = new ArrayList<>();
    for (Long id : preGroupResults) {
      custTrdCmpyTrdExtNewList.add(cmpyTrdExtMap.get(id));
    }
    return custTrdCmpyTrdExtNewList;
  }



  @Override
  public List<CustTrdInfoExcelVo> queryCmpyTrade(int offset, int size, QueryParam queryParam, Map<String, Direction> orderByParam)
      throws Exception {
    List<CustTrdCmpyTrdExt> custTrdCmpyTrdExtsTotal = new ArrayList<>();
    if(size > 20 && offset == 0 ){
      log.info("need to make the results into pages");
      int pageSize = 10;
      int cnt = 0;
      while(cnt >= 0 ){
        List<CustTrdCmpyTrdExt> custTrdCmpyTrdExts = queryCmpy( offset,  pageSize,  queryParam,  orderByParam);
        if(!StringUtils.isEmpty(queryParam.getCity()) && queryParam.getCity().length() >=5){
          custTrdCmpyTrdExts.sort(new Comparator<CustTrdCmpyTrdExt>() {
            @Override
            public int compare(CustTrdCmpyTrdExt o1, CustTrdCmpyTrdExt o2) {
              int o1cnt = getCountCustTrdInfoByCityLike(o1.getCustTrdInfoList(), queryParam.getCity().substring(0,2));
              int o2cnt = getCountCustTrdInfoByCityLike(o2.getCustTrdInfoList(), queryParam.getCity().substring(0,2));
              return o1cnt - o2cnt;
            }
          });
        }

        if(custTrdCmpyTrdExts.size() < pageSize || cnt*pageSize >= size ){
          //it is last one
          cnt = -2;
          custTrdCmpyTrdExtsTotal.addAll(custTrdCmpyTrdExts);
          break;
        }
        cnt ++;
        offset = offset + pageSize;

        custTrdCmpyTrdExtsTotal.addAll(custTrdCmpyTrdExts);
      }

    }else{
      custTrdCmpyTrdExtsTotal = queryCmpy( offset,  size,  queryParam,  orderByParam);
    }

    return convertCmpyToExcelVoes(custTrdCmpyTrdExtsTotal);
  }

  private int getCountCustTrdInfoByCityLike(List<CustTrdInfo> custTrdInfos,  String provinceCode){
    int count = 0;
    for(CustTrdInfo custTrdInfoExt: custTrdInfos){
      if(custTrdInfoExt.getDebtCity().startsWith(provinceCode)){
        count +=1;
      }
    }
    return count;
  }

  private List<CustTrdInfoExcelVo> convertCmpyToExcelVoes(List<CustTrdCmpyTrdExt> custTrdCmpyTrdExts) {
    List<CustTrdInfoExcelVo> custTrdInfoExcelVos = new ArrayList<>();
    StringBuilder sbAddress = new StringBuilder();
    StringBuilder sbPhone = new StringBuilder();
    for(CustTrdCmpyTrdExt custTrdCmpyTrdExt: custTrdCmpyTrdExts){
      sbAddress.setLength(0);
      sbPhone.setLength(0);
      CustTrdInfoExcelVo custTrdInfoExcelVo = new CustTrdInfoExcelVo();
      custTrdInfoExcelVo.setCustId(custTrdCmpyTrdExt.getId());
      if(!StringUtils.isEmpty(custTrdCmpyTrdExt.getCustTrdCmpy().getCmpyAddr()) && !custTrdCmpyTrdExt.getCustTrdCmpy().getCmpyAddr().equals("-1")){
        sbAddress.append(custTrdCmpyTrdExt.getCustTrdCmpy().getCmpyAddr()).append(";");
      }
      if(!StringUtils.isEmpty(custTrdCmpyTrdExt.getCustTrdCmpy().getAnnuReptAddr()) && !custTrdCmpyTrdExt.getCustTrdCmpy().getAnnuReptAddr().equals("-1")){
        sbAddress.append(custTrdCmpyTrdExt.getCustTrdCmpy().getAnnuReptAddr());
      }
      custTrdInfoExcelVo.setAddress(sbAddress.toString());
      custTrdInfoExcelVo.setCustName(custTrdCmpyTrdExt.getCustTrdCmpy().getCmpyName());
      if(!StringUtils.isEmpty(custTrdCmpyTrdExt.getCustTrdCmpy().getCmpyPhone()) && !custTrdCmpyTrdExt.getCustTrdCmpy().getCmpyPhone().equals("-1")){
        sbPhone.append(custTrdCmpyTrdExt.getCustTrdCmpy().getCmpyPhone()).append(";");
      }
      if(!StringUtils.isEmpty(custTrdCmpyTrdExt.getCustTrdCmpy().getAnnuReptPhone()) && !custTrdCmpyTrdExt.getCustTrdCmpy().getAnnuReptPhone().equals("-1")){
        sbPhone.append(custTrdCmpyTrdExt.getCustTrdCmpy().getAnnuReptPhone());
      }
      custTrdInfoExcelVo.setPhone(sbPhone.toString());
      custTrdInfoExcelVo.setTrdCount(custTrdCmpyTrdExt.getCustTrdInfoList().size());
      Long debtTotalAmount = 0L;
      Long trdTotalAmount = 0L;

      Map<String, Integer> invest2Counts = new HashMap<>();
      Map<String, Integer> city2Counts = new HashMap<>();
      String cityName = null;
      String provinceName = null;
      String itemTypeName = null;
      for(CustTrdInfo custTrdInfo: custTrdCmpyTrdExt.getCustTrdInfoList()){
        if(StringUtils.isEmpty(custTrdInfo.getDebtCity()) || custTrdInfo.getDebtCity().equals("-1")){
          if(StringUtils.isEmpty(custTrdInfo.getTrdProvince()) || custTrdInfo.getTrdProvince().equals("-1")){
            log.error("custTrdInfo:{} haven't valid city :{} or province :{} just ignore it ", custTrdInfo.getId(),
                custTrdInfo.getDebtCity(), custTrdInfo.getTrdProvince());
            continue;
          }
          cityName = custRegionDetailMapper.selectByPrimaryKey(Long.valueOf(custTrdInfo.getTrdProvince())).getName();
        }else{
          try{
            CustRegionDetail custRegionDetail =
                custRegionDetailMapper.selectByPrimaryKey(Long.valueOf(custTrdInfo.getDebtCity()));
            cityName = custRegionDetail.getName();
            if(cityName.equals("市辖区")){
              custRegionDetail = custRegionDetailMapper.selectByPrimaryKey(custRegionDetail.getParentId());
              cityName = custRegionDetail.getName();

            }
          }catch (Exception ex){
            log.error("Failed to find city for:{}", custTrdInfo.getDebtCity());
            continue;
          }

        }

        debtTotalAmount += custTrdInfo.getTotalDebtAmount() > 0 ? custTrdInfo.getTotalDebtAmount():0;
        trdTotalAmount += custTrdInfo.getTrdAmount() > 0 ? custTrdInfo.getTrdAmount():0;

        if(custTrdInfo.getItemType() == null){
          continue;
        }
        itemTypeName = ItemTypeEnum.lookupByIdUntil(custTrdInfo.getItemType()).getName();
        if(!invest2Counts.containsKey(itemTypeName)){
          invest2Counts.put(itemTypeName, 1);
        }else{
          invest2Counts.put(itemTypeName, invest2Counts.get(itemTypeName)+1);
        }

        if(!city2Counts.containsKey(cityName)){
          city2Counts.put(cityName, 1);
        }else {
          city2Counts.put(cityName, city2Counts.get(cityName)+1);
        }
      }
      custTrdInfoExcelVo.setDebtTotalAmount( debtTotalAmount > 0 ? debtTotalAmount: -1);
      //拍卖系统金额精确到分
      custTrdInfoExcelVo.setTrdTotalAmount(trdTotalAmount > 0? trdTotalAmount/100:-1);
      custTrdInfoExcelVo.setIntrestCities(city2Counts);
      custTrdInfoExcelVo.setInvestType2Counts(invest2Counts);
      custTrdInfoExcelVos.add(custTrdInfoExcelVo);
    }

    return custTrdInfoExcelVos;
  }

  @Override
  public Long getCmpyTradeCount(QueryParam queryParam) {

//    CustTrdCmpyExample custTrdCmpyExample = SQLUtils.getCustCmpyTrdExample(queryParam);
//    CustTrdCmpyExample custTrdCmpyExample = new CustTrdCmpyExample();
    CustTrdCmpyExtExample custTrdCmpyExtExample = new CustTrdCmpyExtExample();
    String filterBy = SQLUtils.getFilterByForCustTrd(queryParam);
    String whereClause = SQLUtils.getTrdCmpyExtWhereClause(queryParam);
    if(!StringUtils.isEmpty(whereClause)){
      custTrdCmpyExtExample.setWhereClause(whereClause);
    }
//    filterBy = filterBy + " and ctc.cmpy_phone > -1 ";
    Long queryResult = -1L;
//    custTrdCmpyExample.getOredCriteria().forEach(item -> custTrdCmpyExtExample.getOredCriteria().add(item));
    if(!StringUtils.isEmpty(filterBy)){
      custTrdCmpyExtExample.setFilterByClause(filterBy);
      queryResult = custTrdCmpyExtMapper.countByFilter(custTrdCmpyExtExample);
    }else{
      if(queryParam.isAllowNoTrd()){
        queryResult = custTrdCmpyExtMapper.countByFilterAllowNoTrd(custTrdCmpyExtExample);
      }else{
        queryResult = custTrdCmpyExtMapper.countByFilter(custTrdCmpyExtExample);
      }
    }
    return queryResult;

  }

  @Override
  public List<CustTrdInfoVo> queryPersonTradePage(int offset, int size, QueryParam queryParam,
      Map<String, Direction> orderByParam) throws Exception {


    List<CustTrdPersonTrdExt> custTrdPersonTrdExts = queryPerson(offset, size, queryParam, orderByParam);


    return convertPersonToVoes(custTrdPersonTrdExts);
  }

  private List<CustTrdPersonTrdExt> queryPerson(int offset, int size, QueryParam queryParam,
      Map<String, Direction> orderByParam) throws Exception {
    String orderBy = SQLUtils.getOrderBy(orderByParam);
    CustTrdPersonExtExample custTrdPersonExtExample = SQLUtils.getCustPersonTrdExample(queryParam);
    custTrdPersonExtExample.setOrderByClause(orderBy);
    RowBounds rowBounds = new RowBounds(offset, size);
    String filterBy = SQLUtils.getFilterByForCustTrd(queryParam);
    custTrdPersonExtExample.setLimitByClause(String.format(" %d , %d ", offset, size));
    List<CustTrdPersonTrdExt> custTrdPersonTrdExtList = new ArrayList<>();
//    filterBy = filterBy + " and ctp.mobile_num > -1 ";
    List<Long> ids = new ArrayList<>();
    if(!StringUtils.isEmpty(filterBy)) {
      custTrdPersonExtExample.setFilterByClause(filterBy);
      ids = custTrdPersonExtMapper.selectByPreFilter(custTrdPersonExtExample);
    }else{
      if(queryParam.isAllowNoTrd()){
        ids = custTrdPersonExtMapper.selectByPreFilterAllowNoTrd(custTrdPersonExtExample);
      }else{
        ids = custTrdPersonExtMapper.selectByPreFilter(custTrdPersonExtExample);
      }
    }


    if(CollectionUtils.isEmpty(ids)){
      log.error("Failed to get results for filter:{}", filterBy);
      return custTrdPersonTrdExtList;
    }
    StringBuilder sb = new StringBuilder(" ctp.id in ( ");
    ids.forEach(item -> sb.append(item).append(","));
    sb.setLength(sb.length() -1);
    sb.append(")");
    custTrdPersonExtExample.setWhereClause(sb.toString());
    custTrdPersonExtExample.setFilterByClause(null);

    custTrdPersonTrdExtList = custTrdPersonExtMapper.selectByExample(custTrdPersonExtExample);

    //custTrdCmpyTrdExts 转map
    Map<Long, CustTrdPersonTrdExt> cmpyTrdExtMap = custTrdPersonTrdExtList.stream().collect(Collectors.toMap(CustTrdPersonTrdExt::getId, custTrdPersonTrdExt -> custTrdPersonTrdExt));
    //根据id重新排列
    List<CustTrdPersonTrdExt> custTrdPersonTrdExtArrayList = new ArrayList<>();
    for (Long id : ids) {
      custTrdPersonTrdExtArrayList.add(cmpyTrdExtMap.get(id));
    }

    return  custTrdPersonTrdExtArrayList;
  }

  @Override
  public List<CustTrdInfoExcelVo> queryPersonTrade(int offset, int size,  QueryParam queryParam, Map<String,
      Direction> orderByParam)
      throws Exception {

    List<CustTrdPersonTrdExt> custTrdPersonTrdExtsTotal = new ArrayList<>();
    if(size > 20 && offset == 0 ){
      log.info("need to make the results into pages");
      int pageSize = 10;
      int cnt = 0;
      while(cnt >= 0 ){
        List<CustTrdPersonTrdExt> custTrdPersonTrdExts = queryPerson( offset,  pageSize,  queryParam,  orderByParam);
        if(!StringUtils.isEmpty(queryParam.getCity()) && queryParam.getCity().length() >=5){
          custTrdPersonTrdExts.sort(new Comparator<CustTrdPersonTrdExt>() {
            @Override
            public int compare(CustTrdPersonTrdExt o1, CustTrdPersonTrdExt o2) {
              int o1cnt = getCountCustTrdInfoByCityLike(o1.getCustTrdInfoList(), queryParam.getCity().substring(0,2));
              int o2cnt = getCountCustTrdInfoByCityLike(o2.getCustTrdInfoList(), queryParam.getCity().substring(0,2));
              return o1cnt - o2cnt;
            }
          });
        }

        if(custTrdPersonTrdExts.size() < pageSize  || cnt*pageSize >= size ){
          //it is last one
          cnt = -2;
          custTrdPersonTrdExtsTotal.addAll(custTrdPersonTrdExts);
          break;
        }
        cnt ++;
        offset = offset + pageSize;

        custTrdPersonTrdExtsTotal.addAll(custTrdPersonTrdExts);
      }

    }else{
      custTrdPersonTrdExtsTotal = queryPerson( offset,  size,  queryParam,  orderByParam);
    }


    return convertPersonToExcelVoes(custTrdPersonTrdExtsTotal);
  }

  private List<CustTrdInfoExcelVo> convertPersonToExcelVoes(List<CustTrdPersonTrdExt> custTrdPersonTrdExts) {
    List<CustTrdInfoExcelVo> custTrdInfoExcelVos = new ArrayList<>();
    for(CustTrdPersonTrdExt custTrdPersonTrdExt: custTrdPersonTrdExts){
      CustTrdInfoExcelVo custTrdInfoExcelVo = new CustTrdInfoExcelVo();
      custTrdInfoExcelVo.setCustId(custTrdPersonTrdExt.getId());
      custTrdInfoExcelVo.setAddress(String.format("%s",custTrdPersonTrdExt.getCustTrdPerson().getAddr()));
      custTrdInfoExcelVo.setCustName(custTrdPersonTrdExt.getCustTrdPerson().getName());
      custTrdInfoExcelVo.setPhone(String.format("%s;%s",
          custTrdPersonTrdExt.getCustTrdPerson().getMobilePrep(),
          custTrdPersonTrdExt.getCustTrdPerson().getPhonePrep()));
      custTrdInfoExcelVo.setTrdCount(custTrdPersonTrdExt.getCustTrdInfoList().size());
      Long debtTotalAmount = 0L;
      Long trdTotalAmount = 0L;
      Map<String, Integer> invest2Counts = new HashMap<>();
      Map<String, Integer> city2Counts = new HashMap<>();
      String cityName= "";
      String itemTypeName;
      for(CustTrdInfo custTrdInfo: custTrdPersonTrdExt.getCustTrdInfoList()){
        debtTotalAmount += custTrdInfo.getTotalDebtAmount() > 0 ? custTrdInfo.getTotalDebtAmount():0;
        trdTotalAmount += custTrdInfo.getTrdAmount() > 0 ? custTrdInfo.getTrdAmount():0;
        if(custTrdInfo.getItemType() == null){
          continue;
        }
        itemTypeName = ItemTypeEnum.lookupByIdUntil(custTrdInfo.getItemType()).getName();
        if(!StringUtils.isEmpty(custTrdInfo.getDebtCity()) && Long.valueOf(custTrdInfo.getDebtCity()) > -1L){
          CustRegionDetail custRegion =
              custRegionDetailMapper.selectByPrimaryKey(Long.valueOf(custTrdInfo.getDebtCity()));
          if(null != custRegion){
            cityName = custRegion.getName();
          }else{
            log.error("Failed to get cityName for :{}", custTrdInfo.getDebtCity());
          }
        }else{
          log.error("Failed to get cityName for :{}", custTrdInfo.getDebtCity());
        }

        if(!invest2Counts.containsKey(itemTypeName)){
          invest2Counts.put(itemTypeName, 1);
        }else{
          invest2Counts.put(itemTypeName, invest2Counts.get(itemTypeName)+1);
        }
        if(!city2Counts.containsKey(cityName)){
          city2Counts.put(cityName, 1);
        }else {
          city2Counts.put(cityName, city2Counts.get(cityName)+1);
        }
      }
      custTrdInfoExcelVo.setDebtTotalAmount( debtTotalAmount > 0? debtTotalAmount: -1);
      //拍卖系统金额精确到分
      custTrdInfoExcelVo.setTrdTotalAmount( trdTotalAmount > 0? trdTotalAmount/100: -1);
      custTrdInfoExcelVo.setIntrestCities(city2Counts);
      custTrdInfoExcelVo.setInvestType2Counts(invest2Counts);
      custTrdInfoExcelVos.add(custTrdInfoExcelVo);
    }

    return custTrdInfoExcelVos;
  }


  @Override
  public Long getPersonTradeCount(QueryParam queryParam) {
    CustTrdPersonExample custTrdPersonExample = SQLUtils.getCustPersonTrdExample(queryParam);

    Long queryResult = -1L;

    String filterBy = SQLUtils.getFilterByForCustTrd(queryParam);
//    filterBy = filterBy + " and ctp.mobile_num > -1 ";
    CustTrdPersonExtExample custTrdPersonExtExample = new CustTrdPersonExtExample();
    String whereClause = " ctp.status != 2";
    custTrdPersonExtExample.setWhereClause(whereClause);
    custTrdPersonExample.getOredCriteria().forEach(item -> custTrdPersonExtExample.getOredCriteria().add(item));
    if(!StringUtils.isEmpty(filterBy)){
      custTrdPersonExtExample.setFilterByClause(filterBy);
      queryResult = custTrdPersonExtMapper.countByFilter(custTrdPersonExtExample);
    }else{
      if(queryParam.isAllowNoTrd()){
        queryResult = custTrdPersonExtMapper.countByFilterAllowNoTrd(custTrdPersonExtExample);
      }else{
        queryResult = custTrdPersonExtMapper.countByFilter(custTrdPersonExtExample);
      }
    }


    return queryResult;
  }

  @Override
  public CustTrdCmpy getCompany(Long companyId) {

    CustTrdCmpy custTrdCmpy = custTrdCmpyMapper.selectByPrimaryKey(companyId);
    //修改的名称不用给前端
    custTrdCmpy.setCmpyNameUpdate("-1");
    return custTrdCmpy;
  }

  @Override
  public CustTrdPerson getPerson(Long personId) {
    return custTrdPersonMapper.selectByPrimaryKey(personId);
  }

  @Override
  public CustTrdPersonVo getPersonEditable(Long personId) {
    CustTrdPerson custTrdPerson =  custTrdPersonMapper.selectByPrimaryKey(personId);
    CustTrdPersonVo custTrdPersonVo = new CustTrdPersonVo();
    AmcBeanUtils.copyProperties(custTrdPerson, custTrdPersonVo);
    if(!StringUtils.isEmpty(custTrdPerson.getMobileHistory()) && (custTrdPerson.getMobileHistory().contains(SEP_INDICATOR_EDITABLE)
    ||custTrdPerson.getMobileHistory().contains(SEP_INDICATOR_ORIGINAL))){
      List<String> listEditable = new ArrayList<>();
      if(custTrdPerson.getMobileHistory().contains(SEP_INDICATOR_EDITABLE)){
        listEditable = Lists.newArrayList(custTrdPerson.getMobileHistory().split(SEP_INDICATOR_EDITABLE_USED));

      }else{
        listEditable.add(custTrdPerson.getMobileHistory());
      }
      if(!CollectionUtils.isEmpty(listEditable)){
        for(int idx = listEditable.size() -1; idx >= 0 ; idx--){
          if(listEditable.get(idx).contains(SEP_INDICATOR_ORIGINAL)){
            String[] origMobileNums =  listEditable.get(idx).split(SEP_INDICATOR_ORIGINAL_USED);
            custTrdPersonVo.setOriginPhoneNum(origMobileNums[1]);
            listEditable.set(idx, origMobileNums[0]);
            break;
          }
        }
      }
      custTrdPersonVo.setHistoryPhoneNums(listEditable);
    }else{
      custTrdPersonVo.setOriginPhoneNum(custTrdPerson.getMobilePrep());
    }

    if(!StringUtils.isEmpty(custTrdPerson.getHistoryAddr())
        &&(custTrdPerson.getHistoryAddr().contains(SEP_INDICATOR_EDITABLE) || custTrdPerson.getHistoryAddr().contains(SEP_INDICATOR_ORIGINAL))){
      List<String> listEditableAddrs = new ArrayList<>();
      if(custTrdPerson.getHistoryAddr().contains(SEP_INDICATOR_EDITABLE)){
        listEditableAddrs =
            Lists.newArrayList(custTrdPerson.getHistoryAddr().split(SEP_INDICATOR_EDITABLE_USED));
      }else{
        listEditableAddrs.add(custTrdPerson.getHistoryAddr());
      }

      if(!CollectionUtils.isEmpty(listEditableAddrs)){
        for(int idx = listEditableAddrs.size() -1; idx >= 0 ; idx--){
          if(listEditableAddrs.get(idx).contains(SEP_INDICATOR_ORIGINAL)){
            String[] origAddrs =  listEditableAddrs.get(idx).split(SEP_INDICATOR_ORIGINAL_USED);
            custTrdPersonVo.setOriginAddrs(origAddrs[1]);
            listEditableAddrs.set(idx, origAddrs[0]);
            break;
          }
        }
      }
      custTrdPersonVo.setHistoryAddrs(listEditableAddrs);
    }else{
      custTrdPersonVo.setOriginAddrs(custTrdPerson.getAddr());
    }

    return custTrdPersonVo;
  }

  private List<CustTrdInfoVo> convertCmpyToVoes(List<CustTrdCmpyTrdExt> custTrdCmpyTrdExts) {
    List<CustTrdInfoVo> custTrdInfoVos = new ArrayList<>();
    for(int idx = 0 ; idx < custTrdCmpyTrdExts.size(); idx ++){
      CustTrdInfoVo custTrdInfoVo = new CustTrdInfoVo();
      custTrdInfoVo.setCustId(custTrdCmpyTrdExts.get(idx).getId());
      custTrdInfoVo.setAddress(String.format("%s;%s",custTrdCmpyTrdExts.get(idx).getCustTrdCmpy().getCmpyAddr(),
          custTrdCmpyTrdExts.get(idx).getCustTrdCmpy().getAnnuReptAddr()));
      custTrdInfoVo.setAddressUpdate(custTrdCmpyTrdExts.get(idx).getCustTrdCmpy().getCmpyAddrUpdate());
      custTrdInfoVo.setCrawledStatus(custTrdCmpyTrdExts.get(idx).getCustTrdCmpy().getCrawledStatus());
      custTrdInfoVo.setCustName(custTrdCmpyTrdExts.get(idx).getCustTrdCmpy().getCmpyName());
      custTrdInfoVo.setCustCity(custTrdCmpyTrdExts.get(idx).getCustTrdCmpy().getCmpyProvince());
      custTrdInfoVo.setPhonePrep(creatPhonePrep(custTrdCmpyTrdExts.get(idx).getCustTrdCmpy().getCmpyPhone(),
              custTrdCmpyTrdExts.get(idx).getCustTrdCmpy().getAnnuReptPhone()));
      custTrdInfoVo.setPhone(String.format("%s;%s",custTrdCmpyTrdExts.get(idx).getCustTrdCmpy().getCmpyPhone(),
          custTrdCmpyTrdExts.get(idx).getCustTrdCmpy().getAnnuReptPhone()));
      custTrdInfoVo.setPhoneUpdate(custTrdCmpyTrdExts.get(idx).getCustTrdCmpy().getCmpyPhoneUpdate());
      custTrdInfoVo.setTrdCount(custTrdCmpyTrdExts.get(idx).getCustTrdInfoList().size());
      Long totalDebtAmount = 0L;
      Long totalTrdAmount = 0L;
      Set<String> cities = new HashSet<>();
      Map<Integer, Integer> invest2Counts = new HashMap<>();
      Map<String, Integer> city2Counts = new HashMap<>();
      for(CustTrdInfo custTrdInfo: custTrdCmpyTrdExts.get(idx).getCustTrdInfoList()){
        totalDebtAmount += custTrdInfo.getTotalDebtAmount() > 0 ? custTrdInfo.getTotalDebtAmount() : 0 ;
        totalTrdAmount += custTrdInfo.getTrdAmount() > 0 ? custTrdInfo.getTrdAmount() : 0;
        cities.add(custTrdInfo.getDebtCity());
        if(custTrdInfo.getItemType() == null){
          continue;
        }
        if(!invest2Counts.containsKey(custTrdInfo.getItemType())){
          invest2Counts.put(custTrdInfo.getItemType(), 1);
        }else{
          invest2Counts.put(custTrdInfo.getItemType(), invest2Counts.get(custTrdInfo.getItemType())+1);
        }

        if(!city2Counts.containsKey(custTrdInfo.getDebtCity())){
          city2Counts.put(custTrdInfo.getDebtCity(), 1);
        }else {
          city2Counts.put(custTrdInfo.getDebtCity(), city2Counts.get(custTrdInfo.getDebtCity())+1);
        }
      }
      custTrdInfoVo.setDebtTotalAmount( totalDebtAmount > 0 ? totalDebtAmount: -1);
      //拍卖系统金额精确到分
      custTrdInfoVo.setTrdTotalAmount(totalTrdAmount > 0? totalTrdAmount/100 : -1);
      custTrdInfoVo.setIntrestCities(city2Counts);
      custTrdInfoVo.setInvestType2Counts(invest2Counts);
      custTrdInfoVos.add(custTrdInfoVo);
    }

    return custTrdInfoVos;
  }

  /**
   * 拼接PhonePrep
   * @param cmpyPhone
   * @param annuReptPhone
   * @return
   */
  private String creatPhonePrep (String cmpyPhone, String annuReptPhone){
    StringBuffer sbPhoneNew = new StringBuffer();
    if(!"-1".equals(cmpyPhone) && !StringUtils.isEmpty(sbPhoneNew)){
      sbPhoneNew.append(cmpyPhone);
    }
    if(sbPhoneNew.length()>0){
      sbPhoneNew.append(";");
    }
    if(!"-1".equals(annuReptPhone) && !StringUtils.isEmpty(annuReptPhone)){
      sbPhoneNew.append(annuReptPhone);
    }
    return sbPhoneNew.toString();
  }

  private List<CustTrdInfoVo> convertPersonToVoes(List<CustTrdPersonTrdExt> custTrdPersonTrdExtList) {
    List<CustTrdInfoVo> custTrdInfoVos = new ArrayList<>();
    for(CustTrdPersonTrdExt custTrdPersonTrdExt: custTrdPersonTrdExtList){
      CustTrdInfoVo custTrdInfoVo = new CustTrdInfoVo();
      custTrdInfoVo.setCustId(custTrdPersonTrdExt.getId());
      custTrdInfoVo.setAddress(String.format("%s",custTrdPersonTrdExt.getCustTrdPerson().getAddr()));
      custTrdInfoVo.setCustName(custTrdPersonTrdExt.getCustTrdPerson().getName());
      custTrdInfoVo.setPhone(String.format("%s;%s",
          custTrdPersonTrdExt.getCustTrdPerson().getMobilePrep(),
          custTrdPersonTrdExt.getCustTrdPerson().getPhonePrep()));
      custTrdInfoVo.setMobilePrep(custTrdPersonTrdExt.getCustTrdPerson().getMobilePrep());
      custTrdInfoVo.setMobileUpdate(custTrdPersonTrdExt.getCustTrdPerson().getMobileUpdate());
      custTrdInfoVo.setPhonePrep(custTrdPersonTrdExt.getCustTrdPerson().getPhonePrep());
      custTrdInfoVo.setPhoneUpdate(custTrdPersonTrdExt.getCustTrdPerson().getPhoneUpdate());
      custTrdInfoVo.setTrdCount(custTrdPersonTrdExt.getCustTrdInfoList().size());
      Long totalDebtAmount = 0L;
      Long totalTrdAmount = 0L;
      Set<String> cities = new HashSet<>();
      Map<Integer, Integer> invest2Counts = new HashMap<>();
      Map<String, Integer> city2Counts = new HashMap<>();
      for(CustTrdInfo custTrdInfo: custTrdPersonTrdExt.getCustTrdInfoList()){
        totalDebtAmount += custTrdInfo.getTotalDebtAmount() > 0? custTrdInfo.getTotalDebtAmount():0;
        totalTrdAmount += custTrdInfo.getTrdAmount() > 0? custTrdInfo.getTrdAmount():0;
        cities.add(custTrdInfo.getDebtCity());
        if(custTrdInfo.getItemType() == null){
          continue;
        }
        if(!invest2Counts.containsKey(custTrdInfo.getItemType())){
          invest2Counts.put(custTrdInfo.getItemType(), 1);
        }else{
          invest2Counts.put(custTrdInfo.getItemType(), invest2Counts.get(custTrdInfo.getItemType())+1);
        }
        if(!city2Counts.containsKey(custTrdInfo.getDebtCity())){
          city2Counts.put(custTrdInfo.getDebtCity(), 1);
        }else {
          city2Counts.put(custTrdInfo.getDebtCity(), city2Counts.get(custTrdInfo.getDebtCity())+1);
        }
      }
      custTrdInfoVo.setDebtTotalAmount( totalDebtAmount > 0 ? totalDebtAmount: -1);
      //拍卖系统金额精确到分
      custTrdInfoVo.setTrdTotalAmount( totalTrdAmount > 0 ? totalTrdAmount/100: -1);
      custTrdInfoVo.setIntrestCities(city2Counts);
      custTrdInfoVo.setInvestType2Counts(invest2Counts);
      custTrdInfoVos.add(custTrdInfoVo);
    }

    return custTrdInfoVos;
  }
  @Override
  public List<CustInfoGeoNear> queryAllNearByCusts(GeoJsonPoint geoJsonPoint){
    List<CustInfoGeoNear> custInfoGeoNears = new ArrayList<>();
    custInfoGeoNears.add(queryNearByCusts(geoJsonPoint, new Integer[]{0, 100}));
    custInfoGeoNears.add(queryNearByCusts(geoJsonPoint, new Integer[]{100, 200}));
    custInfoGeoNears.add(queryNearByCusts(geoJsonPoint, new Integer[]{200, 300}));
    return custInfoGeoNears;
  }

  @Override
  public boolean modCustPerson(CustTrdPersonVo custTrdPersonVo) {
    if(CollectionUtils.isEmpty(custTrdPersonVo.getHistoryAddrs())){
      log.error("no history addrs");
      custTrdPersonVo.setHistoryAddr(custTrdPersonVo.getOriginAddrs());
    }else{
      StringBuilder historyAddrs =
          new StringBuilder(Joiner.on(SEP_INDICATOR_EDITABLE).join(custTrdPersonVo.getHistoryAddrs()));
      historyAddrs.append(SEP_INDICATOR_ORIGINAL).append(custTrdPersonVo.getOriginAddrs());
      custTrdPersonVo.setHistoryAddr(historyAddrs.toString());
    }
    if(CollectionUtils.isEmpty(custTrdPersonVo.getHistoryPhoneNums())){
      log.error("no history mobiles");
      custTrdPersonVo.setMobileHistory(custTrdPersonVo.getOriginPhoneNum());
    }else{
      StringBuilder historyMobileNums =
          new StringBuilder(Joiner.on(SEP_INDICATOR_EDITABLE).join(custTrdPersonVo.getHistoryPhoneNums()));
      historyMobileNums.append(SEP_INDICATOR_ORIGINAL).append(custTrdPersonVo.getOriginPhoneNum());
      custTrdPersonVo.setMobileHistory(historyMobileNums.toString());
    }

    if(null != custTrdPersonVo.getHistoryPhoneNums() && !CollectionUtils.isEmpty(custTrdPersonVo.getHistoryPhoneNums())){
      updateCustPersonTrdRelations(custTrdPersonVo.getHistoryPhoneNums(), custTrdPersonVo.getMobilePrep(),
          custTrdPersonVo.getName(), custTrdPersonVo.getId());
    }

    CustTrdPerson custTrdPerson = new CustTrdPerson();
    AmcBeanUtils.copyProperties(custTrdPersonVo, custTrdPerson);
    custTrdPersonMapper.updateByPrimaryKeySelective(custTrdPerson);
    return true;
  }

  @Override
  public List<CustTrdCmpy> getCmpyFromDate(Date beginDate) {
    CustTrdCmpyExample custTrdCmpyExample = new CustTrdCmpyExample();
    custTrdCmpyExample.createCriteria().andCreateTimeGreaterThan(beginDate);
    List<CustTrdCmpy> custTrdCmpyList = custTrdCmpyMapper.selectByExample(custTrdCmpyExample);
    return custTrdCmpyList;
  }

  @Override
  public List<CustTrdPerson> getPersonFromDate(Date beginDate) {
    CustTrdPersonExample custTrdPersonExample = new CustTrdPersonExample();
    custTrdPersonExample.createCriteria().andCreateTimeGreaterThan(beginDate)
        .andStatusNotEqualTo(PresonStatusEnum.MERGED_STATUS.getId());
    List<CustTrdPerson> custTrdPersonList = custTrdPersonMapper.selectByExample(custTrdPersonExample);
    return custTrdPersonList;
  }

  @Override
  public CustsCountByTime getCustCountByTime(LocalDateTime startTime) {
    Date startDate = AmcDateUtils.getDateFromLocalDate(startTime);
    CustTrdCmpyExample custTrdCmpyExample = new CustTrdCmpyExample();
    custTrdCmpyExample.createCriteria().andCreateTimeGreaterThan(startDate);
    List<CustTrdCmpy> custTrdCmpyList =  custTrdCmpyMapper.selectByExample(custTrdCmpyExample);

    CustTrdPersonExample custTrdPersonExample = new CustTrdPersonExample();
    custTrdPersonExample.createCriteria().andCreateTimeGreaterThan(startDate)
        .andStatusNotEqualTo(PresonStatusEnum.MERGED_STATUS.getId());

    List<CustTrdPerson> custTrdPersonList = custTrdPersonMapper.selectByExample(custTrdPersonExample);

    CustsCountByTime custsCountByTime = new CustsCountByTime();
    custsCountByTime.setStartTime(startTime);
    custsCountByTime.setNewCustCmpiesByStartTime(custTrdCmpyList);
    custsCountByTime.setNewCustPersonByStartTime(custTrdPersonList);
    return custsCountByTime;
  }

  @Override
  public void patchDuplicateCmpyName() {
    List<Map> dupCmpyNames = custTrdCmpyExtMapper.selectDuplicateNameCmpy();
    if(CollectionUtils.isEmpty(dupCmpyNames)){
      return;
    }
    CustTrdCmpyExample custTrdCmpyExample = new CustTrdCmpyExample();
    CustTrdInfoExample custTrdInfoExample = new CustTrdInfoExample();
    for(Map item: dupCmpyNames){
      custTrdCmpyExample.clear();
      custTrdCmpyExample.createCriteria().andCmpyNameEqualTo((String)item.get("cmpy_name"));
      List<CustTrdCmpy> custTrdCmpyList = custTrdCmpyMapper.selectByExample(custTrdCmpyExample);
      Long bestCmpyId = -1L;
      int maxQuality = 0;
      for(CustTrdCmpy custTrdCmpy: custTrdCmpyList){
        if(custTrdCmpy.getDataQuality() > maxQuality  ){
          maxQuality = custTrdCmpy.getDataQuality();
          bestCmpyId = custTrdCmpy.getId();
        }
      }
      if(bestCmpyId <= 0 ){
        continue;
      }

      for(CustTrdCmpy custTrdCmpy: custTrdCmpyList){
        custTrdInfoExample.clear();
        if(custTrdCmpy.getId() == bestCmpyId){
          //we use bestCmpyId
          continue;
        }
        custTrdInfoExample.createCriteria().andBuyerIdEqualTo(custTrdCmpy.getId()).andBuyerTypeEqualTo(CustTypeEnum.COMPANY.getId());
        List<CustTrdInfo> custTrdInfos = custTrdInfoMapper.selectByExample(custTrdInfoExample);
        for(CustTrdInfo custTrdInfo: custTrdInfos){
          custTrdInfo.setBuyerId(bestCmpyId);
          custTrdInfoMapper.updateByPrimaryKeySelective(custTrdInfo);
        }
        if(CollectionUtils.isEmpty(custTrdInfos)){
          custTrdCmpyMapper.deleteByPrimaryKey(custTrdCmpy.getId());
        }
      }
    }
  }

  @Scheduled(cron = "${spring.task.scheduling.cronExpCmpyInfo}")
  public void patchCmpyInfo() {
    String province;
    if(provinceToHandle != null){
      province = provinceToHandle.get();
    }else{
      province = null;
    }
    CustTrdCmpyExample custTrdCmpyExample = new CustTrdCmpyExample();
    custTrdCmpyExample.setOrderByClause(" id desc ");
    if(StringUtils.isEmpty(province)){
      custTrdCmpyExample.createCriteria().andLegalReptiveEqualTo("-1");

    }else{
      custTrdCmpyExample.createCriteria().andCmpyProvinceEqualTo(province);
    }
    int offset = 0;
    int pageSize = 100;
    RowBounds rowBounds = new RowBounds(offset, pageSize);
    List<CustTrdCmpy> custTrdCmpyList = custTrdCmpyMapper.selectByExampleWithRowbounds(custTrdCmpyExample, rowBounds);
    boolean keepGoing = true;
    if(CollectionUtils.isEmpty(custTrdCmpyList)){
      keepGoing = false;
      return;
    }

    List<CustTrdCmpy> needPatchList = new ArrayList<>();
    while(keepGoing){
      for(CustTrdCmpy custTrdCmpy: custTrdCmpyList){

        //need patch province info
        if(StringUtils.isEmpty(custTrdCmpy.getCmpyName()) || custTrdCmpy.getCmpyName().equals("-1")){
          continue;
        }

        needPatchList.add(custTrdCmpy);

      }
      offset += pageSize;
      rowBounds = new RowBounds(offset, pageSize);
      custTrdCmpyList = custTrdCmpyMapper.selectByExampleWithRowbounds(custTrdCmpyExample, rowBounds);
      if(CollectionUtils.isEmpty(custTrdCmpyList)){
        keepGoing = false;
        break;
      }

    }
    for(CustTrdCmpy custTrdCmpy: needPatchList){
      CustCmpyInfoFromSync custCmpyInfoFromSync = syncService.getCmpyInfoByName(custTrdCmpy.getCmpyName());
      if(custCmpyInfoFromSync == null || StringUtils.isEmpty(custCmpyInfoFromSync.getCmpyProvince())){
        continue;
      }
      syncService.copyCmpySync2CmpyInfo(custCmpyInfoFromSync, custTrdCmpy);
      custTrdCmpyMapper.updateByPrimaryKeySelective(custTrdCmpy);
    }
    needPatchList = null;
  }


  @Override
  public void patchCmpyProvince(String province) {
    synchronized (provinceToHandle){
      if(provinceToHandle == null){
        synchronized (provinceToHandle){
          provinceToHandle = new ThreadLocal<>();
        }
      }
      provinceToHandle.set(province);
    }

  }

  @Override
  public void patchAddCmpyProvince() {
    RestTemplate restTemplate = CommonHandler.getRestTemplate();
    //查询没有省编码的公司
    CustTrdCmpyExtExample custTrdCmpyExtExample = new CustTrdCmpyExtExample();

    custTrdCmpyExtExample.clear();;
    custTrdCmpyExtExample.createCriteria().andCmpyProvinceEqualTo("-1")
        .andCmpyAddrNotEqualTo("-1").andCmpyAddrNotEqualTo("");
    List<CustTrdCmpy> custTrdCmpieListHasAdder= custTrdCmpyMapper.selectByExample(custTrdCmpyExtExample);

    for (CustTrdCmpy custTrdCmpy : custTrdCmpieListHasAdder) {
      AdressResp adressResp = null;
      try {
        adressResp = restTemplate
            .exchange(String.format(getAddressCodeByAddress, custTrdCmpy.getCmpyAddr()),
                HttpMethod.GET, null, new ParameterizedTypeReference<AdressResp>() {
                }).getBody();
      } catch (Exception e) {
        log.error("查询地址信息出错,url:{},错误是：{}", String.format(getAddressCodeByAddress, custTrdCmpy.getCmpyAddr()), e);
      }
      if (null !=adressResp && null != adressResp.getStatus() && "1".equals(adressResp.getStatus())) {
        CustTrdCmpy custTrdCmpyNew = new CustTrdCmpy();
        custTrdCmpyNew.setCmpyProvince(
            adressResp.getResult().getStatsResult().getProvince().get(0).substring(0, 6));
        custTrdCmpyNew.setId(custTrdCmpy.getId());
        log.info("公司省地址：{}，id:{},公司名称：{}",custTrdCmpyNew.getCmpyProvince(),custTrdCmpyNew.getId(),custTrdCmpy.getCmpyName());
        custTrdCmpyMapper.updateByPrimaryKeySelective(custTrdCmpyNew);
      }else {
        log.info("查询地址信息出错,url:{},返回参数是：{}，公司名称：{}", String.format(getAddressCodeByAddress,
            custTrdCmpy.getCmpyAddr()), adressResp, custTrdCmpy.getCmpyName());
      }
    }


    custTrdCmpyExtExample.clear();
    custTrdCmpyExtExample.createCriteria().andCmpyProvinceEqualTo("-1");
    List<CustTrdCmpy> custTrdCmpieList= custTrdCmpyMapper.selectByExample(custTrdCmpyExtExample);
    for (CustTrdCmpy custTrdCmpy:custTrdCmpieList) {
      //根据名称查询爬虫库
      String url = String.format(getCompanyInfoByNameUrl, custTrdCmpy.getCmpyName());
      CustCmpyInfoFromSync custCmpyInfoFromSync = null;
      try {
        custCmpyInfoFromSync = restTemplate
            .getForEntity(url, CustCmpyInfoFromSync.class).getBody();
        log.info("查询爬虫公司信息是：{},url:{}",custCmpyInfoFromSync,url);
      } catch (Exception e) {
        log.error("查询爬虫公司信息出错，url:{},错误是：{}",url,e);
      }
      //有公司信息，有注册地址的直接更新数据
      if (null != custCmpyInfoFromSync && !StringUtils.isEmpty(custCmpyInfoFromSync.getCmpyAddr())) {
        //更新zcc公司省编码信息
        CustTrdCmpy custTrdCmpyNew = new CustTrdCmpy();
        custTrdCmpyNew.setId(custTrdCmpy.getId());
        custTrdCmpyNew.setUniSocialCode(custCmpyInfoFromSync.getUniSocialCode());
        custTrdCmpyNew.setCmpyPhone(custCmpyInfoFromSync.getCmpyPhone());
        custTrdCmpyNew.setCmpyAddr(custCmpyInfoFromSync.getCmpyAddr());
        custTrdCmpyNew.setAnnuReptPhone(custCmpyInfoFromSync.getAnnuReptPhone());
        custTrdCmpyNew.setAnnuReptAddr(custCmpyInfoFromSync.getAnnuReptAddr());
        custTrdCmpyNew
            .setCmpyProvince(StringUtils.isEmpty(custCmpyInfoFromSync.getCmpyProvince()) ? "-1" :
                custCmpyInfoFromSync.getCmpyProvince().substring(0, 6));
        custTrdCmpyMapper.updateByPrimaryKeySelective(custTrdCmpyNew);
      }
    }


    custTrdCmpyExtExample.clear();;
    custTrdCmpyExtExample.createCriteria().andCmpyProvinceEqualTo("-1")
        .andCmpyAddrNotEqualTo("-1").andCmpyAddrNotEqualTo("");
    custTrdCmpieListHasAdder= custTrdCmpyMapper.selectByExample(custTrdCmpyExtExample);
    for (CustTrdCmpy custTrdCmpy : custTrdCmpieListHasAdder) {
      AdressResp adressResp = null;
      try {
        adressResp = restTemplate
            .exchange(String.format(getAddressCodeByAddress, custTrdCmpy.getCmpyAddr()),
                HttpMethod.GET, null, new ParameterizedTypeReference<AdressResp>() {
                }).getBody();
      } catch (Exception e) {
        log.error("查询地址信息出错,url:{},错误是：{}", String.format(getAddressCodeByAddress, custTrdCmpy.getCmpyAddr()), e);
      }
      if (null !=adressResp && null != adressResp.getStatus() && "1".equals(adressResp.getStatus())) {
        CustTrdCmpy custTrdCmpyNew = new CustTrdCmpy();
        custTrdCmpyNew.setCmpyProvince(
            adressResp.getResult().getStatsResult().getProvince().get(0).substring(0, 6));
        custTrdCmpyNew.setId(custTrdCmpy.getId());
        log.info("公司省地址：{}，id:{},公司名称：{}",custTrdCmpyNew.getCmpyProvince(),custTrdCmpyNew.getId(),custTrdCmpy.getCmpyName());
        custTrdCmpyMapper.updateByPrimaryKeySelective(custTrdCmpyNew);
      }else {
        log.info("查询地址信息出错,url:{},返回参数是：{}，公司名称：{}", String.format(getAddressCodeByAddress,
            custTrdCmpy.getCmpyAddr()), adressResp, custTrdCmpy.getCmpyName());
      }
    }

  }

  @Override
  public CustTrdFavorVo getCustFavor(Long custId, Integer custType) {
    CustTrdFavorVo custTrdFavorVo = new CustTrdFavorVo();
    custTrdFavorVo.setCustId(custId);
    custTrdFavorVo.setCustType(custType);
    CustTrdInfoExample custTrdInfoExample = new CustTrdInfoExample();

    if(custType == CustTypeEnum.COMPANY.getId()){
      custTrdInfoExample.createCriteria().andBuyerIdEqualTo(custId).andBuyerTypeEqualTo(CustTypeEnum.COMPANY.getId());
    }else if(custType == CustTypeEnum.PERSON.getId()){
      custTrdInfoExample.createCriteria().andBuyerIdEqualTo(custId).andBuyerTypeEqualTo(CustTypeEnum.PERSON.getId());
    }
    List<CustTrdInfo> custTrdInfos =  custTrdInfoMapper.selectByExample(custTrdInfoExample);
    if(CollectionUtils.isEmpty(custTrdInfos)){
      return custTrdFavorVo;
    }
    custTrdFavorVo.setIntrestCities(new HashMap<>());
    custTrdFavorVo.setInvestType2Counts(new HashMap<>());
    String favCity = null;
    String favType = null;
    int favCityCnt = 0;
    int favTypeCnt = 0;
    for(CustTrdInfo custTrdInfo: custTrdInfos){
      favCityCnt = 0;
      favTypeCnt = 0;
      favCity = custTrdInfo.getDebtCity();
      if(custTrdFavorVo.getIntrestCities().containsKey(favCity)){
        favCityCnt = custTrdFavorVo.getIntrestCities().get(favCity);
        favCityCnt = favCityCnt +1;
        custTrdFavorVo.getIntrestCities().put(favCity, favCityCnt);
      }else{
        custTrdFavorVo.getIntrestCities().put(favCity, 1);
      }

      favType = String.format("%s%s",custTrdInfo.getTrdType(), custTrdInfo.getItemType());
      if(custTrdFavorVo.getInvestType2Counts().containsKey(favType)){
        favTypeCnt = custTrdFavorVo.getInvestType2Counts().get(favType);
        favTypeCnt = favTypeCnt + 1;
        custTrdFavorVo.getInvestType2Counts().put(favType, favTypeCnt);
      }else{
        custTrdFavorVo.getInvestType2Counts().put(favType, 1);
      }
    }
    return custTrdFavorVo;
  }

  @Override
  public List<CustTrdCmpy> getAccurateCmpyByName(String companyName) throws Exception {
    if(StringUtils.isEmpty(companyName) || companyName.contains("%")){
      throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_COMPANY_NAME_ERROR);
    }
    //修改公司名称，先判断是否已有该公司
    List<CustTrdCmpy> custTrdCmpieList = commonHandler.queryCmpyByName(companyName);
    return custTrdCmpieList;
  }

  @Override
  public List<CustTrdCmpy> getCompanyByName(String companyName) throws Exception {
    if(StringUtils.isEmpty(companyName) || companyName.contains("%")){
      throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_COMPANY_NAME_ERROR);
    }
    CustTrdCmpyExample custTrdCmpyExample = new CustTrdCmpyExample();
    StringBuilder sb = new StringBuilder("%").append(companyName).append("%");
    custTrdCmpyExample.createCriteria().andCmpyNameLike(sb.toString());
    List<CustTrdCmpy> custTrdCmpyList = custTrdCmpyMapper.selectByExample(custTrdCmpyExample);
    return custTrdCmpyList;
  }

  private boolean updateCustPersonTrdRelations(List<String> histPhoneNumList, String phoneNum, String custName,
      Long currentCustPersonId){
    // find origin cust and search the trd info of the cust, update the trd info ref buyer id to the
    CustTrdPersonExample custTrdPersonExample = new CustTrdPersonExample();
    custTrdPersonExample.createCriteria().andMobilePrepEqualTo(phoneNum).andNameEqualTo(custName)
        .andStatusNotEqualTo(PresonStatusEnum.MERGED_STATUS.getId());
    List<CustTrdPerson> custTrdPeople = custTrdPersonMapper.selectByExample(custTrdPersonExample);
    if(CollectionUtils.isEmpty(custTrdPeople)){
      return true;
    }
    List<CustTrdPerson> merged = new ArrayList();
    for(String custHistPhone: histPhoneNumList){
      custTrdPersonExample.clear();
      custTrdPersonExample.createCriteria().andMobilePrepEqualTo(custHistPhone).andNameEqualTo(custName)
          .andStatusNotEqualTo(PresonStatusEnum.MERGED_STATUS.getId());
      List<CustTrdPerson> custTrdPeopleHistory = custTrdPersonMapper.selectByExample(custTrdPersonExample);
      merged =  ListUtils.union(custTrdPeople, custTrdPeopleHistory);
    }
    custTrdPeople = merged.stream().filter(item -> !item.getId().equals(currentCustPersonId)).collect(Collectors.toList());
    CustTrdInfoExample custTrdInfoExample = new CustTrdInfoExample();
    for(CustTrdPerson custTrdPerson: custTrdPeople){
      custTrdInfoExample.createCriteria().andBuyerIdEqualTo(custTrdPerson.getId()).andBuyerTypeEqualTo(CustTypeEnum.PERSON.getId());
      List<CustTrdInfo> custTrdInfos = custTrdInfoMapper.selectByExample(custTrdInfoExample);
      if(CollectionUtils.isEmpty(custTrdInfos)){
        continue;
      }else{
        for(CustTrdInfo custTrdInfo: custTrdInfos){
          custTrdInfo.setBuyerId(currentCustPersonId);
          custTrdInfoMapper.updateByPrimaryKeySelective(custTrdInfo);
        }
      }
    }
    return true;
  }



  public CustInfoGeoNear queryNearByCusts(GeoJsonPoint geoJsonPoint, Integer[] distances ){

//      Circle area = new Circle(new Point(wxUserGeoInfo.getLatitude(),  wxUserGeoInfo.getLongitude()),
//          new Distance(10, Metrics.KILOMETERS));

    CustInfoGeoNear custInfoGeoNear = new CustInfoGeoNear();
    custInfoGeoNear.setGeoJsonPoint(geoJsonPoint);
    custInfoGeoNear.setDisttance(distances);
    NearQuery nearQuery = null;
    if(0 == distances[0] ){
      nearQuery =
          NearQuery.near(geoJsonPoint).minDistance(0.0).maxDistance(distances[1]).inKilometers();
    }else{
      nearQuery =
          NearQuery.near(geoJsonPoint).minDistance(distances[0]).maxDistance(distances[1]).inKilometers();
    }
    GeoResults<CustTrdGeo> custTrdGeoGeoResults =
        mongoTemplate.geoNear( nearQuery, CustTrdGeo.class);
    if(CollectionUtils.isEmpty(custTrdGeoGeoResults.getContent()) || custTrdGeoGeoResults.getAverageDistance().getValue() > distances[1]){
      if(distances != null && distances.length >=2 ){
        log.error("failed to find trdInfo nearby in {} - {} range", distances[0], distances[1]);
        return custInfoGeoNear;
      }else{
        log.error("failed to find trdInfo with wrong distance param:{}", distances);
      }
    }

    HashMap<Long,  List<CustTrdGeo>> cmpyIds = new HashMap<>();
    HashMap<Long, List<CustTrdGeo>> personIds = new HashMap<>();
    for(GeoResult<CustTrdGeo> custTrdGeoGeoResult: custTrdGeoGeoResults.getContent()){
      CustTrdGeo item = custTrdGeoGeoResult.getContent();
      if(item.getBuyerType() == CustTypeEnum.COMPANY.getId() ||
          item.getBuyerType() == CustTypeEnum.BANK.getId()){
        if(!cmpyIds.containsKey(item.getBuyerId())){
          cmpyIds.put(item.getBuyerId(), new ArrayList<CustTrdGeo>());
        }
        cmpyIds.get(item.getBuyerId()).add(item);
      }else if(item.getBuyerType() == CustTypeEnum.PERSON.getId()){
        if(!personIds.containsKey(item.getBuyerId())){
          personIds.put(item.getBuyerId(), new ArrayList<>());
        }
        personIds.get(item.getBuyerId()).add(item);
      }else{
        log.error("this buyer type is not handled:{}", item.getBuyerType());
      }
    }
    if(!CollectionUtils.isEmpty(cmpyIds)){
      custInfoGeoNear.setCustTrdCmpyList(new ArrayList<>());
      CustTrdCmpyExample custTrdCmpyExample = new CustTrdCmpyExample();
      custTrdCmpyExample.createCriteria().andIdIn(cmpyIds.keySet().stream().collect(Collectors.toList()));
      List<CustTrdCmpy> custTrdCmpyList =  custTrdCmpyMapper.selectByExample(custTrdCmpyExample);
      for(CustTrdCmpy custTrdCmpy: custTrdCmpyList){
        if((StringUtils.isEmpty(custTrdCmpy.getCmpyPhone())||custTrdCmpy.getCmpyPhone().equals("-1"))
            &&(StringUtils.isEmpty(custTrdCmpy.getAnnuReptPhone())||custTrdCmpy.getAnnuReptPhone().equals("-1")) ){
          log.error("Ignore not useful info for cmpy:{}", custTrdCmpy.getId());
          continue;
        }
        CustTrdCmpyExtVo custTrdCmpyExtVo = new CustTrdCmpyExtVo();
        custTrdCmpyExtVo.setCustTrdCmpy(custTrdCmpy);
        custTrdCmpyExtVo.setCustTrdInfos(cmpyIds.get(custTrdCmpy.getId()));
        custInfoGeoNear.getCustTrdCmpyList().add(custTrdCmpyExtVo);
      }

    }

    if(!CollectionUtils.isEmpty(personIds)){
      custInfoGeoNear.setCustTrdPersonList( new ArrayList<>());
      CustTrdPersonExample custTrdPersonExample = new CustTrdPersonExample();
      custTrdPersonExample.createCriteria().andIdIn(personIds.keySet().stream().collect(Collectors.toList()))
          .andStatusNotEqualTo(PresonStatusEnum.MERGED_STATUS.getId());
      List<CustTrdPerson> custTrdPersonList =  custTrdPersonMapper.selectByExample(custTrdPersonExample);
      for(CustTrdPerson custTrdPerson: custTrdPersonList){
        if(StringUtils.isEmpty(custTrdPerson.getMobilePrep())||custTrdPerson.getMobilePrep().equals("-1")){
          log.error("Ignore not useful info for custTrdPerson:{}", custTrdPerson.getId());
          continue;
        }
        CustTrdPersonExtVo custTrdPersonExtVo = new CustTrdPersonExtVo();
        custTrdPersonExtVo.setCustTrdPerson(custTrdPerson);
        custTrdPersonExtVo.setCustTrdInfos(personIds.get(custTrdPerson.getId()));
        custInfoGeoNear.getCustTrdPersonList().add(custTrdPersonExtVo);
      }
    }
    return custInfoGeoNear;
  }




}
