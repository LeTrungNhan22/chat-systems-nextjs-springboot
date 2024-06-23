package com.snow.oauth2.socialoauth2.configuration.mongo;


import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Objects;

@Configuration
@Slf4j
public class MongoConfig {
    @Autowired
    private Environment env;

    @Bean
    public MongoDatabase mongoDatabase() {
        String mongoUri = env.getProperty("spring.data.mongodb.uri");
        assert mongoUri != null;
        ConnectionString connectionString = new ConnectionString(mongoUri);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        MongoClient mongoClient = MongoClients.create(mongoClientSettings);
        return mongoClient.getDatabase(Objects.requireNonNull(connectionString.getDatabase())); // Lấy tên database từ connectionString
    }
}
