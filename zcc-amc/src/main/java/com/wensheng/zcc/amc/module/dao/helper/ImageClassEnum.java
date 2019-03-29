package com.wensheng.zcc.amc.module.dao.helper;

import com.wensheng.zcc.common.utils.base.EnumUtils;
import java.util.function.Function;

/**
 * @author chenwei on 1/3/19
 * @project zcc-backend
 */
public enum ImageClassEnum {
  MAIN(1, "主图片"),
  LOCATION(2, "位置图片"),
  OTHER(3, "其他"),
  STREET(4, "街景"),
  OUTSIDE(5, "外观"),
  INSIDE(6, "内景"),
  ARIMAGE(7, "AR图"),
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

  private static final Function<Integer, ImageClassEnum> funcId =
      EnumUtils.lookupMap(ImageClassEnum.class, e -> e.getId());
  public static ImageClassEnum lookupByDisplayNameUtil(Integer id) {
    return funcId.apply(id);
  }
  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
