package com.wensheng.zcc.amc.module.dao.mongo.origin;

/**
 * @author chenwei on 12/6/18
 * @project zcc-backend
 */
public class UserCompany {
    String _id;
    String name;				// 公司名
    String type;			    // 公司类型： AMC，律所，
    String email;			//
    String phone;			//
    String url;				//
    String regAuthority;		// 工商注册机关
    String regOrgId;			// 组织机构代码
    String regId;			// 工商注册号
    Address address;
    User    manager;
}
