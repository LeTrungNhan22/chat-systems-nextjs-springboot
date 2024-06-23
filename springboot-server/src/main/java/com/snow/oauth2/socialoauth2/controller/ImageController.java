package com.snow.oauth2.socialoauth2.controller;


import com.snow.oauth2.socialoauth2.dto.response.ImageResponseDto;
import com.snow.oauth2.socialoauth2.service.image.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("api/v1/images")
public class ImageController {


    @Autowired
    private ImageService imageService;

    @PostMapping("/upload")
    @Operation(summary = "Upload images")
    public ResponseEntity<List<ImageResponseDto>> uploadImages(@RequestPart("images") MultipartFile[] files) {
        if (files == null || files.length == 0) {
            return ResponseEntity.badRequest().body(List.of(new ImageResponseDto("No files to upload", null)));
        }
        try {
            List<byte[]> imageDataList = new ArrayList<>();
            List<String> fileNames = new ArrayList<>();
            for (MultipartFile file : files) {
                imageDataList.add(file.getBytes());
                fileNames.add(file.getOriginalFilename());
            }
            List<String> imageIds = imageService.storeImages(imageDataList, fileNames);
            List<ImageResponseDto> responses = new ArrayList<>();
            for (String imageId : imageIds) {
                String downloadUrl = "/images/" + imageId;
                responses.add(new ImageResponseDto(imageId, downloadUrl));
            }
            return ResponseEntity.ok(responses);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of(new ImageResponseDto("Error uploading files", null)));
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{imageId}")
    @Operation(summary = "Download image")
    public ResponseEntity<byte[]> downloadImage(@PathVariable String imageId) {
        try {
            byte[] imageData = imageService.retrieveImage(imageId);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) // Hoặc MediaType phù hợp với loại ảnh
                    .body(imageData);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{imageId}")
    @Operation(summary = "Delete image")
    public ResponseEntity<String> deleteImage(@PathVariable String imageId) {
        try {
            imageService.deleteImage(imageId);
            return ResponseEntity.ok("Image deleted successfully with imageId: " + imageId);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


}
