package com.wensheng.zcc.amc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import static com.wensheng.zcc.amc.config.PrimaryMongoConfig.MONGO_TEMPLATE;

/**
 * @author Marcos Barbero
 */
@Configuration
@EnableMongoRepositories(basePackages = "com.wensheng.zcc.amc.module.dao.mongo.origin",
        mongoTemplateRef = MONGO_TEMPLATE)
public class PrimaryMongoConfig {

    protected static final String MONGO_TEMPLATE = "originalMongoTemplate";
}
