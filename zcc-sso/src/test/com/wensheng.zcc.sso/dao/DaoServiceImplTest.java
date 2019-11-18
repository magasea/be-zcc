package com.wensheng.zcc.sso.service.impl;

import com.wensheng.zcc.common.params.AmcPermEnum;
import com.wensheng.zcc.common.params.AmcRolesEnum;
import com.wensheng.zcc.sso.dao.mysql.mapper.AmcPermissionMapper;
import com.wensheng.zcc.sso.dao.mysql.mapper.AmcRoleMapper;
import com.wensheng.zcc.sso.dao.mysql.mapper.AmcRolePermissionMapper;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcPermission;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcPermissionExample;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcRole;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcRoleExample;
import com.wensheng.zcc.sso.module.dao.mysql.auto.entity.AmcRolePermission;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

/**
 * @author chenwei on 2/1/19
 * @project zcc-backend
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration
@ActiveProfiles(value = "dev")
public class DaoServiceImplTest {

  @Autowired
  AmcRolePermissionMapper amcRolePermissionMapper;

  @Autowired
  AmcPermissionMapper amcPermissionMapper;

  @Autowired
  AmcRoleMapper amcRoleMapper;

  @Test
  public void initPermPerms() {
    for(AmcPermEnum amcPermEnum: AmcPermEnum.values()){
      AmcPermission amcPermission = new AmcPermission();
      amcPermission.setPermId(amcPermEnum.getId());
      amcPermission.setName(amcPermEnum.getName());
      AmcPermissionExample amcPermissionExample = new AmcPermissionExample();
      amcPermissionExample.createCriteria().andNameEqualTo(amcPermEnum.getName());
      List<AmcPermission> amcPermissionInDBs = amcPermissionMapper.selectByExample(amcPermissionExample);
      if(CollectionUtils.isEmpty(amcPermissionInDBs)){
        amcPermissionMapper.insert(amcPermission);
      }else{
        amcPermissionMapper.updateByExampleSelective(amcPermission, amcPermissionExample);
      }
    }
  }

  @Test
  public void initRoleParams() {
    for(AmcRolesEnum amcRolesEnum : AmcRolesEnum.values()){
      AmcRole amcRole = new AmcRole();
      amcRole.setRoleId(amcRolesEnum.getId());
      amcRole.setName(amcRolesEnum.getName());
      AmcRoleExample amcRoleExample = new AmcRoleExample();
      amcRoleExample.createCriteria().andNameEqualTo(amcRolesEnum.getName());
      List<AmcRole> amcRoleInDBs = amcRoleMapper.selectByExample(amcRoleExample);
      if(CollectionUtils.isEmpty(amcRoleInDBs)){
        amcRoleMapper.insert(amcRole);
      }else{
        amcRoleMapper.updateByExampleSelective(amcRole, amcRoleExample);
      }
      amcRole = null;
    }
  }

  @Test
  public void initRolePermParams(){
    List<AmcRolePermission> amcRolePermissions = new ArrayList<>();
    //第一版的权限角色关系

    AmcRolePermission amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_SYSTEM_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_BASIC_INFO.getId()));
    amcRolePermissions.add(amcRolePermission);
    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_SYSTEM_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_CREATE_AMCADMIN.getId()));
    amcRolePermissions.add(amcRolePermission);

    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_SYSTEM_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_CREATE_AMCUSER.getId()));
    amcRolePermissions.add(amcRolePermission);
    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_SYSTEM_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_AMC_REVIEW.getId()));
    amcRolePermissions.add(amcRolePermission);

    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_CREATE_AMCUSER.getId()));
    amcRolePermissions.add(amcRolePermission);

    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_AMC_CRUD.getId()));
    amcRolePermissions.add(amcRolePermission);


    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_AMC_VIEW.getId()));
    amcRolePermissions.add(amcRolePermission);


    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_USER.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_AMC_VIEW.getId()));
    amcRolePermissions.add(amcRolePermission);

    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_USER.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_AMC_CRUD.getId()));
    amcRolePermissions.add(amcRolePermission);


    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_ZCC_CLIENT.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_AMC_VIEW.getId()));
    amcRolePermissions.add(amcRolePermission);

    //第二版的权限角色关系
    //ROLE_AMC_LOCAL_VISITOR
    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_LOCAL_VISITOR.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_DEBTASSET_VIEW.getId()));
    amcRolePermissions.add(amcRolePermission);

    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_LOCAL_VISITOR.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_DEBTCNTR_VIEW.getId()));
    amcRolePermissions.add(amcRolePermission);

    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_LOCAL_VISITOR.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_OPLOG_VIEW.getId()));
    amcRolePermissions.add(amcRolePermission);
    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_LOCAL_VISITOR.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_INVCUST_VIEW.getId()));
    amcRolePermissions.add(amcRolePermission);
    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_LOCAL_VISITOR.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_WECHATPUB_VIEW.getId()));
    amcRolePermissions.add(amcRolePermission);
    amcRolePermission = new AmcRolePermission();
//    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_LOCAL_VISITOR.getId()));
//    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_WECHATCUST_VIEW.getId()));
    amcRolePermissions.add(amcRolePermission);
    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_LOCAL_VISITOR.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_AMCACCOUNT_VIEW.getId()));
    amcRolePermissions.add(amcRolePermission);
    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_LOCAL_VISITOR.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_SCOPE_LOCATION.getId()));
    amcRolePermissions.add(amcRolePermission);


    //ROLE_AMC_VISITOR
    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_VISITOR.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_DEBTASSET_VIEW.getId()));
    amcRolePermissions.add(amcRolePermission);
    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_VISITOR.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_DEBTCNTR_VIEW.getId()));
    amcRolePermissions.add(amcRolePermission);
    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_VISITOR.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_OPLOG_VIEW.getId()));
    amcRolePermissions.add(amcRolePermission);
//    amcRolePermission = new AmcRolePermission();
//    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_VISITOR.getId()));
//    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_INVCUST_VIEW.getId()));
//    amcRolePermissions.add(amcRolePermission);
    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_VISITOR.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_WECHATPUB_VIEW.getId()));
    amcRolePermissions.add(amcRolePermission);
    amcRolePermission = new AmcRolePermission();
//    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_VISITOR.getId()));
//    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_WECHATCUST_VIEW.getId()));
    amcRolePermissions.add(amcRolePermission);
    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_VISITOR.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_AMCACCOUNT_VIEW.getId()));
    amcRolePermissions.add(amcRolePermission);
    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_VISITOR.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_SCOPE_CMPY.getId()));
    amcRolePermissions.add(amcRolePermission);

    //ROLE_AMC_STUFF

    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_STUFF.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_DEBTASSET_VIEW.getId()));
    amcRolePermissions.add(amcRolePermission);

    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_STUFF.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_DEBTASSET_MOD.getId()));
    amcRolePermissions.add(amcRolePermission);



    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_STUFF.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_OPLOG_VIEW.getId()));
    amcRolePermissions.add(amcRolePermission);


    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_STUFF.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_WECHATPUB_VIEW.getId()));
    amcRolePermissions.add(amcRolePermission);

    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_STUFF.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_WECHATPUB_MOD.getId()));
    amcRolePermissions.add(amcRolePermission);



    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_STUFF.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_DEBT_OFFSLF.getId()));
    amcRolePermissions.add(amcRolePermission);



    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_STUFF.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_SCOPE_LOCATION.getId()));
    amcRolePermissions.add(amcRolePermission);


    //ROLE_AMC_LOCAL_STUFF

    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_LOCAL_STUFF.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_DEBTASSET_VIEW.getId()));
    amcRolePermissions.add(amcRolePermission);

    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_LOCAL_STUFF.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_DEBTASSET_MOD.getId()));
    amcRolePermissions.add(amcRolePermission);



    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_LOCAL_STUFF.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_OPLOG_VIEW.getId()));
    amcRolePermissions.add(amcRolePermission);


    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_LOCAL_STUFF.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_WECHATPUB_VIEW.getId()));
    amcRolePermissions.add(amcRolePermission);

    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_LOCAL_STUFF.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_WECHATPUB_MOD.getId()));
    amcRolePermissions.add(amcRolePermission);



    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_LOCAL_STUFF.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_DEBT_OFFSLF.getId()));
    amcRolePermissions.add(amcRolePermission);



    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_LOCAL_STUFF.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_SCOPE_LOCATION.getId()));
    amcRolePermissions.add(amcRolePermission);

    //ROLE_AMC_LOCAL_ADMINx`


    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_LOCAL_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_DEBTASSET_VIEW.getId()));
    amcRolePermissions.add(amcRolePermission);

    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_LOCAL_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_DEBTASSET_MOD.getId()));
    amcRolePermissions.add(amcRolePermission);

    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_LOCAL_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_DEBTCNTR_VIEW.getId()));
    amcRolePermissions.add(amcRolePermission);

    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_LOCAL_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_DEBTCNTR_MOD.getId()));
    amcRolePermissions.add(amcRolePermission);

    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_LOCAL_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_OPLOG_VIEW.getId()));
    amcRolePermissions.add(amcRolePermission);

//    amcRolePermission = new AmcRolePermission();
//    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_LOCAL_ADMIN.getId()));
//    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_INVCUST_VIEW.getId()));
//    amcRolePermissions.add(amcRolePermission);

    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_LOCAL_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_WECHATPUB_VIEW.getId()));
    amcRolePermissions.add(amcRolePermission);

    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_LOCAL_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_WECHATPUB_MOD.getId()));
    amcRolePermissions.add(amcRolePermission);



    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_LOCAL_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_DEBT_OFFSLF.getId()));
    amcRolePermissions.add(amcRolePermission);

    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_LOCAL_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_DEBT_RECOM.getId()));
    amcRolePermissions.add(amcRolePermission);

    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_LOCAL_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_AMCACCOUNT_VIEW.getId()));
    amcRolePermissions.add(amcRolePermission);

    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_LOCAL_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_AMCACCOUNT_MOD.getId()));
    amcRolePermissions.add(amcRolePermission);


    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_LOCAL_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_SCOPE_LOCATION.getId()));
    amcRolePermissions.add(amcRolePermission);


    //ROLE_AMC_ADMIN


    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_DEBTASSET_VIEW.getId()));
    amcRolePermissions.add(amcRolePermission);

    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_DEBTASSET_MOD.getId()));
    amcRolePermissions.add(amcRolePermission);

    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_DEBTCNTR_VIEW.getId()));
    amcRolePermissions.add(amcRolePermission);

    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_DEBTCNTR_MOD.getId()));
    amcRolePermissions.add(amcRolePermission);

    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_OPLOG_VIEW.getId()));
    amcRolePermissions.add(amcRolePermission);

    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_INVCUST_VIEW.getId()));
    amcRolePermissions.add(amcRolePermission);

    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_WECHATPUB_VIEW.getId()));
    amcRolePermissions.add(amcRolePermission);

    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_WECHATPUB_MOD.getId()));
    amcRolePermissions.add(amcRolePermission);



    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_DEBT_OFFSLF.getId()));
    amcRolePermissions.add(amcRolePermission);

    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_DEBT_RECOM.getId()));
    amcRolePermissions.add(amcRolePermission);

    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_AMCACCOUNT_VIEW.getId()));
    amcRolePermissions.add(amcRolePermission);

    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_AMCACCOUNT_MOD.getId()));
    amcRolePermissions.add(amcRolePermission);


    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_AMC_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_SCOPE_CMPY.getId()));
    amcRolePermissions.add(amcRolePermission);


    //ROLE_CO_ADMIN
    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_CO_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_ASSETPACK_VIEW.getId()));
    amcRolePermissions.add(amcRolePermission);

    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_CO_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_ASSETPACK_MOD.getId()));
    amcRolePermissions.add(amcRolePermission);

    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_CO_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_DEBTASSET_VIEW.getId()));
    amcRolePermissions.add(amcRolePermission);

    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_CO_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_DEBTASSET_MOD.getId()));
    amcRolePermissions.add(amcRolePermission);

    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_CO_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_DEBTCNTR_VIEW.getId()));
    amcRolePermissions.add(amcRolePermission);

    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_CO_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_DEBTCNTR_MOD.getId()));
    amcRolePermissions.add(amcRolePermission);

    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_CO_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_OPLOG_VIEW.getId()));
    amcRolePermissions.add(amcRolePermission);

    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_CO_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_INVCUST_VIEW.getId()));
    amcRolePermissions.add(amcRolePermission);

    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_CO_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_WECHATPUB_VIEW.getId()));
    amcRolePermissions.add(amcRolePermission);

    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_CO_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_WECHATPUB_MOD.getId()));
    amcRolePermissions.add(amcRolePermission);



    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_CO_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_DEBT_OFFSLF.getId()));
    amcRolePermissions.add(amcRolePermission);

    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_CO_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_DEBT_RECOM.getId()));
    amcRolePermissions.add(amcRolePermission);

    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_CO_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_AMCACCOUNT_VIEW.getId()));
    amcRolePermissions.add(amcRolePermission);



    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_CO_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_SCOPE_ALLCMPY.getId()));
    amcRolePermissions.add(amcRolePermission);


    //ROLE_SYSTEM_ADMIN
    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_SYSTEM_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_ASSETPACK_VIEW.getId()));
    amcRolePermissions.add(amcRolePermission);

    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_SYSTEM_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_ASSETPACK_MOD.getId()));
    amcRolePermissions.add(amcRolePermission);

    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_SYSTEM_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_DEBTASSET_VIEW.getId()));
    amcRolePermissions.add(amcRolePermission);

    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_SYSTEM_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_DEBTASSET_MOD.getId()));
    amcRolePermissions.add(amcRolePermission);

    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_SYSTEM_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_DEBTCNTR_VIEW.getId()));
    amcRolePermissions.add(amcRolePermission);

    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_SYSTEM_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_DEBTCNTR_MOD.getId()));
    amcRolePermissions.add(amcRolePermission);

    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_SYSTEM_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_OPLOG_VIEW.getId()));
    amcRolePermissions.add(amcRolePermission);

    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_SYSTEM_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_INVCUST_VIEW.getId()));
    amcRolePermissions.add(amcRolePermission);

    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_SYSTEM_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_WECHATPUB_VIEW.getId()));
    amcRolePermissions.add(amcRolePermission);

    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_SYSTEM_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_WECHATPUB_MOD.getId()));
    amcRolePermissions.add(amcRolePermission);

    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_SYSTEM_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_WECHATCUST_VIEW.getId()));
    amcRolePermissions.add(amcRolePermission);

    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_SYSTEM_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_WECHATCUST_MOD.getId()));
    amcRolePermissions.add(amcRolePermission);

    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_SYSTEM_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_DEBT_OFFSLF.getId()));
    amcRolePermissions.add(amcRolePermission);

    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_SYSTEM_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_DEBT_RECOM.getId()));
    amcRolePermissions.add(amcRolePermission);

    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_SYSTEM_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_AMCACCOUNT_VIEW.getId()));
    amcRolePermissions.add(amcRolePermission);

    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_SYSTEM_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_AMCACCOUNT_MOD.getId()));
    amcRolePermissions.add(amcRolePermission);

    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_SYSTEM_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_WECHATCUST_ANALYSIS.getId()));
    amcRolePermissions.add(amcRolePermission);



    amcRolePermission = new AmcRolePermission();
    amcRolePermission.setRoleId(Long.valueOf(AmcRolesEnum.ROLE_SYSTEM_ADMIN.getId()));
    amcRolePermission.setPermissionId(Long.valueOf(AmcPermEnum.PERM_SCOPE_ALLCMPY.getId()));
    amcRolePermissions.add(amcRolePermission);




    putInDb(amcRolePermissions);


  }
  private void putInDb(List<AmcRolePermission> amcRolePermissions){
    for(AmcRolePermission amcRolePermission: amcRolePermissions){
      try{

        amcRolePermissionMapper.insertSelective(amcRolePermission);
      }catch (Exception ex){
        ex.printStackTrace();
      }

    }

  }

}