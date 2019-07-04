package com.wensheng.zcc.comnfunc;

import io.grpc.Server;
import org.springframework.context.ApplicationContext;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.wensheng.zcc.comnfunc.dao.mysql")
public class Application implements CommandLineRunner {

    @Autowired
    ApplicationContext applicationContext;

    public static void main(String[] args) {
        
        SpringApplication.run(Application.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
        Server server = applicationContext.getBean(Server.class);
        System.out.println("Starting server...");
        server.start();
        System.out.println("Server started!");
        server.awaitTermination();
    }
}
