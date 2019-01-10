package com.wensheng.zcc.amc.service.impl;

import com.wensheng.zcc.amc.dao.mysql.mapper.AmcDebtMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcPersonMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.ext.AmcDebtExtMapper;
import com.wensheng.zcc.amc.module.dao.helper.ImageClassEnum;
import com.wensheng.zcc.amc.module.dao.mongo.entity.DebtImage;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcAsset;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebt;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtExample;
import com.wensheng.zcc.amc.module.dao.mysql.auto.ext.AmcDebtExt;
import com.wensheng.zcc.amc.module.vo.AmcAssetVo;
import com.wensheng.zcc.amc.module.vo.AmcDebtVo;
import com.wensheng.zcc.amc.service.AmcDebtService;
import com.wensheng.zcc.amc.service.AmcHelperService;
import com.wensheng.zcc.amc.service.impl.helper.Dao2VoUtils;
import com.wensheng.zcc.amc.utils.AmcNumberUtils;
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
  public AmcDebtVo get(Long AmcDebtId) {
    return null;
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
  public Long getTotalCount() {
    AmcDebtExample amcDebtExample = new AmcDebtExample();
    amcDebtExample.createCriteria().andIdGreaterThan(0L);
    return amcDebtMapper.countByExample(null);
  }
}
