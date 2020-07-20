package com.wensheng.zcc.wechat.service.impl;

import static org.junit.Assert.*;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.wensheng.zcc.common.module.dto.AmcRegionInfo;
import com.wensheng.zcc.wechat.module.dao.mysql.auto.entity.WechatUser;
import com.wensheng.zcc.wechat.module.vo.UserLngLat;
import com.wensheng.zcc.wechat.module.vo.WXUserStatistics;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
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
@ActiveProfiles(value = "preProd")
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

  @Test
  public void getUserStaticFromWX() {
    try {
      WXUserStatistics userStaticFromWX = wxUserService
          .getUserStaticFromWX("2020-05-01", "2020-05-09");
      System.out.println(gson.toJson(userStaticFromWX));
    } catch (ParseException e) {
      e.printStackTrace();
    }

  }


  @Test
  public void getUsersFromWX() {
    String[] openIds = new String[]{"o3hv61bbsT1p5-X9r66Yide3Fv0M","o3hv61QgpYhHJG1Un3B0-0cP20KU",
        "o3hv61SQqWLWfchHHDCjH4FFaptI","o3hv61SCLTS6CnjLHyaNL9Wst1Po","o3hv61eZCPibKHDkDQYFBdA4V_kU",
        "o3hv61cBxHU3enm2zvhah1vfdwh4","o3hv61Q6ZxXIr6khWxmjjGyD_CtY","o3hv61ZLhyiJDAER73RkercBjIrw",
        "o3hv61RgzV0jrvt7H0ml0ta8eJWY","o3hv61cd0ZVAvHkJIAPPbd4Y34Oc","o3hv61ZYMbw6tLtbFUWtifSzLxEg",
        "o3hv61VcU9RAQiGljw_CPKbtp7DQ","o3hv61fdGIFXb1vcii2qThLb5cEA","o3hv61WTGayGIPUBQw1A-xrpLGRE",
        "o3hv61d_WqAelni2VUT6GzQAwwLg","o3hv61ZYYomgYaOnt5pxJCaSeIj4","o3hv61bmtsC0h2lXxGkC0CbuSpyw",
        "o3hv61UhwGbzFtbyN75HQI3-jN8Y","o3hv61U6KfFs3DR7S35WYELewmPw","o3hv61SiWCnHy4pt3oFfiWK3MwbI",
        "o3hv61dbymYHboKG47EII6hx--k8","o3hv61XHOXRzPoo_JEABn2kbO1aM","o3hv61adhhkSNIzR91ZVdtsVzESA",
        "o3hv61YNx0fYdpFZokxTI694-tPc","o3hv61cF7i2pJR2ANRJc4C1CU0-Q","o3hv61Was1N1zlHrs3Psvo0n3g8g",
        "o3hv61dZRWvoOix0bCIEpkg8Hju0","o3hv61TidcPMZr1EqpQ3U2_C6TSQ","o3hv61dny4ooITLoo0PbYR5pst7s",
        "o3hv61WlwuY9kBirEDKK0KXjrVM8","o3hv61TybrgWN9XBRrV8MQdqW75Q","o3hv61cPCIMdKUUb_3Yrd7g2XD2M",
        "o3hv61ZTwdPMsDIh4w11uOnwtsfE","o3hv61T1C3AcTk3YpVlLU6NBBTok","o3hv61VzMdrnztX5FyQcBZVNZb98",
        "o3hv61eQOIKSm9mJKiyHhzggLCbc","o3hv61WIjS7OkymproIdkzfyeDto","o3hv61d_hsJEGWq0czS4ldKYchx8",
        "o3hv61dpsh_9zhS_AUN-Pi41P-cE","o3hv61eHhD0WjUENcF5KLdyZodNQ","o3hv61aSIbr79o9AuPgecQZgz2UA",
        "o3hv61dU-PzO46re451vZf8EFSmw","o3hv61VA9cciJjWAO6Rp01g4gvoA","o3hv61aibM3Q_WgNBkP-rxKSVTnE",
        "o3hv61cq9KVTlSmC3xMWF01bivrE","o3hv61drBsW6YR6mRBKJXXS0FbeQ","o3hv61ahfe-2IZWVBq-GOSqiTbxA",
        "o3hv61c2z49atvMs8klQprf5lXsM","o3hv61Ri7xWCHrx_rEmC0F8GhPws","o3hv61eDQsKUCeDi1fiXsuB-t3js",
        "o3hv61Sb6xz0_ExqirODH7G4cYqw","o3hv61YUOoBXkWiPKBZK6KIJBmJg","o3hv61YU65NkPdZISxjNl4w8owfY",
        "o3hv61ZpoMXPL5YW-1A1GBL__GRw","o3hv61faz9Jg0XDUSTyqR7cRcQPk","o3hv61ZWhWjLsYGERuamhKQN1Da8",
        "o3hv61RynOUrBk8d2TlUreHXfRMU","o3hv61d4cT9IPRUf2z5_zq6bKxQI","o3hv61eGaGTUAR8lcbDgvft40q-I",
        "o3hv61S-9MgPr4JGFeqQ2mhrTibc","o3hv61eV8NC8I9NTESpLxKvgcf0s","o3hv61WOsTYDxbF4ohTEZOjw75io",
        "o3hv61V9SzTepzMC0R7CiwJJkRMg","o3hv61dnhSn-Lm97OQTRatCM2Q7k","o3hv61evk-Oi0IA_qBvBWPrNX47c",
        "o3hv61fwa7D3eEY_eKu0ROKXFusQ","o3hv61dkiieMZ2caFWvzyaE0d4xI","o3hv61XzENOfU9ldUh18PnnFB38o",
        "o3hv61ccmg-URsy9xSWHKMNe8wb0","o3hv61TH9ZsEcp4ftO9hwnpGTU5A","o3hv61Zm_wrDHU9j2gY10NnYkPWM",
        "o3hv61YzUG4qi9XZoZaU2EXSZgPg","o3hv61YNLlPBAH2WDbsqcWATz3qQ","o3hv61Rakz_kX2f13yQrPRkw_-K4",
        "o3hv61aa1aglWHrayjxogZVJvTzk","o3hv61aCN7aNWUSc1fVfKgj1467M","o3hv61V1hrDxDjyr1Iv8cZFdLa_s",
        "o3hv61W71x8_z2qs_YEPv0gt3nhE","o3hv61Rwx3NI_E6Wb_zv91yiSKVg","o3hv61SkegTFSLY2vmBNYUOl3saY",
        "o3hv61VKi4bIFxPlRbGwjLxZgzg4","o3hv61TbZVbTt0AfQXRbB52wZ_KA","o3hv61bIYcnTSVbd1z9wIFOOHE_k",
        "o3hv61c8hoi9_QfhwPkBL5Ofw5fg","o3hv61fs8aaJs0kT_-PRR1Uw2NRY","o3hv61ZJysS1J2HAWI1N25ShY8A4",
        "o3hv61Rryv4OhavnVqSn6QlLSb44","o3hv61WzFYn_Bck6qZvjgza9KPmQ","o3hv61Y-_1tSJWAIAAXY41Is0VNc",
        "o3hv61d0rKigd8AgKFi0A32KLe_4","o3hv61Tg2CRYOXt30b-wCRIboVAM","o3hv61eXDUjgrN43UdupvNbizMA8",
        "o3hv61VIkwoG-OMyR6ePFf3hw1I0","o3hv61erAykK9YKgCHkax7S1p3Po","o3hv61bS-FEDXwrqnmg3vbDfuSrw",
        "o3hv61TTRwe3qSbeS5QXRZJqn17A","o3hv61eIc0grg5lA42t5E0bsh7qc","o3hv61Q8EOUZCwox8nWGmsoB37fU",
        "o3hv61X5J4NgSkwT1jOGjzZuniFc","o3hv61ap5JK4cKR3jZWP0RwuH-aE","o3hv61cUiddrTyMh4uzFFStidE8M",
        "o3hv61fksTpALZk8VLlwM_Pl83n8","o3hv61f4eXmx70YZ_mApGZyfkOrI","o3hv61f-Geo-w31plcBNCSIQIAN4",
        "o3hv61S-t_JpX5Ksxon05FzBzK2I","o3hv61cTLysWiRdyTJbFZEJhjW20","o3hv61RdHIiCPOj9IoKsOzmqu6xc",
        "o3hv61R5zMFPx4xm1vWbnSDkzqTQ","o3hv61RsPyPhKVGiZ9sti0HIs6_Y","o3hv61bqpL4GDKrs6gECT0ahhwCU",
        "o3hv61d_P3myx-moU8TbTrmimILk"};
    try {
      List<WechatUser> wechatPublicUserInfo = wxUserService
          .getWechatPublicUserInfo(Arrays.asList(openIds));
      System.out.println(gson.toJson(wechatPublicUserInfo));
    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}