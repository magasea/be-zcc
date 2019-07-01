package com.wensheng.zcc.comnfunc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.wensheng.zcc.comnfunc.dao.mysql")
public class Application {



    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

    }

}
