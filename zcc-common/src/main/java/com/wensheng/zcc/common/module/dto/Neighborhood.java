package com.wensheng.zcc.common.module.dto;

import java.util.List;
import lombok.Data;

@Data
public class Neighborhood {
  List<NeighborhoodItem> neighborhood;


}
@Data
class NeighborhoodItem{
  String name;
  String type;
}