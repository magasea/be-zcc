package com.wensheng.zcc.amc.module.dao.helper;

import com.wensheng.zcc.common.utils.base.EnumUtils;
import java.util.function.Function;

/**
 * @author chenwei on 1/14/19
 * @project zcc-backend
 */
public enum EditActionEnum {
  ACT_CREATE(1, "create-draft", "创建"),
  ACT_SAVE(2, "save", "保存"),
  ACT_SUBMIT4PUB(3, "submit-pub", "提交审核发布"),
  ACT_REVIEW_FAIL(4, "review-failed","审核未通过"),
  ACT_REVIEW_PASS(5, "review-pass","审核通过发布"),
  ACT_OFF_SHELF(6, "off-shelf","下架"),
  ACT_SOLD(7,"sold","售出"),
  ACT_DEL(8, "del", "删除");


  int id;
  String name;
  String cname;
  EditActionEnum(int id, String name, String cname){
    this.id = id;
    this.name = name;
    this.cname = cname;
  }

  private static final Function<String, EditActionEnum> func =
      EnumUtils.lookupMap(EditActionEnum.class, e -> e.getName());
  public static EditActionEnum lookupByDisplayNameUtil(String name) {
    return func.apply(name);
  }

  private static final Function<Integer, EditActionEnum> funcId =
      EnumUtils.lookupMap(EditActionEnum.class, e -> e.getId());
  public static EditActionEnum lookupByDisplayIdUtil(Integer id) {
    return funcId.apply(id);
  }

  public int getId(){
    return id;
  }

  public String getName(){
    return name;
  }

  public String getCname(){
    return cname;
  }

}
