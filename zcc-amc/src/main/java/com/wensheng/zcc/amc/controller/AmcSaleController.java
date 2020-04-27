package com.wensheng.zcc.amc.controller;

import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcSaleFloor;
import com.wensheng.zcc.amc.module.vo.AmcSaleFilter;
import com.wensheng.zcc.amc.module.vo.AmcSaleFloorVo;
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


  @RequestMapping(value = "/createFloor", method = RequestMethod.POST)
  @ResponseBody
  AmcSaleFloorVo createFloor(@RequestBody AmcSaleFloorVo amcSaleFloorVo){
    return amcSaleService.createFloor(amcSaleFloorVo);
  }

  @RequestMapping(value = "/getFloors", method = RequestMethod.POST)
  @ResponseBody
  List<AmcSaleFloorVo> getAmcSaleFloors(){
    return amcSaleService.getFloors();
  }

  @RequestMapping(value = "/updateFloors", method = RequestMethod.POST)
  @ResponseBody
  boolean updateFloorsSeq(@RequestBody List<Long> floorIds) throws Exception {
    return amcSaleService.updateFloorSeq(floorIds);
  }

  @RequestMapping(value = "/delFloor", method = RequestMethod.POST)
  @ResponseBody
  void getAmcSaleFloors(@RequestBody Long floorId) throws Exception {
    amcSaleService.delFloor(floorId);
  }


  @RequestMapping(value = "/updateFloorWithFilter", method = RequestMethod.POST)
  @ResponseBody
  boolean  updateFloorWithFilter(@RequestBody AmcSaleFloorVo amcSaleFloor) throws Exception {
    return amcSaleService.updateFloor(amcSaleFloor);
  }




  @RequestMapping(value = "/updateFloorBasic", method = RequestMethod.POST)
  @ResponseBody
  boolean  updateFloorFilter(@RequestBody AmcSaleFloor amcSaleFloor ) throws Exception {
    return amcSaleService.updateFloorBasic(amcSaleFloor);
  }
}
