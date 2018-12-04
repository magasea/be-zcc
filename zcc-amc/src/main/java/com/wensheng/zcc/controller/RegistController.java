package com.wensheng.zcc.controller;

import com.wensheng.zcc.model.ApiResponse;
import com.wensheng.zcc.model.AuthToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

public class RegistController {



    @PostMapping("/zcc/wx/regist/sendMobileAuthCode")
    public ApiResponse<Map> sendMobileAuthCode(@RequestHeader Map header){


        return new ApiResponse<>(200, "success", null);

    }
}
