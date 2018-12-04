package com.wensheng.zcc.service;

import com.wensheng.zcc.model.User;
import com.wensheng.zcc.model.UserDto;

import java.util.List;

public interface UserService {

    User save(UserDto user);
    List<User> findAll();
    void delete(int id);

    User findOne(String username);

    User findById(int id);

    UserDto update(UserDto userDto);
}
