package com.wensheng.zcc.amc.service.impl;

import com.wensheng.zcc.amc.dao.mysql.mapper.AmcCmpyMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcCreditorDebtMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcCreditorMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcDebtContactorMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcDebtMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcDebtorMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcGrntctrctMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcGrntorMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcInfoMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcOrigCreditorMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.ext.AmcDebtExtMapper;
import com.wensheng.zcc.amc.module.dao.helper.DebtorTypeEnum;
import com.wensheng.zcc.amc.module.dao.helper.ImageClassEnum;
import com.wensheng.zcc.amc.module.dao.helper.PublishStateEnum;
import com.wensheng.zcc.amc.module.dao.helper.QueryParamEnum;
import com.wensheng.zcc.amc.module.dao.mongo.entity.AmcOperLog;
import com.wensheng.zcc.amc.module.dao.mongo.entity.DebtAdditional;
import com.wensheng.zcc.amc.module.dao.mongo.entity.DebtImage;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcCmpy;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcCmpyExample;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebt;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtContactor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtExample;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtorExample;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcInfo;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcOrigCreditor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcOrigCreditorExample;
import com.wensheng.zcc.amc.module.dao.mysql.auto.ext.AmcDebtExt;
import com.wensheng.zcc.amc.module.vo.AmcDebtCreateVo;
import com.wensheng.zcc.amc.module.vo.AmcDebtExtVo;
import com.wensheng.zcc.amc.module.vo.AmcDebtSummary;
import com.wensheng.zcc.amc.module.vo.AmcDebtVo;
import com.wensheng.zcc.amc.module.vo.AmcDebtorCmpy;
import com.wensheng.zcc.amc.module.vo.AmcDebtorPerson;
import com.wensheng.zcc.amc.module.vo.base.BaseActionVo;
import com.wensheng.zcc.amc.service.AmcAssetService;
import com.wensheng.zcc.amc.service.AmcDebtService;
import com.wensheng.zcc.amc.service.AmcDebtpackService;
import com.wensheng.zcc.amc.service.AmcHelperService;
import com.wensheng.zcc.amc.service.AmcOssFileService;
import com.wensheng.zcc.amc.service.impl.helper.Dao2VoUtils;
import com.wensheng.zcc.amc.utils.SQLUtils;
import com.wensheng.zcc.common.utils.AmcBeanUtils;
import com.wensheng.zcc.common.utils.AmcNumberUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils;
import com.wensheng.zcc.common.utils.ExceptionUtils.AmcExceptions;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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
@CacheConfig(cacheNames = {"DEBT"})
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
  AmcDebtContactorMapper amcDebtContactorMapper;

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

  @Autowired
  AmcAssetService amcAssetService;

  @Autowired
  AmcOssFileService amcOssFileService;



  @Override
  public synchronized DebtImage  saveImageInfo(String ossPath, String originName, Long debtId, String fileDesc,
      ImageClassEnum imageClass) {
    DebtImage debtImage = new DebtImage();
    debtImage.setDebtId(debtId);
    debtImage.setOriginalName(originName);
    debtImage.setDescription(fileDesc);
    debtImage.setOssPath(ossPath);
    debtImage.setIsToOss(!StringUtils.isEmpty(ossPath));
    debtImage.setTag(imageClass.getId());
    Query query = new Query();
    if(imageClass == ImageClassEnum.MAIN){
      query.addCriteria(Criteria.where("debtId").is(debtId).and("tag").is(ImageClassEnum.MAIN.getId()));
      List<DebtImage> debtImageList =  wszccTemplate.find(query, DebtImage.class);
      if(!CollectionUtils.isEmpty(debtImageList)){

        for(DebtImage debtImageItem: debtImageList){

          try {
            if(!debtImageItem.getOssPath().equals(ossPath)){
              logger.info("now need delete history main image");
              amcOssFileService.delFileInOss(debtImageItem.getOssPath());
              wszccTemplate.remove(debtImageItem);
            }

          } catch (Exception e) {
            e.printStackTrace();
            log.error("Failed to del ossFile with osspath:"+ debtImage.getOssPath(), e);
          }

        }
      }
    }
    query = new Query();
    query.addCriteria(Criteria.where("ossPath").is(ossPath).and("debtId").is(debtId));
    List<DebtImage> debtImageList =  wszccTemplate.find(query, DebtImage.class);
    Update update = new Update();

    if(!CollectionUtils.isEmpty(debtImageList)){
      log.info("there is duplicate image, just update it");
      AmcBeanUtils.copyProperties(debtImage, debtImageList.get(0));
      wszccTemplate.save(debtImageList.get(0));
    }else{
      log.info("there is no image, just insert it");
      wszccTemplate.save(debtImage);
    }

    return debtImage;
  }

  @Override
  @Transactional
  @CacheEvict
  public AmcDebtVo create(AmcDebt amcDebt) {
    amcDebtMapper.insertSelective(amcDebt);
    return convertDo2Vo(amcDebt);
  }

  @Override
  @Transactional
  @CacheEvict
  public int del(Long amcDebtId) {
    AmcDebt amcDebt = amcDebtMapper.selectByPrimaryKey(amcDebtId);
    if(amcDebt != null && (amcDebt.getPublishState() == PublishStateEnum.DRAFT.getStatus()||amcDebt.getPublishState() == PublishStateEnum.NOTCLEAR.getStatus())){
      amcAssetService.del(amcDebtId);
      AmcDebtorExample amcDebtorExample = new AmcDebtorExample();
      amcDebtorExample.createCriteria().andDebtIdEqualTo(amcDebtId);
      amcDebtorMapper.deleteByExample(amcDebtorExample);
      Query queryNormal = new Query();
      queryNormal.addCriteria(Criteria.where("amcDebtId").is(amcDebtId));
      wszccTemplate.remove(queryNormal, DebtAdditional.class);
      Query queryImage = new Query();
      queryImage.addCriteria(Criteria.where("debtId").is(amcDebtId));
      List<DebtImage> debtImages = wszccTemplate.find(queryImage, DebtImage.class);
      for(DebtImage debtImage: debtImages){
        try {
          amcOssFileService.delFileInOss(debtImage.getOssPath());
        } catch (Exception e) {
          e.printStackTrace();
          log.error("Failed to del ossFile with osspath:"+ debtImage.getOssPath(), e);
        }
      }
      wszccTemplate.remove(queryNormal, DebtImage.class);
      amcDebtMapper.deleteByPrimaryKey(amcDebtId);
    }else{
      log.error(String.format("Cannot del not %s debt with id:%s",
          PublishStateEnum.lookupByDisplayStatusUtil(amcDebt.getPublishState()).getName(), amcDebtId));
    }

    return 1;
  }

  @Override
  public AmcDebtVo update(AmcDebt amcDebt) {
    int result = amcDebtMapper.updateByPrimaryKeySelective(amcDebt);
    amcAssetService.updateByDebtState(amcDebt.getId(), amcDebt.getPublishState());
    return null;
  }

  @Override
  public AmcDebtVo updatePublishState(AmcDebt amcDebt) {

    amcDebtMapper.updateByPrimaryKeySelective(amcDebt);
    amcAssetService.updateByDebtState(amcDebt.getId(),
        amcDebt.getPublishState());

    return null;


  }

  @Override
  public List<AmcDebt> queryAllByUserId( Long userId) {
    AmcDebtExample amcDebtExample = new AmcDebtExample();
    amcDebtExample.createCriteria().andUpdateByEqualTo(userId);
    amcDebtExample.or(amcDebtExample.createCriteria().andCreatedByEqualTo(userId));
    List<AmcDebt>  amcDebts =  amcDebtMapper.selectByExample(amcDebtExample);
    return amcDebts;
  }

  private DebtAdditional queryAddtional(Long debtId){
    Query query = new Query();
    query.addCriteria(Criteria.where("amcDebtId").is(debtId));
    List<DebtAdditional> debtAdditionals = wszccTemplate.find(query, DebtAdditional.class);
    return debtAdditionals.get(0);
  }

  private DebtImage queryImage(Long debtId){
    Query query = new Query();
    query.addCriteria(Criteria.where("amcDebtId").is(debtId));
    List<DebtImage> debtImages = wszccTemplate.find(query, DebtImage.class);
    return debtImages.get(0);
  }

  @Override
  public AmcDebtExtVo get(Long amcDebtId) throws Exception {

    List<AmcDebtExt> amcDebtExts = amcDebtExtMapper.selectByPrimaryKeyExt(amcDebtId);
    if(CollectionUtils.isEmpty(amcDebtExts )){
      throw ExceptionUtils.getAmcException(AmcExceptions.NO_AMCDEBT_AVAILABLE);
    }
    AmcDebtExtVo amcDebtExtVo = new AmcDebtExtVo();

    AmcDebtVo amcDebtVo = Dao2VoUtils.convertDo2Vo(amcDebtExts.get(0).getDebtInfo());
    amcDebtVo.setAssetVos(Dao2VoUtils.convertDoList2VoList(amcDebtExts.get(0).getAmcAssets()));
    AmcDebtContactor amcDebtContactor = amcDebtContactorMapper.selectByPrimaryKey(amcDebtExts.get(0).getDebtInfo().getAmcContactorId());
    AmcDebtContactor amcDebtContactor2 = amcDebtContactorMapper.selectByPrimaryKey(amcDebtExts.get(0).getDebtInfo().getAmcContactor2Id());

    amcDebtVo.setAmcContactorId(amcDebtContactor);
    amcDebtVo.setAmcContactor2Id(amcDebtContactor2);




    Query query = new Query();
    query.addCriteria(Criteria.where("amcDebtId").is(amcDebtId));

    List<DebtAdditional> debtAdditionals = wszccTemplate.find(query, DebtAdditional.class);
    if(!CollectionUtils.isEmpty(debtAdditionals)){
      amcDebtVo.setDebtAdditional(debtAdditionals.get(0));
    }
    query = new Query();
    query.addCriteria(Criteria.where("debtId").is(amcDebtId).and("tag").is(ImageClassEnum.MAIN.getId()));
    List<DebtImage> debtImages = wszccTemplate.find(query, DebtImage.class);
    if(!CollectionUtils.isEmpty(debtImages)){
      amcDebtVo.setDebtImage(debtImages.get(0));
    }
    amcDebtExtVo.setAmcDebtVo(amcDebtVo);
    return amcDebtExtVo;
  }



  @Override
  public List<AmcDebtVo> query(AmcDebt queryCond, int offset, int size) {

    AmcDebtExample amcDebtExample = new AmcDebtExample();
    amcDebtExample.createCriteria().andPublishStateNotEqualTo(PublishStateEnum.DELETED.getStatus());
    RowBounds rowBounds = new RowBounds(offset, size);

    List<AmcDebt> amcDebts = amcDebtMapper.selectByExampleWithRowbounds(amcDebtExample, rowBounds);



    if(!CollectionUtils.isEmpty(amcDebts)){
      return doList2VoList(amcDebts);
    }

    return null;
  }



  private List<AmcDebtVo> doList2VoList(List<AmcDebt> originList){
    List<AmcDebtVo> amcDebtVos = new ArrayList<>();

    Query query;
    for(AmcDebt amcDebt: originList){
      AmcDebtVo amcDebtVo = convertDo2Vo(amcDebt);
      query = new Query();
      query.addCriteria(Criteria.where("debtId").is(amcDebt.getId()));

      amcDebtVos.add(amcDebtVo);
    }

    updateDebtVosWithMongo(amcDebtVos);

    return amcDebtVos;

  }

  private AmcDebtVo convertDo2Vo(AmcDebt amcDebt) {
    AmcDebtVo amcDebtVo = new AmcDebtVo();
    AmcBeanUtils.copyProperties(amcDebt, amcDebtVo);
    if(amcDebt.getBaseAmount() > 0 ){
      amcDebtVo.setBaseAmount(AmcNumberUtils.getDecimalFromLongDiv100(amcDebt.getBaseAmount()));

    }
    if(amcDebt.getValuation() !=null && amcDebt.getValuation() > 0 ){
      amcDebtVo.setValuation(AmcNumberUtils.getDecimalFromLongDiv100(amcDebt.getValuation()));

    }
    if(amcDebt.getTotalAmount() !=null && amcDebt.getTotalAmount() > 0 ){
      amcDebtVo.setTotalAmount(AmcNumberUtils.getDecimalFromLongDiv100(amcDebt.getTotalAmount()));

    }
    if(amcDebt.getAmcContactorId() > 0){
      amcDebtVo.setAmcContactorId(amcHelperService.getAmcDebtContactor(amcDebt.getAmcContactorId()));
    }

    if(amcDebt.getAmcContactor2Id() != null && amcDebt.getAmcContactor2Id() > 0){
      amcDebtVo.setAmcContactor2Id(amcHelperService.getAmcDebtContactor(amcDebt.getAmcContactor2Id()));
    }
    return amcDebtVo;
  }




  private AmcDebtVo convertDo2Vo(AmcDebtExt amcDebtExt) {
    AmcDebtVo amcDebtVo = new AmcDebtVo();
    AmcBeanUtils.copyProperties(amcDebtExt.getDebtInfo(), amcDebtVo);
    if(amcDebtExt.getDebtInfo().getBaseAmount() > 0 ){
      amcDebtVo.setBaseAmount(AmcNumberUtils.getDecimalFromLongDiv100(amcDebtExt.getDebtInfo().getBaseAmount()));

    }
    if(amcDebtExt.getDebtInfo().getValuation() !=null && amcDebtExt.getDebtInfo().getValuation() > 0 ){
      amcDebtVo.setValuation(AmcNumberUtils.getDecimalFromLongDiv100(amcDebtExt.getDebtInfo().getValuation()));

    }
    if(amcDebtExt.getDebtInfo().getTotalAmount() > 0 ){
      amcDebtVo.setTotalAmount(AmcNumberUtils.getDecimalFromLongDiv100(amcDebtExt.getDebtInfo().getTotalAmount()));

    }
    if(amcDebtExt.getDebtInfo().getAmcContactorId() > 0){
      amcDebtVo.setAmcContactorId(amcHelperService.getAmcDebtContactor(amcDebtExt.getDebtInfo().getAmcContactorId()));
    }

    if(amcDebtExt.getDebtInfo().getAmcContactor2Id() != null && amcDebtExt.getDebtInfo().getAmcContactor2Id() > 0){
      amcDebtVo.setAmcContactor2Id(amcHelperService.getAmcDebtContactor(amcDebtExt.getDebtInfo().getAmcContactor2Id()));
    }

    if(CollectionUtils.isEmpty(amcDebtExt.getAmcAssets())){
      return amcDebtVo;
    }
    try {
      amcDebtVo.setAssetVos(Dao2VoUtils.convertDoList2VoList(amcDebtExt.getAmcAssets()));
    } catch (Exception e) {
      log.error("convert asset to assetVo met exception:", e);
    }

    return amcDebtVo;
  }

  private AmcDebtVo convertDoExt2Vo(AmcDebtExt amcDebtExt) throws Exception {
    AmcDebtVo amcDebtVo = convertDo2Vo(amcDebtExt.getDebtInfo());
    amcDebtVo.setAssetVos(Dao2VoUtils.convertDoList2VoList(amcDebtExt.getAmcAssets()));
    amcDebtVo.setDebtImage(queryImage(amcDebtVo.getId()));
    amcDebtVo.setDebtAdditional(queryAddtional(amcDebtVo.getId()));
    return amcDebtVo;

  }



  @Override
  public List<AmcDebtVo> queryAllExt(Long offset, int size, Map<String, Sort.Direction> orderBy,
      Map<String, Object> queryParam) throws Exception {


    AmcDebtExample amcDebtExample = getAmcDebtExampleWithQueryParam(queryParam);
    RowBounds rowBounds = new RowBounds(offset.intValue(), size);
    try{
      amcDebtExample.setOrderByClause(SQLUtils.getOrderBy(orderBy, rowBounds));
    }catch (Exception ex){
      logger.error("there is no orderBy params:" + ex.getMessage());
    }



    List<AmcDebt> amcDebtList = amcDebtMapper.selectByExample(amcDebtExample);
//    AmcDebtorExample amcDebtorExample = new AmcDebtorExample();
    List<Long> debtIds = amcDebtList.stream().map(item -> item.getId()).collect(Collectors.toList());
//    amcDebtorExample.createCriteria().andDebtIdIn(debtIds);
//    List<AmcDebtor> amcDebtors = amcDebtorMapper.selectByExample(amcDebtorExample);
//
//    Map<Long , List<AmcDebtor>> debtId2Debtor = new HashMap<>();
//    for(AmcDebtor amcDebtor: amcDebtors){
//      if(debtId2Debtor.containsKey(amcDebtor.getDebtId())){
//        debtId2Debtor.get(amcDebtor.getDebtId()).add(amcDebtor);
//      }else{
//        debtId2Debtor.put(amcDebtor.getDebtId(), new ArrayList<AmcDebtor>());
//      }
//    }
    Query query = new Query();
//    query.addCriteria(Criteria.where("amcDebtId").in(debtIds));

//    List<DebtAdditional> debtAdditionals =  wszccTemplate.find(query, DebtAdditional.class);
//    Map<Long, DebtAdditional> debtAdditionalMap =
//        debtAdditionals.stream().collect(Collectors.toMap(item->item.getAmcDebtId(), item -> item));

    query.addCriteria(Criteria.where("debtId").in(debtIds));
    List<DebtImage> debtImages = wszccTemplate.find(query, DebtImage.class);
    Map<Long, DebtImage> debtImageMap = new HashMap<>();
    debtImages.forEach(item -> debtImageMap.put(item.getDebtId(), item));

    List<AmcDebtVo> amcDebtVos = new ArrayList<>();
    for(AmcDebt amcDebt: amcDebtList){
      AmcDebtVo amcDebtVo = convertDo2Vo(amcDebt);
      if(debtImageMap.containsKey(amcDebt.getId())){
        amcDebtVo.setDebtImage(debtImageMap.get(amcDebt.getId()));
      }


      amcDebtVos.add(amcDebtVo);
    }
    return amcDebtVos;
  }


  @Override
  public List<AmcDebtVo> queryAllDebt(Long offset, int size, Map<String, Sort.Direction> orderBy) throws Exception {


    AmcDebtExample amcDebtExample = new AmcDebtExample();
    RowBounds rowBounds = new RowBounds(offset.intValue(), size);
    try{
      String orderByStr = SQLUtils.getOrderBy(orderBy, rowBounds);
      amcDebtExample.setOrderByClause(orderByStr);
    }catch (Exception ex){
      logger.error("there is no orderBy params:" + ex.getMessage());
    }



    List<AmcDebtExt> amcDebtExtList = amcDebtExtMapper.selectByExampleWithRowboundsExt(amcDebtExample);

    List<AmcDebtVo> amcDebtVos = new ArrayList<>();
    for(AmcDebtExt amcDebtExt: amcDebtExtList){
      amcDebtVos.add(convertDo2Vo(amcDebtExt));
    }

    updateDebtVosWithMongo(amcDebtVos);
    return amcDebtVos;
  }


  private void updateDebtVosWithMongo(List<AmcDebtVo> amcDebtVos){
    Map<Long, AmcDebtVo> amcDebtVosMap = amcDebtVos.stream().collect(
        Collectors.toMap(item-> item.getId(), item -> item));
//    Set<Long> debtIds = amcDebtVos.stream().map(item -> item.getId()).collect(Collectors.toSet());
    Query query = new Query();
    query.addCriteria(Criteria.where("debtId").in(amcDebtVosMap.keySet()));
    List<DebtImage> debtImages = wszccTemplate.find(query, DebtImage.class);
    for(DebtImage debtImage: debtImages){
      if(amcDebtVosMap.containsKey(debtImage.getDebtId())){
        amcDebtVosMap.get(debtImage.getDebtId()).setDebtImage(debtImage);
      }
    }

    List<DebtAdditional> additionals = wszccTemplate.find(query, DebtAdditional.class);
    for(DebtAdditional additional: additionals){
      if(amcDebtVosMap.containsKey(additional.getAmcDebtId())){
        amcDebtVosMap.get(additional.getAmcDebtId()).setDebtAdditional(additional);
      }
    }
  }

  @Override
  public List<AmcDebt> queryByDebtpackId(Long debtPackId) {
    AmcDebtExample amcDebtExample = new AmcDebtExample();
    amcDebtExample.createCriteria().andDebtpackIdEqualTo(debtPackId);
    List<AmcDebt> amcDebts = amcDebtMapper.selectByExample(amcDebtExample);
    return amcDebts;
  }

  @Override
  public Long getTotalCount(Map<String, Object> queryParamMap) throws Exception {
    AmcDebtExample amcDebtExample = getAmcDebtExampleWithQueryParam(queryParamMap);
    return amcDebtMapper.countByExample(amcDebtExample);
  }


  @Override
  public boolean isAmcContactexist(Long amcContact1) {
    AmcDebtContactor AmcDebtContactor = amcDebtContactorMapper.selectByPrimaryKey(amcContact1);
    if(AmcDebtContactor == null){
      return false;
    }

    return true;
  }

  @Override
  public AmcDebtor create(AmcDebtor amcDebtor) throws Exception {

    //if the creditor is company, need check if company exists
    if( amcDebtor.getDebtorType() == DebtorTypeEnum.COMPANY.getId() && (amcDebtor.getCompanyId() == null || amcDebtor.getCompanyId() <= 0)){
      //need create company
      if (StringUtils.isEmpty(amcDebtor.getDebtorName())){
        throw ExceptionUtils.getAmcException(AmcExceptions.MISSING_MUST_PARAM,"company name is must");
      }
      AmcCmpyExample amcCmpyExample = new AmcCmpyExample();
      amcCmpyExample.createCriteria().andNameEqualTo(amcDebtor.getDebtorName());
      List<AmcCmpy> amcCmpies = amcCmpyMapper.selectByExample(amcCmpyExample);
      if(!CollectionUtils.isEmpty(amcCmpies)){
        log.error(String.format("the company to create already exists:%s", amcDebtor.getDebtorName()));
        amcDebtor.setCompanyId(amcCmpies.get(0).getId());
      }else{
        AmcCmpy amcCmpy = new AmcCmpy();
        amcCmpy.setName(amcDebtor.getDebtorName());
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
      log.error(String.format("got duplicate insert for :%s", amcDebtor.getDebtorName()), e);
      if(e.toString().contains("Duplicate")){
        gotDuplicate = true;
      }
    }
    if(gotDuplicate){
      AmcDebtorExample amcDebtorExample = new AmcDebtorExample();
      amcDebtorExample.createCriteria().andDebtorNameEqualTo(amcDebtor.getDebtorName());
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
    boolean needQuery = false;
    try{
      amcCmpyMapper.insertSelective(amcCmpy);
    }
    catch (Exception e) {
      log.error(" item to insert already exists", e);
      needQuery = true;
    }
    if(needQuery){
      AmcCmpyExample amcCmpyExample = new AmcCmpyExample();
      amcCmpyExample.createCriteria().andNameEqualTo(amcCmpy.getName());
      List<AmcCmpy> amcCmpyHistories = amcCmpyMapper.selectByExample(amcCmpyExample);
      return amcCmpyHistories.get(0);
    }
    return amcCmpy;
  }

  @Override
  public AmcCmpy update(AmcCmpy amcCmpy) {
    amcCmpyMapper.updateByPrimaryKey(amcCmpy);
    return amcCmpy;
  }

  @Override
  @Transactional
  public void connDebt2Debtors(List<Long> debtorIds, Long debtId) {


    AmcDebtorExample amcDebtorExample = new AmcDebtorExample();
    amcDebtorExample.createCriteria().andDebtIdEqualTo(debtId);
    List<AmcDebtor> amcDebtors = amcDebtorMapper.selectByExample(amcDebtorExample);

    Set<Long> updateList = new HashSet<>();
    Set<Long> historyList = new HashSet<>();
    debtorIds.forEach(item -> updateList.add(item));
    amcDebtors.forEach(item -> historyList.add(item.getId()));
    List<Long> delList = new ArrayList<>();
    List<Long> addList = new ArrayList<>();
    if(!CollectionUtils.isEmpty(historyList)){
      historyList.forEach(historyItem ->{if(!updateList.contains(historyItem)){  delList.add(historyItem);}});
    }
    if(!CollectionUtils.isEmpty(updateList)){
      updateList.forEach(updateItem ->{if(!historyList.contains(updateItem)){  addList.add(updateItem);}});
    }
    updateDebtId4Debtors(addList, debtId);
    amcDebtorExample = new AmcDebtorExample();
    if(!CollectionUtils.isEmpty(delList)){
      amcDebtorExample.createCriteria().andIdIn(delList).andDebtIdEqualTo(debtId);
      amcDebtorMapper.deleteByExample(amcDebtorExample);
    }

  }

  private void updateDebtId4Debtors(List<Long> debtorIds, Long debtId){
    if(!CollectionUtils.isEmpty(debtorIds) && debtId > 0){
      AmcDebtorExample amcDebtorExample = new AmcDebtorExample();
      amcDebtorExample.createCriteria().andIdIn(debtorIds);
      AmcDebtor amcDebtor = new AmcDebtor();
      amcDebtor.setDebtId(debtId);
      int count = amcDebtorMapper.updateByExampleSelective(amcDebtor, amcDebtorExample);
      if(count <= 0){
        log.error(String.format("Failed to update debtors by %s", debtorIds.stream().map(Object::toString).collect(
            Collectors.joining(", "))));
      }
    }
  }

  @Override
  public void saveDebtDesc(String debtDesc, Long debtId) {
    DebtAdditional debtAdditional = new DebtAdditional();
    debtAdditional.setAmcDebtId(debtId);
    debtAdditional.setDesc(debtDesc);
    Query query = new Query();
    query.addCriteria(Criteria.where("amcDebtId").is(debtId));
    Update update = new Update();
    update.set("desc", debtDesc);
    wszccTemplate.upsert(query, update, DebtAdditional.class);

    return;

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
    if (StringUtils.isEmpty(amcOrigCreditor.getBranchName() )){
      String instant = Instant.now().toString();
      String rand = instant.substring(instant.length() - 19);
      amcOrigCreditor.setBranchName(rand);
    }
    amcOrigCreditorMapper.insertSelective(amcOrigCreditor);

    return amcOrigCreditor;
  }

  @Override
  public List<AmcDebtor> getAllUnasignedDebtors(Long offset, int size, int type, Map<String, Direction> orderByParam)
      throws Exception {
    AmcDebtorExample amcDebtorExample = new AmcDebtorExample();
    if(type > 0 ){
      //unasigned Debtor is assigned with debtId as 0L
      amcDebtorExample.createCriteria().andDebtorTypeEqualTo(type).andDebtIdEqualTo(0L);
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

  @Override
  public AmcDebtSummary getSummaryInfo() {
    AmcDebtExample amcDebtExample = new AmcDebtExample();
    amcDebtExample.createCriteria().andPublishStateNotEqualTo(PublishStateEnum.DELETED.getStatus()).andBaseAmountIsNotNull();
    List<AmcDebt> amcDebts = amcDebtMapper.selectByExample(amcDebtExample);
    Long totalBaseAmount = 0L;
    for(AmcDebt amcDebt: amcDebts){
        totalBaseAmount += amcDebt.getBaseAmount();
    }
    BigDecimal totalBaseAmountBigDecimal = AmcNumberUtils.getDecimalFromLongDiv100(totalBaseAmount);
    AmcDebtSummary amcDebtSummary = new AmcDebtSummary();
    amcDebtSummary.setDebtTotalAmount(totalBaseAmountBigDecimal);
    amcDebtSummary.setDebtTotalCount(Long.valueOf(amcDebts.size()));
    if(CollectionUtils.isEmpty(amcDebts)){
      amcDebtSummary.setAssetTotalCount(0L);
      return amcDebtSummary;
    }

    List<Long> amcDebtIds = amcDebts.stream().map(iitem -> iitem.getId()).collect(Collectors.toList());
    Long assetCount = amcAssetService.getAssetCountWithDebtIds(amcDebtIds);
    amcDebtSummary.setAssetTotalCount(assetCount);
    return amcDebtSummary;
  }

  @Override
  public void connDebt2Cmpys(List<AmcDebtorCmpy> newCompanies, Long id) {
    for(AmcDebtorCmpy cmpyItem: newCompanies){
      AmcDebtor amcDebtor = new AmcDebtor();
      amcDebtor.setDebtId(id);
      amcDebtor.setCompanyId(cmpyItem.getCmpyId());
      amcDebtor.setDebtorType(DebtorTypeEnum.COMPANY.getId());
      amcDebtor.setRole(cmpyItem.getRole().getId());
      amcDebtor.setDebtorName(cmpyItem.getName());
      amcDebtorMapper.insertSelective(amcDebtor);
    }
  }

  @Override
  public void connDebt2Persons(List<AmcDebtorPerson> newPersons, Long id) {
    for(AmcDebtorPerson personItem: newPersons){
      AmcDebtor amcDebtor = new AmcDebtor();
      amcDebtor.setDebtId(id);
      amcDebtor.setRole(personItem.getRole().getId());
      amcDebtor.setDebtorName(personItem.getName());
      amcDebtor.setDebtorType(DebtorTypeEnum.PERSON.getId());
      amcDebtor.setDebtorName(personItem.getName());
      amcDebtorMapper.insertSelective(amcDebtor);
    }
  }

  @Override
  public AmcDebt getDebt(Long debtId) {
    return amcDebtMapper.selectByPrimaryKey(debtId);
  }

  @Override
  public <T> void saveOperLog(BaseActionVo<T> amcDebtVoBaseActionVo, String reviewComment) {
    AmcOperLog amcOperLog = new AmcOperLog();
    amcOperLog.setActionId(amcDebtVoBaseActionVo.getEditActionId());
    amcOperLog.setComment(reviewComment);
    if(amcDebtVoBaseActionVo.getContent() instanceof AmcDebtVo){
      amcOperLog.setDebtId(((AmcDebtVo)amcDebtVoBaseActionVo.getContent()).getId());
      amcOperLog.setUserId(((AmcDebtVo)amcDebtVoBaseActionVo.getContent()).getUpdateBy());
    }else if(amcDebtVoBaseActionVo.getContent() instanceof AmcDebtCreateVo){
      amcOperLog.setDebtId(((AmcDebtCreateVo)amcDebtVoBaseActionVo.getContent()).getId());
      amcOperLog.setUserId(((AmcDebtCreateVo)amcDebtVoBaseActionVo.getContent()).getUpdateBy());
    }else if(amcDebtVoBaseActionVo.getContent() instanceof List){
      for(AmcDebtVo item: (List<AmcDebtVo>)amcDebtVoBaseActionVo.getContent()){
        amcOperLog = new AmcOperLog();
        amcOperLog.setDebtId(item.getId());
        amcOperLog.setUserId(item.getUpdateBy());
        amcOperLog.setActionId(amcDebtVoBaseActionVo.getEditActionId());
        amcOperLog.setDateTime(LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC));
        wszccTemplate.save(amcOperLog);
      }
      return;
    }
    amcOperLog.setDateTime(LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC));
    wszccTemplate.save(amcOperLog);
  }

  @Override
  public List<AmcOperLog> getOperLog(Long debtId, Integer actionId) {
    Query query = new Query();
    Criteria criteria = Criteria.where("debtId").is(debtId);

    if(actionId > 0){
      criteria.and("actionId").is(actionId);
    }
    query.addCriteria(criteria);
    return wszccTemplate.find(query, AmcOperLog.class);
  }

  @Override
  public void setRecomm(List<Long> debtIds, int id) {
    AmcDebt amcDebt = new AmcDebt();
    amcDebt.setIsRecommanded(id);
    AmcDebtExample amcDebtExample = new AmcDebtExample();
    amcDebtExample.createCriteria().andIdIn(debtIds);
    amcDebtMapper.updateByExampleSelective(amcDebt, amcDebtExample);
  }

  @Override
  @Cacheable
  public List<Long> getDebtIdsByPackIds(List<Long> debtPackIds){
    AmcDebtExample amcDebtExample = new AmcDebtExample();
    if(debtPackIds.size() > 1){
      amcDebtExample.createCriteria().andDebtpackIdIn(debtPackIds);
    }else{
      amcDebtExample.createCriteria().andDebtpackIdEqualTo(debtPackIds.get(0));
    }

    List<AmcDebt> amcDebts = amcDebtMapper.selectByExample(amcDebtExample);
    if(CollectionUtils.isEmpty(amcDebts)){
      return new ArrayList<>();
    }
    return amcDebts.stream().map(item -> item.getId()).collect(Collectors.toList());
  }

  public AmcDebtExample getAmcDebtExampleWithQueryParam(Map<String, Object> queryParam) throws Exception {
    AmcDebtExample amcDebtExample = new AmcDebtExample();
    AmcDebtExample.Criteria criteria = amcDebtExample.createCriteria();
    boolean needDefaultPublishState = true;
    if(!CollectionUtils.isEmpty(queryParam)){
      for(Entry<String, Object> item: queryParam.entrySet()){

        if(item.getKey().equals(QueryParamEnum.BaseAmount.name())){
          List<Long> amounts = (List) item.getValue();
          if(amounts.get(0) < 0 && amounts.get(1) > 0){
            criteria.andBaseAmountLessThanOrEqualTo(amounts.get(1));
          }else if(amounts.get(1) < 0 && amounts.get(0) > 0){
            criteria.andBaseAmountGreaterThan(amounts.get(0));
          }else if(amounts.get(0) > 0 && amounts.get(1) > 0){
            criteria.andBaseAmountBetween(amounts.get(0), amounts.get(1));
          }else{
            throw ExceptionUtils.getAmcException(AmcExceptions.INVALID_AMOUNT_RANGE,  String.format("%d,%d",amounts.get(0), amounts.get(1))
            );
          }
        }

        if(item.getKey().equals(QueryParamEnum.PublishStates.name())){
          criteria.andPublishStateIn((List) item.getValue());
          needDefaultPublishState = false;
        }



        if(item.getKey().equals(QueryParamEnum.AmcContactorId.name())){
          criteria.andAmcContactorIdEqualTo((Long)item.getValue());
        }

        if(item.getKey().equals(QueryParamEnum.Title.name())){
          StringBuilder sb = new StringBuilder().append("%").append(item.getValue()).append("%");
          criteria.andTitleLike(sb.toString());
        }

        if(item.getKey().equalsIgnoreCase(QueryParamEnum.Recommand.name())){
          criteria.andIsRecommandedIn((List) item.getValue());
        }

        if(item.getKey().equals(QueryParamEnum.CourtId.name())){
          criteria.andCourtIdEqualTo((Long)item.getValue());
        }
        if(item.getKey().equals(QueryParamEnum.DebtPackId.name())){
          List<Long> debtPackIds = (List<Long>)item.getValue();
          if(CollectionUtils.isEmpty(debtPackIds)){
            log.error("There is no debtIds for debtPackIds:{}, and it will return emtpy result set",
                item.getValue().toString());
            criteria.andDebtpackIdEqualTo(-1L);
          }else if(debtPackIds.size() > 1){
            criteria.andDebtpackIdIn(debtPackIds);
          }else{
            criteria.andDebtpackIdEqualTo(debtPackIds.get(0));
          }

        }
      }
    }
    if(needDefaultPublishState){
      criteria.andPublishStateNotEqualTo(PublishStateEnum.DELETED.getStatus());
    }
    return amcDebtExample;
  }


}
