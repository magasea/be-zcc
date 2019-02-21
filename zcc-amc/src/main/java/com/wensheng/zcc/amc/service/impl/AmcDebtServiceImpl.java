package com.wensheng.zcc.amc.service.impl;

import com.wensheng.zcc.amc.dao.mysql.mapper.AmcCmpyMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcCreditorDebtMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcCreditorMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcDebtMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcGrntctrctMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcGrntorMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcOrigCreditorMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcPersonMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.ext.AmcDebtExtMapper;
import com.wensheng.zcc.amc.module.dao.helper.GrantorTypeEnum;
import com.wensheng.zcc.amc.module.dao.helper.ImageClassEnum;
import com.wensheng.zcc.amc.module.dao.mongo.entity.DebtImage;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcCmpy;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcCmpyExample;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcCreditor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcCreditorDebt;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcCreditorDebtExample;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcCreditorExample;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebt;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtExample;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcGrntctrct;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcGrntctrctExample;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcGrntor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcGrntorExample;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcOrigCreditor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcOrigCreditorExample;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcPerson;
import com.wensheng.zcc.amc.module.dao.mysql.auto.ext.AmcDebtExt;
import com.wensheng.zcc.amc.module.vo.AmcDebtVo;
import com.wensheng.zcc.amc.service.AmcDebtService;
import com.wensheng.zcc.amc.service.AmcDebtpackService;
import com.wensheng.zcc.amc.service.AmcHelperService;
import com.wensheng.zcc.amc.service.impl.helper.Dao2VoUtils;
import com.wensheng.zcc.amc.utils.AmcNumberUtils;
import com.wensheng.zcc.amc.utils.ExceptionUtils;
import com.wensheng.zcc.amc.utils.ExceptionUtils.AmcExceptions;
import com.wensheng.zcc.amc.utils.SQLUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * @author chenwei on 1/4/19
 * @project zcc-backend
 */
@Service
@Slf4j
public class AmcDebtServiceImpl implements AmcDebtService {

  Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired
  AmcHelperService amcHelperService;

  @Autowired
  MongoTemplate wszccTemplate;

  @Autowired
  AmcDebtExtMapper amcDebtExtMapper;

  @Autowired
  AmcDebtMapper amcDebtMapper;

  @Autowired
  AmcPersonMapper amcPersonMapper;

  @Autowired
  AmcGrntctrctMapper amcGrntctrctMapper;

  @Autowired
  AmcGrntorMapper amcGrntorMapper;

  @Autowired
  AmcCmpyMapper amcCmpyMapper;

  @Autowired
  AmcCreditorMapper amcCreditorMapper;

  @Autowired
  AmcCreditorDebtMapper amcCreditorDebtMapper;

  @Autowired
  AmcOrigCreditorMapper amcOrigCreditorMapper;

  @Autowired
  AmcDebtpackService amcDebtpackService;

  @Override
  public int saveImageInfo(String ossPath, String originName, Long debtId, String fileDesc, int imageClass) {
    DebtImage debtImage = new DebtImage();
    debtImage.setDebtId(debtId);
    debtImage.setOriginalName(originName);
    debtImage.setDescription(fileDesc);
    debtImage.setOssPath(ossPath);
    debtImage.setIsToOss(!StringUtils.isEmpty(ossPath));
    Query query = new Query();
    if(imageClass == ImageClassEnum.MAIN.getId()){
      query.addCriteria(Criteria.where("debtId").is(debtId).and("imageClass").is(ImageClassEnum.MAIN.getId()));
      List<DebtImage> debtImageList =  wszccTemplate.find(query, DebtImage.class);
      if(!CollectionUtils.isEmpty(debtImageList)){
        logger.info("now need update main pic to class other");
        for(DebtImage debtImageItem: debtImageList){
          debtImageItem.setTag(ImageClassEnum.OTHER.getId());
          wszccTemplate.save(debtImageItem);
        }
      }
    }
    wszccTemplate.save(debtImage);
    return 0;
  }

  @Override
  public AmcDebtVo create(AmcDebt amcDebt) {
    Long debtId = Long.valueOf(amcDebtMapper.insertSelective(amcDebt));
    amcDebt.setAmcId(debtId);
    return convertDo2Vo(amcDebt);
  }

  @Override
  public AmcDebtVo del(AmcDebt AmcDebt) {
    return null;
  }

  @Override
  public AmcDebtVo update(AmcDebt AmcDebt) {
    return null;
  }

  @Override
  public List<AmcDebtVo> queryAll(int offset, int size) {
    return null;
  }

  @Override
  public AmcDebtVo get(Long amcDebtId) throws Exception {
    AmcDebt amcDebt = amcDebtMapper.selectByPrimaryKey(amcDebtId);
    if(amcDebt == null){
      throw ExceptionUtils.getAmcException(AmcExceptions.NO_AMCDEBT_AVAILABLE);
    }
    return Dao2VoUtils.convertDo2Vo(amcDebt);
  }



