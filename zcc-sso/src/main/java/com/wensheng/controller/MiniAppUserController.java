package com.wensheng.controller;

import com.wensheng.config.JwtTokenUtil;
import com.wensheng.model.ApiResponse;
import com.wensheng.model.AuthToken;
import com.wensheng.model.User;
import com.wensheng.service.UserService;
import com.wensheng.service.WXService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController

public class MiniAppUserController {

    private Gson gson = new Gson();

    @Autowired
    private WXService wxService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private  boolean test = true;

    @PostMapping("/zcc/wx/login")
    public ApiResponse<Map> login( @RequestHeader Map header){

        UserDetails userDetails = null;
        try {
            userDetails = wxService.getByInitCode(header.get("initcode").toString());
        } catch (Exception e) {
            e.printStackTrace();
        }


//        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getUsername()));

        final String token = jwtTokenUtil.generateToken(userDetails);
        return new ApiResponse<>(200, "success",new AuthToken(token, userDetails.getUsername()));

    }
}
