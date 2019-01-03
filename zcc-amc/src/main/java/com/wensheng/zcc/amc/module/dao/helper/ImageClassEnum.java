package com.wensheng.zcc.amc.module.dao.helper;

import com.wensheng.zcc.amc.module.dao.helper.base.EnumUtils;
import java.util.function.Function;

/**
 * @author chenwei on 1/3/19
 * @project zcc-backend
 */
public enum ImageClassEnum {
  MAIN(1, "主图片"),
  LOCATION(2, "位置图片"),
  OTHER(3, "其他"),
  ;
  private int id;
  private String name;
  ImageClassEnum(int id, String name){
    this.id = id;
    this.name = name;
  }

  private static final Function<String, ImageClassEnum> func =
      EnumUtils.lookupMap(ImageClassEnum.class, e -> e.getName());
  public static ImageClassEnum lookupByDisplayNameUtil(String name) {
    return func.apply(name);
  }
  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