  @Override
  public List<AmcDebtVo> query(AmcDebt queryCond, int offset, int size) {

    AmcDebtExample amcDebtExample = null;
    RowBounds rowBounds = new RowBounds(offset, size);

    List<AmcDebt> amcDebts = amcDebtMapper.selectByExampleWithRowbounds(amcDebtExample, rowBounds);


    if(!CollectionUtils.isEmpty(amcDebts)){
      return doList2VoList(amcDebts);
    }

    return null;
  }



  private List<AmcDebtVo> doList2VoList(List<AmcDebt> originList){
    List<AmcDebtVo> amcDebtVos = new ArrayList<>();
    for(AmcDebt amcDebt: originList){
      AmcDebtVo amcDebtVo = convertDo2Vo(amcDebt);
      amcDebtVos.add(amcDebtVo);
    }
    return amcDebtVos;

  }

  private AmcDebtVo convertDo2Vo(AmcDebt amcDebt) {
    AmcDebtVo amcDebtVo = new AmcDebtVo();
    BeanUtils.copyProperties(amcDebt, amcDebtVo);
    if(amcDebt.getBaseAmount() > 0 ){
      amcDebtVo.setBaseAmount(AmcNumberUtils.getDecimalFromLongDiv100(amcDebt.getBaseAmount()));

    }
    if(amcDebt.getEstimatedPrice() > 0 ){
      amcDebtVo.setEstimatedPrice(AmcNumberUtils.getDecimalFromLongDiv100(amcDebt.getEstimatedPrice()));

    }
    if(amcDebt.getTotalAmount() > 0 ){
      amcDebtVo.setTotalAmount(AmcNumberUtils.getDecimalFromLongDiv100(amcDebt.getTotalAmount()));

    }
    if(amcDebt.getAmcContact1() > 0){
      amcDebtVo.setAmcContact1(amcHelperService.getAmcPerson(amcDebt.getAmcContact1()));
    }

    if(amcDebt.getAmcContact2() > 0){
      amcDebtVo.setAmcContact2(amcHelperService.getAmcPerson(amcDebt.getAmcContact2()));
    }
    return amcDebtVo;
  }

  private AmcDebtVo convertDoExt2Vo(AmcDebtExt amcDebtExt){
    AmcDebtVo amcDebtVo = convertDo2Vo(amcDebtExt.getDebtInfo());
    amcDebtVo.setAssetVos(Dao2VoUtils.convertDoList2VoList(amcDebtExt.getAmcAssets()));
    return amcDebtVo;

  }



  @Override
  public List<AmcDebtVo> queryAllExt(Long offset, int size, Map<String, Sort.Direction> orderBy) throws Exception {


    AmcDebtExample amcDebtExample = new AmcDebtExample();
    try{
      amcDebtExample.setOrderByClause(SQLUtils.getOrderBy(orderBy));
    }catch (Exception ex){
      logger.error("there is no orderBy params:" + ex.getMessage());
    }

    RowBounds rowBounds = new RowBounds(offset.intValue(), size);

    List<AmcDebtExt> amcDebtExtList = amcDebtExtMapper.selectByExampleWithRowboundsExt(amcDebtExample, rowBounds);

    List<AmcDebtVo> amcDebtVos = new ArrayList<>();
    for(AmcDebtExt amcDebtExt: amcDebtExtList){
      amcDebtVos.add(convertDoExt2Vo(amcDebtExt));
    }

    return amcDebtVos;
  }

  @Override
  public List<AmcDebt> queryByDebtpackId(Long debtPackId) {
    AmcDebtExample amcDebtExample = new AmcDebtExample();
    amcDebtExample.createCriteria().andDebtpackIdEqualTo(debtPackId);
    List<AmcDebt> amcDebts = amcDebtMapper.selectByExample(amcDebtExample);
    return amcDebts;
  }

  @Override
  public Long getTotalCount() {
    AmcDebtExample amcDebtExample = new AmcDebtExample();
    amcDebtExample.createCriteria().andIdGreaterThan(0L);
    return amcDebtMapper.countByExample(null);
  }

  @Override
  public Long addGrantContract(AmcGrntctrct amcGrntctrct) {
    Long id = Long.valueOf(amcGrntctrctMapper.insertSelective(amcGrntctrct));
    return id;
  }

  @Override
  public AmcGrntctrct updateGrantContract(AmcGrntctrct amcGrntctrct) {
     amcGrntctrctMapper.updateByPrimaryKeySelective(amcGrntctrct);
    return amcGrntctrct;
  }

