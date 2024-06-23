package com.snow.oauth2.socialoauth2.configuration.mongo;


import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class GridFsConfig {
    @Autowired
    private MongoDatabase mongoDatabase;

    @Bean
    public GridFSBucket gridFSBucket() {
        return GridFSBuckets.create(mongoDatabase, "fs");
    }
}
