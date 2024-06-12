package com.snow.oauth2.socialoauth2.configuration.mongo;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;


@Configuration
public class MongoConnectConfig {

    MongoClientSettings settings = MongoClientSettings.builder()
            .applyToSocketSettings(builder ->
                    builder.readTimeout(300, TimeUnit.SECONDS))  //  tăng timeout lên 300 giây
            .build();

    MongoClient mongoClient = MongoClients.create(settings);
}
