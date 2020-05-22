package com.wensheng.zcc.cust.service.impl;

import com.google.gson.Gson;
import com.wensheng.zcc.common.mq.kafka.module.AmcUserOperation;
import com.wensheng.zcc.common.mq.kafka.module.WechatUserLocation;
import com.wensheng.zcc.common.utils.AmcBeanUtils;
import com.wensheng.zcc.cust.dao.mysql.mapper.CustAmcCmpycontactorMapper;
import com.wensheng.zcc.cust.dao.mysql.mapper.CustIntrstInfoMapper;
import com.wensheng.zcc.cust.dao.mysql.mapper.CustTrdCmpyMapper;
import com.wensheng.zcc.cust.dao.mysql.mapper.CustTrdInfoMapper;
import com.wensheng.zcc.cust.dao.mysql.mapper.CustWechatInfoMapper;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustAmcCmpycontactor;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustAmcCmpycontactorExample;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdCmpy;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdInfo;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdInfoExample;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustWechatInfo;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustWechatInfoExample;
import com.wensheng.zcc.cust.module.dao.mysql.ext.CustTrdCmpyExtExample;
import com.wensheng.zcc.cust.module.helper.CustTypeEnum;
import com.wensheng.zcc.cust.module.sync.AddCrawlCmpyDTO;
import com.wensheng.zcc.cust.module.sync.AddCrawlCmpyResultDTO;
import com.wensheng.zcc.cust.module.sync.CmpyBasicBizInfoSync;
import com.wensheng.zcc.cust.module.sync.CmpyBizInfoResult;
import com.wensheng.zcc.cust.module.sync.CrawlResultDTO;
import java.util.Date;
import java.util.List;
import java.util.stream.StreamSupport;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
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
public class KafkaServiceImpl {

  @Autowired
  CustWechatInfoMapper custWechatInfoMapper;

  @Autowired
  CustTrdCmpyMapper custTrdCmpyMapper;

  @Autowired
  CustTrdInfoMapper custTrdInfoMapper;

  @Autowired
  CustAmcCmpycontactorMapper custAmcCmpycontactorMapper;

  @Autowired
  CommonHandler commonHandler;

  @Value("${cust.syncUrls.getCompanyBasicBizInfo}")
  String getCompanyBasicBizInfo;


  private Gson gson = new Gson();

  private static String typeIdHeader(Headers headers) {
    return StreamSupport.stream(headers.spliterator(), false)
        .filter(header -> header.key().equals("__TypeId__"))
        .findFirst().map(header -> new String(header.value())).orElse("N/A");
  }

  @KafkaListener( topics = "${kafka.topic_wechatuser}", clientIdPrefix = "zcc-cust",
      containerFactory = "baWechatUserContainerFactory")
  public void listenUserOperation(ConsumerRecord<String, WechatUserLocation> cr,
      @Payload AmcUserOperation payload) {
    log.info("Logger 1 [JSON] received key {}: Type [{}] | Payload: {} | Record: {}", cr.key(),
        typeIdHeader(cr.headers()), payload, cr.toString());
    String gsonStr = null;
    try{
      gsonStr = gson.toJson(payload);
      CustWechatInfo custWechatInfo = new CustWechatInfo();
      AmcBeanUtils.copyProperties(payload, custWechatInfo);
      CustWechatInfoExample custWechatInfoExample = new CustWechatInfoExample();
      custWechatInfoExample.createCriteria().andWechatOpenidEqualTo(custWechatInfo.getWechatOpenid());
      List<CustWechatInfo> custWechatInfoList = custWechatInfoMapper.selectByExample(custWechatInfoExample);
      if(CollectionUtils.isEmpty(custWechatInfoList)){
        custWechatInfoMapper.insertSelective(custWechatInfo);
      }else{

        custWechatInfoMapper.updateByExampleSelective(custWechatInfoList.get(0), custWechatInfoExample);
      }
    }catch (Exception ex){
      log.error("Failed to handle:{}", gsonStr, ex);
    }
  }

//  final String TOPIC = "cmpy_biz_info";
//  @KafkaListener(topics = {TOPIC}, containerFactory = "crawlSystemKafkaListenerContainerFactory")
//  public void listenerOne(ConsumerRecord<?, ?> record) {
//    log.info(" listenerOne 接收到消息：{}", record.value());
//    AddCrawlCmpyDTO addCrawlCmpyDTO = new Gson().fromJson((String) record.value(),AddCrawlCmpyDTO.class);
//    System.out.println(addCrawlCmpyDTO);
//  }

