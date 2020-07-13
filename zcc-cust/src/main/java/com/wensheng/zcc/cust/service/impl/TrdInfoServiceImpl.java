package com.wensheng.zcc.cust.service.impl;

import com.wensheng.zcc.common.utils.AmcNumberUtils;
import com.wensheng.zcc.cust.dao.mysql.mapper.CustTrdInfoMapper;
import com.wensheng.zcc.cust.dao.mysql.mapper.ext.CustTrdInfoExtMapper;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdInfo;
import com.wensheng.zcc.cust.module.dao.mysql.auto.entity.CustTrdInfoExample;
import com.wensheng.zcc.cust.module.dao.mysql.ext.CustTrdInfoExtExample;
import com.wensheng.zcc.cust.module.helper.CustTypeEnum;
import com.wensheng.zcc.cust.service.TrdInfoService;
import java.util.List;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * @author chenwei on 4/18/19
 * @project miniapp-backend
 */
@Service
public class TrdInfoServiceImpl implements TrdInfoService {

  @Autowired
  CustTrdInfoMapper custTrdInfoMapper;

  @Autowired
  CustTrdInfoExtMapper custTrdInfoExtMapper;

  @Override
  public CustTrdInfo addTrdInfo(CustTrdInfo custTrdInfo) {
    custTrdInfoMapper.insertSelective(custTrdInfo);
    return custTrdInfo;
  }

  @Override
  public List<CustTrdInfo> getTrdInfo() {
    return custTrdInfoMapper.selectByExample(null);
  }

  @Override
  public List<CustTrdInfo> getTrdInfo(Long custId, int custType) {
    CustTrdInfoExtExample custTrdInfoExtExample = new CustTrdInfoExtExample();
    custTrdInfoExtExample.createCriteria().andBuyerIdEqualTo(custId).andBuyerTypeEqualTo(custType);
    List<CustTrdInfo> result = custTrdInfoExtMapper.selectByExample(custTrdInfoExtExample);
    //拍卖系统金额精确到分
    result.stream().forEach(item->item.setTrdAmount(item.getTrdAmount()/100));
    return result;
  }

  @Override
  public void patchPhoneAndAddress() {
    CustTrdInfoExample custTrdInfoExample = new CustTrdInfoExample();
    custTrdInfoExample.createCriteria().andTrdContactorPhoneEqualTo("-1").andTrdContactorAddrNotEqualTo("-1");
    custTrdInfoExample.setOrderByClause(" id desc ");
    int offset = 0;
    int pageSize = 100;
    RowBounds rowBounds = new RowBounds(offset, pageSize);
    List<CustTrdInfo> custTrdInfos = custTrdInfoMapper.selectByExampleWithRowbounds(custTrdInfoExample, rowBounds);
    boolean keepGoing = true;
    if(CollectionUtils.isEmpty(custTrdInfos)){
      keepGoing = false;
      return;
    }

    while(keepGoing){
      for(CustTrdInfo custTrdInfo: custTrdInfos){
        String[] phoneAndAddr = null;
        if(!StringUtils.isEmpty(custTrdInfo.getTrdContactorAddr())){
          phoneAndAddr  = custTrdInfo.getTrdContactorAddr().split(" ");
          if(phoneAndAddr.length >= 2){
            custTrdInfo.setTrdContactorPhone(phoneAndAddr[0]);
            custTrdInfo.setTrdContactorAddress(phoneAndAddr[1]);
          }else{
            if(Character.isDigit(phoneAndAddr[0].charAt(0))){
              custTrdInfo.setTrdContactorPhone(phoneAndAddr[0]);
              custTrdInfo.setTrdContactorAddress("-1");
            }else{
              custTrdInfo.setTrdContactorAddress(phoneAndAddr[0]);
              custTrdInfo.setTrdContactorPhone("-1");
            }
          }
          if(!StringUtils.isEmpty(custTrdInfo.getTrdContactorPhone())){
            custTrdInfoMapper.updateByPrimaryKeySelective(custTrdInfo);
          }
        }
      }
      offset += pageSize;
      rowBounds = new RowBounds(offset, pageSize);
      custTrdInfos = custTrdInfoMapper.selectByExampleWithRowbounds(custTrdInfoExample, rowBounds);
      if(CollectionUtils.isEmpty(custTrdInfos)){
        keepGoing = false;
        break;
      }
    }
  }


}
