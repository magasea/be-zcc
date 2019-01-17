package com.wensheng.zcc.amc.service.impl;

import com.wensheng.zcc.amc.dao.mysql.mapper.AmcCmpyMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcDebtMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcGrntctrctMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcGrntorMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcPersonMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.ext.AmcDebtExtMapper;
import com.wensheng.zcc.amc.module.dao.helper.GrantorTypeEnum;
import com.wensheng.zcc.amc.module.dao.helper.ImageClassEnum;
import com.wensheng.zcc.amc.module.dao.mongo.entity.DebtImage;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcCmpy;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebt;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtExample;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcGrntctrct;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcGrntor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcPerson;
import com.wensheng.zcc.amc.module.dao.mysql.auto.ext.AmcDebtExt;
import com.wensheng.zcc.amc.module.vo.AmcDebtVo;
import com.wensheng.zcc.amc.service.AmcDebtService;
import com.wensheng.zcc.amc.service.AmcHelperService;
import com.wensheng.zcc.amc.service.impl.helper.Dao2VoUtils;
import com.wensheng.zcc.amc.utils.AmcNumberUtils;
import com.wensheng.zcc.amc.utils.ExceptionUtils;
import com.wensheng.zcc.amc.utils.ExceptionUtils.AmcExceptions;
import com.wensheng.zcc.amc.utils.SQLUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * @author chenwei on 1/4/19
 * @project zcc-backend
 */
@Service
public class AmcDebtServiceImpl implements AmcDebtService {

  Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired
  AmcHelperService amcHelperService;

  @Autowired
  MongoTemplate secondaryMongoTemplate;

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
      List<DebtImage> debtImageList =  secondaryMongoTemplate.find(query, DebtImage.class);
      if(!CollectionUtils.isEmpty(debtImageList)){
        logger.info("now need update main pic to class other");
        for(DebtImage debtImageItem: debtImageList){
          debtImageItem.setTag(ImageClassEnum.OTHER.getId());
          secondaryMongoTemplate.save(debtImageItem);
        }
      }
    }
    secondaryMongoTemplate.save(debtImage);
    return 0;
  }

  @Override
  public AmcDebtVo create(AmcDebt AmcDebt) {
    return null;
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
}
