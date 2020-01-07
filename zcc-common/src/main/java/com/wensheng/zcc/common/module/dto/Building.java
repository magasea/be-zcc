package com.wensheng.zcc.common.module.dto;

import java.util.List;
import lombok.Data;

@Data
public class Building {
  List<BuildingItem> building;
}
class BuildingItem{
  String name;
  String type;
}