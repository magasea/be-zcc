package com.wensheng.zcc.amc.service.impl;

import com.wensheng.zcc.amc.module.dto.SSOContactorDTO;
import com.wensheng.zcc.amc.service.AmcSSORpcService;
import com.wensheng.zcc.common.params.sso.SSOAmcUser;
import com.wensheng.zcc.common.utils.AmcBeanUtils;
import com.wenshengamc.sso.AmcContactorName;
import com.wenshengamc.sso.AmcSSOContactorName;
import com.wenshengamc.sso.AmcSSOGrpcServiceGrpc.AmcSSOGrpcServiceBlockingStub;
import com.wenshengamc.sso.CheckSSOContactorNameReq;
import com.wenshengamc.sso.CheckSSOContactorNameResp;
import com.wenshengamc.sso.GetSSOUsersByIdsReq;
import com.wenshengamc.sso.SSOUser;
import com.wenshengamc.sso.SSOUserResp;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AmcSSORpcServiceImpl implements AmcSSORpcService {


  @Autowired
  AmcSSOGrpcServiceBlockingStub amcSSOServiceStub;

  @Override
  public List<SSOAmcUser> getSSOUsersByIds(List<Long> ids) {
    GetSSOUsersByIdsReq.Builder gssouReq = GetSSOUsersByIdsReq.newBuilder();
    gssouReq.addAllSsoUserIds(ids);
    SSOUserResp ssoUserResp = null;
    try{
      ssoUserResp = amcSSOServiceStub.getSSOUsersByIds(gssouReq.build());
    }catch (Exception ex){
      log.error("Failed to retrieve sso users by:{}", ids, ex);
      return new ArrayList<>();
    }

    List<SSOAmcUser> ssoAmcUsers = new ArrayList<>();
    for(SSOUser ssoUser :ssoUserResp.getSsoUsersList()){
      SSOAmcUser ssoAmcUser = new SSOAmcUser();
      AmcBeanUtils.copyProperties(ssoUser, ssoAmcUser);
      ssoAmcUsers.add(ssoAmcUser);
    }
    return ssoAmcUsers;
  }

  @Override
  public List<SSOContactorDTO> checkSSOContactorInfo(List<SSOContactorDTO> ssoContactorDTOS) {
    CheckSSOContactorNameReq.Builder cssocBuilder = CheckSSOContactorNameReq.newBuilder();

    for(SSOContactorDTO ssoContactorDTO: ssoContactorDTOS){
      AmcContactorName.Builder acnBuilder = AmcContactorName.newBuilder();
      AmcBeanUtils.copyProperties(ssoContactorDTO, acnBuilder);
      cssocBuilder.addContactorNames(acnBuilder);
    }
    CheckSSOContactorNameResp checkSSOContactorNameResp = amcSSOServiceStub
        .checkSSOContactorName(cssocBuilder.build());
    List<SSOContactorDTO> ssoContactorDTOSList = new ArrayList<>();
    for(AmcSSOContactorName amcSSOContactorName: checkSSOContactorNameResp.getSsoContactorNamesList()){
      SSOContactorDTO ssoContactorDTO = new SSOContactorDTO();
      AmcBeanUtils.copyProperties(amcSSOContactorName, ssoContactorDTO);
      ssoContactorDTO.setPhoneNum(amcSSOContactorName.getPhone());
      ssoContactorDTO.setFound(true);
      ssoContactorDTO.setSsoUserId(amcSSOContactorName.getId());
      ssoContactorDTOSList.add(ssoContactorDTO);
    }
    return ssoContactorDTOSList;
  }
}
