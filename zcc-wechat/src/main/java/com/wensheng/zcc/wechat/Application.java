package com.wensheng.zcc.wechat;

import io.grpc.Server;
import java.io.IOException;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan({"com.wensheng.zcc.wechat.dao.mysql.mapper"})
@EnableScheduling
@EnableCaching
public class Application implements CommandLineRunner {
    @Autowired
    ApplicationContext context;

    public static void main(String[] args) throws IOException, InterruptedException {
        SpringApplication.run(Application.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
        Server server = context.getBean(Server.class);
        System.out.println("Starting server...");
        server.start();
        System.out.println("Server started!");
        server.awaitTermination();
    }
}
