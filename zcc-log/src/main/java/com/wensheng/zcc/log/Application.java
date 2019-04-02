package com.wensheng.zcc.log;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
@MapperScan({"com.wensheng.zcc.log.dao.mysql.mapper"})

public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
