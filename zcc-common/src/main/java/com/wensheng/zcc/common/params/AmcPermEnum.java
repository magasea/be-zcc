package com.wensheng.zcc.common.params;

import com.wensheng.zcc.common.utils.base.EnumUtils;
import java.util.function.Function;

/**
 * @author chenwei on 3/20/19
 * @project miniapp-backend
 */
public enum AmcPermEnum {
  PERM_AMC_CRUD("PERM_AMC_CRUD","AMC增删改查", 2),
  PERM_AMC_REVIEW("PERM_AMC_REVIEW","发布审核",5),
  PERM_AMC_VIEW("PERM_AMC_VIEW","AMC查看",3),
  PERM_BASIC_INFO("PERM_BASIC_INFO","基础信息",7),
  PERM_CREATE_AMCADMIN("PERM_CRUD_AMCADMIN","创建AMC管理员",6),
  PERM_CREATE_AMCUSER("PERM_CRUD_AMCUSER","创建AMC用户", 1),
  PERM_LAWYERAMC_VIEW("PERM_LAWYERAMC_VIEW", "律师AMC查看权限",4),

  // new version perms



  PERM_ASSETPACK_VIEW("PERM_ASSETPACK_VIEW","资产包查看", 11),
  PERM_ASSETPACK_MOD("PERM_ASSETPACK_MOD","资产包管理", 12),
  PERM_DEBTASSET_VIEW("PERM_DEBTASSET_VIEW","债权管理", 13),
  PERM_DEBTASSET_MOD("PERM_DEBTASSET_MOD","编辑债权/资产", 14),
  PERM_DEBTCNTR_VIEW("PERM_DEBTCNTR_VIEW","承办人管理", 15),
  PERM_DEBTCNTR_MOD("PERM_DEBTCNTR_MOD","承办人编辑（昵称）", 16),
  PERM_OPLOG_VIEW("PERM_OPLOG_VIEW","操作日志", 17),
  PERM_INVCUST_VIEW("PERM_INVCUST_VIEW","投资人库", 18),
  PERM_WECHATPUB_VIEW("PERM_WECHATPUB_VIEW","微信发布管理", 19),
  PERM_WECHATPUB_MOD("PERM_WECHATPUB_MOD","微信发布", 20),
  PERM_WECHATCUST_VIEW("PERM_WECHATCUST_VIEW","微信用户查看", 21),
  PERM_WECHATCUST_MOD("PERM_WECHATCUST_MOD","微信用户管理", 22),
  PERM_DEBT_OFFSLF("PERM_DEBT_OFFSLF","下架债权", 23),
  PERM_DEBT_RECOM("PERM_DEBT_RECOM","推荐债权/资产", 24),
  PERM_AMCACCOUNT_VIEW("PERM_AMCACCOUNT_VIEW","账号查看", 25),
  PERM_AMCACCOUNT_MOD("PERM_AMCACCOUNT_MOD","账号增删改 (下一级)", 26),
  PERM_WECHATCUST_ANALYSIS("PERM_CUST_ANALYSIS", "用户行为分析", 27),
  PERM_SCOPE_CMPY("PERM_SCOPE_CMPY", "本公司", 28),
  PERM_SCOPE_ALLCMPY("PERM_SCOPE_ALLCMPY", "所有公司", 29),
  PERM_SCOPE_LOCATION("PERM_SCOPE_LOCATION", "本地区", 30),

  ;
  private String name;
  private String cname;
  private Integer id;

  AmcPermEnum(String name, String cname, int id){
    this.name = name;
    this.cname = cname;
    this.id = id;
  }

  private static final Function<String, AmcPermEnum> func =
      EnumUtils.lookupMap(AmcPermEnum.class, e -> e.getName());
  public static AmcPermEnum lookupByDisplayNameUtil(String name) {
    return func.apply(name);
  }

  private static final Function<Integer, AmcPermEnum> funcStatus =
      EnumUtils.lookupMap(AmcPermEnum.class, e -> e.getId());
  public static AmcPermEnum lookupByDisplayIdUtil(Integer id) {
    return funcStatus.apply(id);
  }


  public String getName() {
    return name;
  }

  public Integer getId() {
    return id;
  }

  public String getCname() {
    return cname;
  }
}
