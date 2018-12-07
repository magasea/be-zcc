package com.wensheng.zcc.amc.module.dao.mongo.origin;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenwei on 12/6/18
 * @project zcc-backend
 */
public class UserProfile {
    private Byte[] avatar;
    private String type = "普通用户";
    private String verification = "未认证";
    private List<String> interests = new ArrayList<String>();
    private String contact;
    private String gender;
    private String companyName;
    private Address address;
}
