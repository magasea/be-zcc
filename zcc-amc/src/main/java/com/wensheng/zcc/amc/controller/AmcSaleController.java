package com.wensheng.zcc.amc.controller;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcSaleFloor;
import com.wensheng.zcc.amc.service.AmcSaleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author chenwei on 1/3/19
 * @project zcc-backend
 */
@Controller
@Slf4j
@RequestMapping("/api/amc/sale")
public class AmcSaleController {



  @Autowired
  AmcSaleService amcSaleService;


  @RequestMapping(value = "/floor", method = RequestMethod.POST)
  @ResponseBody
  List<AmcSaleFloor> getAmcSaleFloors(){
    return amcSaleService.getFloors();
  }





}
