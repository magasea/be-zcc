package com.wensheng.zcc.sso.service;

import com.wensheng.zcc.common.mq.kafka.module.WechatUserLocation;
import com.wensheng.zcc.common.params.AmcRolesEnum;
import com.wensheng.zcc.common.params.sso.AmcDeptEnum;
import com.wensheng.zcc.common.params.sso.AmcSSOTitleEnum;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcUser;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcUserRoleRule;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcWechatUser;
import com.wensheng.zcc.sso.module.vo.AmcSpecialUserVo;
import com.wensheng.zcc.sso.module.vo.WechatCode2SessionVo;
import com.wensheng.zcc.sso.module.vo.WechatLoginResult;
import com.wensheng.zcc.sso.module.vo.WechatPhoneRegistry;
import com.wensheng.zcc.sso.module.vo.WechatUserInfo;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

/**
 * @author chenwei on 3/19/19
 * @project miniapp-backend
 */
public interface AmcSsoService {
  OAuth2AccessToken generateToken(Long userId);
  OAuth2AccessToken generateToken(String mobilePhone);
  UserDetails getUserDetailByUserId(Long userId);
  UserDetails getUserDetailByUserPhone(String phoneNum);
  boolean syncUserWithSSO();

  OAuth2AccessToken generateTokenFromToken(String acccessToken) throws Exception;
  AmcRolesEnum getRoleByUser(AmcDeptEnum amcDept, AmcSSOTitleEnum titleEnum);
  AmcRolesEnum getRoleByUserAndMobile(AmcDeptEnum amcDept, AmcSSOTitleEnum titleEnum, String mobileNum);

  String modifySpecUser(Long userId, List<Long> permIds, Long currentUser);
  List<AmcSpecialUserVo> getSpecUser();

  List<AmcUserRoleRule> getZccRoleRules();

  String createZccRoleRules(List<AmcUserRoleRule> amcUserRoleRules, Long userId);

  String modifyZccRoleRules(List<AmcUserRoleRule> amcUserRoleRules, Long userId);
}
