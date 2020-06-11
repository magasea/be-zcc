package com.wensheng.zcc.amc.service.impl;

import com.wensheng.zcc.amc.dao.mysql.mapper.AmcAssetMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcDebtContactorMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.AmcDebtMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.ZccDebtpackMapper;
import com.wensheng.zcc.amc.module.dao.helper.HasImageEnum;
import com.wensheng.zcc.amc.module.dao.helper.ImageClassEnum;
import com.wensheng.zcc.amc.module.dao.helper.PublishStateEnum;
import com.wensheng.zcc.amc.module.dao.mongo.entity.AssetImage;
import com.wensheng.zcc.amc.module.dao.mongo.entity.DebtImage;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcAsset;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcAssetExample;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebt;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtContactor;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcDebtExample;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.ZccDebtpack;
import com.wensheng.zcc.amc.service.AmcMiscService;
import com.wensheng.zcc.common.params.AmcSexEnum;
import com.wensheng.zcc.common.params.sso.AmcLocationEnum;
import com.wensheng.zcc.common.utils.AmcDateUtils;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class AmcMiscServiceImpl implements AmcMiscService {

  @Autowired
  AmcDebtMapper amcDebtMapper;

  @Autowired
  AmcAssetMapper amcAssetMapper;

  @Autowired
  MongoTemplate wszccTemplate;

  @Autowired
  ZccDebtpackMapper zccDebtpackMapper;

  @Autowired
  AmcDebtContactorMapper amcDebtContactorMapper;

  @Value("${recom.urls.getClickCount}")
  String getClickCountUrl;


  Map<Long, String> areaMapper = null;


  private RestTemplate restTemplate;

  @PostConstruct
  private void init(){
    restTemplate = new RestTemplate();
    GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
    gsonHttpMessageConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
    restTemplate.getMessageConverters().removeIf(item -> item instanceof MappingJackson2HttpMessageConverter);
    restTemplate.getMessageConverters().add(gsonHttpMessageConverter);
  }
  /**
   * generate amc code to empty field
   * for example: wensheng-gd-00001
   */
  public void patchAmcDebtCode(){


    AmcDebtExample amcDebtExample = new AmcDebtExample();
    amcDebtExample.createCriteria().andZccDebtCodeEqualTo("-1");
    List<AmcDebt> amcDebts = amcDebtMapper.selectByExample(amcDebtExample);
    for(AmcDebt amcDebt: amcDebts){
      if(amcDebt.getDebtpackId() != null &&  amcDebt.getDebtpackId() != -1L && areaMapper.containsKey(amcDebt.getDebtpackId())){
        String amcDebtCode = generateZccDebtCode(amcDebt.getDebtpackId(), amcDebt.getId());
        amcDebt.setZccDebtCode(amcDebtCode);
        amcDebtMapper.updateByPrimaryKeySelective(amcDebt);
      }else{
        log.error("amcDebt id:{}, amcDebtpackId:{}", amcDebt.getId(), amcDebt.getDebtpackId());
      }
    }

  }

  public String generateZccDebtCode(Long debtPackId, Long debtId){
    if(areaMapper == null || CollectionUtils.isEmpty(areaMapper)){
      areaMapper = new HashMap<>();
      List<ZccDebtpack> amcDeptpacks = zccDebtpackMapper.selectByExample(null);
      for(ZccDebtpack zccDebtpack : amcDeptpacks){
        if(zccDebtpack.getArea() == AmcLocationEnum.JIANGSU_LOCATION.getId()){
          areaMapper.put(zccDebtpack.getId(), "js");
        }else if(zccDebtpack.getArea() == AmcLocationEnum.GUANGDONG_LOCATION.getId()){
          areaMapper.put(zccDebtpack.getId(), "gd");
        }else if(zccDebtpack.getArea() == AmcLocationEnum.ZHEJIANG_LOCATION.getId()){
          areaMapper.put(zccDebtpack.getId(), "zj");
        }else if(zccDebtpack.getArea() == AmcLocationEnum.SHANGHAI_LOCATION.getId()){
          areaMapper.put(zccDebtpack.getId(),"sh");
        }else if(zccDebtpack.getArea() == AmcLocationEnum.BEIJING_LOCATION.getId()){
          areaMapper.put(zccDebtpack.getId(), "bj");
        }
      }
    }
    String amcDebtCode = "-1";
    if(debtPackId != null && debtPackId > 0){
      amcDebtCode = String.format("wensheng-%s-%s%s", areaMapper.get(debtPackId),
              AmcDateUtils.getCurrentYear(), debtId);
    }

    return amcDebtCode;

  }

  public String generateZccAssetCode( Long debtId, Long assetId){
    if(areaMapper == null || CollectionUtils.isEmpty(areaMapper)){
      areaMapper = new HashMap<>();
      List<ZccDebtpack> amcDeptpacks = zccDebtpackMapper.selectByExample(null);
      for(ZccDebtpack zccDebtpack : amcDeptpacks){
        if(zccDebtpack.getArea() == AmcLocationEnum.JIANGSU_LOCATION.getId()){
          areaMapper.put(zccDebtpack.getId(), "js");
        }else if(zccDebtpack.getArea() == AmcLocationEnum.GUANGDONG_LOCATION.getId()){
          areaMapper.put(zccDebtpack.getId(), "gd");
        }else if(zccDebtpack.getArea() == AmcLocationEnum.ZHEJIANG_LOCATION.getId()){
          areaMapper.put(zccDebtpack.getId(), "zj");
        }else if(zccDebtpack.getArea() == AmcLocationEnum.SHANGHAI_LOCATION.getId()){
          areaMapper.put(zccDebtpack.getId(),"sh");
        }else if(zccDebtpack.getArea() == AmcLocationEnum.BEIJING_LOCATION.getId()){
          areaMapper.put(zccDebtpack.getId(), "bj");
        }
      }
    }
    AmcDebt debt = amcDebtMapper.selectByPrimaryKey(debtId);
    String amcAssetCode = String.format("wensheng-%s-%s%s", areaMapper.get(debt.getDebtpackId()),
        AmcDateUtils.getCurrentYear(), assetId);
    return amcAssetCode;

  }

  public void patchAmcDebtContactor() {
    List<AmcDebt> amcDebts = amcDebtMapper.selectByExample(null);
    for(AmcDebt amcDebt: amcDebts){
      if(amcDebt.getAmcContactorName() == null || amcDebt.getAmcContactorPhone() == null||
          amcDebt.getAmcContactorName().equals("-1")||amcDebt.getAmcContactorPhone().equals("-1")){
        if(amcDebt.getAmcContactorId() !=null && amcDebt.getAmcContactorId() != -1){
          //can patch
          AmcDebtContactor amcDebtContactor = amcDebtContactorMapper.selectByPrimaryKey(amcDebt.getAmcContactorId());
          if(amcDebtContactor != null){
            amcDebt.setAmcContactorName(amcDebtContactor.getName());
            amcDebt.setAmcContactorPhone(amcDebtContactor.getPhoneNumber());
            amcDebtMapper.updateByPrimaryKeySelective(amcDebt);
            patchAmcAssets(amcDebt.getId(), amcDebtContactor);
          }
          else{
            log.error("Failed to patch debt with id:{} anc amcContactId:{}", amcDebt.getId(), amcDebt.getAmcContactorId());
          }
        }
      }
    }
    List<AmcAsset> amcAssets = amcAssetMapper.selectByExample(null);
    for(AmcAsset amcAsset: amcAssets){
      if(amcAsset.getAmcContactorName() == null || amcAsset.getAmcContactorPhone() == null || amcAsset.getAmcContactorPhone().equals("-1")
          || amcAsset.getAmcContactorName().equals("-1")){
          AmcDebtContactor amcDebtContactor = getContactorByDebtId(amcAsset.getDebtId());
          if(amcDebtContactor == null){
            log.error("Failed to patch AmcAsset with id:{}", amcAsset.getId());
          }else{
            amcAsset.setAmcContactorName(amcDebtContactor.getName());
            amcAsset.setAmcContactorPhone(amcDebtContactor.getPhoneNumber());
            amcAssetMapper.updateByPrimaryKeySelective(amcAsset);
          }
      }
    }
  }

  private AmcDebtContactor getContactorByDebtId(Long debtId){
    AmcDebt amcDebt = amcDebtMapper.selectByPrimaryKey(debtId);
    if(amcDebt != null && amcDebt.getAmcContactorId() != null && amcDebt.getAmcContactorId() > 0){
      AmcDebtContactor amcDebtContactor = amcDebtContactorMapper.selectByPrimaryKey(amcDebt.getAmcContactorId());
      return amcDebtContactor;
    }
    return null;
  }

  private void patchAmcAssets(Long id, AmcDebtContactor amcDebtContactor) {
    AmcAssetExample amcAssetExample = new AmcAssetExample();
    amcAssetExample.createCriteria().andDebtIdEqualTo(id);
    List<AmcAsset> amcAssets = amcAssetMapper.selectByExample(amcAssetExample);
    for(AmcAsset amcAsset: amcAssets){
      if(amcAsset.getAmcContactorName() == null || amcAsset.getAmcContactorPhone() == null ||
          amcAsset.getAmcContactorName().equals("-1") || amcAsset.getAmcContactorPhone().equals("-1")){
        amcAsset.setAmcContactorName(amcDebtContactor.getName());
        amcAsset.setAmcContactorPhone(amcDebtContactor.getPhoneNumber());
        amcAssetMapper.updateByPrimaryKeySelective(amcAsset);
      }
    }

  }
  @Scheduled(cron = "${spring.task.scheduling.cronExprRecom}")
  @Override
  public void updateClickCountInfo(){
    String param = "type=debt";
    String urlFinal = String.format(getClickCountUrl, param);
    ResponseEntity<Map> clickInfo = null;
    try{
      clickInfo = restTemplate.exchange(urlFinal, HttpMethod.GET, null,   Map.class);
    }catch (Exception ex){
      log.error("Error:", ex);
      ResponseEntity<String> resp = restTemplate.exchange(urlFinal, HttpMethod.GET, null,   String.class);
      log.info(resp.getBody());
    }
    Map<String, Double> clickCount = (Map<String, Double>) clickInfo.getBody();
    if(!CollectionUtils.isEmpty(clickCount)){
      for(Entry<String, Double> entry: clickCount.entrySet()){
        if(entry.getValue() > 0){
          AmcDebt amcDebt =  amcDebtMapper.selectByPrimaryKey(Long.valueOf(entry.getKey()));
          amcDebt.setVisitCount(entry.getValue().longValue());
          amcDebtMapper.updateByPrimaryKeySelective(amcDebt);
        }
      }
    }

    param = "type=asset";
    urlFinal = String.format(getClickCountUrl, param);
    clickInfo = restTemplate.exchange(urlFinal, HttpMethod.GET, null,   Map.class);
    clickCount = (Map<String, Double>) clickInfo.getBody();
    if(!CollectionUtils.isEmpty(clickCount)){
      for(Entry<String, Double> entry: clickCount.entrySet()){
        if(entry.getValue() > 0){
          AmcAsset amcAsset =  amcAssetMapper.selectByPrimaryKey(Long.valueOf(entry.getKey()));
          amcAsset.setVisitCount(entry.getValue().longValue());
          amcAssetMapper.updateByPrimaryKeySelective(amcAsset);
        }
      }
    }

  }

  @Override
  public void updateHasImgTag() {
    Query query = new Query();
    query.addCriteria(Criteria.where("tag").is(ImageClassEnum.MAIN.getId()));
    List<AssetImage> assetImages =  wszccTemplate.find(query, AssetImage.class);
    if(!CollectionUtils.isEmpty(assetImages)){
      List<Long> amcAssetIds = assetImages.stream().filter(item->item.getAmcAssetId() != null).map(item->item.getAmcAssetId()).collect(
          Collectors.toUnmodifiableList());
      AmcAssetExample amcAssetExample = new AmcAssetExample();
      amcAssetExample.createCriteria().andIdIn(amcAssetIds);
      AmcAsset amcAsset = new AmcAsset();
      amcAsset.setHasImg(HasImageEnum.HASIMAGE.getStatus());
      amcAssetMapper.updateByExampleSelective(amcAsset, amcAssetExample);
    }
    List<DebtImage> debtImages =  wszccTemplate.find(query, DebtImage.class);
    if(!CollectionUtils.isEmpty(debtImages)){
      List<Long> amcDebtIds = debtImages.stream().filter(item->item.getDebtId() != null).map(item->item.getDebtId()).collect(
          Collectors.toUnmodifiableList());
      AmcDebtExample amcDebtExample = new AmcDebtExample();
      amcDebtExample.createCriteria().andIdIn(amcDebtIds);
      AmcDebt amcDebt = new AmcDebt();
      amcDebt.setHasImg(HasImageEnum.HASIMAGE.getStatus());
      amcDebtMapper.updateByExampleSelective(amcDebt, amcDebtExample);
    }
  }

  @Override
  public void patchAmcDebtAssetContactor() {
    AmcAssetExample amcAssetExample = new AmcAssetExample();
    amcAssetExample.createCriteria().andAmcContactorNameEqualTo("-1");
    List<AmcAsset> amcAssets =  amcAssetMapper.selectByExample(amcAssetExample);
    for(AmcAsset amcAsset : amcAssets){
      AmcDebt amcDebt =  amcDebtMapper.selectByPrimaryKey(amcAsset.getDebtId());
      if(amcDebt != null && !StringUtils.isEmpty(amcDebt.getAmcContactorName()) &&
          !StringUtils.isEmpty(amcDebt.getAmcContactorPhone())){

        amcAsset.setAmcContactorName(amcDebt.getAmcContactorName());
        amcAsset.setAmcContactorPhone(amcDebt.getAmcContactorPhone());
        amcAssetMapper.updateByPrimaryKeySelective(amcAsset);
      }
    }

  }

  @Override
  public void patchPublishDate() throws ParseException {
    AmcDebtExample amcDebtExample = new AmcDebtExample();
    amcDebtExample.createCriteria().andPublishStateEqualTo(PublishStateEnum.PUBLISHED.getStatus()).andPublishDateLessThan(AmcDateUtils.getDateFromStr("1950-01-01"));
    List<AmcDebt> amcDebts = amcDebtMapper.selectByExample(amcDebtExample);
    for(AmcDebt amcDebt: amcDebts){
      if(amcDebt.getUpdateDate().after(AmcDateUtils.getDateFromStr("1950-01-01"))){
        amcDebt.setPublishDate(amcDebt.getUpdateDate());
        AmcAssetExample amcAssetExample = new AmcAssetExample();
        amcAssetExample.createCriteria().andDebtIdEqualTo(amcDebt.getId());
        List<AmcAsset> amcAssets = amcAssetMapper.selectByExample(amcAssetExample);
        for(AmcAsset amcAsset: amcAssets){
          amcAsset.setPublishDate(amcDebt.getPublishDate());
        }
      }else{
        log.error("DebtId:{} need manual update publish date", amcDebt.getId());
      }

    }

  }

  @Override
  public void patchContactorSex(String contactorName, Integer sex) {
    AmcDebtExample amcDebtExample = new AmcDebtExample();
    amcDebtExample.createCriteria().andAmcContactorNameEqualTo(contactorName);
    AmcDebt amcDebt = new AmcDebt();
    amcDebt.setAmcContactorSex(sex);
    amcDebtMapper.updateByExampleSelective(amcDebt, amcDebtExample);

    AmcAssetExample amcAssetExample = new AmcAssetExample();
    amcAssetExample.createCriteria().andAmcContactorNameEqualTo(contactorName);
    AmcAsset amcAsset = new AmcAsset();
    amcAsset.setAmcContactorSex(sex);
    amcAssetMapper.updateByExampleSelective(amcAsset, amcAssetExample);
  }

  @Override
  public Map<String, Integer> getDebtAssetStatus(List<String> debtAssetList) {

    List<Long> assetIds = new ArrayList<>();
    List<Long> debtIds = new ArrayList<>();

    for(String debtAssetStr : debtAssetList){
      String[] items = StringUtils.split(debtAssetStr,"_");
      if(debtAssetStr.startsWith("asset")){

        assetIds.add(Long.valueOf(items[1]));
      }else if(debtAssetStr.startsWith("debt")){
        debtIds.add(Long.valueOf(items[1]));
      }
    }
    Map<String, Integer> statusResult = new HashMap<>();
    if(!CollectionUtils.isEmpty(assetIds)){
      AmcAssetExample amcAssetExample = new AmcAssetExample();
      amcAssetExample.createCriteria().andIdIn(assetIds);
      List<AmcAsset> amcAssets = amcAssetMapper.selectByExample(amcAssetExample);
      if(!CollectionUtils.isEmpty(amcAssets)){
        for(AmcAsset amcAsset: amcAssets){
          statusResult.put(String.format("asset_%s",amcAsset.getId()), amcAsset.getPublishState());
        }
      }
    }

    if(!CollectionUtils.isEmpty(debtIds)){
      AmcDebtExample amcDebtExample = new AmcDebtExample();
      amcDebtExample.createCriteria().andIdIn(debtIds);
      List<AmcDebt> amcDebts = amcDebtMapper.selectByExample(amcDebtExample);
      if(!CollectionUtils.isEmpty(amcDebts)){
        for(AmcDebt amcDebt: amcDebts){
          statusResult.put(String.format("debt_%s",amcDebt.getId()), amcDebt.getPublishState());
        }
      }
    }
    return statusResult;
  }
}
