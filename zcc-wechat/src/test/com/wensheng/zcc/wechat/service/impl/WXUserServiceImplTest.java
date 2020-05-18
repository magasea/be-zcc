package com.wensheng.zcc.wechat.service.impl;

import static org.junit.Assert.*;

import com.google.gson.Gson;
import com.wensheng.zcc.common.module.dto.AmcRegionInfo;
import com.wensheng.zcc.wechat.module.vo.UserLngLat;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration
@ActiveProfiles(value = "dev")
@Slf4j
public class WXUserServiceImplTest {

  @Autowired
  WXUserServiceImpl wxUserService;

  Gson gson = new Gson();

  @Test
  public void syncUserInfoFromWX() {
    wxUserService.syncUserInfoFromWX();
  }

  @Test
  public void getUserLocation() {
    UserLngLat userLngLat = new UserLngLat();
    userLngLat.setLng(119.482549);
    userLngLat.setLat(32.202587);
    AmcRegionInfo amcRegionInfo = wxUserService.getUserLocation(userLngLat);
    log.info(gson.toJson(amcRegionInfo));
  }

  @Test
  public void syncUserInfoFromWX1() {
    wxUserService.syncUserInfoFromWX();
  }
}