package com.snow.oauth2.socialoauth2.service.image;


import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private GridFSBucket gridFSBucket;

    @Autowired
    private Jedis jedis;

    @Override
    public List<String> storeImages(List<byte[]> imageDataList, List<String> fileNames) throws InterruptedException, ExecutionException {
        if (imageDataList.size() != fileNames.size()) {
            throw new IllegalArgumentException("Number of images and file names do not match");
        }

        List<String> imageIds = new ArrayList<>(); // Danh sách id của các ảnh đã lưu
        List<Integer> indicesToProcess = new ArrayList<>(); // Chỉ số của các ảnh cần xử lý
        int numThreads = Runtime.getRuntime().availableProcessors(); // Số luồng tối ưu
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        List<Future<String>> futures = new ArrayList<>();

        // Kiểm tra Redis trước khi xử lý
        for (int i = 0; i < imageDataList.size(); i++) {
            String imageKey = "image:" + fileNames.get(i);
            jedis.incr("views:" + imageKey); // Tăng số lượt xem của ảnh
            if (jedis.exists(imageKey)) {
                imageIds.add(jedis.get(imageKey)); // Lấy imageId từ Redis
            } else {
                indicesToProcess.add(i);
            }
        }

        // Xử lý các ảnh chưa có trong Redis
        for (int index : indicesToProcess) {
            Future<String> future = executor.submit(() -> {
                byte[] imageData = imageDataList.get(index);
                String fileName = fileNames.get(index);

                ObjectId imageId;
                try (ByteArrayInputStream inputStream = new ByteArrayInputStream(imageData)) {
                    GridFSUploadOptions options = new GridFSUploadOptions()
                            .metadata(new Document("filename", fileName));
                    imageId = gridFSBucket.uploadFromStream(fileName, inputStream, options);
                }

                String imageIdString = imageId.toHexString();

                // Lưu imageId vào Redis sau khi xử lý xong
                int ttl = 3600; // TTL mặc định là 1 giờ
                if (isPopularImage(fileName)) {
                    ttl = 86400; // Nếu là ảnh phổ biến, tăng TTL lên 1 ngày
                }
                jedis.setex("image:" + fileName, ttl, imageIdString);

                return imageIdString;
            });
            futures.add(future);
        }

        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        for (Future<String> future : futures) {
            imageIds.add(future.get());
        }

        return imageIds;
    }

    @Override
    public byte[] retrieveImage(String imageId) {
        GridFSFile gridFSFile = gridFSBucket.find(new Document("_id", new ObjectId(imageId))).first();

        if (gridFSFile == null) {
            throw new RuntimeException("Image not found with imageId: " + imageId);
        }

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            gridFSBucket.downloadToStream(new ObjectId(imageId), outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void deleteImage(String imageId) {
        gridFSBucket.delete(new ObjectId(imageId));
    }

    public boolean isPopularImage(String fileName) {
        String imageKey = "image:" + fileName;
        long views = Long.parseLong(jedis.get("views:" + imageKey));
        return views >= 1000;
    }
}
