package com.snow.oauth2.socialoauth2.service.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final long MEDIA_TTL = 1; // TTL (Time to Live) của file media (1 ngày)
    private static final TimeUnit MEDIA_TTL_UNIT = TimeUnit.DAYS;

    public String storeMedia(byte[] mediaData) {
        try {
            // Tạo key duy nhất cho file media
            String mediaKey = "media:" + UUID.randomUUID().toString();

            // Lưu trữ file vào Redis
            redisTemplate.opsForValue().set(mediaKey, Arrays.toString(mediaData), MEDIA_TTL, MEDIA_TTL_UNIT);

            // Trả về URL hoặc đường dẫn của file trên Redis
            return mediaKey;
        } catch (Exception e) {
            throw new RuntimeException("Error storing media in Redis", e);
        }
    }
}
