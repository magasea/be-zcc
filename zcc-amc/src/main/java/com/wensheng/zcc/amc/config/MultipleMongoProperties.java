package com.wensheng.zcc.amc.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Marcos Barbero
 */
@Data
@ConfigurationProperties(prefix = "mongodb")
public class MultipleMongoProperties {

    private MongoProperties original = new MongoProperties();
    private MongoProperties wszcc = new MongoProperties();
}
