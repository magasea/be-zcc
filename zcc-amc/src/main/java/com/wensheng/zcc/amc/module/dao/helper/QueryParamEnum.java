package com.wensheng.zcc.amc.module.dao.helper;

/**
 * @author chenwei on 3/13/19
 * @project zcc-backend
 */
public enum QueryParamEnum {
  DebtId("DebtId"),
//  EditStatus("EditStatus"),
  PublishStates("PublishStates"),
  Area("Area"),
  LandArea("LandArea"),
  SealedState("SealedState"),
  Title("Title"),
  AssetType("AssetType"),
  Location("Location"),
  Recommand("Recommand"),
  AmcContactorId("AmcContactorId"),

  ;

  private String name;
  QueryParamEnum(String name){
    this.name = name;
  }
}
