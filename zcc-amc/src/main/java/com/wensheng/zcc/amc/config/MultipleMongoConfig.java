package com.wensheng.zcc.amc.config;

import com.mongodb.MongoClient;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

/**
 * @author Marcos Barbero
 */
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(MultipleMongoProperties.class)
public class MultipleMongoConfig {

    private final MultipleMongoProperties mongoProperties;


    @Bean(name = PrimaryMongoConfig.MONGO_TEMPLATE)
    public MongoTemplate originalMongoTemplate() throws Exception {
        return new MongoTemplate(originalFactory(this.mongoProperties.getOriginal()));
    }

    @Primary
    @Bean(name = SecondaryMongoConfig.MONGO_TEMPLATE)
    public MongoTemplate wszccTemplate() throws Exception {
        return new MongoTemplate(wszccFactory(this.mongoProperties.getWszcc()));
    }

    @Bean
    public MongoDbFactory originalFactory(final MongoProperties mongo) throws Exception {
        return new SimpleMongoDbFactory(new MongoClient(mongo.getHost(), mongo.getPort()),
                mongo.getDatabase());
    }

    @Bean
    public MongoDbFactory wszccFactory(final MongoProperties mongo) throws Exception {
        return new SimpleMongoDbFactory(new MongoClient(mongo.getHost(), mongo.getPort()),
                mongo.getDatabase());
    }

}