  final String RESULT_TOPIC = "crawler_response_zcc";
  @KafkaListener(topics = {RESULT_TOPIC}, containerFactory = "crawlSystemKafkaListenerContainerFactory")
  public void listenerResult(ConsumerRecord<?, ?> record) {
    log.info(" listenerResult 接收到消息：{}", record.value());
    AddCrawlCmpyResultDTO addCrawlCmpyResultDTO = new Gson().fromJson((String) record.value(),AddCrawlCmpyResultDTO.class);
    List<CmpyBizInfoResult> cmpyBizInfoResultList = addCrawlCmpyResultDTO.getCmpyBizInfoResults();
    //只有一个。
    CmpyBizInfoResult cmpyBizInfoResult = cmpyBizInfoResultList.get(0);

    //查询本数据库对应的数据1、公司名称。2、修改名称。
    CustTrdCmpyExtExample custTrdCmpyExtExample = new CustTrdCmpyExtExample();
    custTrdCmpyExtExample.createCriteria().andCmpyNameEqualTo(cmpyBizInfoResult.getCmpyName());
    custTrdCmpyExtExample.or().andCmpyNameUpdateEqualTo(cmpyBizInfoResult.getCmpyName());
    List<CustTrdCmpy> custTrdCmpyList = custTrdCmpyMapper.selectByExample(custTrdCmpyExtExample);

    if(CollectionUtils.isEmpty(custTrdCmpyList)){
      log.error("未找到与kafka匹配的公司信息");
      return;
    }
    //查到多个公司时删除只留一个
    if(custTrdCmpyList.size() > 1){
      for (int i = 1; i < custTrdCmpyList.size(); i++) {
        CustTrdCmpy custTrdCmpy = custTrdCmpyList.get(i);

        CustTrdInfoExample custTrdInfoExample = new CustTrdInfoExample();
        CustAmcCmpycontactorExample custAmcCmpycontactorExample = new CustAmcCmpycontactorExample();
        //before delete check the related id in trdInfo and update it with current cmpy id
        custTrdInfoExample.clear();
        custTrdInfoExample.createCriteria().andBuyerIdEqualTo(custTrdCmpy.getId()).andBuyerTypeEqualTo(
            CustTypeEnum.COMPANY.getId());
        List<CustTrdInfo> custTrdInfos = custTrdInfoMapper.selectByExample(custTrdInfoExample);
        if(!CollectionUtils.isEmpty(custTrdInfos)){
          for(CustTrdInfo custTrdInfoOld: custTrdInfos){
            custTrdInfoOld.setBuyerId(custTrdCmpyList.get(0).getId());
            custTrdInfoMapper.updateByPrimaryKeySelective(custTrdInfoOld);
          }
        }
        //before delete check the related id in custAmcCmpycontactor and update it with current cmpy id
        custAmcCmpycontactorExample.clear();
        custAmcCmpycontactorExample.createCriteria().andCmpyIdEqualTo(custTrdCmpy.getId());
        List<CustAmcCmpycontactor> custAmcCmpycontactors =
            custAmcCmpycontactorMapper.selectByExample(custAmcCmpycontactorExample);
        if(!CollectionUtils.isEmpty(custAmcCmpycontactors)){
          for(CustAmcCmpycontactor custAmcCmpycontactor: custAmcCmpycontactors){
            custAmcCmpycontactor.setCmpyId(custTrdCmpyList.get(0).getId());
            custAmcCmpycontactorMapper.updateByPrimaryKeySelective(custAmcCmpycontactor);
          }
        }
        commonHandler.creatCmpyHistory(null,"listenerResult",
            "接收Kafka信息时删除重复公司信息", custTrdCmpy);
        custTrdCmpyMapper.deleteByPrimaryKey(custTrdCmpy.getId());

      }
    }
    CustTrdCmpy custTrdCmpyOriginal = custTrdCmpyList.get(0);

    log.info("要修改的公司信息custTrdCmpyOriginal：{}",custTrdCmpyOriginal);
    CrawlResultDTO crawlResultDTO =null;
    CmpyBasicBizInfoSync cmpyBasicBizInfoSync =null;
    CustTrdCmpy custTrdCmpy = new CustTrdCmpy();
    custTrdCmpy.setId(custTrdCmpyOriginal.getId());

    if(!"1".equals(cmpyBizInfoResult.getStatus())) {
      //不成功记，录信息把公司状态改为-1
      log.error("爬取公司信息失败，公司名称为：{}", cmpyBizInfoResult.getCmpyName());
      commonHandler.creatCmpyHistory(null,"KafkaServiceImpl",
          "kafka爬取公司信息失败",custTrdCmpyOriginal);
      custTrdCmpy.setCrawledStatus("-1");
      custTrdCmpyMapper.updateByPrimaryKeySelective(custTrdCmpy);
      return;
    }
    //查询爬虫基础库中信息
    RestTemplate restTemplate = CommonHandler.getRestTemplate();
    String url = String.format(getCompanyBasicBizInfo, cmpyBizInfoResult.getCmpyName());
    log.info("查询爬虫基础库中信息url:{}",url);
    crawlResultDTO = restTemplate.getForEntity(
        url, CrawlResultDTO.class).getBody();

    if(null == crawlResultDTO || CollectionUtils.isEmpty(crawlResultDTO.getList())){
      log.info("查询爬虫基础库中信息,暂无信息");
      return;
    }
    log.info("kafka爬取公司信息成功", cmpyBizInfoResult.getCmpyName());

    cmpyBasicBizInfoSync = crawlResultDTO.getList().get(0);
    log.info("查询爬虫基础库中信息,cmpyBasicBizInfoSync{}", cmpyBasicBizInfoSync);
    //对应修改名称则判断原公司名称是查询公司信息的曾用名中，
    if(custTrdCmpyOriginal.getCmpyName().equals(cmpyBasicBizInfoSync.getName()) || cmpyNameMatch(cmpyBasicBizInfoSync.getHistoryName(),custTrdCmpyOriginal.getCmpyName())){
      custTrdCmpy.setCmpyName(cmpyBasicBizInfoSync.getName());
      //修改信息
      custTrdCmpy.setCmpyNameHistory(cmpyBasicBizInfoSync.getHistoryName());
      custTrdCmpy.setCmpyProvince(cmpyBasicBizInfoSync.getCmpyProvince());
      custTrdCmpy.setUniSocialCode(cmpyBasicBizInfoSync.getSocialCode());
      custTrdCmpy.setCmpyPhone(cmpyBasicBizInfoSync.getEntPhone());
      custTrdCmpy.setCmpyAddr(cmpyBasicBizInfoSync.getEntAddress());
      custTrdCmpy.setAnnuReptPhone(cmpyBasicBizInfoSync.getReportPhone());
      custTrdCmpy.setAnnuReptAddr(cmpyBasicBizInfoSync.getReportAddress());
      custTrdCmpy.setLegalReptive(cmpyBasicBizInfoSync.getLegalPerson());
      custTrdCmpy.setCrawledStatus("2");
      custTrdCmpy.setUpdateTime(new Date());
      commonHandler.creatCmpyHistory(null,"KafkaServiceImpl",
          "kafka爬取公司信息成功", custTrdCmpyOriginal);
    }else {
      custTrdCmpy.setCrawledStatus("-1");
      commonHandler.creatCmpyHistory(null,"KafkaServiceImpl",
          "kafka爬取公司信息成功,和原公司不匹配", custTrdCmpyOriginal);
    }
    custTrdCmpy.setSyncTime(new Date());
    custTrdCmpyMapper.updateByPrimaryKeySelective(custTrdCmpy);
  }


  private Boolean cmpyNameMatch(String nameHistorys, String originalName){
    if(StringUtils.isEmpty(nameHistorys)){
      return false;
    }
    String[] nameHistoryArray = nameHistorys.split(",");
    for (int i = 0; i < nameHistoryArray.length; i++) {
      if(originalName.equals(nameHistoryArray[i])){
        return true;
      }
    }
    return false;
  }

}
