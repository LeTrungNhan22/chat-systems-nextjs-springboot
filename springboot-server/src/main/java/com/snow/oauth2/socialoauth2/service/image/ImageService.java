package com.snow.oauth2.socialoauth2.service.image;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface ImageService {

    List<String> storeImages(List<byte[]> imageDataList, List<String> fileNames) throws IOException, InterruptedException, ExecutionException;

    byte[] retrieveImage(String imageId);

    void deleteImage(String imageId);

}
