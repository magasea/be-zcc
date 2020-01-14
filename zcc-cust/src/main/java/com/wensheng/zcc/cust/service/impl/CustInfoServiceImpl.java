package com.wensheng.zcc.cust.service.impl;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.wensheng.zcc.common.utils.AmcBeanUtils;
import com.wensheng.zcc.cust.controller.helper.QueryParam;
import com.wensheng.zcc.cust.dao.mysql.mapper.CustRegionDetailMapper;
import com.wensheng.zcc.cust.dao.mysql.mapper.CustRegionMapper;
import com.wensheng.zcc.cust.dao.mysql.mapper.CustTrdCmpyMapper;
import com.wensheng.zcc.cust.dao.mysql.mapper.CustTrdInfoMapper;
import com.wensheng.zcc.cust.dao.mysql.mapper.CustTrdPersonMapper;
import com.wensheng.zcc.cust.dao.mysql.mapper.ext.CustTrdCmpyExtMapper;
import com.wensheng.zcc.cust.dao.mysql.mapper.ext.CustTrdPersonExtMapper;
import com.wensheng.zcc.cust.module.dao.mongo.CustTrdGeo;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustRegion;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustRegionDetail;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdCmpy;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdCmpyExample;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdInfo;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdInfoExample;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdPerson;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdPersonExample;
import com.wensheng.zcc.cust.module.dao.mysql.ext.CustTrdCmpyExtExample;
import com.wensheng.zcc.cust.module.dao.mysql.ext.CustTrdCmpyTrdExt;
import com.wensheng.zcc.cust.module.dao.mysql.ext.CustTrdPersonExtExample;
import com.wensheng.zcc.cust.module.dao.mysql.ext.CustTrdPersonTrdExt;
import com.wensheng.zcc.cust.module.helper.CustTypeEnum;
import com.wensheng.zcc.cust.module.helper.InvestTypeEnum;
import com.wensheng.zcc.cust.module.vo.CustInfoGeoNear;
import com.wensheng.zcc.cust.module.vo.CustTrdCmpyExtVo;
import com.wensheng.zcc.cust.module.vo.CustTrdInfoExcelVo;
import com.wensheng.zcc.cust.module.vo.CustTrdInfoVo;
import com.wensheng.zcc.cust.module.vo.CustTrdPersonExtVo;
import com.wensheng.zcc.cust.module.vo.CustTrdPersonVo;
import com.wensheng.zcc.cust.service.CustInfoService;
import com.wensheng.zcc.cust.utils.SQLUtils;
import java.util.ArrayList;
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
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

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

  @Value("${env.image-repo}")
  String fileBase;

  static final String SEP_INDICATOR_EDITABLE = "|$|";
  static final String SEP_INDICATOR_ORIGINAL = "|#|";

  static final String SEP_INDICATOR_EDITABLE_USED = "\\|\\$\\|";
  static final String SEP_INDICATOR_ORIGINAL_USED = "\\|#\\|";
  @Autowired
  MongoTemplate mongoTemplate;

  Gson gson = new Gson();

  @Override
  public CustTrdCmpy addCompany(CustTrdCmpy custTrdCmpy) {
    custTrdCmpyMapper.insertSelective(custTrdCmpy);
    return custTrdCmpy;
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
    CustTrdCmpyExtExample custTrdCmpyExtExample = SQLUtils.getCustCmpyTrdExample(queryParam);
    custTrdCmpyExtExample.setOrderByClause(orderBy);
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

      preGroupResults =
          custTrdCmpyExtMapper.selectByPreFilter(custTrdCmpyExtExample);
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
    return custTrdCmpyTrdExts;
  }

  @Override
  public List<CustTrdInfoExcelVo> queryCmpyTrade(int offset, int size, QueryParam queryParam, Map<String, Direction> orderByParam)
      throws Exception {
    List<CustTrdCmpyTrdExt> custTrdCmpyTrdExts = queryCmpy( offset,  size,  queryParam,  orderByParam);
    return convertCmpyToExcelVoes(custTrdCmpyTrdExts);
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
      if(!StringUtils.isEmpty(custTrdCmpyTrdExt.getCustTrdCmpy().getCmpyPhone()) && custTrdCmpyTrdExt.getCustTrdCmpy().getCmpyPhone().equals("-1")){
        sbPhone.append(custTrdCmpyTrdExt.getCustTrdCmpy().getCmpyPhone()).append(";");
      }
      if(!StringUtils.isEmpty(custTrdCmpyTrdExt.getCustTrdCmpy().getAnnuReptPhone()) && custTrdCmpyTrdExt.getCustTrdCmpy().getAnnuReptPhone().equals("-1")){
        sbPhone.append(custTrdCmpyTrdExt.getCustTrdCmpy().getAnnuReptPhone());
      }
      custTrdInfoExcelVo.setPhone(sbPhone.toString());
      custTrdInfoExcelVo.setTrdCount(custTrdCmpyTrdExt.getCustTrdInfoList().size());
      Long totalAmount = 0L;

      Map<String, Integer> invest2Counts = new HashMap<>();
      Map<String, Integer> city2Counts = new HashMap<>();
      String cityName = null;
      String trdTypeName = null;
      for(CustTrdInfo custTrdInfo: custTrdCmpyTrdExt.getCustTrdInfoList()){
        if(StringUtils.isEmpty(custTrdInfo.getTrdCity()) || custTrdInfo.getTrdCity().equals("-1")){
          if(StringUtils.isEmpty(custTrdInfo.getTrdProvince()) || custTrdInfo.getTrdProvince().equals("-1")){
            log.error("custTrdInfo:{} haven't valid city :{} or province :{} just ignore it ", custTrdInfo.getId(),
                custTrdInfo.getTrdCity(), custTrdInfo.getTrdProvince());
            continue;
          }
          cityName = custRegionDetailMapper.selectByPrimaryKey(Long.valueOf(custTrdInfo.getTrdProvince())).getName();
        }else{
          try{
            cityName = custRegionDetailMapper.selectByPrimaryKey(Long.valueOf(custTrdInfo.getTrdCity())).getName();
          }catch (Exception ex){
            log.error("Failed to find city for:{}", custTrdInfo.getTrdCity());
            continue;
          }

        }

        totalAmount += custTrdInfo.getTotalAmount();

        if(custTrdInfo.getTrdType() == null){
          continue;
        }
        trdTypeName = InvestTypeEnum.lookupByIdUntil(custTrdInfo.getTrdType()).getName();
        if(!invest2Counts.containsKey(trdTypeName)){
          invest2Counts.put(trdTypeName, 1);
        }else{
          invest2Counts.put(trdTypeName, invest2Counts.get(trdTypeName)+1);
        }

        if(!city2Counts.containsKey(cityName)){
          city2Counts.put(cityName, 1);
        }else {
          city2Counts.put(cityName, city2Counts.get(cityName)+1);
        }
      }
      custTrdInfoExcelVo.setTrdTotalAmount( totalAmount > 0 ? totalAmount: -1);
      custTrdInfoExcelVo.setIntrestCities(city2Counts);
      custTrdInfoExcelVo.setInvestType2Counts(invest2Counts);
      custTrdInfoExcelVos.add(custTrdInfoExcelVo);
    }

    return custTrdInfoExcelVos;
  }

  @Override
  public Long getCmpyTradeCount(QueryParam queryParam) {
    CustTrdCmpyExample custTrdCmpyExample = SQLUtils.getCustCmpyTrdExample(queryParam);
    String filterBy = SQLUtils.getFilterByForCustTrd(queryParam);
//    filterBy = filterBy + " and ctc.cmpy_phone > -1 ";
    CustTrdCmpyExtExample custTrdCmpyExtExample = new CustTrdCmpyExtExample();
    custTrdCmpyExample.getOredCriteria().forEach(item -> custTrdCmpyExtExample.getOredCriteria().add(item));
    custTrdCmpyExtExample.setFilterByClause(filterBy);
    return custTrdCmpyExtMapper.countByFilter(custTrdCmpyExtExample);




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
//    filterBy = filterBy + " and ctp.mobile_num > -1 ";
    if(!StringUtils.isEmpty(filterBy)) {
      custTrdPersonExtExample.setFilterByClause(filterBy);
    }
    custTrdPersonExtExample.setLimitByClause(String.format(" %d , %d ", offset, size));
    List<CustTrdPersonTrdExt> custTrdPersonTrdExtList = new ArrayList<>();
    List<Long> ids = custTrdPersonExtMapper.selectByPreFilter(custTrdPersonExtExample);
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
    return  custTrdPersonTrdExtList;
  }

  @Override
  public List<CustTrdInfoExcelVo> queryPersonTrade(int offset, int size,  QueryParam queryParam, Map<String,
      Direction> orderByParam)
      throws Exception {
    List<CustTrdPersonTrdExt> custTrdPersonTrdExts = queryPerson(offset, size, queryParam, orderByParam);


    return convertPersonToExcelVoes(custTrdPersonTrdExts);
  }

  private List<CustTrdInfoExcelVo> convertPersonToExcelVoes(List<CustTrdPersonTrdExt> custTrdPersonTrdExts) {
    List<CustTrdInfoExcelVo> custTrdInfoExcelVos = new ArrayList<>();
    for(CustTrdPersonTrdExt custTrdPersonTrdExt: custTrdPersonTrdExts){
      CustTrdInfoExcelVo custTrdInfoExcelVo = new CustTrdInfoExcelVo();
      custTrdInfoExcelVo.setCustId(custTrdPersonTrdExt.getId());
      custTrdInfoExcelVo.setAddress(String.format("%s",custTrdPersonTrdExt.getCustTrdPerson().getAddr()));
      custTrdInfoExcelVo.setCustName(custTrdPersonTrdExt.getCustTrdPerson().getName());
      custTrdInfoExcelVo.setPhone(String.format("%s;%s",
          custTrdPersonTrdExt.getCustTrdPerson().getMobileNum(),
          custTrdPersonTrdExt.getCustTrdPerson().getTelNum()));
      custTrdInfoExcelVo.setTrdCount(custTrdPersonTrdExt.getCustTrdInfoList().size());
      Long totalAmount = 0L;
      Map<String, Integer> invest2Counts = new HashMap<>();
      Map<String, Integer> city2Counts = new HashMap<>();
      String cityName= "";
      String trdTypeName;
      for(CustTrdInfo custTrdInfo: custTrdPersonTrdExt.getCustTrdInfoList()){
        totalAmount += custTrdInfo.getTotalAmount();
        if(custTrdInfo.getTrdType() == null){
          continue;
        }
        trdTypeName = InvestTypeEnum.lookupByIdUntil(custTrdInfo.getTrdType()).getName();
        if(!StringUtils.isEmpty(custTrdInfo.getTrdCity()) && Long.valueOf(custTrdInfo.getTrdCity()) > -1L){
          CustRegionDetail custRegion =
              custRegionDetailMapper.selectByPrimaryKey(Long.valueOf(custTrdInfo.getTrdCity()));
          if(null != custRegion){
            cityName = custRegion.getName();
          }else{
            log.error("Failed to get cityName for :{}", custTrdInfo.getTrdCity());
          }
        }else{
          log.error("Failed to get cityName for :{}", custTrdInfo.getTrdCity());
        }

        if(!invest2Counts.containsKey(trdTypeName)){
          invest2Counts.put(trdTypeName, 1);
        }else{
          invest2Counts.put(trdTypeName, invest2Counts.get(trdTypeName)+1);
        }
        if(!city2Counts.containsKey(cityName)){
          city2Counts.put(cityName, 1);
        }else {
          city2Counts.put(cityName, city2Counts.get(cityName)+1);
        }
      }
      custTrdInfoExcelVo.setTrdTotalAmount( totalAmount > 0? totalAmount: -1);
      custTrdInfoExcelVo.setIntrestCities(city2Counts);
      custTrdInfoExcelVo.setInvestType2Counts(invest2Counts);
      custTrdInfoExcelVos.add(custTrdInfoExcelVo);
    }

    return custTrdInfoExcelVos;
  }


  @Override
  public Long getPersonTradeCount(QueryParam queryParam) {
    CustTrdPersonExample custTrdPersonExample = SQLUtils.getCustPersonTrdExample(queryParam);



    String filterBy = SQLUtils.getFilterByForCustTrd(queryParam);
//    filterBy = filterBy + " and ctp.mobile_num > -1 ";
    CustTrdPersonExtExample custTrdPersonExtExample = new CustTrdPersonExtExample();
    custTrdPersonExample.getOredCriteria().forEach(item -> custTrdPersonExtExample.getOredCriteria().add(item));
    custTrdPersonExtExample.setFilterByClause(filterBy);
    return custTrdPersonExtMapper.countByFilter(custTrdPersonExtExample);
  }

  @Override
  public CustTrdCmpy getCompany(Long companyId) {

    return custTrdCmpyMapper.selectByPrimaryKey(companyId);

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
    if(!StringUtils.isEmpty(custTrdPerson.getHistoryMobileNum()) && (custTrdPerson.getHistoryMobileNum().contains(SEP_INDICATOR_EDITABLE)
    ||custTrdPerson.getHistoryMobileNum().contains(SEP_INDICATOR_ORIGINAL))){
      List<String> listEditable = new ArrayList<>();
      if(custTrdPerson.getHistoryMobileNum().contains(SEP_INDICATOR_EDITABLE)){
        listEditable = Lists.newArrayList(custTrdPerson.getHistoryMobileNum().split(SEP_INDICATOR_EDITABLE_USED));

      }else{
        listEditable.add(custTrdPerson.getHistoryMobileNum());
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
      custTrdPersonVo.setOriginPhoneNum(custTrdPerson.getMobileNum());
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
    for(CustTrdCmpyTrdExt custTrdCmpyTrdExt: custTrdCmpyTrdExts){
      CustTrdInfoVo custTrdInfoVo = new CustTrdInfoVo();
      custTrdInfoVo.setCustId(custTrdCmpyTrdExt.getId());
      custTrdInfoVo.setAddress(String.format("%s;%s",custTrdCmpyTrdExt.getCustTrdCmpy().getCmpyAddr(),
          custTrdCmpyTrdExt.getCustTrdCmpy().getAnnuReptAddr()));
      custTrdInfoVo.setCustName(custTrdCmpyTrdExt.getCustTrdCmpy().getCmpyName());
      custTrdInfoVo.setPhone(String.format("%s;%s",custTrdCmpyTrdExt.getCustTrdCmpy().getCmpyPhone(),
          custTrdCmpyTrdExt.getCustTrdCmpy().getAnnuReptPhone()));
      custTrdInfoVo.setTrdCount(custTrdCmpyTrdExt.getCustTrdInfoList().size());
      Long totalAmount = 0L;
      Set<String> cities = new HashSet<>();
      Map<Integer, Integer> invest2Counts = new HashMap<>();
      Map<String, Integer> city2Counts = new HashMap<>();
      for(CustTrdInfo custTrdInfo: custTrdCmpyTrdExt.getCustTrdInfoList()){
        totalAmount += custTrdInfo.getTotalAmount() > 0 ? custTrdInfo.getTotalAmount() :
            custTrdInfo.getTrdAmount() > 0 ? custTrdInfo.getTrdAmount() : 0 ;
        cities.add(custTrdInfo.getTrdCity());
        if(custTrdInfo.getTrdType() == null){
          continue;
        }
        if(!invest2Counts.containsKey(custTrdInfo.getTrdType())){
          invest2Counts.put(custTrdInfo.getTrdType(), 1);
        }else{
          invest2Counts.put(custTrdInfo.getTrdType(), invest2Counts.get(custTrdInfo.getTrdType())+1);
        }

        if(!city2Counts.containsKey(custTrdInfo.getTrdCity())){
          city2Counts.put(custTrdInfo.getTrdCity(), 1);
        }else {
          city2Counts.put(custTrdInfo.getTrdCity(), city2Counts.get(custTrdInfo.getTrdCity())+1);
        }
      }
      custTrdInfoVo.setTrdTotalAmount( totalAmount > 0 ? totalAmount: -1);
      custTrdInfoVo.setIntrestCities(city2Counts);
      custTrdInfoVo.setInvestType2Counts(invest2Counts);
      custTrdInfoVos.add(custTrdInfoVo);
    }

    return custTrdInfoVos;
  }

  private List<CustTrdInfoVo> convertPersonToVoes(List<CustTrdPersonTrdExt> custTrdPersonTrdExtList) {
    List<CustTrdInfoVo> custTrdInfoVos = new ArrayList<>();
    for(CustTrdPersonTrdExt custTrdPersonTrdExt: custTrdPersonTrdExtList){
      CustTrdInfoVo custTrdInfoVo = new CustTrdInfoVo();
      custTrdInfoVo.setCustId(custTrdPersonTrdExt.getId());
      custTrdInfoVo.setAddress(String.format("%s",custTrdPersonTrdExt.getCustTrdPerson().getAddr()));
      custTrdInfoVo.setCustName(custTrdPersonTrdExt.getCustTrdPerson().getName());
      custTrdInfoVo.setPhone(String.format("%s;%s",
          custTrdPersonTrdExt.getCustTrdPerson().getMobileNum(),
          custTrdPersonTrdExt.getCustTrdPerson().getTelNum()));
      custTrdInfoVo.setTrdCount(custTrdPersonTrdExt.getCustTrdInfoList().size());
      Long totalAmount = 0L;
      Set<String> cities = new HashSet<>();
      Map<Integer, Integer> invest2Counts = new HashMap<>();
      Map<String, Integer> city2Counts = new HashMap<>();
      for(CustTrdInfo custTrdInfo: custTrdPersonTrdExt.getCustTrdInfoList()){
        totalAmount += custTrdInfo.getTotalAmount() > 0? custTrdInfo.getTotalAmount():
            custTrdInfo.getTrdAmount() > 0 ? custTrdInfo.getTrdAmount() : 0;
        cities.add(custTrdInfo.getTrdCity());
        if(custTrdInfo.getTrdType() == null){
          continue;
        }
        if(!invest2Counts.containsKey(custTrdInfo.getTrdType())){
          invest2Counts.put(custTrdInfo.getTrdType(), 1);
        }else{
          invest2Counts.put(custTrdInfo.getTrdType(), invest2Counts.get(custTrdInfo.getTrdType())+1);
        }
        if(!city2Counts.containsKey(custTrdInfo.getTrdCity())){
          city2Counts.put(custTrdInfo.getTrdCity(), 1);
        }else {
          city2Counts.put(custTrdInfo.getTrdCity(), city2Counts.get(custTrdInfo.getTrdCity())+1);
        }
      }
      custTrdInfoVo.setTrdTotalAmount( totalAmount > 0 ? totalAmount: -1);
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
      custTrdPersonVo.setHistoryMobileNum(custTrdPersonVo.getOriginPhoneNum());
    }else{
      StringBuilder historyMobileNums =
          new StringBuilder(Joiner.on(SEP_INDICATOR_EDITABLE).join(custTrdPersonVo.getHistoryPhoneNums()));
      historyMobileNums.append(SEP_INDICATOR_ORIGINAL).append(custTrdPersonVo.getOriginPhoneNum());
      custTrdPersonVo.setHistoryMobileNum(historyMobileNums.toString());
    }

    if(null != custTrdPersonVo.getHistoryPhoneNums() && !CollectionUtils.isEmpty(custTrdPersonVo.getHistoryPhoneNums())){
      updateCustPersonTrdRelations(custTrdPersonVo.getHistoryPhoneNums(), custTrdPersonVo.getMobileNum(),
          custTrdPersonVo.getName(), custTrdPersonVo.getId());
    }

    CustTrdPerson custTrdPerson = new CustTrdPerson();
    AmcBeanUtils.copyProperties(custTrdPersonVo, custTrdPerson);
    custTrdPersonMapper.updateByPrimaryKeySelective(custTrdPerson);
    return true;
  }
  private boolean updateCustPersonTrdRelations(List<String> histPhoneNumList, String phoneNum, String custName,
      Long currentCustPersonId){
    // find origin cust and search the trd info of the cust, update the trd info ref buyer id to the
    CustTrdPersonExample custTrdPersonExample = new CustTrdPersonExample();
    custTrdPersonExample.createCriteria().andMobileNumEqualTo(phoneNum).andNameEqualTo(custName);
    List<CustTrdPerson> custTrdPeople = custTrdPersonMapper.selectByExample(custTrdPersonExample);
    if(CollectionUtils.isEmpty(custTrdPeople)){
      return true;
    }
    List<CustTrdPerson> merged = new ArrayList();
    for(String custHistPhone: histPhoneNumList){
      custTrdPersonExample.clear();
      custTrdPersonExample.createCriteria().andMobileNumEqualTo(custHistPhone).andNameEqualTo(custName);
      List<CustTrdPerson> custTrdPeopleHistory = custTrdPersonMapper.selectByExample(custTrdPersonExample);
      merged =  ListUtils.union(custTrdPeople, custTrdPeopleHistory);
    }
    custTrdPeople = merged.stream().filter(item -> item.getId() != currentCustPersonId).collect(Collectors.toList());
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
      custTrdPersonExample.createCriteria().andIdIn(personIds.keySet().stream().collect(Collectors.toList()));
      List<CustTrdPerson> custTrdPersonList =  custTrdPersonMapper.selectByExample(custTrdPersonExample);
      for(CustTrdPerson custTrdPerson: custTrdPersonList){
        if(StringUtils.isEmpty(custTrdPerson.getMobileNum())||custTrdPerson.getMobileNum().equals("-1")){
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
