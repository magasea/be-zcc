package com.wensheng.zcc.amc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import static com.wensheng.zcc.amc.config.SecondaryMongoConfig.MONGO_TEMPLATE;


/**
 * @author Marcos Barbero
 */
@Configuration
@EnableMongoRepositories(basePackages = "com.wensheng.zcc.amc.module.dao.mongo.entity",
        mongoTemplateRef = MONGO_TEMPLATE)
public class SecondaryMongoConfig {

    protected static final String MONGO_TEMPLATE = "wszccMongoTemplate";
}
