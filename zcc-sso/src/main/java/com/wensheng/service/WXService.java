package com.wensheng.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface WXService extends UserDetailsService {

    UserDetails loginWX(String code) throws  Exception;

    UserDetails getByInitCode(String code);





}
