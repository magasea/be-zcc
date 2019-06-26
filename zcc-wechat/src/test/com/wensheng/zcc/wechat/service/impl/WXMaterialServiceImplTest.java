package com.wensheng.zcc.wechat.service.impl;


import com.wensheng.zcc.wechat.module.vo.ReGeoCode;
import net.webby.protostuff.runtime.Generators;
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
public class WXMaterialServiceImplTest {


@Autowired
WXMaterialServiceImpl wxMaterialService;

  @Test
  public void uploadImageTest() throws Exception {
//    wxMaterialService.uploadImageTest(null);
  }

  @Test
  public void generateProto(){
    ReGeoCode geoCode = new ReGeoCode();
    String content = Generators.newProtoGenerator(geoCode).generate();
    System.out.println(content);
  }
}
