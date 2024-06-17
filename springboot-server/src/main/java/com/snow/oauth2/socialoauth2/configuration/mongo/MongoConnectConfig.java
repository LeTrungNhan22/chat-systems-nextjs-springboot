package com.snow.oauth2.socialoauth2.configuration.mongo;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoSocketReadTimeoutException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class MongoConnectConfig {
    private static final Logger logger = LoggerFactory.getLogger(MongoConnectConfig.class);

    MongoClientSettings settings = MongoClientSettings.builder()
            .applyToSocketSettings(builder ->
                    builder.readTimeout(300, TimeUnit.SECONDS))
            .build();
    @Getter
    MongoClient mongoClient;

    public MongoConnectConfig() {
        try {
            mongoClient = MongoClients.create(settings);
            logger.info("Connect to MongoDB successfully");
        } catch (MongoSocketReadTimeoutException ex) {
            logger.error("Timeout when connecting to MongoDB", ex);

        } catch (Exception ex) {
            logger.error("Error when connecting to MongoDB", ex);
        }
    }

}