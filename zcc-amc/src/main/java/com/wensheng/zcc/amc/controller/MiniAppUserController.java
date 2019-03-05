package com.wensheng.zcc.amc.controller;

import com.wensheng.zcc.amc.module.dao.mongo.entity.WechatQrImage;
import com.wensheng.zcc.amc.service.MiniAppService;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author chenwei on 2/21/19
 * @project zcc-backend
 */
@Controller
@Slf4j
@RequestMapping("/api/client/miniapp/user")
public class MiniAppUserController {

  @Autowired
  MiniAppService miniAppService;


  @RequestMapping(value = "/miniapp-qrcode-image", method = RequestMethod.GET)
  public ResponseEntity<byte[]> getMiniappQrImage(@RequestParam("scene") String scene,
      @RequestParam("page") String page) {

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.IMAGE_JPEG);
    byte[] in = miniAppService.getImageBytes4QRCode(scene, page);
    ResponseEntity<byte[]> responseEntity = new ResponseEntity<byte[]>(in, httpHeaders, HttpStatus.OK);
    return responseEntity;


  }


}
