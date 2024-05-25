package com.snow.oauth2.socialoauth2.configuration;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;

@Configuration
public class MongoConfig implements InitializingBean {

    @Autowired
    private MappingMongoConverter mongoConverter;

    @Override
    public void afterPropertiesSet() throws Exception {
        mongoConverter.setTypeMapper(new DefaultMongoTypeMapper(null)); // No _class in document
    }
}
