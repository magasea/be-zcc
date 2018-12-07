package com.wensheng.zcc.sso.service;

import com.wensheng.zcc.sso.model.User;
import com.wensheng.zcc.sso.model.UserDto;

import java.util.List;

public interface UserService {

    User save(UserDto user);
    List<User> findAll();
    void delete(int id);

    User findOne(String username);

    User findById(int id);

    UserDto update(UserDto userDto);
}
