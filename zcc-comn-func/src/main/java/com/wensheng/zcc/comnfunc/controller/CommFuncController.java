package com.wensheng.zcc.comnfunc.controller;

import com.wensheng.zcc.comnfunc.module.vo.base.GaodeGeoQueryVal;
import com.wensheng.zcc.comnfunc.service.GaoDeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/amc/commFunc")
public class CommFuncController {

  @Autowired
  GaoDeService gaoDeService;

  @RequestMapping(value = "/queryGeoInfo", method = RequestMethod.POST)
  @ResponseBody
  public List<GaodeGeoQueryVal> queryGeoInfo(@RequestParam  String city, @RequestParam String address) throws Exception {
    return gaoDeService.getGeoInfoFromAddress(address, city);
  }

}
