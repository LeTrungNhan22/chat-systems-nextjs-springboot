package com.snow.oauth2.socialoauth2.service.redis;

import com.snow.oauth2.socialoauth2.dto.request.chat.MessageRequestDto;
import com.snow.oauth2.socialoauth2.model.chat.Chat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final long MEDIA_TTL = 1; // TTL (Time to Live) của file media (1 ngày)
    private static final TimeUnit MEDIA_TTL_UNIT = TimeUnit.DAYS;
    private final Set<String> processedChunks = new HashSet<>(); // Set để theo dõi các chunk đã xử lý

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

//    public String appendMediaChunk(Chat chat, MessageRequestDto messageRequestDto, Message message) {
//        String redisKey = "chat:" + chat.getId() + ":media:" + message.getId();
//        String chunkId = chat.getId() + ":" + message.getId() + ":" + messageRequestDto.getChunkIndex();
//
//        if (!processedChunks.contains(chunkId) && messageRequestDto.getChunkIndex() == processedChunks.size()) {
//            redisTemplate.opsForHash().put(redisKey, String.valueOf(messageRequestDto.getChunkIndex()), messageRequestDto.getMediaBase64());
//            processedChunks.add(chunkId);
//
//            Long chunkCount = redisTemplate.opsForHash().size(redisKey);
//            if (chunkCount != null && chunkCount == messageRequestDto.getTotalChunks()) {
//                List<String> chunks = new ArrayList<>();
//                for (int i = 0; i < messageRequestDto.getTotalChunks(); i++) {
//                    String chunk = (String) redisTemplate.opsForHash().get(redisKey, String.valueOf(i));
//                    if (chunk != null) {
//                        chunks.add(chunk);
//                    } else {
//                        log.error("Missing chunk: {}", i);
//                        throw new MediaProcessingException("Error processing media chunks: Missing chunk");
//                    }
//                }
//
//                String fullMediaBase64 = String.join("", chunks);
//                redisTemplate.delete(redisKey);
//                processedChunks.clear();
//
//                try {
//                    byte[] mediaData = Base64.getDecoder().decode(fullMediaBase64);
//                    return storeMedia(mediaData);
//                } catch (IllegalArgumentException e) {
//                    log.error("Invalid Base64 string: {}", e.getMessage());
//                    throw new MediaProcessingException("Error processing media chunks: Invalid Base64 data");
//                }
//            }
//        }
//        return null;
//    }


    public void deleteMediaChunks(Chat chat, MessageRequestDto messageRequestDto) {
        String redisKey = "chat:" + chat.getId() + ":media:" + messageRequestDto.getChatId();
        redisTemplate.delete(redisKey);
        processedChunks.clear(); // Xóa danh sách chunk đã xử lý khi hoàn thành
    }
}
