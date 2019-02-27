package com.wensheng.zcc.amc.service.impl;

import com.wensheng.zcc.amc.dao.mysql.mapper.AmcCmpyMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcCreditorDebtMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcCreditorMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcDebtMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcDebtorMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcGrntctrctMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcGrntorMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcInfoMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcOrigCreditorMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcPersonMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.ext.AmcDebtExtMapper;
import com.wensheng.zcc.amc.module.dao.helper.DebtorTypeEnum;
import com.wensheng.zcc.amc.module.dao.helper.ImageClassEnum;
import com.wensheng.zcc.amc.module.dao.mongo.entity.DebtImage;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcCmpy;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcCmpyExample;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcCreditorDebt;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcCreditorDebtExample;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebt;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtExample;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtorExample;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcInfo;
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
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
  AmcDebtorMapper amcDebtorMapper;

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
  AmcInfoMapper amcInfoMapper;

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
    amcDebtMapper.insertSelective(amcDebt);
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
    if(amcDebt.getValuation() > 0 ){
      amcDebtVo.setValuation(AmcNumberUtils.getDecimalFromLongDiv100(amcDebt.getValuation()));

    }
    if(amcDebt.getTotalAmount() > 0 ){
      amcDebtVo.setTotalAmount(AmcNumberUtils.getDecimalFromLongDiv100(amcDebt.getTotalAmount()));

    }
    if(amcDebt.getAmcContact() > 0){
      amcDebtVo.setAmcContact1(amcHelperService.getAmcPerson(amcDebt.getAmcContact()));
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
  public boolean isAmcContactexist(Long amcContact1) {
    AmcPerson amcPerson = amcPersonMapper.selectByPrimaryKey(amcContact1);
    if(amcPerson == null){
      return false;
    }

    return true;
  }

  @Override
  public AmcDebtor create(AmcDebtor amcDebtor) throws Exception {

    //if the creditor is company, need check if company exists
    if( amcDebtor.getType() == DebtorTypeEnum.COMPANY.getId() && (amcDebtor.getCompanyId() == null || amcDebtor.getCompanyId() <= 0)){
      //need create company
      if (StringUtils.isEmpty(amcDebtor.getName())){
        throw ExceptionUtils.getAmcException(AmcExceptions.MISSING_MUST_PARAM,"company name is must");
      }
      AmcCmpyExample amcCmpyExample = new AmcCmpyExample();
      amcCmpyExample.createCriteria().andNameEqualTo(amcDebtor.getName());
      List<AmcCmpy> amcCmpies = amcCmpyMapper.selectByExample(amcCmpyExample);
      if(!CollectionUtils.isEmpty(amcCmpies)){
        log.error(String.format("the company to create already exists:%s", amcDebtor.getName()));
        amcDebtor.setCompanyId(amcCmpies.get(0).getId());
      }else{
        AmcCmpy amcCmpy = new AmcCmpy();
        amcCmpy.setName(amcDebtor.getName());
        amcCmpyMapper.insertSelective(amcCmpy);
        amcDebtor.setCompanyId(amcCmpy.getId());
      }
    }
    boolean gotDuplicate = false;
    try {
      amcDebtor.setId(null);
      amcDebtorMapper.insertSelective(amcDebtor);

      return amcDebtor;
    }
    catch (DataIntegrityViolationException e) {
      log.error(String.format("got duplicate insert for :%s", amcDebtor.getName()), e);
      if(e.toString().contains("duplicate")){
        gotDuplicate = true;
      }
    }
    if(gotDuplicate){
      AmcDebtorExample amcDebtorExample = new AmcDebtorExample();
      amcDebtorExample.createCriteria().andNameEqualTo(amcDebtor.getName());
      List<AmcDebtor> amcDebtors = amcDebtorMapper.selectByExample(amcDebtorExample);
      return amcDebtors.get(0);
    }else{
      throw ExceptionUtils.getAmcException(AmcExceptions.INSERT_DB_ERROR);
    }

  }

  @Override
  public AmcDebtor update(AmcDebtor debtor) {
    amcDebtorMapper.updateByPrimaryKey(debtor);
    return debtor;
  }

  @Override
  public AmcCmpy create(AmcCmpy amcCmpy) {
    amcCmpyMapper.insertSelective(amcCmpy);
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
  public AmcInfo getAmcInfo(Long amcDebtId) throws Exception {
    AmcDebt amcDebt = amcDebtMapper.selectByPrimaryKey(amcDebtId);
    if(null == amcDebt){
      throw ExceptionUtils.getAmcException(AmcExceptions.NO_AMCDEBT_AVAILABLE, amcDebtId.toString());
    }
    AmcInfo amcInfo = amcInfoMapper.selectByPrimaryKey(amcDebt.getAmcId());

    return amcInfo;
  }

  @Override
  public List<AmcDebtor> getDebtors(Long amcDebtId) {
    AmcDebtorExample amcDebtorExample = new AmcDebtorExample();
    amcDebtorExample.createCriteria().andDebtIdEqualTo(amcDebtId);
    List<AmcDebtor> amcDebtors = amcDebtorMapper.selectByExample(amcDebtorExample);


    return amcDebtors;
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
  public AmcOrigCreditor createOrigCreditor(AmcOrigCreditor amcOrigCreditor) {
    amcOrigCreditorMapper.insertSelective(amcOrigCreditor);
    return amcOrigCreditor;
  }

  @Override
  public List<AmcDebtor> getAllDebtors(Long offset, int size, int type, Map<String, Direction> orderByParam)
      throws Exception {
    AmcDebtorExample amcDebtorExample = new AmcDebtorExample();
    if(type > 0 ){
      amcDebtorExample.createCriteria().andTypeEqualTo(type);
    }
    amcDebtorExample.setOrderByClause(SQLUtils.getOrderBy(orderByParam));
    return amcDebtorMapper.selectByExample(amcDebtorExample);
  }

  @Override
  public Long getDebtorCount() {
    AmcDebtorExample amcDebtorExample = new AmcDebtorExample();
    return amcDebtorMapper.countByExample(amcDebtorExample);
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
