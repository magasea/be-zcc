package com.wensheng.zcc.amc.module.dao.helper;

/**
 * @author chenwei on 3/13/19
 * @project zcc-backend
 */
public enum QueryParamEnum {
  DebtId("DebtId"),
  DebtType("DebtType"),
  CourtLocations("CourtLocations"),
//  EditStatus("EditStatus"),
  PublishStates("PublishStates"),
  Area("Area"),
  LandArea("LandArea"),
  SealedState("SealedState"),
  Title("Title"),
  BaseAmount("BaseAmount"),
  AssetType("AssetType"),
  Location("Location"),
  LocationCode("LocationCode"),
  Recommand("Recommand"),
  AmcContactorId("AmcContactorId"),
  AmcContactorName("AmcContactorName"),
  Valuation("Valuation"),
  CourtId("CourtId"),
  DebtPackId("DebtPackId"),

  ;

  private String name;
  QueryParamEnum(String name){
    this.name = name;
  }
}
