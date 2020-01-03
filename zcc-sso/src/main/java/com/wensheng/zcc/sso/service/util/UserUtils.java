package com.wensheng.zcc.sso.service.util;

import com.wensheng.zcc.common.params.AmcRolesEnum;
import com.wensheng.zcc.common.params.sso.AmcDeptEnum;
import com.wensheng.zcc.common.params.sso.AmcSSOTitleEnum;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcRole;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcRoleExample;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author chenwei on 1/30/19
 * @project zcc-backend
 */
@Slf4j
public class UserUtils {

  private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


  public static String getEncode(String password){
    return passwordEncoder.encode(password);
  }

  public static boolean match( String rawPw, String encodedPw){
    return passwordEncoder.matches(rawPw, encodedPw);
  }

  public static AmcRolesEnum getRoleByUser(AmcDeptEnum amcDept, AmcSSOTitleEnum titleEnum){
    switch (amcDept){
      case BUSINESS_DEPT:
        switch (titleEnum){
          case TITLE_MGR:
            return AmcRolesEnum.ROLE_AMC_LOCAL_VISITOR;
          case TITLE_STAFF:
            return AmcRolesEnum.ROLE_AMC_LOCAL_STUFF;
          case TITLE_LDR:
            return AmcRolesEnum.ROLE_AMC_LOCAL_ADMIN;
          default:
            log.error("No match for user of dept:{} with title:{}", amcDept, titleEnum);
            return AmcRolesEnum.ROLE_AMC_LOCAL_VISITOR;
        }
      case TECH_DEPT:
        switch (titleEnum){
          case TITLE_MGR:
          case TITLE_LDR:
            return AmcRolesEnum.ROLE_SYSTEM_ADMIN;
          case TITLE_STAFF:
            return AmcRolesEnum.ROLE_CO_ADMIN;
          default:
            log.error("No match for user of dept:{} with title:{}", amcDept, titleEnum);
            return AmcRolesEnum.ROLE_AMC_LOCAL_STUFF;
        }
      case RISKCTRL_DEPT:
      case ESTATE_DEPT:
      case EQUITY_DEPT:
//      case ALL_CMPY:
        switch (titleEnum){
          case TITLE_MGR:
          case TITLE_LDR:
            return AmcRolesEnum.ROLE_AMC_ADMIN;
          case TITLE_STAFF:
            return AmcRolesEnum.ROLE_AMC_VISITOR;
          default:
            log.error("No match for user of dept:{} with title:{}", amcDept, titleEnum);
            return AmcRolesEnum.ROLE_AMC_LOCAL_VISITOR;
        }
      default:
        log.error("No match for user of dept:{} with title:{}", amcDept, titleEnum);
        return AmcRolesEnum.ROLE_AMC_LOCAL_VISITOR;
    }
  }

}
