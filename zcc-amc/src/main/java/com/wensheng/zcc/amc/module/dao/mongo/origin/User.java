package com.wensheng.zcc.amc.module.dao.mongo.origin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author chenwei on 12/6/18
 * @project zcc-backend
 */
public class User {
    private transient Object springSecurityService;
    private String username;
    private String password;
    private String mobileNumber;
    private boolean enabled = true;
    private boolean accountExpired = false;
    private boolean accountLocked = false;
    private boolean passwordExpired = false;
    private String email;
    private String nickname;
    private Date createdTime;
    private UserProfile userProfile;
    private String name;
    private UserCompany company;
    private static List<String> transients = new ArrayList<String>(Arrays.asList("springSecurityService"));
}