  @Override
  public Boolean isDebtIdExist(Long debtId) {
    AmcDebt amcDebt = amcDebtMapper.selectByPrimaryKey(debtId);
    if(null == amcDebt){
      return false;
    }
    return true;
  }

  @Override
  public Boolean isGrntIdExist(Long grantorId, int grantorType) throws Exception {
    GrantorTypeEnum type = GrantorTypeEnum.lookupByDisplayNameUtil(grantorType);


    if( type == null || GrantorTypeEnum.lookupByDisplayNameUtil(grantorType) == GrantorTypeEnum.NO_INFO ){
      throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_GRANTORTYPE);
    }
    switch(type){
      case COMPANY:
        AmcCmpy amcCmpy = amcCmpyMapper.selectByPrimaryKey(grantorId);
        if(null != amcCmpy){
          return true;
        }else{
          return false;
        }

      case PERSONAL:
        AmcGrntor amcGrntor = amcGrntorMapper.selectByPrimaryKey(grantorId);
        if(null != amcGrntor){
          return true;
        }else{
          return false;
        }

      default:
        throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_GRANTORTYPE);
    }
  }

  @Override
  public boolean isAmcContactexist(Long amcContact1) {
    AmcPerson amcPerson = amcPersonMapper.selectByPrimaryKey(amcContact1);
    if(amcPerson == null){
      return false;
    }

    return true;
  }

  @Override
  public AmcCreditor create(AmcCreditor creditor) throws Exception {
    //if the creditor is company, need check if company exists
    if( creditor.getType() == GrantorTypeEnum.COMPANY.getId() && (creditor.getCompanyId() == null || creditor.getCompanyId() <= 0)){
      //need create company
      if (StringUtils.isEmpty(creditor.getName())){
        throw ExceptionUtils.getAmcException(AmcExceptions.MISSING_MUST_PARAM,"company name is must");
      }
      AmcCmpyExample amcCmpyExample = new AmcCmpyExample();
      amcCmpyExample.createCriteria().andNameEqualTo(creditor.getName());
      List<AmcCmpy> amcCmpies = amcCmpyMapper.selectByExample(amcCmpyExample);
      if(!CollectionUtils.isEmpty(amcCmpies)){
        log.error(String.format("the company to create already exists:%s", creditor.getName()));
        creditor.setCompanyId(amcCmpies.get(0).getId());
      }else{
        AmcCmpy amcCmpy = new AmcCmpy();
        amcCmpy.setName(creditor.getName());
        Long companyId = Long.valueOf(amcCmpyMapper.insertSelective(amcCmpy));
        creditor.setCompanyId(companyId);
      }
    }
    Long id = Long.valueOf( amcCreditorMapper.insertSelective(creditor));
    creditor.setId(id);
    return creditor;
  }

  @Override
  public AmcCreditor update(AmcCreditor creditor) {
    amcCreditorMapper.updateByPrimaryKey(creditor);
    return creditor;
  }

  @Override
  public AmcCmpy create(AmcCmpy amcCmpy) {
    Long id = Long.valueOf(amcCmpyMapper.insertSelective(amcCmpy));
    amcCmpy.setId(id);
    return amcCmpy;
  }

  @Override
  public AmcCmpy update(AmcCmpy amcCmpy) {
    amcCmpyMapper.updateByPrimaryKey(amcCmpy);
    return amcCmpy;
  }

  @Override
  @Transactional
  public void connDebt2Creditors(List<Long> creditorIds, Long debtId) {
    AmcCreditorDebtExample amcCreditorDebtExample = new AmcCreditorDebtExample();
    amcCreditorDebtExample.createCriteria().andDebtIdEqualTo(debtId);
    amcCreditorDebtMapper.deleteByExample(amcCreditorDebtExample);
    AmcCreditorDebt amcCreditorDebt = new AmcCreditorDebt();
    for(Long creditorId: creditorIds){
      amcCreditorDebt.setCreditorId(creditorId);
      amcCreditorDebt.setDebtId(debtId);
      amcCreditorDebtMapper.insertSelective(amcCreditorDebt);
    }
  }

  @Override
  public List<AmcCreditor> getCreditors(Long amcDebtId) throws Exception {
    AmcCreditorDebtExample amcCreditorDebtExample = new AmcCreditorDebtExample();
    amcCreditorDebtExample.createCriteria().andDebtIdEqualTo(amcDebtId);
    List<AmcCreditorDebt> creditors = amcCreditorDebtMapper.selectByExample(amcCreditorDebtExample);
    if(CollectionUtils.isEmpty(creditors)){
      throw ExceptionUtils.getAmcException(AmcExceptions.NO_CREDITOR);
    }
    AmcCreditorExample amcCreditorExample = new AmcCreditorExample();
    List<Long> creditorIds =
        creditors.stream().map( creditor  -> creditor.getCreditorId()).collect(Collectors.toList());
    amcCreditorExample.createCriteria().andIdIn(creditorIds);
    List<AmcCreditor> amcCreditors =
        amcCreditorMapper.selectByExample(amcCreditorExample);
    return amcCreditors;
  }

  @Override
  public List<AmcGrntor> getGrantors(Long amcDebtId) {
    AmcGrntctrctExample amcGrntctrctExample = new AmcGrntctrctExample();
    amcGrntctrctExample.createCriteria().andDebtIdEqualTo(amcDebtId);
    List<AmcGrntctrct> amcGrntctrcts =  amcGrntctrctMapper.selectByExample(amcGrntctrctExample);
    List<Long> grantorIds = amcGrntctrcts.stream().map(amcGrntctrct -> amcGrntctrct.getGrantorId()).collect(Collectors.toList());
    AmcGrntorExample amcGrntorExample = new AmcGrntorExample();
    amcGrntorExample.createCriteria().andIdIn(grantorIds);
    List<AmcGrntor> grntors = amcGrntorMapper.selectByExample(amcGrntorExample);
    return grntors;
  }

  @Override
  public AmcOrigCreditor getOriginCreditor(Long amcDebtId) {
    AmcDebt amcDebt = amcDebtMapper.selectByPrimaryKey(amcDebtId);
    AmcOrigCreditor amcOrigCreditor = amcOrigCreditorMapper.selectByPrimaryKey(amcDebt.getOrigCreditorId());

    return amcOrigCreditor;
  }

  @Override
  public List<AmcOrigCreditor> getAllOrigCreditors(int offset, int size, Map<String, Direction> orderByParam)
      throws Exception {
    AmcOrigCreditorExample amcOrigCreditorExample = new AmcOrigCreditorExample();
    amcOrigCreditorExample.createCriteria().andIdGreaterThan(0L);
    amcOrigCreditorExample.setOrderByClause(SQLUtils.getOrderBy(orderByParam));
    RowBounds rowBounds = new RowBounds(offset, size);
    List<AmcOrigCreditor> amcOrigCreditors = amcOrigCreditorMapper.selectByExampleWithRowbounds(amcOrigCreditorExample, rowBounds);
    return amcOrigCreditors;
  }

  @Override
  public Long getCreditorsCount(){

    return amcOrigCreditorMapper.countByExample(null);
  }

  @Override
  public void delImage(DebtImage debtImage) {


    Query query = new Query();
    query.addCriteria(Criteria.where("debtId").is(debtImage.getDebtId()).and("ossPath").is(debtImage.getOssPath()));
    wszccTemplate.findAllAndRemove(query, DebtImage.class);

  }


  @Override
  public AmcOrigCreditor createCreditor(AmcOrigCreditor amcOrigCreditor) {
    Long id = Long.valueOf(amcOrigCreditorMapper.insertSelective(amcOrigCreditor));
    amcOrigCreditor.setId(id);
    return amcOrigCreditor;
  }

  @Override
  public List<AmcCreditor> getAllCreditors(Long offset, int size, int type, Map<String, Direction> orderByParam)
      throws Exception {
    AmcCreditorExample amcCreditorExample = new AmcCreditorExample();
    if(type > 0 ){
      amcCreditorExample.createCriteria().andTypeEqualTo(type);
    }
    amcCreditorExample.setOrderByClause(SQLUtils.getOrderBy(orderByParam));
    return amcCreditorMapper.selectByExample(amcCreditorExample);
  }

  @Override
  public Long getTotalCreditorCount() {
    AmcCreditorExample amcCreditorExample = new AmcCreditorExample();
    return amcCreditorMapper.countByExample(amcCreditorExample);
  }

  @Override
  public List<AmcCmpy> getAllCompanies(Long offset, int size, Map<String, Direction> orderByParam) throws Exception {
    AmcCmpyExample amcCmpyExample = new AmcCmpyExample();
    amcCmpyExample.setOrderByClause(SQLUtils.getOrderBy(orderByParam));
    return amcCmpyMapper.selectByExample(amcCmpyExample);
  }

  @Override
  public Long getTotalCompanyCount() {
    return amcCmpyMapper.countByExample(null);
  }

  @Override
  public Map<String, List<Long>> getAllTitles() {
    List<AmcDebt> amcDebts = amcDebtExtMapper.selectAllTitlesByExample(null);
    Map<String, List<Long>> result = new HashMap<>();
    for(AmcDebt amcDebt: amcDebts){

      if(!result.containsKey(amcDebt.getTitle())){

        result.put(amcDebt.getTitle(), new ArrayList<Long>());
      }
      result.get(amcDebt.getTitle()).add(amcDebt.getId());

    }
    return result;
  }
}
