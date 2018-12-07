package com.wensheng.zcc.sso.controller;

import com.wensheng.zcc.sso.config.JwtTokenUtil;
import com.wensheng.zcc.sso.model.ApiResponse;
import com.wensheng.zcc.sso.model.AuthToken;
import com.wensheng.zcc.sso.service.WXService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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
